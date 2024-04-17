CREATE OR REPLACE FUNCTION get_heat_flux(indoors_sad boolean, longwork_sad boolean, nominalDiameter integer, temp integer) returns float as
    $$
    DECLARE
mini record;
    maxi record;
    mint int;
    maxt int;
    result float;
BEGIN

    temp = temp::smallint;
select max(temperature) into strict maxt from heat_flux;
select min(temperature) into strict mint from heat_flux;
if temp < mint or temp > maxt then
        RAISE EXCEPTION 'not allowed temp --> %', temp
        USING HINT = 'Please check your temperature';
end if;

    if exists(select temperature from heat_flux
        where temperature = temp) then
select heat_flux_value into strict result from heat_flux hf where
    temperature = temp and
    hf.nominal_diameter = nominalDiameter
                                                              and hf.indoors = indoors_sad
                                                              and hf.long_work = longwork_sad;
return result;
else
select temperature, heat_flux_value into strict maxi from heat_flux hf where
    hf.nominal_diameter = nominalDiameter
                                                                         and hf.indoors = indoors_sad
                                                                         and hf.long_work = longwork_sad
                                                                         and hf.temperature >= temp
order by hf.temperature
    limit 1;
select temperature, heat_flux_value into strict mini from heat_flux hf where
    hf.nominal_diameter = nominalDiameter
                                                                         and hf.indoors = indoors_sad
                                                                         and hf.long_work = longwork_sad
                                                                         and hf.temperature <= temp
order by hf.temperature desc
    limit 1;
result = ((maxi.heat_flux_value::float - mini.heat_flux_value::float ) / (maxi.temperature::float  - mini.temperature::float ))
                 * (temp - mini.temperature::float ) + mini.heat_flux_value::float;
return result;
end if;
END;
$$ LANGUAGE 'plpgsql' IMMUTABLE;

CREATE OR REPLACE FUNCTION get_conductivity(isolation_uuid uuid, temp integer) returns double precision as
    $$
    DECLARE
mini record;
    maxi record;
    mint int;
    maxt int;
    result double precision;
BEGIN

    temp = temp::smallint;
select max(temperature) into strict maxt from thermal_property;
select min(temperature) into strict mint from thermal_property;
if temp < mint or temp > maxt then
        RAISE EXCEPTION 'not allowed temp --> %', temp
        USING HINT = 'Please check your temperature';
end if;
    if exists(select temperature from thermal_property
        where temperature = temp
        and isolation_id = isolation_uuid
        ) then
select conductivity into strict result from thermal_property tp where
    tp.temperature = temp
                                                                  and tp.isolation_id = isolation_uuid;
return result;
else
select temperature, conductivity into strict maxi from thermal_property tp where
    tp.isolation_id = isolation_uuid
                                                                             and tp.temperature >= temp
order by tp.temperature
    limit 1;
select temperature, conductivity into strict mini from thermal_property tp where
    tp.isolation_id = isolation_uuid
                                                                             and tp.temperature <= temp
order by tp.temperature desc
    limit 1;
result = ((maxi.conductivity::float - mini.conductivity::float ) / (maxi.temperature::float  - mini.temperature::float ))
                 * (temp - mini.temperature::float ) + mini.conductivity::float;
return result;
end if;
END;
$$ LANGUAGE 'plpgsql' IMMUTABLE;

CREATE OR REPLACE FUNCTION get_thermal_resistance(temp integer, diameter integer, surface varchar) returns double precision as
$$
DECLARE
miniy1 record;
    maxiy1 record;
    miniy2 record;
    maxiy2 record;
    y1     record;
    y2     record;
    mint   double precision;
    maxt   double precision;
    mind   double precision;
    maxd   double precision;
    result double precision;
BEGIN

    temp = temp::smallint;
select max(temperature) into strict maxt from thermal_resistance
where surface_type = surface;
select max(nominal_diameter) into strict maxd from thermal_resistance
where surface_type = surface;
select min(temperature) into strict mint from thermal_resistance
where surface_type = surface;
select min(nominal_diameter) into strict mind from thermal_resistance
where surface_type = surface;
if temp < mint or temp > maxt then
        RAISE EXCEPTION 'not allowed temp --> %', temp
            USING HINT = 'Please check your temperature';
end if;
    if diameter < mind or diameter > maxd then
        RAISE EXCEPTION 'not allowed diameter --> %', temp
            USING HINT = 'Please check your diameter';
end if;
    if exists(select temperature
              from thermal_resistance
              where temperature = temp
                and nominal_diameter = diameter
                and surface_type = surface
        ) then
select resistance_value
into strict result
from thermal_resistance
where temperature = temp
  and nominal_diameter = diameter
  and surface_type = surface;
return result;
else
        if exists(select temperature
                  from thermal_resistance
                  where temperature > temp
                    and nominal_diameter = diameter
                    and surface_type = surface_type) then
select temperature, resistance_value
into y1
from thermal_resistance
where temperature > temp
  and nominal_diameter = diameter
  and surface_type = surface
order by temperature
    limit 1;
select temperature, resistance_value
into y2
from thermal_resistance
where temperature < temp
  and nominal_diameter = diameter
  and surface_type = surface
order by temperature desc
    limit 1;
result = ((y1.resistance_value::float - y2.resistance_value::float) /
                      (y1.temperature::float - y2.temperature::float))
                         * (temp - y2.temperature::float) + y2.resistance_value::float;
return result;
end if;
        if exists(select temperature
                  from thermal_resistance
                  where temperature = temp
                    and nominal_diameter > diameter
                    and surface_type = surface_type) then
select nominal_diameter, resistance_value
into y1
from thermal_resistance
where temperature = temp
  and nominal_diameter > diameter
  and surface_type = surface
order by nominal_diameter
    limit 1;
select nominal_diameter, resistance_value
into y2
from thermal_resistance
where temperature = temp
  and nominal_diameter < diameter
  and surface_type = surface
order by nominal_diameter desc
    limit 1;
result = ((y1.resistance_value::float - y2.resistance_value::float) /
                      (y1.nominal_diameter::float - y2.nominal_diameter::float))
                         * (diameter - y2.nominal_diameter::float) + y2.resistance_value::float;
return result;
else
select temperature, nominal_diameter, resistance_value
into maxiy1
from thermal_resistance
where temperature > temp
  and nominal_diameter > diameter
  and surface_type = surface
order by temperature, nominal_diameter
    limit 1;
select temperature, nominal_diameter, resistance_value
into maxiy2
from thermal_resistance
where temperature > temp
  and nominal_diameter < diameter
  and surface_type = surface
order by temperature, nominal_diameter desc
    limit 1;
maxt = ((maxiy1.resistance_value::float - maxiy2.resistance_value::float) /
                    (maxiy1.nominal_diameter::float - maxiy2.nominal_diameter::float))
                       * (diameter - maxiy2.nominal_diameter::float) + maxiy2.resistance_value::float;

select temperature, nominal_diameter, resistance_value
into miniy1
from thermal_resistance
where temperature < temp
  and nominal_diameter > diameter
  and surface_type = surface
order by temperature desc, nominal_diameter
    limit 1;
select temperature, nominal_diameter, resistance_value
into miniy2
from thermal_resistance
where temperature < temp
  and nominal_diameter < diameter
  and surface_type = surface
order by temperature desc, nominal_diameter desc
    limit 1;
mint = ((miniy1.resistance_value::float - miniy2.resistance_value::float) /
                    (miniy1.nominal_diameter::float - miniy2.nominal_diameter::float))
                       * (diameter - miniy2.nominal_diameter::float) + miniy2.resistance_value::float;
            result = ((maxt - mint) / (maxiy2.temperature::float - miniy2.temperature::float))
                         * (temp - miniy2.temperature::float) + mint;
return result;
end if;
end if;
END;
$$ LANGUAGE 'plpgsql' IMMUTABLE;
create table if not exists thermal_resistance
(
    temperature  smallint,
    nominal_diameter int,
    resistance_value float,
    surface_type varchar(32)
)
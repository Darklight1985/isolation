create table isolation
(
    id   uuid not null
        primary key,
    mark varchar(55)
);

create table thermal_property
(
    temperature  smallint,
    density int,
    conductivity double precision,
    isolation_id uuid
        constraint fk_isolation_thermal_property
            references isolation
)
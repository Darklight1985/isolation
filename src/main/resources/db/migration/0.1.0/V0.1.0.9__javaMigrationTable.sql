create table java_migration
(
    name           varchar not null
        constraint java_migration_pk
            primary key,
    execution_date timestamp
);

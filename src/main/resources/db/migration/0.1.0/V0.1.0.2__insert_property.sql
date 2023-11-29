insert into isolation (id, mark)
values ('402a2341-ae81-46a7-9223-5da523a59fd8'::uuid, 'МПБ-30'),
       ('103a2341-ae81-46a7-9223-5da523a59fd8'::uuid, 'URSA N-III');

insert into thermal_property (temperature, density, conductivity, isolation_id)
values (10, 30, 0.035, '402a2341-ae81-46a7-9223-5da523a59fd8'::uuid),
    (25, 30, 0.037, '402a2341-ae81-46a7-9223-5da523a59fd8'::uuid),
    (125, 30, 0.059, '402a2341-ae81-46a7-9223-5da523a59fd8'::uuid),
    (300, 30, 0.109, '402a2341-ae81-46a7-9223-5da523a59fd8'::uuid),
    (10, 22, 0.044, '103a2341-ae81-46a7-9223-5da523a59fd8'::uuid),
    (25, 22, 0.040, '103a2341-ae81-46a7-9223-5da523a59fd8'::uuid);
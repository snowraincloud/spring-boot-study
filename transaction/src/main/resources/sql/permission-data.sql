INSERT INTO permission(name, type)
VALUES ('one', 0),
('two', 0),
('two1', 1),
('three', 1)
;

UPDATE permission
set
pid = 1,
name = 'name',
value = 'value',
type = 1,
uri = 'uri',
status = 0,
create_time = '2033-02-09 12:00:00'
where id = 2;


INSERT into role(name)
VALUES ('test1'),
('test12'),
('1test')
;


UPDATE role
SET create_time = '2033-02-01 12:00:00'
WHERE id = 1;

UPDATE role
SET create_time = '2033-02-05 12:00:00'
WHERE id = 2;

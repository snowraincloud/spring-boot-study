INSERT into user(username, password)
VALUES('admin', 'e10adc3949ba59abbe56e057f20f883e'),
('test1', 'e10adc3949ba59abbe56e057f20f883e'),
('test12', 'e10adc3949ba59abbe56e057f20f883e'),
('1test', 'e10adc3949ba59abbe56e057f20f883e')
;

UPDATE user
SET create_time = '2033-02-01 09:52:24',
login_time = '2033-02-05 09:52:24'
WHERE id = 1;

UPDATE user
SET create_time = '2033-02-03 09:52:24',
login_time = '2033-02-04 09:52:24'
WHERE id = 2;



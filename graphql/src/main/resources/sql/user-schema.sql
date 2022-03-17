create table `user` (
    `id` int unsigned not null AUTO_INCREMENT,
    `name` varchar(50) not null comment 'name',
    `age` tinyint unsigned not null comment 'age',
    primary key(`id`)
);
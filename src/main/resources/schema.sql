create database if not exists db;

use db;

create table if not exists todo (
  id int auto_increment not null primary key comment 'ユーザーID',
  name varchar(36) not null comment 'ユーザー名',
  version int comment '排他制御用バージョン',
  create_timestamp timestamp default current_timestamp comment '作成日時',
  update_timestamp timestamp default current_timestamp on update current_timestamp comment '更新日時'
);

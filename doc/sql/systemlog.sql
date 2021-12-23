 create table t_system_log(
 log_id  bigint primary key,
 method varchar(12),
 timeout int(12),
 request_uri varchar(512) ,
 params varchar(2048)  ,
 title varchar(64) ,
 user_id bigint,
 operate_date datetime ,
 remote_addr varchar(32),
 exception text,
 type varchar(8)
)
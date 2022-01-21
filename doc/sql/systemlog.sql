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


 alter table t_system_log add req_params varchar(256);

 alter table t_system_log add req_header varchar(256);


 alter table t_system_log add rsp_msg text ;

 alter table t_system_log add rsp_time date ;


 alter table t_system_log add err_msg text ;

 alter table t_system_log add req_id varchar(32) ;




  ALTER TABLE `user`.`t_system_log`
MODIFY COLUMN `req_header` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL AFTER `req_params`;

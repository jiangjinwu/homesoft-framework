java web 开发基础框架

###使用技术
* mysql
* mybatis-plus
* redis
* lombok

使用说明
#### id 生成


1 maven 引入：
  ```
  <artifactId>homesoft-framework-id-generator</artifactId>
  ```
2 自动注入
  ```
  import cn.hutool.core.lang.Snowflake;
  
  @Autowired
  Snowflake snowflake;
  ```
#### 系统日志
 * maven 引入：
  ```
  <artifactId>homesoft-framework-log</artifactId>
  ```
* 执行doc/sql/t_system_log.sql

引入homesoft-framework-log后会对有io.swagger.annotations.ApiOperation注解
的方法记录日志到t_system_log表
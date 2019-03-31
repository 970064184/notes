# 命令行操作mysql

- mysql -u root -p root：进入mysql

- show databases：显示所有数据库

- show tables：显示所有表

- use databasename：切换到对应的数据库

- select database()：查看当前使用的数据库

- ```mysql
  create table tb3(

  id int unsigned key,

  name varchar(32) not null unique key,

  sex enum('1','2','3') default '3'

  );
  ---约束条件
  primary key 主键约束
  unique key唯一约束
  default 默认约束
  not null 非空约束
  ```

- show columns from test;：查看表结构

- ​

# 遇到的坑 

- mysql连接报错：Mysql:Host is not allowed to connect to this MySQL server

  - ```mysql
    mysql -u root -p
    mysql>GRANT   ALL   PRIVILEGES   ON   *.*   TO   'root'@'%'   WITH   GRANT   OPTION;
    mysql> flush privileges;
    (这么设置后，登录不需要密码)
    ```

  - https://www.cnblogs.com/Bighua/p/7629082.html 
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

- 复制表结构和数据

  - ```mysql
    create table 表名 【as】select * from 要复制的表名
    ```

- 复制表结构

  - ```mysql
    create table 表名 like 要复制的表名
    ```

  - 

# 遇到的坑 

- mysql连接报错：Mysql:Host is not allowed to connect to this MySQL server

  - ```mysql
    mysql -u root -p
    mysql>GRANT   ALL   PRIVILEGES   ON   *.*   TO   'root'@'%'   WITH   GRANT   OPTION;
    mysql> flush privileges;
    (这么设置后，登录不需要密码)
    ```

  - https://www.cnblogs.com/Bighua/p/7629082.html 

# mysql大表优化方案 

（晋阶学习计划文档有详细说明）

## 字段 

（当一个列可以选择多种数据类型时，应该优先考虑数字类型，其次是日期或二进制类型，最后是字符类型。对于相同级别的数据类型，应该优先选择占用空间小的数据类型）

- 尽量使用TINYINT（1）、SMALLINT（2）、MEDIUM_INT（3）作为整数类型而非INT（4），如果非负则加上UNSIGNED
- varchar的长度只分配真正需要的空间
- 使用枚举或整数代替字符串类型
- j尽量使用TIMESTAMP（4个字节）而非DATETIME（8个字节的存储空间）
- 单表不要有太多字段，建议在20以内
- 避免使用NULL字段，很难查询优化且占用额外索引空间
- 用整型来存IP

## 索引 

- 索引并不是越多越好，要根据查询有针对性的创建，考虑在where和order by命令上涉及的列建立索引，可根据explain来查看是否用了索引还是全表扫描
- 应尽量避免在where字句中对字段进行null值判断，否则将导致引擎放弃使用索引而进行全表扫描
- 值分布很稀少的字段不适合建索引，例如“性别”这种只有两三个值的字段
- 字符字段只建前缀索引
- 字符字段最好不要做主键
- 不用外键，由程序员保证约束
- 尽量不用unique，由程序保证约束
- 使用多列索引时注意顺序和查询条件保持一致，同时删除不必要的单列索引

## 查询SQL 

- 可通过开启慢查询日志来找出比较慢的SQL

  - slow_query_log：慢查询开启状态
  - slow_query_log_file：慢查询日志存放的位置
  - long_query_time：查询超过多少秒才记录

  - show variables like 'slow_query%'
  - https://www.cnblogs.com/luyucheng/p/6265594.html

- 不做列运算：select id where age+1 = 10，任何对列的操作都将导致表扫描，它包括数据库教程函数、计算表达式等等，查询时要尽可能将操作移至等号右边

- SQL语句尽可能简单：一条SQL只能在一个CPU运算；大语句拆小语句，减少锁时间；一条大SQL可以堵死整个库

- 不用select * 

- or改写成in：or的效率是n级别，in的效率是log(n)级别，in的个数建议控制在200以内

- 不用函数和触发器，在应用程序实现

- 避免%XXX式查询

- 少用join

- 使用同类型进行比较，比如用‘123’和‘123’比，123和123 比

- 尽量避免在where子句中使用!=或<>操作符，否则将引擎放弃使用索引而进行全表扫描

- 对于连续数值，使用between不用IN：select id from t where num between 1 and 5

- 列表数据不要拿全表，要使用limit来分页，每页数量也不要太大

## 引擎 

|                  | MyISAM | INnodb                         |
| ---------------- | ------ | ------------------------------ |
| 行锁             | 不支持 | 支持行锁，采用MVCC来支持高并发 |
| 事务             | 不支持 | 支持事务                       |
| 外键             | 不支持 | 支持外键                       |
| 奔溃后的安全恢复 | 不支持 | 支持                           |

（myISAM适合select密集型的表，而Innodb适合insert和update密集型的表）


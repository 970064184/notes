# oracle优化 

https://blog.csdn.net/tonghui_tonghui/article/details/76696260

- 选择最有效率的表名顺序

  - oracle的解析器按照从右到左的顺序处理from子句中的表名，from子句中写在最后的表（基础表 driving table）将被最先处理；

    所以，在from子句中包含多个表的情况下，你必须选择记录条数最少的表作为基础表

- where子句中的连接顺序

  - oracle采用自下而上的顺序解析where子句，根据这个原理，表之间的连接必须写在其他where条件之前，那么可以过滤掉最大数量记录的条件必须写在where子句的末尾

- select子句中避免使用“*”

  - oracle在解析的过程中，会将“*”依次转换成所有的列名，这个工作是通过查询数据字典完成的，这意味着将消耗更多的时间

- 减少访问数据库的次数

  - 解析SQL语句，估算索引的利用率，绑定变量，读数据库块等

- 在SQL *plus，SQL*froms和pro*c中重新设置arraysize参数

  - 可增加每次数据库访问的检索数据量，建议值为200

- 使用decode函数来减少处理时间

  - 使用decode函数可避免重复扫描相同记录或重复连接相同的表

- 整合简单、无关联的数据库访问

  - 如果你有几个简单的数据库查询语句，你可整合到一个查询中

- 删除重复记录

  - 最高效的删除重复记录方法：delete from emp e where w.rowid >(select min(x.rowid) from emp x where x.emp_no = e.emp_no)

- 用truncate 替代delete

- 尽量多使用commit

- 用where子句替换having子句

- j减少对表的查询

- 通过内部函数提高SQL效率

- 使用表的别名（alias）

- 用exist代替in、用not exist代替not in

- 识别“低效执行”的SQL语句

- 用索引提高效率

- 用exist替换distinct

- SQL语句用大写的

- 在java代码中尽量少用连接符“+”连接字符串

- 避免在索引列上使用not

- 避免在索引列上使用计算

- 用>=替代>

- 用union替换or（使用于索引列）

- 用in 替换or

- 避免在索引列上使用is null和is not null

- 总是使用索引的第一个列

- 用union-all替换union

- 用where替代order by

- 避免改变索引列的类型

- !=，||，+停用了索引

- 如果检索数据量超过30%的表中记录数，使用索引将没有显著的效率提高

- 避免使用耗费资源的操作

  - distinct、UNicon、minus、intersect、order by会启动SQL引擎，执行耗费资源

- 优化group by

- 

# 锁表

```java
select session_id from v$locked_object;
SELECT sid, serial#, username, osuser FROM v$session where sid =3768;
SELECT SID FROM V$MYSTAT WHERE ROWNUM =1;

SELECT object_name, machine, s.sid, s.serial# 
FROM gv$locked_object l, dba_objects o, gv$session s 
WHERE l.object_id　= o.object_id 
AND l.session_id = s.sid; 

--alter system kill session 'sid, serial#'; 
ALTER system kill session '3768, 10751'; 


```


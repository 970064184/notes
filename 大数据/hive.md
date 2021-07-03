# 权限

```sql
--root用户下赋予admin角色
set role admin;
--查看所有角色（admin/public）
show roles; 

--查看所有权限
show grant;
--给用户biwu2赋予该表所有权限
grant all on table biwu2_tmp.c_creative to user biwu2;
grant all on table biwu2_tmp.c_creative_group_info to user biwu2;
grant select on table biwu2_tmp.c_creative_group_info to user biwu2;
/**
名称                    描述
ALL                 赋予所有的权限
ALTER               有修改表结构的权限
CREATE              有创建表的权限
DROP                有删除表或表中的分区的权限
LOCK                开启并发后，锁定和解锁定表的权限
SELECT              查询表或者分区中数据的权限
SHOW_DATABASE       查看所有数据库的权限
UPDATE              向表或者分区中插入或加载数据的权限
**/

--查看该用户该表的权限
show grant user biwu2 on table biwu2_tmp.c_creative_group_info ;
--查看该用户所有权限
show grant user biwu2 on all;
--查看该用户
show grant user guozhan3 on database guozhan3;


--创建数据库
create database daqin;
--创建角色
create role daqin_admin;
--修改数据库所属角色
--alter database daqin set owner  role public;

--撤回权限：撤回该用户biwu2对某张表的权限，或撤回某个库的权限
--revoke  all on table biwu2_tmp.c_creative_group_info from user biwu2;
--revoke all on database biwu2_tmp from user biwu2;



--在该角色在某个数据库的权限
show grant role public on database biwu2;
--在该角色在某个表的权限
show grant role public on table biwu2.c_creative_group_info;

--角色
--把角色public 授权给用户biwu2
grant role public to user biwu2;
--查看用户biwu2有哪些权限(public)
show role grant user biwu2;
show role grant user admin;

--取消角色关联
--revoke daqin_admin from user daqin2;


grant all On DATABASE xinji_tmp to user daqin 
grant all On DATABASE xinji_test to user daqin 	
show grant user daqin on all 	
show grant user daqin on database xinji 
show grant user daqin on database diguo 
show grant user daqin on database daqin 
show grant user daqin on database xunqinji 
show grant user daqin on database daqin 
show grant user daqin on database xinji 
grant all On DATABASE xinji to user daqin 
show grant role public on table biwu2.c_creative_group_info; 
show grant role public on database biwu2; 
show grant role public on database daqin; 
show roles 
```

# 分区

```sql
---查看分区
show partitions diguo.a_role ;
show partitions guozhan3.a_role partition(ds='2020-11-01');

---增加分区
---ALTER TABLE ${PROJECT}.${TABLE} ADD IF NOT EXISTS PARTITION (gameid = ${GAMEID}, ds = '${TOMORROW}');

ALTER TABLE ${PROJECT}.${TABLE} ADD IF NOT EXISTS PARTITION (gameid = ${GAMEID}, ds = '${TOMORROW}');
```

```java
set role admin
desc daqin.f_dau;

select * from f_dau;

show partitions daqin.f_dau;

show partitions daqin.f_dar_pay;

show partitions daqin.a_user;

show create table f_dau;

show create table daqin.a_user;

show create table daqin.f_dar;

alter table daqin.f_dau partition column (gameid int, ds string);



ALTER TABLE ${PROJECT}.${TABLE} ADD IF NOT EXISTS PARTITION (gameid = ${GAMEID}, ds = '${TOMORROW}');



CREATE EXTERNAL TABLE f_dar
(
    dar int
) PARTITIONED BY (gameid int, ds string)
    STORED AS ORC
    LOCATION 'oss://${OSSPATH}/f_dar/'
    TBLPROPERTIES ("orc.compress" = "SNAPPY");


```


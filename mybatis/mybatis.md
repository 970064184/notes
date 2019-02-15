# 使用注解配置SQL映射器 

## 映射语句 

- @Insert
- @Update
- @Delete
- @SelectStatements

## 动态SQL

- @SelectProvider
- @InsertProvider
- @UpdateProvider
- @DeleteProvider

## 结果映射 

- @Result

- @ResultMap

- ```java
  @Select({"select id, name, class_id from my_student"})
  @Results(id="studentMap", value={
      @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
      @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
      @Result(column="class_id ", property="classId", jdbcType=JdbcType.INTEGER)
  })
  List<Student> selectAll();
   
  @Select({"select id, name, class_id from my_student where id = #{id}"})
  @ResultMap(value="studentMap")
  Student selectById(integer id);
  ```

- ​


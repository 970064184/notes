# 坑 

- save时数据库默认值不生效
  
- ```java
    //方法一
    @Column(name="p_id",insertable = false,updatable = false)
    private Long pId;
    ```
  
  - ```java
    //方法二
    @DynamicUpdate
    @DynamicInsert
    
    @Column(name = "sort",columnDefinition = "Integer default 0")
    private Integer sort;
    ```
  
  - 
  
- 分页查询

  - ```java
    /** * 分页查询 * @param lastname * @param pageable * @return */
    @Query(value = "SELECT * FROM tb_shop_brand b WHERE isdeleted = 0 and audit_status=?1  ORDER BY update_time desc nulls last,audit_time desc NULLS LAST",        countQuery = "SELECT count(*) FROM  tb_shop_brand b WHERE isdeleted = 0 and audit_status=?1", nativeQuery = true)
    Page<TbShopBrand> findByPage(int auditStatus,Pageable pageable);
    ```

    
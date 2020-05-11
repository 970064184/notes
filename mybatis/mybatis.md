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


## 分页查询 

```java
   /**
     * 分页查询
     * @param lastname
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM tb_shop_brand b WHERE isdeleted = 0 and audit_status=?1  ORDER BY update_time desc nulls last,audit_time desc NULLS LAST",
            countQuery = "SELECT count(*) FROM  tb_shop_brand b WHERE isdeleted = 0 and audit_status=?1", nativeQuery = true)
    Page<TbShopBrand> findByPage(int auditStatus,Pageable pageable);
```



# mybatis-plus 

- > 特性：
  >
  > - 支持lambda
  > - 支持注解自动生成：支持多达4种主键策略（内含分布式唯一ID生成器-Sequence）
  > - 内置代码生成器：采用代码或maven插件可快速生成Mapper、Model、service、controller层代码，支持模板引擎，更有超多自定义配置
  > - 内置分页插件：写分页等同于普通List查询
  > - 分页插件支持多种数据库：支持mysql、oracle等
  > - 内置性能分析插件：可输出SQL语句及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
  > - 内置全局拦截插件：提供全部delete、update操作智能分析阻断，也可自定义拦截规则，预防误操作

# 坑

## mybatis-plus使用jdk8的LocalDateTime 查询时报错

- **mybatis-plus版本降至3.1.0或以下即可**



## mybatis一级缓存问题 

```java
   @Autowired
    private SqlSession sqlSession;

@Transactional
    public HttpResponse receiveRight(HttpRequest<ReceiveRightRequest> request) throws Exception {
        //单权益单天激活数
        TbscRightUser tbscRightUser = tbscRightUserMapper.selectByPrimaryKey(request.getData().getEquityUserId());
        if (null != tbscRightUser) {
            //判断是否在领取时间内
            TbscRightActivityOrder tbscRightActivityOrder = tbscRightActivityOrderMapper.selectByPrimaryKey(tbscRightUser.getOrderId());
            Date endTime = tbscRightActivityOrder.getEndTime();
            if (endTime.before(new Date())) {
                //不在领取范围内，提示领取失败
                throw exceptionFactory.create(EcologicalExceptionMsg.ERROR_RIGHT_OUTTIME_EXCEPTION, EcologicalExceptionMsg.msg.get(EcologicalExceptionMsg.ERROR_RIGHT_OUTTIME_EXCEPTION));
            }
            RightAlarmParams alarmRightDayActivateParams = RightAlarmParams.builder().rightAlarmEnum(RightAlarmEnum.RIGHTDAYACTIVATESUM).rightId(tbscRightUser.getRightId()).rightName(tbscRightUser.getRightName()).build();
            String rightDayActivateSum = alarmRightDayActivateParams.getRightAlarmEnum().getKey().apply(alarmRightDayActivateParams);
            String lockKey = RedissonConstant.RLOCK + rightDayActivateSum;
            RLock lock = redissonClient.getLock(lockKey);
            try {
                lock.lock();
                //剩余权益数量为负数的解决办法
                sqlSession.clearCache();//mybatis一级缓存导致数据库查询的记录相同
                tbscRightUser = tbscRightUserMapper.selectByPrimaryKey(request.getData().getEquityUserId());
                //单天单用户单权益激活告警数
                RightAlarmParams alarmDayUserRightActivateParams = RightAlarmParams.builder().rightAlarmEnum(RightAlarmEnum.DAYUSERRIGHTACTIVATESUM).phone(tbscRightActivityOrder.getUserPhone()).rightId(tbscRightUser.getRightId()).rightName(tbscRightUser.getRightName()).build();
                boolean alarmDayUserRightActivateSum = rightLimitUtils.isAlarm(alarmDayUserRightActivateParams);
                //单权益单天激活数
                boolean alarmRightDayActivateSum = rightLimitUtils.isAlarm(alarmRightDayActivateParams);
                //判断是否还有权益数量
                Integer rightRemain = tbscRightUser.getRightRemain();//剩余权益数量
                if (0 >= rightRemain) {//订购失败
                    throw exceptionFactory.create(EcologicalExceptionMsg.ERROR_RIGHT_OUTNUM_EXCEPTION, EcologicalExceptionMsg.msg.get(EcologicalExceptionMsg.ERROR_RIGHT_OUTNUM_EXCEPTION));
                }
                //            判断是否是二选一或无限制或其他
                Integer groupRule = tbscRightUser.getGroupRule();
                if ((1 == groupRule || 3 == groupRule) && 1 == tbscRightUser.getRightStatus()) {//二选一、三选一
                    throw exceptionFactory.create(EcologicalExceptionMsg.ERROR_RIGHT_OUTRULE_EXCEPTION, EcologicalExceptionMsg.msg.get(EcologicalExceptionMsg.ERROR_RIGHT_OUTRULE_EXCEPTION));
                }
                //            判断是否组合中的组合权益
                TbscRight tbscRight = tbscRightMapper.selectByPrimaryKey(tbscRightUser.getRightId());
                List<TbscRightReceive> rightReceives = new ArrayList<>();
                //          根据权益id去权益表中查询权益下单接口需要的参数：产品编码等
                //            调用权益下单接口
                if (EquitystorecenterConstant.RIGHT_FID.equals(tbscRight.getRightFid())) {//组合权益
                    List<TbscRight> byRightFid = tbscRightMapper.findByRightFid(tbscRight.getRightId());
                    if (!CollectionUtils.isEmpty(byRightFid)) {

                    }

                } else {
                    String orderId = IdFactory.createId();
                    TbscRightReceive rightReceive = new TbscRightReceive(IdFactory.createId(), tbscRightUser.getEquityUserId(), new Date(), request.getData().getReceiveNum(), orderId);
                    rightReceives.add(rightReceive);
                }

                // 更新用户权益记录表中的权益剩余数量和权益状态（是否置灰，不可选）
                if (1 == groupRule || 3 == groupRule) {
                    //根据用户资格发放记录id+权益组id查其他权益，并置灰
                    List<String> other = tbscRightUserMapper.findByOrderIdAndGroupIdAndNotEquityUserId(tbscRightUser.getOrderId(), tbscRightUser.getGroupId(), tbscRightUser.getEquityUserId());
                    if (!CollectionUtils.isEmpty(other)) {
                        //未领取的要更新该权益为置灰状态，因为规则为二选一
                        int i = tbscRightUserMapper.updateRightStatusByPrimaryKeyIn(other);
                    }

                }
                tbscRightUserMapper.updateRightRemainByPrimaryKey(tbscRightUser.getEquityUserId());
                //            领取成功，批量插入用户权益领取记录表
                tbscRightReceiveMapper.insertTbscRightReceives(rightReceives);
                //更新redis中单天单用户单权益激活告警数
                rightLimitUtils.setRightActivateSum(alarmDayUserRightActivateParams);
                //更新redis中单权益单天激活数
                rightLimitUtils.setRightActivateSum(alarmRightDayActivateParams);
                //发送短信，通知用户领取成功
                asynSendMessage.sendMessage(Arrays.asList(tbscRightActivityOrder.getUserPhone()), EquitystorecenterConstant.ALARMCONTEXT,EquitystorecenterConstant.SMSTYPE);
            } finally {
                if(lock.isLocked()){
                    if(lock.isHeldByCurrentThread()){
                        lock.unlock(); //两个判断条件是非常必要的
                    }
                }
            }
        }
        return HttpResponse.build();
    }
```

# mybatis中的#和$的区别

- >  将传入的数据都当成一个字符串，会对自动传入的数据加一个双引号。如：order by #user_id#，如果传入的值是111,那么解析成sql时的值为order by "111", 如果传入的值是id，则解析成的sql为order by "id"

- > $将传入的数据直接显示生成在sql中。如：order by $user_id$，如果传入的值是111,那么解析成sql时的值为order by user_id, 如果传入的值是id，则解析成的sql为order by id

# 
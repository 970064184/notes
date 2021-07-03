# 常用查询

## 解决 两个字段比较查询

```java
    @Override
    public Page<ApplyMakeup> selectList(ApplyMakeupReq req) {
        List<AggregationOperation> operations = new ArrayList<>();
        long count = 0;
        List<ApplyMakeup> result = new ArrayList<>();
        operations.add(Aggregation.sort(Sort.Direction.DESC,"id"));//排序
        //查询条件
        Criteria criteria = new Criteria();
        if( null !=req.getScope() && 1 == req.getScope()){//0全部人，1当前用户
            String username = SecurityUtil.getUsername();
            criteria.and("applyer").is(req.getApplyer());
        }

        DBObject obj = new BasicDBObject();
        if(null !=req.getIsUrgency() &&  2 == req.getIsUrgency()){//审批方式：2应急，1审批
            operations.add(Aggregation.project("applyer","auditor")
                    .and(AggregationSpELExpression.expressionOf("cond(applyer == auditor, 1, 0)")).as("flag1") );
//            obj.put("$where","this.applyer == this.auditor");
            criteria.and("flag1").is(1);

        }else if(null !=req.getIsUrgency() &&  1 == req.getIsUrgency()){
            operations.add(Aggregation.project("applyer","auditor")
                    .and(AggregationSpELExpression.expressionOf("cond(applyer != auditor, 1, 0)")).as("flag1") );
            criteria.and("flag1").is(1);
        }

        if(null != req.getPlayerId()){
            criteria.and("playerId").is(req.getPlayerId());
        }

        if(!StringUtils.isEmpty(req.getApplyer())){
            Pattern pattern= Pattern.compile("^.*"+req.getApplyer()+".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("applyer").regex(pattern);
        }

        if(!StringUtils.isEmpty(req.getAuditor())){
            Pattern pattern= Pattern.compile("^.*"+req.getAuditor()+".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("auditor").regex(pattern);
        }

        if(null != req.getApplyDateEnd() && null != req.getApplyDateStart()){
            criteria.and("applyDate").gte(req.getApplyDateStart());
            criteria.and("applyDate").lte(req.getApplyDateEnd());
        }

        if(null != req.getState()){
            criteria.and("state").is(req.getState());
        }
        operations.add(Aggregation.match(criteria));

        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<ApplyMakeup> results = mongoTemplate.aggregate(aggregation, ApplyMakeup.class, ApplyMakeup.class);
        result = results.getMappedResults();
        count = result.size();
        //分页
        operations.add(Aggregation.skip((long)req.getPage()*req.getSize()));
        operations.add(Aggregation.limit(req.getSize()));

        result =  mongoTemplate.aggregate(Aggregation.newAggregation(operations), ApplyMakeup.class, ApplyMakeup.class).getMappedResults();
        Page<ApplyMakeup> page = new PageImpl(result, PageRequest.of(req.getPage(),req.getSize()), count);
        return page;
    }
```

# 

## like

```sql
db.getCollection("User").find({nickname:/张/})
```

## id

```sql
db.getCollection("User").find({_id:ObjectId("5efc5fa8c918f5711233ccaa")})
```

## in & notin & remove

```java
db.getCollection("Server").find({_id:{$in:["1", "2", "777", "11212", "111", "31"]}})
db.getCollection("Creative").remove({"projectId":NumberInt(7),"title":{ $nin : ["大渠道","小渠道01"] }})
```

## count && 是否存在此字段

```java
db.getCollection('Creative').find({"projectId":{"$exists":false}}).count()
```

## 数组中包含某个值的查询

```sq
db.User.find({projectIds:{$elemMatch:{$eq:3}}})
```

## 更新

```java
db.getCollection("Creative").update({"projectId":{$exists:false}}, {$set:{'projectId':NumberInt(7)}},{multi:true})
```

remove && not in

## 事务测试

```sql
##测试失败回滚
session = db.getMongo().startSession({ readPreference: { mode: "primary" } });
activity = session.getDatabase("gmbackend_daqin2").Activity;
activityContent = session.getDatabase("gmbackend_daqin2").ActivityContent;
session.startTransaction({ readConcern: { level: "local" }, writeConcern: { w: "majority" } });
try {
    activity.insertOne({ "name": "Wilson" });
    activityContent.insertOne({ "desc": "Wilson"});
		session.getDatabase("mongo").user.insert({ "time": new Date() });    //多增加操作一个不存在的表

} catch (error) {
    session.abortTransaction();
    throw error;
}
session.commitTransaction();
session.endSession();
```

```sql
##测试正常提交
session = db.getMongo().startSession({ readPreference: { mode: "primary" } });
activity = session.getDatabase("gmbackend_daqin2").Activity;
activityContent = session.getDatabase("gmbackend_daqin2").ActivityContent;
session.startTransaction({ readConcern: { level: "local" }, writeConcern: { w: "majority" } });
try {
    activity.insertOne({ "name": "Wilson" });
    activityContent.insertOne({ "desc": "Wilson"});

} catch (error) {
    session.abortTransaction();
    throw error;
}
session.commitTransaction();
session.endSession();
```

## 查询文档值

```java
db.getCollection("User").find({"roles.7":ObjectId("5f69a39bca0bcfe8078a10a9")})
```

> //字段名：roles
>
> {
>     7: [
>         ObjectId("5f69a39bca0bcfe8078a10a9")
>     ],
>     8: [
>         ObjectId("5f69a79dca0bcfe8078a10fa")
>     ]
> }



## 批量修改字段类型

```sql
###https://blog.csdn.net/qq_24678987/article/details/80547866

db.Server.find({updateTime:{$type:"string"}}).forEach( function (x) {
 x.updateTime = new ISODate(x.updateTime);
  db.Server.save(x);
});


```

## 修改字段名

```sql
db.columnsample.updateMany(
{},
{
  "$rename":{"remponse":"response"}
}
)
```


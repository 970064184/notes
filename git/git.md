# git命令 

**HEAD^ 上个版本,HEAD~2 上上个版本miced commitId**

**1. 查看日志** 

　　**git log**

**2.** **此时如果想撤销commit，同时保留git add**

　　**git reset --soft HEAD^**

**3. 删除工作空间改动代码，撤销commit，撤销git add**

　　**git reset --hard HEAD^**

> 在windows的cmd控制台下操作git，想要回滚到上一次提交，但是输入git reset --hard HEAD^后就显示more?，多按几次回车后就报错如下，如何解决呢？
>
> fatal: ambiguous argument 'HEAD
> ': unknown revision or path not in the working tree.
> Use '--' to separate paths from revisions, like this:
> 'git <command> [<revision>...] -- [<file>...]'
> 1
> 2
> 3
> 4
> 这是因为cmd控制台中换行符默认是^，而不是\ ，所以它的more？的意思是问你下一行是否需要再输入，而^ 符号就被当做换行符而被git命令忽略掉了。
>
> 解决方法有如下几种：
> 加引号：git reset --hard "HEAD^"
> 加一个^：git reset --hard HEAD^^
> 换成~：git reset --hard HEAD~ 或者 git reset --hard HEAD~1
> ~ 后面的数字表示回退几次提交，默认是一次
>
> 当然还可以换成git bash，powershell等就不会出现这种问题了
> 

**4. 不删除工作空间改动代码，撤销commit，并且撤销git add(常用)**

　　**git reset --mixed HEAD^ 或者git reset HEAD^**

**5. 推到远程**

　　**git push -f**

**6. 如果只想修改下git commit 的注释内容**

　　**git commit --amend**

**7. 还没git commit ，只撤销git add，此时会保留本地修改（绿字变红字）**

　　**git reset HEAD filename**

　　**全部：git reset HEAD**

**8. 不想保留本地修改**

　　**git checkout filename**

# github骚操作 

<https://blog.csdn.net/weixin_43499626/article/details/88414830> 

- **watch**：会持续收到该项目的动态
- **fork**，复制某个项目到自己的Github仓库中
- **star**，可以理解为点赞
- **clone**，将项目下载至本地
- **follow**，关注你感兴趣的作者，会收到他们的动态

## in

***通过in关键词限制搜索范围***

**xxx in:name**  项目名包含xxx的

**xxx in:description** 项目描述包含xxx的

**xxx in:readme** 项目的readme文件中包含xxx的

当然也可以通过**xxx in:name,desciption**来组合使用

## 通过 Star 或者Fork数 去查找项目

通过通配符 > < = 即可，区间范围内可通过 num1..num2

如，要查找stars数不小于666的springboot项目

springboot  stars:>=666

forks 大于等于500

springboot forks:>500

查找fork在100到200之间 且stars数在80到100之间的springboot项目

springboot forks:100..200 stars:80..100


## ***awesome + 关键字***  

搜索和关键字匹配的优秀项目

awesome springboot 搜索优秀的springboot相关的项目，包括框架、教程等

## 分享项目中某一行的代码

只需要在具体的网址后面拼接#Lxx(xx为行数)

如

我需要分享这个类中的@SprintBootApplication注解，值需要在后面拼接上#L6 即可

https://github.com/lxy-go/SpringBoot/.../JpaApplication.java#L6


## 项目内搜索

打开你想要搜索的项目，然后按一下‘T’键。会跳转至一个新的网页，

## 搜索某个地区内的大佬

可以通过location:地区 进行检索，在具体可以通过language:语言  缩小检索范围

如搜索地区在北京的Java方向的用户

location:beijing language:java

# git 分支git flow开发规范 

## 例子1 

<https://baijiahao.baidu.com/s?id=1637502371036658321&wfr=spider&for=pc> 

**以下为Git分支开发规范的简单总结**

**分支结构**

**主要分支**

**master**

主分支，用于部署生产环境的分支，需确保master分支稳定性master分支存储了正式发布的历史属于只读唯一分支，只能从其它分支（如develop，hotfix）合并，不能直接在此修改所有向master分支的Push推送都应当打TAG标签做记录,方便追溯**develop**

开发分支，基于master分支检出的平行分支develop分支始终保持最新完成以及bug修复后的代码属于只读唯一分支，只能从master以外的分支（如feature，hotfix）合并，不能直接在此修改**下为master和develop平行分支示意图：**

![img](https://pics5.baidu.com/feed/279759ee3d6d55fb02db1ac74c15444f21a4ddea.jpeg?token=7bb5c3906a8edf3a5cf062dc7bbd6d91&s=94B4E432C35AF5284E7CC4DE02009073)

**支持分支**

**feature（官方亦称topic）**

功能分支，基于develop分支检出，用于新功能的开发feature分支可同时存在多个，用于团队中多个功能同时开发命名规则：feature/*，如feature/shoppingcart，feature/userlogin属于临时分支，最终会被合并回develop（作为新版本功能）或丢弃（放弃功能），最后可选删除feature通常仅存在于开发人员存储库中(本地库)，而不存在于远程origin**下为feature分支应用示意图：**

![img](https://pics6.baidu.com/feed/43a7d933c895d1433d3eff1351c789075aaf0775.jpeg?token=816342e217b17061ca419718c44e575a&s=9094E4329F227401487CC0DE02009032)

**release**

发布分支，基于develop分支检出，用于准备发布新阶段版本用于Bug测试及修复，文档生成和其它面向发布任务属于临时分支，最终会先被合并到master（发布新版本），打TAG标签，再被合并到develop，最后可选删除命名规则：推荐为release-*或release/*，如release-1.0**下为release分支应用示意图：**

![img](https://pics2.baidu.com/feed/2cf5e0fe9925bc31078827707fe886b4ca137038.jpeg?token=6f41c4a580ca44a5d7c2fa856ebf81d9&s=1094E4331F0A6441487CD0DA0200D032)

**hotfix**

补丁分支，基于master分支检出，用于对线上发布的版本进行BUG修复属于临时分支，最终会先被合并到master（发布新版本，亦称修复版本），打TAG标签，再被合并到develop，可选删除命名规则：推荐为hotfix-*或hotfix/*，如hotfix-1.0.1**下为hotfix分支应用示意图：**

![img](https://pics4.baidu.com/feed/c8177f3e6709c93d61ddbbc4bd0af3d9d1005495.jpeg?token=9d84b0228d8ce68da87bafbd0c312382&s=D294E423130B554908F5D8D90200D033)

**最后**

推荐使用对Git Flow很好应用的图形工具SourceTree，可以帮助我们很好的理解和管理git分支。

**下图为SourceTree工作流默认配置：**

![img](https://pics3.baidu.com/feed/b3119313b07eca8004cad601b0149cd8a34483c1.jpeg?token=81384c75f554ca974ed170b4572d9f9f&s=6D88EC1B195C41CC46FD65DA0000C0B3)

## 例子2 

<https://cloud.tencent.com/developer/news/207575>

**规则说明**

开发阶段

开发新需求都从master拉取feature分支，不同迭代发布代码使用不同的分支，避免相互影响。

需要进行开发环境联调的代码合并到develop分支，由jekins自动发布到开发环境。

命名方式：dev-mmdd(时间)-需求名称，例如：dev-0812-redeemModfiy

测试阶段

每月为一个迭代周期由系统owner拉取提测分支test，并定期维护(merge master)。

开发feature分支合并到提测test分支，合并前需要由模块负责人进行review或交叉review。

多个feature分支在测试过程中，需要借助release分支来进行合并，然后再进行提测。

多个feature分支在测试过程中，某一个需求不能如期发布，需要将release分支删除，将可继续的分支重复执行第2步进行测试验证；

命名方式：test-mmdd(每月15日)，例如：test-0815 / release-mmdd(每月15日)，例如：release-0815

发布阶段

测试通过后release进行merge master，并解决冲突，在release分支回归测试

回归通过后，先在master分支拉取tag分支备份，然后把release合并到master。

发布master

命名方式：tag-mmdd(时间)，例如：tag-0812

分支拉取时间点和负责人

feature分支：

拉取时间点：开发开始时

负责人：开发人员

test分支

拉取时间点：每月迭代需求开始，不定期merge master

负责人：系统Owner

release分支

拉取时间点：开发需求合并提测前 or 回归测试前

负责人：开发人员

deelop分支

拉取时间点：合并发布开发环境时

负责人：开发人员


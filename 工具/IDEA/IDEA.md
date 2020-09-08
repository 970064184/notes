<https://blog.csdn.net/ThinkWon/article/details/101020481>

# IDEA 永久激活

<https://segmentfault.com/a/1190000021220727?utm_source=tag-newest>

# 常用配置

## @Autowired提示问题

- ### IDEA报错Could not autowire. No beans of ‘xxxxMapper’ type found

- 解决办法是：降低Autowired检测的级别，将Severity的级别由之前的error改成warning

![](images/QQ%E6%88%AA%E5%9B%BE20191207164153.png)

## Auto import

![](C:/Users/Administrator/Desktop/notes/%E4%B9%B1%E4%B8%83%E5%85%AB%E7%B3%9F/images/QQ%E6%88%AA%E5%9B%BE20191207164329.png)

- 

## 配置JDK

在IDEA启动页面中，下拉Configure，选择Project Defaults – Project Structure，这样可以设置所有项目的默认的JDK版本，如下图

![](images/20190919134900196.png)

## 文件编码

![](images/aHR0cHM6Ly9yYXcuZ2l0aHVidXNlcmNvbnRlbnQuY29tL0pvdXJXb24vaW1hZ2UvbWFzdGVyL0lERUElRTUlQjglQjglRTclOTQlQTglRTklODUlOEQlRTclQkQlQUUlRTUlOTIlOEMlRTUlQjglQjglRTclOTQlQTglRTYlOEYlOTIlRTQlQkIlQjY.jpg)

## 文件和代码模板

![](images/20190919134940157.png)

## Maven配置

![](images/aHR0cHM6Ly9yYXcuZ2l0aHVidXNlcmNvbnRlbnQuY29tL0pvdXJXb24vaW1hZ2UvbWFzdGVyL0lERUElRTUlQjglQjglRTclOTQlQTglRTklODUlOEQlRTclQkQlQUUlRTUlOTIlOEMlRTUlQjglQjglRTclOTQlQTglRTYlOEYlOTIlRTQlQkI (1).jpg)

## 自动导入Maven依赖

![](images/20190919135012549.png)

## Java代码单行注释添加空格

![](images/2019121509061531.png)

## 优化导入和智能删除无关依赖

![](images/20190919135155737.png)

## 修改主题

![](images/20190919135213154.png)

## 修改字体

![](images/20190919135228539.png)

## 代码提示不区分大小写

![](images/20190919135244518.png)

## 显示行数和方法线

![](images/20190919135259168.png)

## 目录展示设置

![](images/aHR0cHM6Ly9yYXcuZ2l0aHVidXNlcmNvbnRlbnQuY29tL0pvdXJXb24vaW1hZ2UvbWFzdGVyL0lERUElRTUlQjglQjglRTclOTQlQTglRTklODUlOEQlRTclQkQlQUUlRTUlOTIlOEMlRTUlQjglQjglRTclOTQlQTglRTYlOEYlOTIlRTQlQkI (2).jpg)

## 打开IDEA项目选择

![](images/20190919135317919.png)

## 代码自动提示快捷键

![](images/20190919135333462.png)

设置Basic快捷键为Alt+斜杠

![](images/20190919135358583.png)

## 全局修改文件描述信息（推荐）

```java
/**
 * Description:
 *
 * @author zb
 * @date ${DATE} ${TIME}
 */
```



![](images/20190919135417548.png)

## 生成方法注释

1.打开Preferences

2.Editor -> Live Templates -> 点击右边加号为自己添加一个Templates Group -> 然后选中自己的Group再次点击加号添加Live Templates

![](images/20190919135512519.png)

3.然后设置自己喜欢的快捷键 在Abbreviation里面 记得在Applicable in 里面勾选Java

![](images/20190919135523250.png)

4.然后在Edit variables里面添加参数和返回值的自动取值

```java
*
 * Description: $Description$
 *
 * @author JourWon
 * @date $DATE$ $TIME$
$param$
 * @return $return$
 */
```



![](images/20190919135539209.png)

5.然后再你的方法上面直接输入`/*+*+Tab`

## IDEA生成序列号serialVersionUID

设置完成后，按Alt+Enter键，这个时候可以看到"Add serialVersionUID field"提示信息

![](images/20190919135641281.png)

## 统一编码

```java
File->Settings->Editor->File Encodings
```

![](images/QQ截图20200427195531.png)

## IDEA如何执行maven命令进行打包编译及常用命令

前提条件：maven配置环境变量。
在保证环境变量配置没问题的情况下执行过程出现mvn不是内部命令类似的错误，建议重启编译器或者命令窗口。

执行maven命令，方式一：
　　在IDEA主界面左下角找到 Terminal 点击进入，直接输入想执行的命令即可。

![](images/20180619133613687.png)



执行maven命令，方式二：
　　像配置tomcat一样，找到如图 Edit Configuration 选项，点击进入后在加号出找到 maven 选项，然后在 Command line 处输入要执行的命令即可。

![](images/QQ截图20200518212841.png)




注意，方式一执行maven命令要加 mvn 前缀，方式二因为本身就是添加maven命令模块，所以不用加 mvn 前缀即可

常用maven命令总结：
mvn -v //查看版本
mvn archetype:create //创建 Maven 项目
mvn compile //编译源代码
mvn test-compile //编译测试代码
mvn test //运行应用程序中的单元测试
mvn site //生成项目相关信息的网站
mvn package //依据项目生成 jar 文件
mvn install //在本地 Repository 中安装 jar
mvn -Dmaven.test.skip=true //忽略测试文档编译
mvn clean //清除目标目录中的生成结果
mvn clean compile //将.java类编译为.class文件
mvn clean package //进行打包
mvn clean test //执行单元测试
mvn clean deploy //部署到版本仓库
mvn clean install //使其他项目使用这个jar,会安装到maven本地仓库中
mvn archetype:generate //创建项目架构
mvn dependency:list //查看已解析依赖
mvn dependency:tree //看到依赖树
mvn dependency:analyze //查看依赖的工具
mvn help:system //从中央仓库下载文件至本地仓库
mvn help:active-profiles //查看当前激活的profiles
mvn help:all-profiles //查看所有profiles
mvn help:effective -pom //查看完整的pom信息

# 安装的插件

- 阿里巴巴代码规范：Alibaba java coding guidelines 

- git提交详细信息查看：GitToolBox

- java类库：Lombok

- ### JSON转领域对象工具：GsonFormat

- ### 领域对象转JSON工具：POJO to JSON

- ### 代码作色工具：Rainbow Brackets

- ### 日志工具：Grep Console

- ### Redis可视化：Iedis

- ### K8s工具：Kubernetes

- ### 中英文翻译工具：Translation

- ### 一个对象的所有set方法：GenerateAllSetter

- ### mybatis代码自动生成：MyBatisCodeHelperPro

- <https://mp.weixin.qq.com/s/zyIKY0Bc7DXic7kQN-zuRA>

- **Free Mybatis plugin -Mybatis 辅助插件**

- maven help：解决jar冲突

# 骚技巧

## 快速补全行末分号

shfit+ctrl +enter

## 自带的HTTP请求工具

Shfit+Ctrl+A



## Language Injection

将String转换为JSON格式，不用担心转换字符串时的转义问题

![](images/QQ截图20200721102440.png)


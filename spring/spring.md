# spring常用的注入方式有哪些 

https://blog.csdn.net/a909301740/article/details/78379720

- setter属性注入
- 构造方法注入
- 注解方式注入
  - 描述依赖关系主要有两种：
    - @Resource
      - 默认以byName方式匹配与属性名相同的bean的id，如果没有找到就会以byType的方式查找，如果byType查找到多个的话，使用@Qualifier注解指定某个具体名称的bean
    - @Autowired
      - 默认是以byType的方式去匹配类型相同的bean，如果只匹配到一个，那么就直接注入该bean，无论要注入的bean的name是什么；如果匹配到多个，就会调用DefaultListablBeanFactory的determineAutowireCondidate方法来决定具体注入哪个bean

# spring框架中的单例bean是线程安全的吗

- spring作用域（scope）的配置区别：

  - 非线程安全：Singleton（默认）：spring容器只存在一个共享的bean实例

    （只需维护一个实例，可提高性能，加快访问速度）

    - @Scope(“prototype”)：让单例变成多例

  - 线程安全：prototype：每次对bean的请求都会创建一个新的bean实例

# spring bean的完整生命周期

- 从创建spring容器开始，直到最终spring容器销毁bean
- ![](images/181453414212066.png)
- 

# Spring缓存 

- 声明某些方法使用缓存
  - @Cacheable
    - value：指定cache名称
    - key：
    - condition：
- 配置spring对cache的支持

- （对于一个支持缓存的方法，spring会在其被调用后将其返回值缓存起来，以保证下次利用同样的参数来执行该方法时可以直接从缓存中获取结果，而不需要再次执行该方法。spring在缓存方法的返回值时是以键值对进行缓存的，值就是方法的返回结果，至于键的话，spring又支持两种策略，默认策略和自定义策略。需注意的是，当一个支持缓存的方法在对象内部被调用时是不会触发缓存功能的。）
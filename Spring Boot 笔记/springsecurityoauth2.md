> 认证（你是谁）（登录校验）
>
> 授权（你能干什么）（用户角色权限控制）

> 令牌可以让第三方应用获得权限，同时又随时可控，不会危及系统安全。这就是oauth2的优点。

> 第三方应用
>
> 授权服务器
>
> 资源服务器
>
> 用户

# 四种模式

https://mp.weixin.qq.com/s/AELXf1nmpWbYE3NINpLDRg

## 授权码模式

- 常见的第三方平台登录功能基本都是使用这种模式

- > https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488214&idx=1&sn=5601775213285217913c92768d415eca&chksm=e9c340b6deb4c9a01bc383b2c0ab124358663adf22a58ba385f792224ba532079a028ba92a3d&scene=178&cur_album_id=1319833457266163712#rd 
  >
  > - 授权服务器
  >
  >   - 继承WebSecurityConfigurerAdapter ：用户信息配置
  >   - 继承AuthorizationServerConfigurerAdapter：授权服务器
  >     - AuthorizationServerSecurityConfigurer ：用来配置令牌端点的安全约束，也就是这个端点谁能访问，谁不能访问。
  >     - ClientDetailsServiceConfigurer ：配置校验客户端。客户端的信息科存在数据库中。
  >     - AuthorizationServerEndpointsConfigurer 
  >       - authorizationCodeServices：用来配置授权码存储（code：授权码用来获取令牌的，使用一次就失效）
  >       - tokenServices：用来配置令牌的存储（access_token：令牌是用来获取资源的）
  >
  > - 资源服务器：用来存放用户的资源：例如微信头像、openid等信息
  >
  >   - 继承ResourceServerConfigurerAdapter ：
  >
  >     - > RemoteTokenServices ：资源服务器和授权服务器是分开的两个服务的时候，需要配置 access_token的校验地址、client_id、client_secret这三个信息，当用户来资源服务器请求资源时，会携带一个access_token，通过这里的配置，就能够校验出token是否正确等
  >
  >     - ResourceServerSecurityConfigurer ：spring security的知识资源拦截？
  >
  >     - HttpSecurity ：spring security的知识资源拦截？

## 简化模式

## 密码模式

- 密码模式是用户把用户名密码直接告诉客户端，客户端使用说这些信息向授权服务器申请令牌（token）。这需要用户对客户端高度信任，例如客户端应用和服务提供商就是同一家公司，我们自己做前后端分离登录就可以采用这种模式

## 客户端模式

# 令牌五种存储方式

## InMemoryTokenStore

## JdbcTokenStore

## JwtTokenStore

## RedisTokenStore



## JwkTokenStore
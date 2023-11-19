config：对springSecurity进行配置，暴露不经过过滤链的接口等
controller/LoginController：实现登录接口以及退出登录接口
domain/LoginUser：实现UserDetails接口，对权限处理
      /ResponseResult：统一响应
filter/JwtAuthenticationFilter:继承OncePerRequestFilter，属于自定义过滤器，功能是认证，获取请求头中的token，解析后得出userID，去redis/mysql中获取LoginUser对象，封装后存入SecurityContextHolder
model/dao: 用户表的映射
     /pojo: 用户类（封装）
service/LoginService：属于@Service层，1. 在用户登录的时候，进行判断，生成jwt返回，并存入redis
                                     2. 退出登录操作，redis中删除该用户的jwt（token）即可
       /UserDetailsServiceImpl：实现UserDetailsService接口，进行修改，从数据库中比对用户信息，确认用户是否存在（用户名密码是否正确）
utils/JwtUtil: jwt的加密和解密

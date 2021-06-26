### 项目特点

- [x] MD5双重加盐摘要
- [x] Redis分布式Token
- [x] RabbitMQ流量削峰
- [x] 验证码防止恶意抢购



### 运行环境

- 💻 OS：CentOS7.9
- ⭕ CPU：i7-10750H
- ☕️ JDK：1.8
- 🐋 Docker：19.03.14
- 🐬 MySQL：8.0.20
- 💠 Redis：5.0.9
- 🐇 RabbitMQ：3.8.9



### 测试账号

| 序号 | 昵称   | 电话        | 密码   |
| ---- | ------ | ----------- | ------ |
| 1    | 龙子异 | 13611339999 | 111111 |
| 2    | 段依依 | 15600330837 | 222222 |
| 3    | 唐君灿 | 13005518377 | 333333 |
| 4    | 赵芳娜 | 13200246245 | 444444 |
| 5    | 金庄诚 | 13201297721 | 555555 |



### 压测工具

🔨 jemeter：https://jmeter.apache.org/download_jmeter.cgi

新建线程组

添加三个监听器，用于收集结果

- 查看结果树
- 聚合报告
- 用表格查看结果

添加一个配置原件，配置请求令牌

- CSV数据文件配置



### 原始数据

版本回退：

```powershell
git reset --hard a124a080633251f119ddaed510cd025edb362c3b
```

（1）商品列表

- 线程：3000
- 次数：10

| 测试编号 | 样本  | 平均值 | 中位数 | 90% 百分位 | 95% 百分位 | 99% 百分位 | 最小值 | 最大值 | 异常 % | 吞吐量     | 接收 KB/sec | 发送 KB/sec |
| -------- | ----- | ------ | ------ | ---------- | ---------- | ---------- | ------ | ------ | ------ | ---------- | ----------- | ----------- |
| 1        | 30000 | 1076   | 917    | 1776       | 2105       | 3771       | 1      | 8258   | 0.00%  | 2313.3868  | 7773.79     | 304.99      |
| 2        | 30000 | 838    | 757    | 1137       | 1694       | 3345       | 1      | 8116   | 0.21%  | 3021.45231 | 10147.91    | 397.49      |
| 3        | 30000 | 966    | 837    | 1423       | 1804       | 7852       | 2      | 15056  | 0.00%  | 1919.50861 | 6450.22     | 253.06      |
| 4        | 30000 | 756    | 713    | 1027       | 1630       | 3599       | 2      | 4906   | 0.00%  | 3300.33003 | 11090.27    | 435.1       |
| 5        | 30000 | 944    | 903    | 1335       | 1719       | 2187       | 2      | 4458   | 1.32%  | 2755.83318 | 9228.16     | 358.51      |

平均QPS：2,662.102186

（2）商品秒杀

- 线程：5000
- 次数：2

| 测试编号 | 样本  | 平均值 | 中位数 | 90% 百分位 | 95% 百分位 | 99% 百分位 | 最小值 | 最大值 | 异常 % | 吞吐量    | 接收 KB/sec | 发送 KB/sec |
| -------- | ----- | ------ | ------ | ---------- | ---------- | ---------- | ------ | ------ | ------ | --------- | ----------- | ----------- |
| 1        | 10000 | 12501  | 14647  | 21001      | 21017      | 21834      | 4      | 27055  | 14.01% | 299.01624 | 587.15      | 68.55       |
| 2        | 10000 | 9167   | 9117   | 15865      | 21002      | 21007      | 3      | 25068  | 22.78% | 346.88497 | 713.02      | 71.41       |
| 3        | 10000 | 11647  | 9688   | 24468      | 25322      | 27658      | 3      | 27743  | 39.31% | 320.73898 | 751.66      | 51.9        |
| 4        | 10000 | 13527  | 14826  | 21002      | 21051      | 22586      | 495    | 30201  | 13.65% | 322.42463 | 628.26      | 74.23       |
| 5        | 10000 | 10662  | 10628  | 16345      | 19220      | 21100      | 8      | 27048  | 10.40% | 334.20226 | 624.87      | 79.83       |

平均QPS：324.653416



### 页面优化

版本回退：

```powershell
git reset --hard 5d2b50ba80f7a9c8f17de17a98f98d230321fbef
```

优化：

1. 使用Redis缓存页面，指定一个较短的缓存时间，保证最终一致性；
2. 页面静态化，将静态资源直接缓存在浏览器中，js、css和image等。

测试：

- 线程：3000
- 次数：10

| 测试编号 | 样本  | 平均值 | 中位数 | 90% 百分位 | 95% 百分位 | 99% 百分位 | 最小值 | 最大值 | 异常 % | 吞吐量     | 接收 KB/sec | 发送 KB/sec |
| -------- | ----- | ------ | ------ | ---------- | ---------- | ---------- | ------ | ------ | ------ | ---------- | ----------- | ----------- |
| 1        | 30000 | 315    | 126    | 808        | 1130       | 2551       | 0      | 5341   | 0.00%  | 4743.83302 | 15783.44    | 625.41      |
| 2        | 30000 | 212    | 45     | 547        | 1053       | 3003       | 0      | 3892   | 0.00%  | 6031.36309 | 20067.24    | 795.15      |
| 3        | 30000 | 280    | 88     | 700        | 1091       | 2989       | 0      | 7005   | 0.00%  | 4024.14487 | 13388.93    | 530.53      |
| 4        | 30000 | 194    | 45     | 378        | 1061       | 3009       | 0      | 4391   | 0.00%  | 6272.21409 | 20868.59    | 826.9       |
| 5        | 30000 | 218    | 52     | 488        | 1010       | 3023       | 0      | 4119   | 0.00%  | 5772.56109 | 19206.17    | 761.03      |

平均QPS：5,368.823232



### 秒杀优化

版本回退：

```powershell
git reset --hard 685eeed58ffd7a13281979efdb5ba73ecba125a0
```

关键：

- 在秒杀订单中用户ID和商bbID建立唯一索引
- 执行商品库存减少时保证库存数量要大于零

优化：

1. 系统初始化时，把商品库存数量加载到Redis，并设置一个MAP，记录库存不足的商品ID；
2. 收到秒杀请求，首先从MAP中查看商品是否抢购结束，结束则直接返回该商品秒杀结束；
3. 进入Redis处理，查询当前商品的库存是否充足，充足进入下一步，否则设置MAP中该商品秒杀结束；
4. 在Redis中将该商品预减库存，然后进行异步处理，抢购请求进入消息队列，直接返回等待处理结果；
5. 消费者逐一处理抢购请求，进行业务处理，写数据库，生成订单，减少库存，至此后端处理结束；
6. 客户端可以对处理结果进行轮询，是否秒杀成功。

测试：

- 线程：5000
- 次数：2

| 测试编号 | 样本  | 平均值 | 中位数 | 90% 百分位 | 95% 百分位 | 99% 百分位 | 最小值 | 最大值 | 异常 % | 吞吐量     | 接收 KB/sec | 发送 KB/sec |
| -------- | ----- | ------ | ------ | ---------- | ---------- | ---------- | ------ | ------ | ------ | ---------- | ----------- | ----------- |
| 1        | 10000 | 2233   | 2275   | 3275       | 3952       | 4118       | 28     | 4147   | 0.00%  | 1696.06513 | 582.2       | 442.24      |
| 2        | 10000 | 2445   | 2479   | 3471       | 3637       | 3937       | 27     | 4439   | 0.00%  | 1547.74803 | 531.28      | 403.56      |
| 3        | 10000 | 2271   | 2220   | 3118       | 3673       | 4025       | 36     | 4796   | 0.00%  | 1706.7759  | 585.92      | 445.03      |
| 4        | 10000 | 2263   | 2352   | 3160       | 3348       | 3701       | 18     | 4198   | 0.00%  | 1746.41984 | 599.47      | 455.37      |
| 5        | 10000 | 2134   | 2161   | 3113       | 3218       | 3436       | 24     | 3777   | 0.00%  | 1839.9264  | 631.6       | 479.75      |

平均QPS：1,707.38706



### 安全优化

主要：

1. 数学公式的验证码
2. 秒杀接口地址隐藏
3. 秒杀接口限流防刷

流程：

1. 秒杀接口地址上增加一段动态路径，这个路径存储在Redis中；
2. 用户输入验证码后进行校验，正确则返回一个路径，并以用户ID和商品ID为键将该路径存储在Redis中；
3. 用户在抢购时客户端请求时带上上述路径，服务器会根据用户ID和商品ID取出Redis中的路径并验证；
4. 路径验证成功后才进行秒杀相关业务。

限流：

1. 自定义注解+拦截器或者AOP；
2. 将注解标注在方法上，注解包含两个值，限定时间（秒）内的最大次数；
3. 将每个请求的URI和用户ID作为键，用户每次访问，增加访问次数，限定时间内达到上限则返回错误。






- [x] MD5双重加盐摘要

- [x] Redis分布式Token



> 运行环境

💻 OS：CentOS7.9

⭕ CPU：i7-10750H 6核



> 压测工具

🔨 jemeter：https://jmeter.apache.org/download_jmeter.cgi



> 优化之前

商品列表：

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

商品秒杀：

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



> 页面缓存

商品列表：

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



> 页面静态化

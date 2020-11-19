## 一. 项目介绍

这是一个大学Java SE的实验课项目，模拟传感器收集与监测温度，结合了数据库JDBC的使用。

原题目如下：

用java语言开发2个程序。
第一个程序模拟温度传感器，每10秒钟向数据库发送一个温度值，要求温度值在20±5℃范围内随机变化。
第二个程序要求每10秒钟在console上显示一次实时温度，每1分钟在console上显示最近1分钟的平均温度。
数据库表名：`sample`，要求有2个字段：
`sample_time  timestamp`
`sample_data  decimal(5,1)`
选做：软件自动跟踪温度变化，当采集的温度超出阈值[18℃, 22℃]长达1分钟时自动报警。

## 二. 我的收获

通过这个项目，可以掌握MySQL数据库的基本使用，并使用JDBC连接MySQL，除此之外，还能学到控制台字体变色的操作！

另外，我使用了多线程（为了实现第二个程序时间间隔不同的情况），当然也可以用其他方法代替。

## 三. 使用方法

包含两个程序，第一个是 `sensor`，第二个是 `monitor`。首先创建数据库，SQL语句如下：

~~~sql
CREATE TABLE `sample` (
  `sample_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '插入时间戳',
  `sample_data` decimal(5,1) DEFAULT NULL COMMENT '温度值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
~~~

然后运行 `sensor` 中的main类方法，在运行 `monitor` 中的main类方法即可。

## 四. 参考链接

MySQL：https://www.515code.com/posts/fm1u9eli/

Java线程：https://www.515code.com/posts/0dcvvor4/
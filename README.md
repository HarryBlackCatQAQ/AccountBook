# AccountBook
记账本

JavaFX + SpringBoot + JPA + SqlLite3 开发的记账小软件

用于记录平时生活账单,杜绝变成月光族！
可以生成Excel表格或者生成饼状图查看自己的消费情况！


# 演示
![](https://github.com/HarryBlackCatQAQ/Image/blob/main/AccountBook/AccountBookLogo.png)

![](https://github.com/HarryBlackCatQAQ/Image/blob/main/AccountBook/AccountBookLogin.png)

![](https://github.com/HarryBlackCatQAQ/Image/blob/main/AccountBook/AccountBookMainView.png)

# 启动方法 #
因为使用了querydsl来写了部分sql所以要在Maven中先生成querydsl的Qxx类,直接在Maven中compile就可以运行了。

内置了默认用户为root 密码为123456  可以自行更改密码 或者创建提个新的用户自己使用。

可在Windows MacOS Linux 系统运行。 可在IDEA中自行打包。

需要的可以下载尝试学习！[windows版本下载](https://github.com/HarryBlackCatQAQ/AccountBook/releases/tag/1.0)
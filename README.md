# Java主流技术栈SSM+SpringBoot商铺系统

本项目运用SSM技术快速迭代出版校园商铺1.0，再结合SSM转型成Spring Boot的思路，切换到校园商铺2.0。同时包含MySQL主从同步实现读写分离，利用SUI Mobile快速实现响应式页面，Redis缓存，数据库加密配置，阿里云部署等实用技术点。

## 项目各个模块开发

### 通用模块
- 通用Dao层开发
- Redis访问层开发
- 单元测试模块建立

### 数据备份模块
- 搭建MySQL主从库
- 数据定期备份冗灾（包括数据以及图片的备份涉及Crontab）

### 前端模版搭建
借助第三方组件SUI快速搭建前后台界面

### 前台模块
- 会员注册
- 会员登录
- 首页模板数据填充开发
- 店铺详情页开发
- 商品详情页开发
- 会员个人信息展示
- 搜索功能开发

### 商家模块
- 商户入驻申请
- 商户登录/退出登录
- 门店管理
- 添加商品折扣信息
- 商品活动列表
- 其他操作
- 顾客活跃度数据展示
- 积分管理

### 超级管理员模块
- 头条管理
- 店铺管理
- 商品管理
- 顾客信息管理
- 主后台商家入驻列表开发
- 主后台新开店铺审批列表开发
- 主后台新开店铺审批

## 技术栈
- 前端：前端UI库 SUI Mobile／ jQuery／ Chrome UA
- 后端：SSM／Spring Boot／图片开源工具 Thumbnailator/验证码组件Kaptcha／作业调度框架 Quartz/Shell
- 平台：微信测试号
- 数据库：MySQL
- 缓存：Redis
- 部署：阿里云
- 技术亮点：MySQL主从同步/项目读写分离/框架迁移/代码重构

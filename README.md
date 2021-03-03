# Application Recommendation System
技术栈：Spring Boot + MySQL(MyBatis) + Redis + Spring Security

先配置一下application.properties里面的数据库账号密码，再在本机建立interact数据库（注意编码为utf8mb4），之后启动后会自动进行数据库的迁移（Flyway），建库的sql文件在db/migration下，无需手动导入。

## 个人中心

* 登录（id /密码/其他方式/短信验证码）

  第一次使用弹出绑定手机号

* 个人身份信息初始化（昵称/头像/地区/学校/高考模式（选科/文理）/分数预估）、

* app“我的”窗口   

  * 关于我们（我们优势等一些自我介绍
  * 设置-->功能1.评分2.当前版本3.清楚缓存4.账号安全（修改密码/注销账号）5.服务条款/免责政策/隐私政策等说明窗口
  * 问题反馈--》问题类型选择---问题描述----问题截图----提交按钮
  * 商务合作联系：姓名/手机（验证码）/性别/地区/合作说明

## 专业定位测评（倾向性信息收集）

* 性格测试
* 专业定位测试
* 生成报告

## 智能填报

--->确认信息界面（给客户展示他的预估分数/选科/职业倾向（可不填）等）

* 冲

  - 搜索框（用于搜索大学/专业/职业）

  - 筛选（省份/层次/专业等）

  - 排序（学校排名优先/专业排名优先/附近地区优先等）

  - **学校卡片展示**（1）学校所属层次（985/211/双一流/省属等）

    ​               （2）地区/大学类型（理工/邮电/师范/综合等）/综合排名/软科排名等

    ​               （3）学校代码

    ​               （4）计划人数/最低分数/位次

    ​               （5）可报取专业（可进入）-->界面分上（学校信息卡片，同学校卡片展示）下（专业信息--（名称/最低分/位次等）

    ​               （6）学校详情（可进入）-->分出进入模块（招生政策/计划/专业情况/校园风貌展示）

    ​               （7）推荐理由（可进入）-->图文并茂详细分析(专业特色/学校特点/师资力量/发展前景等)

* 稳（同上）

* 保（同上）

## 非填报信息了解模块

#### 了解大学

* 搜索栏（用于查找大学名称）（放在最上方）

* 我关注的院校（用于收藏,可增删改差）

* 初始展示（全部院校）

  * 所展示的学校卡片可以精简-->学校详细信息/招生简章/排名（可参考优志愿排名的给出方式）/特色专业（按照国家特色/xx特色分别标注显示）/院系/分数线/校园风貌等

    补充说明：界面不需要展示太长太多-->利用“更多”来进行详细加载

  * 分享功能（分享给微信好友）

  * 加关注

* 筛选功能（学校层次筛选/学校类型筛选/地区筛选/专业筛选/分数区间筛选等）

  * 筛选出的大学--》长按--》进行院校对比

#### 了解专业

* 专业测评链接

* 热门专业显示

* 全部专业

  * 展示第一层级：XX学与其内容XX类

    展示第二层级：由xx类-->某某专业-->（界面上边界栏）专业概况（主界面）/开设院校

    ​                           专业概况：1.基础概况：授予xx学位/学制/等

    ​                                               2.专业介绍

    ​                                               3.考研方向 

    ​                                               4.主要课程

    ​                                               5.就业方向（职位/主要企业公司/主要地区等）

    ​           

    * 分享功能/关注功能/讨论功能


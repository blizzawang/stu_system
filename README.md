本项目为学生管理系统，使用Spring + SpringMVC + MyBatis + Redis + MySQL架构实现，先介绍一下如何使用：

直接将项目clone到本地：

```git
git clone https://github.com/blizzawang/stu_system.git
```

然后执行文件夹中的```sql语句.sql```文件，构建数据库和数据表。

由于项目中使用了Redis作为MyBatis的二级缓存，所以你还需要搭建一下Redis的环境，并修改RedisCache类中的主机ip：

![image-20201009113431536](C:\Users\Administrator\Desktop\stu_system\imgs\01.png)

若是你不想使用Redis，或者你还没有学习过Redis，你可以选择在项目中移除掉Redis，将Mapper配置文件中关于Redis的配置项去掉即可：

```xml
<cache flushInterval="60000" size="1024" readOnly="true" eviction="FIFO" type="com.wwj.util.RedisCache"/>
```

**需要注意的是，因为项目中使用的Spring版本为4.3.7，所以你的jdk版本至少得是1.7，建议使用jdk1.8和tomcat8.0。**

本项目包含五个页面，分别为：添加学生信息、学生信息列表、登录、中注册、更新学生信息。

效果图如下：

![](C:\Users\Administrator\Desktop\stu_system\imgs\02.png)

![image-20201009112937280](C:\Users\Administrator\Desktop\stu_system\imgs\03.png)

![image-20201009113004792](C:\Users\Administrator\Desktop\stu_system\imgs\04.png)

![image-20201009113017035](C:\Users\Administrator\Desktop\stu_system\imgs\05.png)

![image-20201009113030909](C:\Users\Administrator\Desktop\stu_system\imgs\06.png)

## 登录模块

登录模块含有登录注册功能，通过点击页面上的注册按钮可以跳转至注册页面，登录注册页面均进行了部分校验，比如注册时用户名重复、登录时用户名不存在等。

![image-20201009114227514](C:\Users\Administrator\Desktop\stu_system\imgs\07.png)

![image-20201009114244385](C:\Users\Administrator\Desktop\stu_system\imgs\08.png)

登录页面还实现了锁定功能，当输入密码错误超过三次后，系统将锁定该用户，被锁定后，即使密码输入正确也无法再登录系统，需要等待五分钟后才能重新登录。

![image-20201009114414896](C:\Users\Administrator\Desktop\stu_system\imgs\09.png)

锁定时间可以在MyTimer类中进行修改：

![image-20201009114736066](C:\Users\Administrator\Desktop\stu_system\imgs\10.png)

这是一个定时器，每隔一分钟会执行一次，所以当你将count设置为一个value值后，系统就会在几分钟后重新激活用户，原理是改变用户状态，0为锁定，1为激活。

## 学生列表模块

该页面会显示出数据表中的所有学生信息，并提供分页功能，左上角有欢迎词，右上角显示当前时间但并不是实时显示的，安全退出按钮可以退出当前系统回到登录页面。

![image-20201009115203531](C:\Users\Administrator\Desktop\stu_system\imgs\11.png)

分页功能有一个小细节，当你处在首页时，上一页按钮将会隐藏；同理，当你处在末页时，下一页按钮将会隐藏。

由于删除功能比较简单，这里直接使用Ajax在原页面实现删除功能，当你点击某个条目的删除按钮时，会提示是否确认删除该学生信息：

![image-20201009115355807](C:\Users\Administrator\Desktop\stu_system\imgs\12.png)

当点击确定后，页面会重新加载，对应的学生信息会被删除。

## 更新模块

点击条目上的更新按钮，会跳转至更新页面：

![image-20201009115501684](C:\Users\Administrator\Desktop\stu_system\imgs\13.png)

更新页面会回显对应的学生信息，并做了部分校验，比如当你未做修改时，系统会进行提示：

![image-20201009115542480](C:\Users\Administrator\Desktop\stu_system\imgs\0015.png)

当你填入空内容时，系统也会提示输入为空：

![image-20201009115605286](C:\Users\Administrator\Desktop\stu_system\imgs\14.png)

页面左上角的 回到主页 按钮能够回到学生列表页面。

## 添加模块

点击学生列表页面左下角的 添加学生信息 按钮可以跳转至添加页面：

![image-20201009115733368](C:\Users\Administrator\Desktop\stu_system\imgs\15.png)

你需要填入学生信息，并点击确认提交，系统会自动跳转至学生列表页面，同样地，点击左上角的 回到主页 按钮能够回到学生列表页面。

以上便是该系统的所有功能，类中的注释我都写得非常详细，若是你正在从SSM框架过渡到SpringBoot微服务，或者是急需一个项目来练习一下SSM框架之间的整合，那么本项目无疑非常适合你。

**开源不易，若本项目帮助到了你，可以给项目点个star**


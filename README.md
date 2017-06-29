# datacrab
一个简单的数据访问组件


## [db]关系数据库部分

datacrab数据库访问基于Springframework和Mybatis框架。

## Quick Start

#### 1. 数据库操作入口

(1) 构建查询参数
```java
DbQueryCriterion<T> criterion = DataCrabDb.build(T.class).eq("fieldA", fieldA);
```

(2) 方法传入DbQueryCriterion对象
```java
@AutoWired
DemoService demoService;
...
List<T> list = demoService.getList(criterion);
```

#### 2. 分页查询
方法1：

调用criterion的page()方法传入分页参数，如果没有传入，则默认查询第1页前10条
```java
DbQueryCriterion<T> criterion = DataCrabDb.build(T.class).gt("fieldA", valueA).page(pageNo, pageSize);
Page<T> page = demoService.getPage(criterion);
```

方法2：
分页参数以独立的Page对象传入
```java
DbQueryCriterion<T> criterion = DataCrabDb.build(T.class).gt("fieldA", valueAList);
Page<T> page = demoService.getPage(criterion, Page.build(pageNo, pageSize));
```


#### 3. 读写分离配置

(1) pom中依赖spring-tx包

(2) spring配置文件中配置

   ```xml
   <tx:annotation-driven transaction-manager="transactionManager"proxy-target-class="true" />
   ```

(3) jdbc连接参数修改(**注**：多个数据源用逗号分开，validationQuery不能为空，可设置为select 1)

   ```properties
   jdbc.driver=com.mysql.jdbc.ReplicationDriver
   jdbc.url=jdbc:mysql:replication//master.db.xxxx:3306,slave.db.xxxx:3306/database-name?characterEncoding=UTF-8
   ```

(4) 在需要查询从库的service方法上配置注解

   ```java
   @Transactional(readonly=true)
   ```

#### 4. DbBaseService和DbWRSeparateService

DbWRSeparateService类继承自DbBaseService类。

如果需要读写分离，则使用DbWRSeparateService，否则使用DbBaseService即可。




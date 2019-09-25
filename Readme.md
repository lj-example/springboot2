## 写在前面
+ 任何一个对技术稍有追求的人都不愿意在复制粘贴中来消耗自己对编码的热情。编程注定是一个长期学习的过程，一个项目组的基础代码库的建设正好给后入项目的一个学习的机会，同时也是建设者自我提升的一个过程。
+ 自工作以来，前后经历了不少项目，见识了技术大牛也遇见了复制粘贴的小白。不论实现方式如何，每个人的代码都有值得借鉴的地方，奈何个人不同阶段学习能力不足，无法逐一理解记录，遂期望于通过记录的方式以便日后学习。

## 项目启动请访问`http://localhost:8080/t/doc.html`,需要额外配置 `dev`文件。

## 目录
<details>
<summary>详细信息</summary>
	
* [`common-util-base`：常用工具](#common-util-base)
* [`common-util-dataSource`：基于`spring-boot`配置的多数据源自动注入、`mybatis`组件自动装配、`Druid`监控自动装配](#common-util-dataSource)
* [`common-util-mybatis`：基于`tk.mybatis.mapper.starter` 简单`sql`工具、`pagehelper-spring-boot-starter`分页工具](#common-util-mybatis)
* [`common-util-redis`：基于`spring-boot-starter-data-redis`工具，实现了key服务隔离](#common-util-redis)
* [`common-util-i18n`：基于`spring-message`的多语言解决方案](#common-util-i18n)
* [`common-util-mail`：基于spring-boot-starter-mail封装的邮件发送工具](#common-util-mail)
* [`starts-web-tool`：`spring-web`项目的基本信息配置](#starts-web-tool)
* [`starts-web-user`：基于`redis`的`token`无状态用户登录信息状态管理](#starts-web-user)
* [`starts-web-version`：基于注解的接口版本控制、隔离](#starts-web-version)

</details>

---

## 结构描述
项目分为三个子项目：`base-parent`、`common-util`、`starts`
###  base-parent
<details>
	
#### 描述
1. 定义了所有`maven`包的版本信息、`maven-plugin`常用的组件信息、`maven`配置信息。
2. 所有的工具包的`maven-parent`。
3. 定义了`web-base-parent`，该`prrent`为`web`项目继承的`maven-parent`模板。
4. 统一的`maven`版本管理，有效的避免不同`maven`组件包版本冲突、不同`maven`组件包版本不一致导致不必要包引用。
#### 规约
1. 系统中所有出现的公共组件`maven`版本信息 都应当在该目录中声明。
2. 所有项目`pom`都应继承`base-parent`或`web-base-parent`。
3. 应当避免在业务项目模块中二次声明`maven`组件版本信息。

</details>

### common-util
<details>
	
#### 描述
1. 基于`springBoot`实现了项目开发中常用工具。
2. 版本信息定义在`base-parent`，所有组件引用应该避免二次声明`maven`组件版本信息。
#### 规约
1. 该模块中的所有子项目都以`common-util-xx`命名。
2. 该模块中的所有子项目都必须是`commons`子模块。
</details>

### starts

<details>
	
#### 描述
1. 基于`springBoot` 实现了项目中开发中常用组件。
2. 版本信息定义在`base-parent`，所有组件引用应该避免二次声明`maven`组件版本信息。
#### 规约
1. 该模块中的所有子项目都以`start-xx`命名。
2. 该模块中的所有子项目都必须是`starts`子模块。
3. 该模块中的组件应当区分于`commons`包：开发无感知且不需要通过声明`bean`使用的组件。

</details>

---

## commons
### common-util-base
<details>
<summary>常用工具</summary>

#### 描述
1. 常用的`util`工具，该模块下的工具一般都是静态方法。
##### collection
实现了一个简单的`HashMap.builder`工具。
```java  
HashMap<String, String> hashMap = HashMapBuilder.<String, String>newBuilder()
                    .put("key", "value").build();
```

##### date
常用的时间格式化工具
```java
System.out.println(DateFormatUtil.DEFAULT_FORMAT.format(new Date()));
--- 2019-07-15 18:48:48
```

##### enumUtil
枚举工具
+ `EnumUtilInterface` 定义了接口，使用该工具的枚举类必须实现该接口。

  ```java
	  @AllArgsConstructor
	  @Getter
	  enum TestEnum implements EnumUtilInterface {
	      TestEnumOne(1, "name-1"),
	      TestEnumTwo(2, "name-2");
	      private Integer code;
	      private String name;

	      @Override
	      public int getKey() {
		  return code;
	      }

	      @Override
	      public String getValue() {
		  return name;
	      }
	  }

	  ---
	  TestEnum anEnum = EnumUtil.getEnum(1, TestEnum.class);
	  Assert.assertTrue(TestEnum.TestEnumOne.equals(anEnum)); --true
	  Assert.assertTrue(anEnum.is(1)); -- true
  ```

##### phone
+ 基于谷歌的开源包封装的手机号归属定查询、手机号验证工具。
+ 有关手机号的强规则验证，建议使用该工具包。
  ```java
  PhoneUtilBo phoneBoInfo = PhoneUtil.getPhoneBoInfo("15120052168");
  ---
  PhoneUtilBo(provinceName=北京市, cityName=北京市, carrier=中国移动)
  ```

##### poi
+ `List`直接转化成`Excel`工具。
  ```java
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public class PoiModel {
      @ExcelField(name = "名称", column = "A")
      private String name;

      @ExcelField(name = "年龄", column = "B")
      private Integer age;
  }

  ---
  ArrayList<PoiModel> poiModelArrayList = Lists.newArrayList(
          new PoiModel("name-1", 1),
          new PoiModel("name-2", 2));
  ExcelUtil<PoiModel> poiModelExcelUtil = new ExcelUtil<>(PoiModel.class);
  File file = new File("/Users/xxx/test.xls");
  //导出
  try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      poiModelExcelUtil.exportExcel(poiModelArrayList, "sheet1", fileOutputStream);
  }

  //导入
  try(FileInputStream fileInputStream = new FileInputStream(file)){
      List<PoiModel> poiModelList = poiModelExcelUtil.importBatch(fileInputStream);
      System.out.println(poiModelArrayList.toString());
  }

  ---
  //web接口 文件上传 excel 示例
  @PostMapping
  public void test(@RequestParam("file") MultipartFile file) throws Exception {
      try (InputStream inputStream = file.getInputStream()) {
          ExcelUtil<PoiModel> modelExcelUtil = new ExcelUtil<>(PoiModel.class);
          List<PoiModel> poiModelList = modelExcelUtil.importExcel(inputStream);
          System.out.println(poiModelList.toString());
      }
  }
  ```
</details>

---
### common-util-dataSource

<details>
<summary>基于spring-boot配置的多数据源自动注入、mybatis组件自动装配、Druid监控自动装配</summary>

#### 描述
+ `dataSource` 提供了基于`spring-boot`配置的多自动数据源注入、`mybatis`组件自动装配、`Druid`监控自动装配。
+ 该模块依赖`com.alibaba.druid`：阿里的数据库连击工具。
+ 该模块依赖`tk.mybatis.mapper-spring-boot-starter`：`mybatis`增强工具，如果引入该模块之后不使用`mybatis`自动装配可排除。
#### 使用
使用该组件强烈建议排除`spring-DataSourceAutoConfiguration`
  ```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
  ```
在`yml`加入如下最简配置：

```yml
spring:
  datasource:
    ### 数据源
    autoDataSource: enable
    ### 监控
    monitor:
      enable: false
    ### mybatis自动配置
    mybatis:
      enable: true
    url: xx
    username: xx
    password: xx
    dynamicDataSource:
      read:
        url: xx
        username: xxx
        password: xx
      write:
        url: xxx
        username: xxx
        password: xxx
```
代码调用
  ```java
@DataSourceType("read")
public List<Demo> selectFromReadDataSource(String name) {
  return demoMapper.customizeSqlSelectByName(name);
}
---
"read" 对应配置文件中的名称，此处建议声明为静态常量。
  ```


1. `autoDataSource : enable`：是否开启`dataSource`自动注入，默认`true`开启，开启该配置，会在启动时候根据配置的数据源信息注入**两个`bean`**
	+ `DataSource(name = masterDataSource)`：`DruidDataSource`对象，对应配置信息为`spring.datasource.url`指定的数据源信息。
	+ `MultiDataSources(name = multiDataSources)`：包含一个字段`Map<String,DataSource>`：`key`为 `dynamicDataSource` 中数据源名称，`DatsSource`为`DruidDataSource`对象，对应配置信息为各个数据源指定的配置信息。
	
2. `monitor : enable`：是否`DruidDatsSource`监控，默认`true`开启，非`true` 关闭，开启该配置，会在项目中提供`druid`的监控服务，包括`sql`监控、`web`请求监控，由于该信息写在内存中，所以会随项目启动被清除，也可以扩展接入日志持久化，具体信息可以参考`Druid`官方。
	+ 开启之后访问路径为：`http://host:port/druid`。
	+ 该配置提供自定义配置文件，对应类信息为：`MultiDruidProperties.MonitorProperties`，配置信息如下所示：
	```yml
	spring:
	  datasource:
	    monitor:
	      enable: true
	      ### 白名单
	      allow: xxxx
	      ...
	```
	**强烈建议在线上服务中配置 白名单、黑名单，不要把该地址在对公网开放**。
	
3. `mybatis : enable`：是否开启`mybatis`自动装备，默认`true`开启，开启该配置，会在启动时候根据配置信息注入`mybatis-SqlSessionFactory`、`mybatis-SqlSessionTemplate`、`mybatis-DataSourceTransactionManager`。
  
	+ **该配置只有在开启了`autoDataSource`才会生效。**
	+ 该配置会注册一个基于`AbstractRoutingDataSource`实现的多数据源`bean`, `DynamicDataSource(name = dynamicDataSource)`。
	+ 该配置会注册一个`Aop`，拦截对象为`@interface DataSourceType`。
	+ 存储本地`dataSource`的`ThreadLocal`为 `DynamicHandler`。

#### 扩展
1. 获取当前系统中的注册的`dataSource`对象。
  ```java
  //获取 主数据源bean
  @Autowired @Qualifier(value = Common.MASTER_DATA_SOURCE_NAME)
  private DataSource dataSource;

  //获取 多数据源配置信息
  @Autowired @Qualifier(value = Common.MULTI_DATA_SOURCE_NAME)
  private MultiDataSources multiDataSources;

  //获取 Mybatis配置信息
  ...
  ---
  Common：dataSource.common.Common
  ```
2. 不注入`mybatis`组件，即`mybatis: false`，依旧想使用`DynamicDataSource`。
  ```java
  @Import(value = {SelfDynamicAutoConfiguration.class, SelfDynamicAutoConfiguration.AspectComponent.class})
  @Configuration
  public class XxxxAutoConfiguration {
        ...
  }
  ```
3. 扩展`DynamicDataSource`多数据源`aop`
  ```java
  @Component
  @Aspect
  public class DynamicAspectTemplateComponent extends DynamicAspectTemplate {
      ...
  }
  ```
**特别注意：`mybatis`使用的数据源`bean`为 DynamicDataSource(nam = "dynamicDataSource"),该 数据源 基于`AbstractRoutingDataSource`,获取`key`方法如下**

  ```java
  public class DynamicDataSource extends AbstractRoutingDataSource {
      @Override
      protected Object determineCurrentLookupKey() {
          final String dataType = DynamicHandler.get();
          return dataType;
      }
  }
  ```
**如果 重写 `Aop`，务必保证在执行主体方法之前 `DynamicHandler`中已经设置了`DataSourceKey`。**

</details>

---
### common-util-mybatis

<details>
<summary>基于tk.mybatis.mapper.starter 简单sql工具、pagehelper-spring-boot-starter分页工具</summary>

#### 描述
+ 提供基于`tk.mybatis.mapper.starter` 简单`sql`工具、`pagehelper-spring-boot-starter`分页工具。
+ 建议配合`common-util-dataSource`一起使用，可以快速实现多数据源、持久层集成。
+ 该组件未提供`mybatis`相关的`bean`装配信息，可以自行定义。
#### 使用
该工具提供了两套工具，可以根据当前的业务需求自行选择。
+ 实体`Entity extend BaseEntityOnlyId`：该类型，工具只会自动处理`id`、`createTime`：创建时间、`modifyTime`：修改时间。
+ 实体`Entity extend BaseEntity`：该类型，工具会额外维护`status`: 数据状态、
	`creatorId`：创建人`ID`、`modifyId`：修改人`ID`。
+ **如果项目中只有逻辑删除，不做物理删除，请使用`BaseEntity`，提供了一套完整的 逻辑`CRUD`操作**

1. 定义`mybatis-mapper`扫描包路径
  ```java
  @Configuration
  @MapperScan(basePackages = {DataSourceCommon.MAPPER_PATH, DynamicMapperPackage.DYNAMIC_PACKAGE_PATH})
  public class DataSourceCommon {
      /**
       * 定义Mapper包路径
       */
      public static final String MAPPER_PATH = "xxx.mapper";
  }
  ---
  DynamicMapperPackage.DYNAMIC_PACKAGE_PATH 为 自定义扩展的 sql 工具，此处必须声明。
  ```
2. 声明`Entity`
  ```java
  @Data
  @EqualsAndHashCode(callSuper = true)
  @Table(name = "demo")
  public class Demo extends BaseEntity {
      /**
       * 测试 - 名称 当数据库字段与实体字段不一致时候
       */
      @Column(name = "demo_name")
      private String name;

      /**
       * 示例 - 字符 当数据库字段与实体字段一致
       */
      private Integer demoNum;
  }
  ```
3. 声明`Mapper`
  ```java
  @Repository
  public interface DemoMapper extends Mapper<Demo> {
  }
  ```
4. 声明`service`
  ```java
  public interface DemoService extends BaseDecoratorService<Demo> {
  }
  ```
5. 声明`serviceImpl`
  ```java
  @Service
  @RequiredArgsConstructor
  public class DemoServiceImpl extends BaseDecoratorServiceImpl<Demo> implements DemoService {
  }
  ```
至此，在注入`@Autowired DemoService demoService`，即可调用基础的`CRUD`方法。

#### 扩展
1. 自定义`mybatis` 相关配置：根据官方自动配置即可，需要在`@MapperScan`中额外声明`DynamicMapperPackage.DYNAMIC_PACKAGE_PATH`路径。**此处建议使用`tk.mybatis.spring.annotation.MapperScan`**

2.  自定义`sql`语句
	+ 定义`SqlProvider`。
	
```java
  public class DemoSqlProvider {
	/**
	 * 自定义sql 根据 `name` 模糊查询
	 */
	public String customizeSqlSelectByName(@Param("name") final String name) {
	    String table = SqlHelper.getDynamicTableName(Demo.class, Demo.class.getAnnotation(Table.class).name());
	    String allColumns = SqlHelper.getAllColumns(Demo.class);
	    return new SQL() {{
		SELECT(allColumns);
		FROM(table);
		WHERE("demo_name like CONCAT('%',#{name},'%')");
	    }}.toString();
	}
      }
```
  + 在`Mapper`声明方法。
       ```java
        @Repository
        public interface DemoMapper extends Mapper<Demo> {
            /**
             * 自定义sql 根据 `name` 模糊查询
             */
            @SelectProvider(type = DemoSqlProvider.class, method = "customizeSqlSelectByName")
            List<Demo> customizeSqlSelectByName(@Param("name") String name);
        }
     ```

 3. 模糊查询语法支持和分页语法支持
 **如果自定义了`mybatis`组件信息，最好手动声明`PageHelper`组件注册，注册示例请参照`common-util-dataSource.SelfMybatisAutoConfiguration`**
     ```java
    WeekendSqls<Demo> demoWeekendSql = WeekendSqls.<Demo>custom()
            .andLike(Demo::getName, "%" + name + "%");
    Example example = Example.builder(Demo.class)
            .andWhere(demoWeekendSql)
            .build();
    PageInfo<Demo> pageInfo = PageHelper.startPage(pageNum, pageSize)
            .doSelectPageInfo(() -> demoService.selectByExample(example));
     ```
     
</details>

---
### common-util-redis

<details>
<summary>基于spring-boot-starter-data-redis工具，实现了key服务隔离</summary>

#### 描述

+ 提供基于`spring-boot-starter-data-redis`工具，实现了`key`服务隔离。
+ 提供基于`reids`实现的分布式锁。

#### 使用
1. 配置`redis`配置信息，与`spring-boot-starter-data-redis`官方配置相同。
2. 配置`key`隔离前缀，优先匹配`spring.redis.prefix`，如果不存在，会使用`application-name`，如果依旧不存在会使用**`unknown`**。
  ```yml
  spring:
    redis:
      prefix: prefix
    application:
      name: serverName
  ```
3. 使用`redisTemplate`
    ```java
    @Autowired private RedisTemplate redisTemplate
    ValueOperations valueOperations = redisTemplate.opsForValue();
    valueOperations.set(key, value);
    ---
    redis DB 存储信息为: prefix.key : value
    ```
4. 使用不带有`prefix`的`redisTemplate`
  ```java
  @Autowired @Qualifier(Common.REDIS_TEMPLATE_WITHOUT_PREFIX_NAME)
  private RedisTemplate redisTemplate;
  ---
  Common为：common-util-redis.common.Common
  ```
5. 使用`redis` 锁
  ```java
  @Autowired private DistributedLock distributedLock;

  final String lockKey = "lockKey";
  distributedLock.lock(lockKey);
  .....
  distributedLock.releaseLock(lockKey);
  ```
**加锁、释放锁必须在同一线程中进行，否则会释放锁失败**

#### 扩展
暂无

</details>

---
### common-util-i18n

<details>
<summary>基于spring-message的多语言解决方案</summary>

#### 描述
+ 提供了基于`spring-message`的多语言解决方案。
+ 扩展了`spring-message`语言检索策略。
+ 提供了**正常业务**、**枚举类**、**数据字典** 多语言工具。
+ 提供了一套多语言文件分包配置的解决方案。
+ 提供了一套多人配置`key`冲突解决方案，可通过配置优先级来保证当前业务模块的配置`code`与其他模块冲突后优先使用该模块对应的配置信息。**该功能，在多人协同开发中特别好用！！！**
+ **该模块的设计的初衷是为了让业务开发时，不用去关注语言信息，避免在业务代码中显示声明语言类型导致业务代码污染，也不利于后续代码的扩展与迁移，故此提供一套基于`Aop`的多语言工具。**


#### 使用
1.  配置文件中开启`message`注入，默认开启，此步骤可以跳过。
  ```yml
  spring:
    i18n: enable
  ```
2. 声明多语言文件信息：**固定目录 `resources/i18n`**
	+ 2.1 在该目录下声明多语言配置文件：`i18n.properties`
    
    ```yml
    i18n.useCodeAsDefaultMessage = true
    i18n.headKey = LOCALE
    i18n.defaultLocale = zh_CN
    i18n.file-path = demo
    ```
	  `useCodeAsDefaultMessage`：当在多语言文件中没有找到语言信息时是否把`code`值作为返回值，默认为` false`，系统会在找不到对应值之后抛出异常。
    `headKey`:  请求`head`中存放语言信息的`key`，默认：`LOCALE`。
    `defaultLocale`: 默认语言信息，如果从头信息中没有获取到语言信息使用该值，默认：`zh_CN`。
    **`file-path`：多语言文件目录，必须配置**。
  + 2.2 在该目录下声明各个业务多语言配置文件信息。如：新建文件夹`resources/i18n/demo`。新建文件`messages.properties`、`messages_en_US.properties`、`messages_zh_CN.properties`。`xxx.properties`中配置如下：
		
  	```yml
  	test = testValue  -- en_US
  	test = 测试 -- zh_CN
  	```
3. 在请求头中 添加`LOCALE:zh_CN`
4. 正常业务有关多语言调用
  ```java
  I18nSourceUtil.INSTANCE.getMessage(key);
  
  key 为 语言文件中配置的 key,
  ```
5. 枚举类有关多语言调用
	```java
	@AllArgsConstructor
	@Getter
	public enum TestI18nEnum implements I18nEnumInterface {
		TEST_ONE("testOne"),
		TEST_TWO("testTwo");

		private String name;

		@Override
		public String getI18nCode() {
		  return name;
		}

		@Override
		public String getI18nKey() {
		  return name;
		}
	}


	TestI18nEnum.TEST_ONE.getI8nMessage(); -- 可以获取 语言文件中 getI18nKey 返回 code 对应的 value 值。

	I18nEnumInterface.as18nList(TestI18nEnum.class); -- 可以把该枚举转换成 key-value 的 list,常用于返回给客户端下拉列表。
	```
6. 数据字典有关多语言调用。
由于各个不同的业务数据字典设计可能各不相同，在此只是个人的一种设计，不一定是最优方法。
在数据字典中，常用的存储结构是`code`、`name`。但是当出现多语言后，可能会配置多个`name`,但是这种情况不太利于代码扩展，*且在连表查询中有非常大的困难*。个人设想是 所有`name`全部存储在一个字段中，在获取到`name`之后再解析出与当前请求的语言信息相对应的`name`。
  ```java
  I18nFormatValue messageChina = I18nFormatValue.of(Locale.CHINA, "信息");
  I18nFormatValue messageUS = I18nFormatValue.of(Locale.US, "message");
  String format = I18nDBUtil.INSTANCE.format(messageChina, messageUS);
  //在 name 中存储 该 format 信息。

  ---
  //获取对应的语言信息
  I18nDBUtil.INSTANCE.getI18nValue(format);
  ```
#### 扩展
1.  该模块通过注册了一个拦截器在`head`中获取的语言信息，可以自定义拦截器来覆盖默认拦截器。**请务必保证重写的`beanName`为`Common.I18N_INTERCEPTOR_NAME`，务必保证进入方法之前`I18nResourceHandler`有语言信息。**
	```java
		@Component(value = Common.I18N_INTERCEPTOR_NAME)
		public class I18nInterceptorConfiguration implements HandlerInterceptor {

		    @Override
		    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
			I18nResourceHandler.setInfo(Common.DEFAULT_LOCALE);
			//此处必须返回true 保证后续继续可执行
			return true;
		    }

		    @Override
		    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
			I18nResourceHandler.clean();
		    }
		}
	```
2.  该模块通过`Aop`来确认当前线程优先检索哪个语言文件，**如果想保证优先检索某个文件，处理在重名情况下优先使用该目录信息，务必关注。`spring` 会默认从第一个文件开始查找，找到即返回。**默认的`Aop`切面为`@annotation(xxx.i18n.core.I18nHandler.I18nFolderName)`。不扩展此`Aop`写法为:
  ```java
  @GetMapping
  @Override
  @I18nFolderName("demo")
  public Result getMessageByKey(String key) {
      String message = I18nSourceUtil.INSTANCE.getMessage(key);
      return ResultSuccess.of(message);
  }
  ```
  ` @I18nFolderName("demo")` 也可以直接配置当前类上面。
   配置代码如下：
    ```java
    @I18nFolderName("demo")
    public class I18nController(){}
    ```
   但是这种情况配置默认的`Aop`就无法生效，建议按照如下扩展：
  
    ```java
	    @Aspect
	    @Component
	    public class I18nAspectTemplateComponent extends AbstractI18nAspectTemplate {
		@Override
		@Pointcut("execution(* xxx.controller.*.*(..))")
		public void enablePath() { }
	    }
    ```
    
</details>

---
### common-util-mail

<details>
<summary>基于spring-boot-starter-mail封装的邮件发送工具</summary>
	
#### 描述
+ 提供了基于`spring-boot-starter-mail`封装的邮件发送工具。
+ 该工具使用了全异步处理事件。
+ 提供了基础邮件发送、模板邮件发送、附件类邮件发送、文件流类型附件发送工具方法。
#### 使用
1. 在yml文件配置邮件发送者信息,完整配置信息参考`mail.config.MailProperties`,最小配置如下:
  ```yml
  spring:
      mail:
        ## 邮箱地址
        username: xxx
        ## 邮箱密码
        password: xxxx
  ```
2. 注入当前发送邮件`bean`
  ```java
  @Autowired  MailSenderTemplate mailSenderTemplate;
  ```
3. 发送简单邮件
  ```java
  SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
  simpleMailMessage.setToUserList(Lists.newArrayList("xxxx"));
  simpleMailMessage.setContent("内容");
  simpleMailMessage.setSubject("主题");
  mailSenderTemplate.sendSimpleMail(simpleMailMessage);
  ```
4. 发送模板邮件，该功能模板使用的是`springFreeMark`模板引擎，具体配置参考`mail.config.MailProperties.MailFreeMarkConfigurer`,默认模板目录：`classpath:/templates/`
	4.1. 配置模板信息：`message.ftl`
    ```html
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>消息通知</title>
    </head>
    <body>
    <div>
        <h2>邮件消息通知1</h2>
        <p>${username}</p>
    </div>
    </body>
    </html>
    ```
	4.2. 发送模板邮件
    ```java
    TemplateSimpleMailMessage templateSimpleMailMessage = new TemplateSimpleMailMessage();
    templateSimpleMailMessage.setToUserList(Lists.newArrayList("xxxx"));
    templateSimpleMailMessage.setContent("内容");
    templateSimpleMailMessage.setSubject("主题");
    templateSimpleMailMessage.setTemplateName("message.ftl");
    HashMap<String, Object> data = HashMapBuilder.<String, Object>newBuilder()
            .put("username", "示例名称")
            .build();
    templateSimpleMailMessage.setData(data);
    mailSenderTemplate.sendTemplateMail(templateSimpleMailMessage);
    ```
5. 发送附件邮件，此处通过文件流的形式发送 附件`excel`。
  ```java
  AttachmentStreamMailMessage streamMailMessage = new AttachmentStreamMailMessage();
  streamMailMessage.setToUserList(Lists.newArrayList("xxxx.com"));
  streamMailMessage.setContent("内容");
  streamMailMessage.setSubject("主题");
  //构建文件流
  ArrayList<MailBo> mailBoArrayList = Lists.newArrayList(
          new MailBo("name-1", 1),
          new MailBo("name-2", 2));
  ExcelUtil<MailBo> mailBoExcelUtil = new ExcelUtil<>(MailBo.class);

  try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024)) {
      mailBoExcelUtil.exportExcel(mailBoArrayList, "sheetName", byteArrayOutputStream);
      InputStreamSource inputStreamSource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
      //构建邮件发送需要的文件流
      AttachmentStreamMailMessage.AttachmentStream attachmentStream = AttachmentStreamMailMessage.AttachmentStream.builder()
              .fileName("附件名称.xls")
              .inputStreamSource(inputStreamSource)
              .build();
      streamMailMessage.setAttachmentStreamList(Lists.newArrayList(attachmentStream));
  } catch (IOException e) {
      e.printStackTrace();
  }
  mailSenderTemplate.sendAttachmentStreamMail(streamMailMessage);
  ```
#### 扩展
1.  在一个项目中可能会存在多个邮件发送业务，这些业务会用到不同的通知邮箱，系统配置只提供了一个`bean`，这种情况需要再额外生成`bean`。构建`bean 的方式如下：
  ```java
  @Bean(Common.JAVA_MAIL_FREEMARKER_CONFIGURER)
  public FreeMarkerConfigurer freeMarkerConfigurer() {
      MailProperties.MailFreeMarkConfigurer freeMark = mailProperties.getFreeMark();
      FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
      freeMarkerConfigurer.setTemplateLoaderPath(freeMark.getTemplateLoaderPath());
      freeMarkerConfigurer.setDefaultEncoding(freeMark.getCharset());
      return freeMarkerConfigurer;
  }
  ---
  @Bean(Common.MAIL_SENDER_TEMPLATE)
  public MailSenderTemplate mailSenderTemplate(
          @Qualifier MailProperties mailProperties,
          @Qualifier(Common.JAVA_MAIL_SENDER) JavaMailSender javaMailSender,
          @Qualifier(Common.JAVA_MAIL_FREEMARKER_CONFIGURER) FreeMarkerConfigurer freeMarkerConfigurer) {
      return new MailSenderTemplateImpl(mailProperties, javaMailSender, freeMarkerConfigurer);
  }
  ```
`FreeMarkerConfigurer` 可以根据业务需求决定是否生成。`MailSenderTemplate`为邮件发送工具`bean`。使用如上构造方法构建`bean`即可注入一个其他 配置的`bean`。

</details>

---
## starts
### starts-web-tool

<details>
<summary>spring-web项目的基本信息配置</summary>

#### 描述
+ 提供了`spring-web`项目的基本信息配置。
+ 统一异常处理器。
+ 统一参数返回包装器。
+ 配置统一序列化信息。
+ 提供基础`restTemplate`
+ **该包在各个项目组中应该在项目开始时就定义好所有信息，且不建议该包进行扩展配置。**
#### 使用
1.  直接引入该包。
2. `controller` 中返回正常的`Object`对象会被包装成
  ```json
  {
      "code": 1000000,
      "message": "操作成功",
      "data": {}
  }
  ```
3. `controller` 调用`void`方法，返回。
  ```
  ResultSuccess.defaultResultSuccess();
  ```
4. 业务异常处理
  ```
  throw BizException.builder().message("错误信息").resultFail(ResultFail.of(500)).build();
  ```
#### 扩展
1. 自定义异常处理。异常处理提供了两个扩展`bean`：`ExceptionResolver`异常信息接受器，该`bean`可以注册多个，捕获异常后会根据`order`排序依次调用所有`bean`的处理方法。`ExceptionResultHandler` 异常结果处理器，该`bean`只可以注册一	个，在所有`ExceptionResolver`处理完成之后 调用该`bean`方法。系统现在默认提供：
	+ `DefaultExceptionResultHandler`：默认异常结果处理，**该`bean`不建议覆盖**。
	
	+ `DefaultBizExceptionResolver`：业务异常接收器。

  	+ `DefaultRuntimeExceptionResolver`：系统异常接收器。
  
    自定义异常接收器：
  ```java
  @Configuration
  @Slf4j
  public class ExceptionLog implements ExceptionResolver {
      @Override
      public void resolve(HttpServletRequest request, Exception exception) {
        String prefix = exception instanceof BizException ? "【业务异常】" : "【系统异常】";
          log.error(prefix + ExceptionUtils.getStackTrace(exception));
      }
  
      @Override
      public boolean canResolve(HttpServletRequest request, Exception exception, HttpStatus httpStatus) {
          return exception instanceof BizException || exception instanceof RuntimeException;
      }
  }
  ```
2. 扩展异常处理器，如果系统提供的异常处理规则不满足 当前项目时候可以扩展处理，也可以通过扩展`ExceptionResultHandler`来达到相同的效果。
  ```java
  @Configuration
  @Order(Ordered.HIGHEST_PRECEDENCE + 101)
  public class ExceptionAdviceConfiguration extends ExceptionAdvice {

      @ExceptionHandler({HttpRequestMethodNotSupportedException.class, NoHandlerFoundException.class})
      @Override
      public Result notFoundHandler(HttpServletRequest request, Exception exception) {
          return ResultSuccess.defaultResultSuccess();
      }
  }
  ```
3. 扩展参数解析器，如果当前项目中集成了其他的`web`项目，例如`swagger`，统一的增强结果封装也会封装这类的请求接口从而导致`web`组件失效，此时可以通过扩展参数解析来解决。
  ```java
  @RestControllerAdvice
  public class ResponseAdviceConfiguration extends AbstractResponseAdviceTemplate {

      //定义需要 返回结果需要被封装的 包路径 建议直接返回`controller`路径
      private static final List<String> SUPPORT_PATH = Lists.newArrayList("com.xx.controller");

      @Override
      public List<String> supportPath() {
          return SUPPORT_PATH;
      }
  }

  
  如果如上依旧不能满足需求，可以考虑重写 supports 方法
  ```
  
</details>

---
### starts-web-user

<details>
<summary>基于redis的token无状态用户登录信息状态管理</summary>

#### 描述
+ 提供了一个基于`redis`的`token`无状态用户登录信息状态管理。
+ 提供了基于注解的`userToken`信息转换。
+ 提供了`userToken`自动校验、过期处理、单点互踢等功能。
+ 实现了一个基于`lettuce`的简单`redisTemplate`。

#### 使用
1.  考虑在生成环境中用户登录信息`redis`一般会与业务分开，在`yml`文件中配置`user-redis`使用的`redis`信息。
  ```yml
  user-sessions:
    redis:
      password:
      cluster:
        nodes: 
  ```
`user-sessions.redis.tokenExpireSecond`：`token` 生存周期，默认 90天。
`user-sessions.redis.oldTokenExpireSecond`：当前`token`被踢后在系统中保留时间，默认 7 天。

2. 在业务登录、登出接口中植入相关的`user-redis-session`代码
	+ 2.1 登录逻辑
    ```java
    //处理正常登录逻辑之后之后
    UserBusinessBo businessBo = UserBusinessBo.builder()
            .mobile(mobile)
            .userId(userId)
            .build();
    String token = userLoginSessionService.buildUserTokenAfterLogin(businessBo);
    ```
	+ 2.2 登出逻辑
    ```java
    //处理正常登出逻辑之后
    userLoginSessionService.cleanUserTokenAfterLoginOut(token);
    ```
  
3. 在`Controller`获取用户信息
	+ 3.1 简单获取`userId`
    
    ```java
      test(@UserToken Long userId){}
      ---
      可以直接获取 userId.
    ```
  + 3.2 获取用户信息`UserSession`
    
    ```java
    test(@UserToken UserSession userSession){}
    ---
    可以直接获取 UserSession.对象
	  ```
	  **不建议在业务代码中直接从本地线程中获取`userId`，个人建议在`service`中显示声明，保证接口的语义明确**
	  
#### 扩展
  1. 扩展异常提示信息，组件默认提供了不同情况下的异常信息提示，可以根据实际情况自定义异常信息提示。
  ```java
    @Slf4j
    @Component
    @RequiredArgsConstructor
    public class UserIllegalTokenHandleDefaultServiceImpl implements UserIllegalTokenHandleService {

	/**
	 * Head 头中没有 token 信息
	 */
	@Override
	public void assertAndHandleNoTokenHead() {
	  throw new TokenBizException("head miss token info");
	}

	/**
	 * 当前 token 不存在
	 */
	@Override
	public void assertAndHandleNoTokenInfo() {
	  throw new TokenBizException("Token information is lost");
	}

	/**
	 * 当前 token 已经过期
	 * @param oldUserSession 过期用户信息
	 */
	@Override
	public void assertAndHandOldToken(UserSessionRedis oldUserSession) {
	    throw new TokenBizException("Your account is logged in elsewhere");
      }
    }
  ``` 
 </details>
 
---
### starts-web-version
<details>
<summary>基于注解的Api接口版本管理,实现不同客户端接口隔离</summary>

#### 描述
+ 提供了基于注解的接口版本控制器功能。
+ 实现了多终端接口隔离。
+ 提供多种可选注解，可以灵活自由搭配。
+ 该实现中需要使用`Head`公共信息，`Head`公共信息在`HeadCommon`中与客户端约定好。
#### 使用
1. 开启版本控制，`spring.version.enable:true`默认开启。
2. 类级别控制。
```java
@RestController
@RequestMapping("apiVersion")
@RequiredArgsConstructor
@ApiVersion("v2")
public class ApiVersionController(){
    ...
}
```
 + 添加注解 `@ApiVersion("v2")`。
 + 设置当前类下所有接口访问的最低版本信息，与方法上的配置 为 "&&"关系，如例所示: 该类最低版本未"V2",对应：VERSION 参数 `version.compareTo("2") >= 0`。
 + 该配置会在请求路径中自动生成版本信息，因此该类的请求路径为 apiVersion/xx -> v2/apiVersion/xx。
 + 兼容模式：2 、V2 等价：V2。

3. 方法级别控制
```java
/**
 * 方法版本控制
 * 该接口只能在 渠道为 1,3 的客户端生效
 */
@GetMapping("apiForMethod")
@ApiClientInfo(channel = @ApiClientChannel(value = "1,3", operator = VersionOperator.IN))
public Result apiForMethod() {
	return ResultSuccess.defaultResultSuccess();
}
```
```java
/**
 * 方法版本控制
 * 该接口只有在 客户端版本 > 2.2，渠道号为 1001、1002 ,来源非 IOS 情况下生效
 */
@GetMapping("apiForTypeAndMethod")
@ApiClientInfo(
	version = @ApiClientVersion(value = "2.2", operator = VersionOperator.GT),
	channel = @ApiClientChannel(value = "1001,1002", operator = VersionOperator.IN),
	platform = @ApiClientPlatform(value = "ios", operator = VersionOperator.NE)
)
public Result apiForTypeAndMethod() {
	return ResultSuccess.defaultResultSuccess();
}
```
+ 该配置为方法上版本、渠道、来源限制。三个条件为 "&&" 关系
+ `version`: 配置该接口版本信息，与类上配置 "&&" 关系，如例所示，该接口实际对应版本信息为: `>2.2`
+ `channel`: 配置该接口渠道信息，如例所示，该接口对应渠道为：1001 || 1002, 不区分大小写
+ `platform`:配置该接口来源信息，如例所示，该接口对应来源为：ios，不区分大小写

#### 扩展
暂无
</details>

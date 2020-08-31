galaxy ua parser Java Library
======================

此项目根据开源项目地址(https://github.com/ua-parser)基础上改编。解析网络撒花姑娘常见的user-agent 。
这是ua-parser的Java实现。该实现使用共享的正则表达式模式并覆盖regexes.yaml。

environment:
------
```
    * JRE环境尽量使用JDK 1.8 或更高版本
    * maven 3.3.9 或更高版本
```

maven:
--------

```
    <dependency>
        <groupId>com.zdjizhi</groupId>
        <artifactId>galaxy-ua-parser</artifactId>
        <version>1.0.0</version>
    </dependency>
```
Usage:
--------
```java


...

  String uaString = "Mozilla/5.0 (Linux; U; android 2.3.7; en-us; Nexus One Build/FRF91) " +
   "AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

  Parser uaParser = new Parser();
  UserAgent u = uaParser.parse(uaString);

  System.out.println(u.client.name); // => "Safari"
  System.out.println(u.client.type);  // => "Browser"
  System.out.println(u.client.engine);  // => "WebKit"
  System.out.println(u.client.engineVersion);  // => "533.1"

  System.out.println(c.os.name);        // => "Android"
  System.out.println(c.os.version);         // => "2"
  System.out.println(c.os.platform);         // => "HTC Nexus One"

  System.out.println(c.device.name);    // => "HTC Nexus One"
  System.out.println(c.device.brand);    // => "HTC"
  System.out.println(c.device.model);    // => "Nexus One"
  System.out.println(c.device.type);    // => "MOBILE"
```  

version
-------
版本信息查询[说明](https://git.mesalab.cn/galaxy/galaxy-ua-parser.git).

repository:
-------
目前工具类库未支持外网，maven构建时请自行添加局域网仓库URL，具体如下： 
nexus 
Team Nexus Repository 
http://192.168.10.125:8099/content/repositories/galaxy-ua-parser 

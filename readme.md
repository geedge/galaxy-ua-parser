ua_parser Java Library
======================

This is the Java implementation of [ua-parser](https://github.com/ua-parser).
The implementation uses the shared regex patterns and overrides from [regexes.yaml](https://github.com/ua-parser/uap-core/blob/master/regexes.yaml).

Build:
------

uap-java depends on the uap-core project therefore it uses a Git submodule to represent that dependency.  
Before building uap-java, a copy of the uap-core project must be checked out within the local uap-java repository.  
In order to do this, execute the following command (from the base `uap-java` folder) to initialize and checkout the submodule.  

```
git submodule update --init --remote --checkout --recursive
```

you will then have the following folder `uap-java/uap-core` which contains the child repository.

To build the project, execute
```
mvn package

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

  String uaString = "Mozilla/5.0 (Linux; U; android 2.3.7; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

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

## Publish to Sonatype OSSRH and Maven Central Repository

Create a ~/.m2/settings.xml file with the following contents:
```
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>YOUR_OSSRH_USERNAME</username>
      <password>YOUR_OSSRH_PASSWORD</password>
    </server>
  </servers>

  <profiles>
    <profile>
      <id>ossrh</id>
      <properties>
        <gpg.keyname>YOUR_GPG_KEY_ID</gpg.keyname>
        <gpg.executable>GPG_EXECUTABLE</gpg.executable>
        <gpg.passphrase>YOUR_KEY_PASSPHRASE</gpg.passphrase>
      </properties>
    </profile>
  </profiles>
</settings>
```
On Mac OS X, the `gpg.executable` property should be `gpg2`.  

### Instructions for Build/Deploy/Release  
1. bump pom.xml version to non-snapshot version  
1. commit and push to github  
1. `mvn clean deploy -P ossrh`  
1. log in to [Sonatype OSSRH](https://oss.sonatype.org/)  
1. click Staging Repositories  
1. find the release `comgithubua-parser-*`  
1. verify the contents are ok  
1. click Close (wait for validation steps under the Activity tab to complete)  
1. click Release  
1. bump pom.xml version to next snapshot version  
1. commit and push to GitHub  
1. update changelog wiki page  

### Additional Resources for Deploying to Sonatype OSSRH and Maven Central Repository    
[Deploying to Sonatype OSSRH using Maven](http://central.sonatype.org/pages/apache-maven.html)  
[Releasing the artifact in Sonatype OSSRH](http://central.sonatype.org/pages/releasing-the-deployment.html)  

Sonatype OSSRH is synced with Maven Central Repository so the artifacts will appear in Maven Central Repo
automatically shortly after releasing.  

### Changelog
Changelog can be found [here](https://github.com/ua-parser/uap-java/wiki#changelog).

Author:
-------

  * Steve Jiang [@zdjz](http://www.zdjizhi.com/)

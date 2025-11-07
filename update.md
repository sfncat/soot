# update dexlib2
from 2.5.2 to 3.0.9
### compile dexlib2
```
git clone https://github.com/google/smali.git
cd smali
./gradlew smali:fatJar
./gradlew baksmali:fatJar
```
output dir
```bazaar
dexlib2/build/libs
```
### install dexlib2
```
mvn install:install-file -Dfile=./dexlib2/build/libs/dexlib2-3.0.9-dev.jar -DgroupId=com.android.tools.smali -DartifactId=dexlib2 -Dversion=3.0.9-dev -Dpackaging=jar
```

### update pox.xml
```bazaar
        <dependency>
            <groupId>com.android.tools.smali</groupId>
            <artifactId>dexlib2</artifactId>
            <version>3.0.9-dev</version>
        </dependency>
               <repository>
            <id>google</id>
            <url>https://maven.google.com/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
```
### replace code
replace all 'org.jf.dexlib2' to 'com.android.tools.smali.dexlib2' 

## compile soot and install in soot directory
compile a fat jar
mvn clean compile assembly:single
mvn install:install-file -Dfile="./target/sootclasses-trunk-jar-with-dependencies.jar"  -DpomFile="pom.xml"
## install by maven
```bazaar
mvn install -Dcheckstyle.skip=true -DskipTests
```
## install soot by file
```bazaar
copy sootclasses-trunk-jar-with-dependencies.jar and pom.xml
mvn install:install-file -Dfile="sootclasses-trunk-jar-with-dependencies.jar"  -DpomFile="pom.xml"

```
## use soot
```bazaar
        <dependency>
            <groupId>org.soot-oss</groupId>
            <artifactId>soot</artifactId>
            <version>4.7.0-SNAPSHOT</version>
        </dependency>
```

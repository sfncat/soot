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
mvn install:install-file -Dfile=./dexlib2/build/libs/dexlib2-3.0.9-dev.jar -DgroupId=com.android.tools.smali -DartifactId=smali-dexlib2 -Dversion=3.0.9 -Dpackaging=jar
```

### update pox.xml
```bazaar
        <dependency>
            <groupId>com.android.tools.smali</groupId>
            <artifactId>smali-dexlib2</artifactId>
            <version>3.0.9</version>
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

## compile soot

mvn clean compile assembly:single
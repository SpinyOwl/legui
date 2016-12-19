_**(Work in progress)**_
___
# [LEGUI - What is it?](https://liquidengine.github.io/legui/)  
GUI implementation for using with LWJGL3  

To contribute you can add your proposals in **issues**  

#### Dependencies
For using this library you should add these urls as repositories:  
`https://oss.sonatype.org/content/repositories/snapshots/` - for **[LWJGL](https://github.com/LWJGL/lwjgl3)** and **[JOML](https://github.com/JOML-CI/JOML)** dependencies

`https://raw.github.com/LiquidEngine/repo/releases` or  
`https://raw.github.com/LiquidEngine/repo/snapshots` for **[CBCHAIN](https://github.com/LiquidEngine/cbchain)** dependency

And add this dependency to your build script:  
### Maven:
 ```xml
<!-- LEGUI - SNAPSHOT - https://github.com/LiquidEngine/legui -->
<dependency>
    <groupId>org.liquidengine</groupId>
    <artifactId>legui</artifactId>
    <version>${legui_version}-SNAPSHOT</version>
</dependency>
 ```
 ```xml
<!-- LEGUI - RELEASE - https://github.com/LiquidEngine/legui -->
<dependency>
    <groupId>org.liquidengine</groupId>
    <artifactId>legui</artifactId>
    <version>${legui_version}</version>
</dependency>
 ```
### Gradle:
  ```groovy
// LEGUI - SNAPSHOT - https://github.com/LiquidEngine/legui
compile group: 'org.liquidengine', name: 'legui', version: legui_version + '-SNAPSHOT', changing: true;
  ```
  ```groovy
// LEGUI - RELEASE - https://github.com/LiquidEngine/legui
compile group: 'org.liquidengine', name: 'legui', version: legui_version, changing: true;
  ```
  
  Current `SNAPSHOT` version you can find in [SNAPSHOT version.properties](https://github.com/LiquidEngine/legui/blob/snapshots/version.properties)  
  Current `RELEASE` version you can find in [RELEASE version.properties](https://github.com/LiquidEngine/legui/blob/releases/version.properties)  

## Links
-------------------------------
[LWJGL - Lightweight Java Game Library 3](https://github.com/LWJGL/lwjgl3)

[JOML – Java OpenGL Math Library](https://github.com/JOML-CI/JOML)

[CBCHAIN - Callback Chain for LWJGL3](https://github.com/LiquidEngine/cbchain)

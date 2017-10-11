<table>
  <tr>
    <th align="center">Size</th>
    <th align="center">Codacy</th>
    <th align="center">Develop</th>
    <th align="center">Snapshot</th>
    <th align="center">Release</th>
  </tr>
  <tr>
    <td align="center"><img src="https://reposs.herokuapp.com/?path=LiquidEngine/legui"/></td>
    <td align="center"><a href="https://www.codacy.com/app/LiquidEngine/legui?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=LiquidEngine/legui&amp;utm_campaign=Badge_Grade"><img src="https://api.codacy.com/project/badge/Grade/e3a864cc6d6a4f08938a7b368bdb35da"/></a></td>
    <td align="center"><a href="http://jenkins.liquidengine.tk/job/LEGUI_DEVELOP/" target="_blank">
      <img src="http://jenkins.liquidengine.tk/buildStatus/icon?job=LEGUI_DEVELOP"/>
    </a></td>
    <td align="center"><a href="http://jenkins.liquidengine.tk/job/LEGUI_SNAPSHOTS/" target="_blank">
      <img src="http://jenkins.liquidengine.tk/buildStatus/icon?job=LEGUI_SNAPSHOTS"/>
    </a></td>
    <td align="center"><a href="http://jenkins.liquidengine.tk/job/LEGUI_RELEASES/" target="_blank">
      <img src="http://jenkins.liquidengine.tk/buildStatus/icon?job=LEGUI_RELEASES"/>
    </a></td>
  </tr>
</table>

_**(This is too early stuff and it would be implemented in little bit new way than previous version)**_  
_**(Work in progress)**_
___
# LEGUI - [What is it?](https://liquidengine.github.io/legui/)  
GUI implementation for using with LWJGL3.
Renderering implementation made on top of NanoVG.
<table>
  <tr>
    <td><img src="https://liquidengine.github.io/legui/images/demo/0.bmp" /></td>
    <td><img src="https://liquidengine.github.io/legui/images/demo/55.bmp" /></td>
    <td><img src="https://liquidengine.github.io/legui/images/demo/209.bmp" /></td>
  </tr>
  <tr>
    <td><img src="https://liquidengine.github.io/legui/images/demo/646.bmp" /></td>
    <td><img src="https://liquidengine.github.io/legui/images/demo/813.bmp" /></td>
    <td></td>
  </tr>
</table>

## Contribution to LEGUI
See the [contribution guide](CONTRIBUTING.md) for more information.

## System requirements
LEGUI requires Java 8+ cause it uses lambda expressions.

## Dependencies
For using this library you should add these urls as repositories:  
For **[LWJGL](https://github.com/LWJGL/lwjgl3)** and **[JOML](https://github.com/JOML-CI/JOML)** dependencies  
`https://oss.sonatype.org/content/repositories/snapshots/`
Also you can add your own versions of these libraries using [LWJGL form](https://www.lwjgl.org/download).

For **LEGUI** and **[CBCHAIN](https://github.com/LiquidEngine/cbchain)** dependency  
`https://raw.github.com/LiquidEngine/repo/releases` or  
`https://raw.github.com/LiquidEngine/repo/snapshots` 

And add this dependency to your build script:  
### Maven:
 ```xml
<!-- LEGUI - SNAPSHOT - https://github.com/LiquidEngine/legui -->
<dependency>
    <groupId>org.liquidengine</groupId>
    <artifactId>legui</artifactId>
    <version>${legui_version}-SNAPSHOT</version>
</dependency>
<!-- OR -->
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
// OR
// LEGUI - RELEASE - https://github.com/LiquidEngine/legui
compile group: 'org.liquidengine', name: 'legui', version: legui_version, changing: true;
  ```
  
  Current `SNAPSHOT` version you can find in [SNAPSHOT version.properties](https://github.com/LiquidEngine/legui/blob/snapshots/version.properties)  
  Current `RELEASE` version you can find in [RELEASE version.properties](https://github.com/LiquidEngine/legui/blob/releases/version.properties)  
  
## Examples:
All examples located here: 
[LEGUI.DEMO](https://github.com/LiquidEngine/legui.demo)

## Links
[LWJGL - Lightweight Java Game Library 3](https://github.com/LWJGL/lwjgl3)  
[JOML – Java OpenGL Math Library](https://github.com/JOML-CI/JOML)  
[CBCHAIN - Callback Chain for LWJGL3](https://github.com/LiquidEngine/cbchain)  
[NanoVG -Small antialiased vector graphics rendering library for OpenGL.](https://github.com/memononen/nanovg)  

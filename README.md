![REPO SIZE](https://img.shields.io/github/repo-size/LiquidEngine/legui.svg?style=flat-square) 
![CODE SIZE](https://img.shields.io/github/languages/code-size/LiquidEngine/legui.svg?style=flat-square) 
[![CODACY](https://img.shields.io/codacy/grade/e3a864cc6d6a4f08938a7b368bdb35da.svg?style=flat-square)](https://www.codacy.com/app/LiquidEngine/legui) 
![License](https://img.shields.io/github/license/LiquidEngine/legui.svg?style=flat-square)  
[![DEVELOP BUILD](https://img.shields.io/jenkins/s/http/jenkins.liquidengine.tk/job/LEGUI_DEVELOP.svg?label=develop&style=flat-square)](http://jenkins.liquidengine.tk/job/LEGUI_DEVELOP/)
[![](https://img.shields.io/jenkins/s/http/jenkins.liquidengine.tk/job/LEGUI_SNAPSHOTS.svg?label=snapshot&style=flat-square)](http://jenkins.liquidengine.tk/job/LEGUI_SNAPSHOTS/)
[![](https://img.shields.io/jenkins/s/http/jenkins.liquidengine.tk/job/LEGUI_RELEASES.svg?label=release&style=flat-square)](http://jenkins.liquidengine.tk/job/LEGUI_RELEASES/)  
![](https://img.shields.io/badge/dynamic/json.svg?label=develop&colorB=00796b&prefix=&suffix=&query=$.version&uri=http%3A%2F%2Fjenkins.liquidengine.tk%2Fjob%2FLEGUI_DEVELOP%2FlastSuccessfulBuild%2Fartifact%2FfullVersion.json&style=flat-square)
![](https://img.shields.io/badge/dynamic/json.svg?label=snapshot&colorB=00796b&prefix=&suffix=&query=$.version&uri=http%3A%2F%2Fjenkins.liquidengine.tk%2Fjob%2FLEGUI_SNAPSHOTS%2FlastSuccessfulBuild%2Fartifact%2FfullVersion.json&style=flat-square)
![](https://img.shields.io/badge/dynamic/json.svg?label=release&colorB=00796b&prefix=&suffix=&query=$.version&uri=http%3A%2F%2Fjenkins.liquidengine.tk%2Fjob%2FLEGUI_RELEASES%2FlastSuccessfulBuild%2Fartifact%2FfullVersion.json&style=flat-square)

___
# LEGUI - [What is it?](https://liquidengine.github.io/legui/)  
GUI implementation for using with LWJGL3.
Renderering implementation made on top of NanoVG.
<table>
  <tr>
    <td><img src="https://liquidengine.github.io/legui/images/demo/0.bmp" height="100px"/></td>
    <td><img src="https://liquidengine.github.io/legui/images/demo/55.bmp" height="100px"/></td>
    <td><img src="https://liquidengine.github.io/legui/images/demo/209.bmp" height="100px"/></td>
    <td><img src="https://liquidengine.github.io/legui/images/demo/646.bmp" height="100px"/></td>
    <td><img src="https://liquidengine.github.io/legui/images/demo/813.bmp" height="100px"/></td>
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
 ```
**OR** 
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
// OR
// LEGUI - RELEASE - https://github.com/LiquidEngine/legui
compile group: 'org.liquidengine', name: 'legui', version: legui_version, changing: true;
  ```
  
## Examples:
All examples located here: 
[LEGUI.DEMO](https://github.com/LiquidEngine/legui.demo)

## Links
[LWJGL - Lightweight Java Game Library 3](https://github.com/LWJGL/lwjgl3)  
[JOML – Java OpenGL Math Library](https://github.com/JOML-CI/JOML)  
[CBCHAIN - Callback Chain for LWJGL3](https://github.com/LiquidEngine/cbchain)  
[NanoVG -Small antialiased vector graphics rendering library for OpenGL.](https://github.com/memononen/nanovg)  

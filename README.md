# Game API
The API dedicated for minecraft widely understood game and minigame development, based on Spigot API.

## Purpose
Provides all needed tools and functionalities to fully control the game flow and store necessary data. Every element of this API is coded with much care to make it understandable and clear as well as comfortable and easy to use.

Every class will obtain carefully written documentation, by far classes and mechanic themself are under development. The plan for JavaDoc is to implement it with following releases, possibly along with ALPHA ones.

## Maven
This API is created with `Java 8` and built using `Maven`. It uses Spigot API 1.8.8.

**Repository:**

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

**Dependency:**

```xml
<dependency>
    <groupId>com.github.TheKaVu</groupId>
    <artifactId>GameAPI</artifactId>
    <version>LatestCommitId</version>
</dependency>
```

Latest commit id can be found [here](https://jitpack.io/#TheKaVu/GameAPI/). Until there are no releases, it is the only way to depend on this repo. When new commits are pushed the commit id must be changed to get the updated version. However this will be fixed as soon as first releases come out.
***
**Note:** 

This API is in early development state thus depending on it might be risky. Changes are done almost day by day so major incompatibilities must be expected.
As any of provided functionality were barely tested right now it is obvious that many bugs and malfunction could encounter.

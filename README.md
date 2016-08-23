queryfly-analyzer
============================
Analyzer module for QueryFly.
----------------------------
Currently it's a buggy prototype!

What is queryfly-analyzer?
----------------------------
__queryfly-analyzer__ is a web server which analyzes input data based on a _Question/Reply(Q/R)_ format. It aims to parse any kind of text data which is in the _Q/R_ format. More than parsing, it will try to mix up similar data and give reliable mixed data.

Running queryfly-analyzer
----------------------------
1. ```git clone``` this repository.
2. Install MongoDB and run it with default settings.
3. ```maven clean install exec:java -Dexec.mainClass="kr.ec.queryfly.analyzer.main.AppServerMain" ```

Usage
----------------------------

* To do


This project is based on
----------------------------
* [Netty](http://netty.io/)
* [Spring Framework](https://projects.spring.io/spring-framework)
* [google-gson](https://github.com/google/gson)

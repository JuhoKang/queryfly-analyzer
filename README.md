queryfly-analyzer
============================
Analyzer module for QueryFly.
----------------------------
Currently it's a buggy prototype!

What is queryfly-analyzer?
----------------------------
__queryfly-analyzer__ is a web server which analyzes input data based on a _Question/Reply(Q/R)_ format. It aims to parse any kind of text data which is in the _Q/R_ format. More than parsing, it will try to mix up similar data and give reliable mixed data. Currently it only aims to analyze Korean texts.

![alt text](https://github.com/JuhoKang/queryfly-analyzer/blob/master/picture.png "picture")

Running queryfly-analyzer
----------------------------
1. ```git clone``` this repository.
2. Install MongoDB and run it with default settings.
3. Install arirang dependencies
4. ```mvn install:install-file -Dfile=src/main/resources/arirang.lucene-analyzer-6.1-1.0.0.jar -DgroupId=arirang -DartifactId=arirang.analyzer -Dversion=6.1 -Dpackaging=jar ```
5. ```mvn install:install-file -Dfile=src/main/resources/arirang-morph-1.0.3.jar -DgroupId=arirang -DartifactId=arirang.morph -Dversion=1.0.3 -Dpackaging=jar ```
6. Run queryfly-analyzer  
7. ```mvn clean install exec:java -Dexec.mainClass="kr.ec.queryfly.analyzer.main.ApplicationMain" ```

Usage, How To
----------------------------

* Read API spec doc


API spec doc
----------------------------
https://docs.google.com/document/d/1V9O-RYaYt1RX4b96pMbf-Pa1-kGa4706fS36imVE2Hc/edit?usp=sharing


This project is based on
----------------------------
* [Netty](http://netty.io/)
* [arirang-analyzer,morph](http://cafe.naver.com/korlucene)
* [Spring Framework](https://projects.spring.io/spring-framework)
* [google-gson](https://github.com/google/gson)

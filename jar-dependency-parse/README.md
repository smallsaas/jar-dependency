# jar-dependency-parse

## 使用方法

提取目录下的`jar-dependency-parse-1.0.0-jar-with-dependencies.jar`，随后执行下述Java命令提取Jar包依赖**（仅支持Spring Boot框架依赖提取）**。

```shell
# Java命令执行Jar包
$ java -jar jar-dependency-parse-1.0.0-jar-with-dependencies.jar
Please input JAR File path :
e:\path\test-1.0.0-standalone.jar

Found 95 dependencies in lib.

OUT PUT Dependencies JSON in file: e:\path\test-1.0.0-standalone_dependencies_1596680844616.json
```

随后可在` e:\path\test-1.0.0-standalone_dependencies_1596680844616.json`路径下查看生成的JSON文件。


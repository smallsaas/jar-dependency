# jar-dependency-parse

## 使用方法

提取目录下的`parse-jar-with-dependencies.jar`，随后执行下述Java命令提取Jar包依赖 **（仅支持Spring Boot框架依赖提取）** 。

```shell
# Java命令执行Jar包 （确保解析工具包与待解析JAR包位于同级目录下）
$ java -jar parse-jar-with-dependencies.jar test
# 运行结果
Parsing test.jar...
Found 5 dependencies in lib.
SUCCESS OUTPUT Dependencies JSON in file: test_dependencies_1596708223404.json
```

随后可在同路径下查看生成的JSON文件。


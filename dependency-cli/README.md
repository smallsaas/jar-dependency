# dependency-cli

## 使用方法

提取目录下的`parse-jar-with-dependencies.jar`，随后执行下述Java命令提取Jar包依赖 **（仅支持Spring Boot框架依赖提取）** 。

```shell
Usage: java -jar dependency-cli.jar Options [Variables...]

用于 Standalone Jar包依赖输出 和 Maven module依赖对比

Options:
  -c, --compare [Maven module I] [Maven module II]  对比两个Maven module依赖情况并生成JSON文件
  -p, --parse [Jar Path]  解析Jar包依赖并生成JSON文件
  -v, --version  输出当前工具版本信息
```

随后可在提示路径下查看生成的JSON文件。


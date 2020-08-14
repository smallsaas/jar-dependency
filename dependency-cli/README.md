# dependency-cli

## 使用方法

```shell
$ bash dependency.sh

Usage: dependency Options [Variables...]
e.g. dependency -p ./lib/test.jar
用于Jar包依赖输出 和 Maven module依赖对比

Options:
  -c, --compare [Maven module I] [Maven module II]  对比两个Maven module依赖情况并生成JSON文件
  -p, --parse [Jar Path]  解析Jar包依赖并生成JSON文件
  -v, --version  输出当前工具版本信息
```

执行成狗后可在提示路径下查看生成的JSON文件。


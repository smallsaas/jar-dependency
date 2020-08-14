#!/bin/bash
option=$1

########dependency-cli.jar path########
jar_path=$(cd `dirname $0`;pwd)/lib/dependency-cli.jar
compare='^-c$'
parse='^-[p][j]?$'
version='^-v$'
#############debug############
#echo jar_path=${jar_path}
#echo option=${option}
##############################

if [ ! -f $jar_path ]
then
  echo "dependency: 无法获取jar包: $jar_path"
  exit;
fi

usage(){
	cat <<- EOF
	
	Usage: dependency Options [Variables...]
	e.g. dependency -p ./lib/test.jar
	用于Jar包依赖输出 和 Maven module依赖对比

	Options:
	  -c, --compare [Maven module I Path] [Maven module II Path]  对比两个Maven module依赖情况并生成输出为JSON格式
	  -p, --parse [Jar Path]  解析Jar包依赖并输出
		   -> -pj --parse To JSON 解析Jar包依赖并输出为JSON格式
	  -v, --version  输出当前工具版本信息
	EOF
	exit
}


if [ ! $option ]
then
	usage
fi

if [[ "$option" =~ $compare ]]
then
	java -jar $jar_path $option $(readlink -f "$2") $(readlink -f "$3")
elif [[ "$option" =~ $parse ]]
then
	java -jar $jar_path $option $(readlink -f "$2")
elif [[ "$option" =~ $version ]]
then
	java -jar $jar_path $*
fi


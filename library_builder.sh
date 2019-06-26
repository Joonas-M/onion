#!/bin/bash
set -euxo pipefail

PROJECT_NAME=$1
PROJECT_NAME_U=$( echo $PROJECT_NAME | sed 's/-/_/g' )
SOURCES_TO_COPY=("${@:2:${#@}}")

if [ -d ./lib ];
then
    mv lib lib_old
fi;

wrapper_libraries=(reagent om_next)
for w_l in ${wrapper_libraries[*]}
do
    mkdir -p "lib/${PROJECT_NAME_U}_$w_l"
    cp -r ${SOURCES_TO_COPY[*]} "lib/${PROJECT_NAME_U}_${w_l}/"
    sed -i "s/defproject ${PROJECT_NAME}/defproject ${PROJECT_NAME}-$( echo ${w_l} | sed 's/_/-/g' )/" "lib/${PROJECT_NAME_U}_${w_l}/project.clj"
done

function parse_variables () {
    FILE=$1
    PROJECT_NAME_UU=$2
    SOURCE_FILE=$( echo "$FILE" | sed -n 's/^lib\/'"${PROJECT_NAME_UU}"'_\([a-zA-Z0-9_-]*\)\/.*/\1/p' )
    source "./.variables/$SOURCE_FILE"
    variables=($( sed -n "s/.*<<\([a-zA-Z0-9_-]*\)>>.*/\1/gp" $FILE ))
    for v in ${variables[*]}
    do
	if [ ! -z "$v" ];
        then
            sed -i "s/<<${v}>>/${!v}/g" $FILE;
	fi;
    done
}



export -f parse_variables
find lib -type f -exec bash -c 'parse_variables "$0" "$1"' {} $PROJECT_NAME_U \;

if [ -d ./lib_old ];
then
    rm -rf lib_old
fi

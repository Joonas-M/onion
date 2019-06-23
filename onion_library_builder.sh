#!/bin/bash
set -euxo pipefail

if [ -f ./lib ];
then
    mv lib lib_old
fi;

wrapper_libraries=(reagent om_next)
for w_l in ${wrapper_libraries[*]}
do
    mkdir -p "lib/onion_$w_l"
    cp -r src project.clj LICENSE "lib/onion_${w_l}/"
    sed -i "s/defproject onion/defproject onion-$( echo ${w_l} | sed 's/_/-/g' )/" "lib/onion_${w_l}/project.clj"
    for w_l_n in ${wrapper_libraries[*]}
    do
	if [ ! "$w_l" == "$w_l_n" ];
	then
	    rm lib/onion_${w_l}/src/onion/${w_l_n}/*.cljs
	fi
    done
done

function parse_variables () {
    FILE=$1
    SOURCE_FILE="$( echo $FILE | sed -n 's/^lib\/onion_\([a-zA-Z0-9_-]*\)\/.*/\1/p' )"
    
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
find lib -type f -exec bash -c 'parse_variables "$0"' {} \;

if [ -f ./lib_old ];
then
    rm -rf lib_old
fi

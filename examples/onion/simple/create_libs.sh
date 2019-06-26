#!/bin/bash
set -euxo pipefail

bash ../../../library_builder.sh onion-simple project.clj src

wrapper_libraries=(reagent om_next)
for w_l in ${wrapper_libraries[*]}
do
    mkdir -p "lib/onion_simple_${w_l}/checkouts"
    ln -s -t "lib/onion_simple_${w_l}/checkouts" "../../../../../../lib/onion_${w_l}"
done

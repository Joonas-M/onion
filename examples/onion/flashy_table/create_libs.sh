#!/bin/bash
set -euxo pipefail

bash ../../../library_builder.sh onion-flashy-table project.clj src

wrapper_libraries=(reagent om_next)
for w_l in ${wrapper_libraries[*]}
do
    mkdir -p "lib/onion_flashy_table_${w_l}/checkouts"
    ln -s -t "lib/onion_flashy_table_${w_l}/checkouts" "../../../../../../lib/onion_${w_l}"
done

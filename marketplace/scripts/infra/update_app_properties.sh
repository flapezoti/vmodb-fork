#!/bin/bash

echo "Script name: $0"
echo "Number of arguments: $#"

help_="--help"
param1="$1"

if [ "$param1" = "$help_" ]; then
    echo "It is expected that the script runs in the marketplace project's root folder."
    echo "Specify the following properties in the order below:"
    echo "<file_path> <num_vertices> <vms_thread_pool_size> <logging> <checkpointing> <max_records>"
    exit 1
fi

sed "s|num_vertices=.*|num_vertices=$2|g" $1 > changed.txt && mv changed.txt $1
sed "s|vms_thread_pool_size=.*|vms_thread_pool_size=$3|g" $1 > changed.txt && mv changed.txt $1
sed "s|logging=.*|logging=$4|g" $1 > changed.txt && mv changed.txt $1
sed "s|checkpointing=.*|checkpointing=$5|g" $1 > changed.txt && mv changed.txt $1
sed "s|max_records=.*|max_records=$6|g" $1 > changed.txt && mv changed.txt $1

#!/bin/sh

# go to the directory of the Oracle dockerfile with the script of buildDockerImage.sh
cd $1

./buildDockerImage.sh -v 18.3.0 -s -i
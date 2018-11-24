#!/bin/sh

java -cp ./app.jar:./lib/* com.nedzhang.util.jdbcverify.App "$@"

#!/bin/bash

gcc "$1/main.c" -o "$1/main" 2> $2/compile_stderr.txt
exit $?
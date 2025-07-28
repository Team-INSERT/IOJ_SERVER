#!/bin/bash

g++ "$1/main.cpp" -o "$1/main" 2> $2/compile_stderr.txt
exit $?
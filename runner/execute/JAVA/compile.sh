#!/bin/bash

javac "$1/main.java" 2> "$2/compile_stderr.txt"
exit $?
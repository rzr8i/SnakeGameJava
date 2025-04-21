#!/bin/bash

javac ./src/com/github/rzr8i/*.java -d build/
java -cp build/ com.github.rzr8i.Main

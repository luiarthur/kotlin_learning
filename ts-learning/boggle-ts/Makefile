SHELL := /bin/bash

.PHONY: compile clean all

all: compile

compile:
	# tsc src/*.ts --pretty --watch --outDir js
	tsc src/*.ts --pretty --watch --outFile js/main.js --module amd

clean:
	rm -rf js/*.js

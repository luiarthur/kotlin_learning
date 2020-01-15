SHELL := /bin/bash

.PHONY: compile clean all

all: compile

compile:
	tsc *.ts --pretty --watch --outDir js --module es2015
	# tsc *.ts --pretty --watch --outFile js/index.js --module amd

clean:
	rm -rf js

SHELL := /bin/bash

.PHONY: compile clean all

all: compile

compile:
	# tsc *.ts --pretty --watch --outDir js
	tsc *.ts --pretty --watch --outFile js/index.js --module amd

clean:
	rm -rf js

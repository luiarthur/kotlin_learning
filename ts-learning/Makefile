SHELL := /bin/bash

.PHONY: compile clean all

all: compile

compile:
	tsc *.ts --pretty --watch --outDir js

clean:
	rm -rf js

SHELL := /bin/bash

.PHONY: compile clean all

all: compile

compile:
	# tsc *.ts --pretty --watch --outDir js
	tsc *.ts --pretty --watch --outFile js/main-app.js --module amd
	# tsc *.ts --pretty --watch --outDir js/ --module amd

clean:
	rm -rf js

serve:
	python3 -m http.server

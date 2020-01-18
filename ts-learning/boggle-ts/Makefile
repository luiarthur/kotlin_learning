SHELL := /bin/bash

.PHONY: compile clean all

all: compile

compile:
	# tsc src/*.ts --pretty --watch --outDir js
	# tsc src/*.ts --pretty --watch --outFile js/boggle.js --module amd 
	npx tsc --watch


clean:
	rm -rf js/*.js

serve:
	python3 -m http.server

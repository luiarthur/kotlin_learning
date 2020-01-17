SHELL := /bin/bash

.PHONY: compile clean all

all: compile

TSVERSION := "Version 3.7.4"

tsversion:
	@echo $(TSVERSION)

compile:
	# tsc src/*.ts --pretty --watch --outDir js
	tsc src/*.ts --pretty --watch --outFile js/boggle.js --module amd

clean:
	rm -rf js/*.js

serve:
	python3 -m http.server

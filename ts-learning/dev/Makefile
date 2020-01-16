SHELL := /bin/bash

.PHONY: compile clean all

BIN = ./node_modules/.bin

all: compile

compile:
	# tsc *.ts --pretty --watch --outDir js
	# tsc src/*.ts --pretty --watch --outFile js/main-app.js --module amd
	$(BIN)/tsc --watch

clean:
	rm -rf dist/*

serve:
	python3 -m http.server

bundle:
	$(BIN)/webpack

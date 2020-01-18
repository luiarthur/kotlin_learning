#!/bin/bash

.PHONY: all bundle clean compile

all: bundle

clean:
	rm -rf dist/*

compile:
	npx tsc

bundle: compile
	cd dist && \
		find . ! -name 'file.txt' -type f -exec rm -f {} + && \
		rm -rf components
	npx webpack

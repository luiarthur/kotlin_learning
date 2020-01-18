#!/bin/bash

.PHONY: all bundle clean compile watch serve

all: bundle

serve:
	python3 -m http.server

clean:
	rm -rf dist/*

watch:
	npx tsc --watch

compile:
	npx tsc

bundle:
	npx webpack
	# npm run dev

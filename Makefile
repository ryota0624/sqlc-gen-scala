.PHONY: build test

build:
	go build ./...

test:
	go test ./...

all: bin/sqlc-gen-scala bin/sqlc-gen-scala.wasm

bin/sqlc-gen-scala: bin go.mod go.sum $(wildcard **/*.go)
	cd plugin && go build -o ../bin/sqlc-gen-scala ./main.go

bin/sqlc-gen-scala.wasm: bin/sqlc-gen-scala
	cd plugin && GOOS=wasip1 GOARCH=wasm go build -o ../bin/sqlc-gen-scala.wasm main.go

bin:
	mkdir -p bin

build_example:
	rm -rf bin
	make bin/sqlc-gen-scala
	cd examples && sqlc generate

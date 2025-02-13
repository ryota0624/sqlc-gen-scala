package main

import (
	"github.com/sqlc-dev/plugin-sdk-go/codegen"

	scala "github.com/sqlc-dev/sqlc-gen-scala/internal"
)

func main() {
	codegen.Run(scala.Generate)
}

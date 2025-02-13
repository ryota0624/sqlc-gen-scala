## Usage

```yaml
version: '2'
plugins:
- name: scala
  wasm:
    url: https://github.com/ryota0624/sqlc-gen-kotlin/releases/download/test0/sqlc-gen-scala.wasm
    sha256: d180bd06a414ad475f54873ec28435c5a6f894f1baebe6b0780d8ebbfef2f317
sql:
- schema: src/main/resources/authors/postgresql/schema.sql
  queries: src/main/resources/authors/postgresql/query.sql
  engine: postgresql
  codegen:
  - out: src/main/scala/com/example/authors/postgresql
    plugin: scala
    options:
      package: com.example.authors.postgresql
```

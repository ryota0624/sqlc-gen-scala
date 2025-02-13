// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.27.0

package com.example.booktest.postgresql

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.sql.Types
import java.time.OffsetDateTime
import scala.util.Using

enum BookType(val value: String) {
  case FICTION extends BookType("FICTION")
  case NONFICTION extends BookType("NONFICTION")
}

case class Author (
  authorId: Int,
  name: String
)

case class Book (
  bookId: Int,
  authorId: Int,
  isbn: String,
  bookType: BookType,
  title: String,
  year: Int,
  available: OffsetDateTime,
  tags: List[String]
)


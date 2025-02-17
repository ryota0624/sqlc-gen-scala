// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.27.0

package com.example.booktest.mysql

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.time.LocalDateTime
import scala.util.Using

val booksByTagsSQL = """-- name: booksByTags :many
SELECT
  book_id,
  title,
  name,
  isbn,
  tags
FROM books
LEFT JOIN authors ON books.author_id = authors.author_id
WHERE tags = ?
"""

case class BooksByTagsRow (
  bookId: Int,
  title: String,
  name: Option[String],
  isbn: String,
  tags: String
)

val booksByTitleYearSQL = """-- name: booksByTitleYear :many
SELECT book_id, author_id, isbn, book_type, title, yr, available, tags FROM books
WHERE title = ? AND yr = ?
"""

val createAuthorSQL = """-- name: createAuthor :execresult
INSERT INTO authors (name) VALUES (?)
"""

val createBookSQL = """-- name: createBook :execresult
INSERT INTO books (
    author_id,
    isbn,
    book_type,
    title,
    yr,
    available,
    tags
) VALUES (
    ?,
    ?,
    ?,
    ?,
    ?,
    ?,
    ?
)
"""

val deleteAuthorBeforeYearSQL = """-- name: deleteAuthorBeforeYear :exec
DELETE FROM books
WHERE yr < ? AND author_id = ?
"""

val deleteBookSQL = """-- name: deleteBook :exec
DELETE FROM books
WHERE book_id = ?
"""

val getAuthorSQL = """-- name: getAuthor :one
SELECT author_id, name FROM authors
WHERE author_id = ?
"""

val getBookSQL = """-- name: getBook :one
SELECT book_id, author_id, isbn, book_type, title, yr, available, tags FROM books
WHERE book_id = ?
"""

val updateBookSQL = """-- name: updateBook :exec
UPDATE books
SET title = ?, tags = ?
WHERE book_id = ?
"""

val updateBookISBNSQL = """-- name: updateBookISBN :exec
UPDATE books
SET title = ?, tags = ?, isbn = ?
WHERE book_id = ?
"""

class QueriesImpl(private val conn: Connection) extends Queries {

  override def booksByTags(tags: String): List[BooksByTagsRow] = {
    Using.resource(conn.prepareStatement(booksByTagsSQL)) { stmt =>
      stmt.setString(1, tags)

      val results = stmt.executeQuery()
      val ret = scala.collection.mutable.ListBuffer.empty[BooksByTagsRow]
      while (results.next()) {
          ret += BooksByTagsRow(
                results.getInt(1),
                results.getString(2),
                Option(results.getObject(3)).map { _ => results.getString(3) },
                results.getString(4),
                results.getString(5)
            )
      }
      ret.toList
    }
  }

  override def booksByTitleYear(title: String, yr: Int): List[Book] = {
    Using.resource(conn.prepareStatement(booksByTitleYearSQL)) { stmt =>
      stmt.setString(1, title)
          stmt.setInt(2, yr)

      val results = stmt.executeQuery()
      val ret = scala.collection.mutable.ListBuffer.empty[Book]
      while (results.next()) {
          ret += Book(
                results.getInt(1),
                results.getInt(2),
                results.getString(3),
                BooksBookType.valueOf(results.getString(4)),
                results.getString(5),
                results.getInt(6),
                results.getObject(7, classOf[LocalDateTime]),
                results.getString(8)
            )
      }
      ret.toList
    }
  }

  override def createAuthor(name: String): Long = {
    Using.resource(conn.prepareStatement(createAuthorSQL, Statement.RETURN_GENERATED_KEYS)) { stmt =>
      stmt.setString(1, name)

      stmt.execute()

      val results = stmt.getGeneratedKeys
      if (!results.next()) {
          throw SQLException("no generated key returned")
      }
      results.getLong(1)
    }
  }

  override def createBook(
      authorId: Int,
      isbn: String,
      bookType: BooksBookType,
      title: String,
      yr: Int,
      available: LocalDateTime,
      tags: String): Long = {
    Using.resource(conn.prepareStatement(createBookSQL, Statement.RETURN_GENERATED_KEYS)) { stmt =>
      stmt.setInt(1, authorId)
          stmt.setString(2, isbn)
          stmt.setString(3, bookType.value)
          stmt.setString(4, title)
          stmt.setInt(5, yr)
          stmt.setObject(6, available)
          stmt.setString(7, tags)

      stmt.execute()

      val results = stmt.getGeneratedKeys
      if (!results.next()) {
          throw SQLException("no generated key returned")
      }
      results.getLong(1)
    }
  }

  override def deleteAuthorBeforeYear(yr: Int, authorId: Int): Unit = {
    Using.resource(conn.prepareStatement(deleteAuthorBeforeYearSQL)) { stmt =>
      stmt.setInt(1, yr)
          stmt.setInt(2, authorId)

      stmt.execute()
    }
  }

  override def deleteBook(bookId: Int): Unit = {
    Using.resource(conn.prepareStatement(deleteBookSQL)) { stmt =>
      stmt.setInt(1, bookId)

      stmt.execute()
    }
  }

  override def getAuthor(authorId: Int): Option[Author] = {
    Using.resource(conn.prepareStatement(getAuthorSQL)) { stmt =>
      stmt.setInt(1, authorId)

      val results = stmt.executeQuery()
      Option.when(results.next()) {
        val ret = Author(
                results.getInt(1),
                results.getString(2)
            )
        if (results.next()) {
          throw SQLException("expected one row in result set, but got many")
        }
        ret
      }
    }
  }

  override def getBook(bookId: Int): Option[Book] = {
    Using.resource(conn.prepareStatement(getBookSQL)) { stmt =>
      stmt.setInt(1, bookId)

      val results = stmt.executeQuery()
      Option.when(results.next()) {
        val ret = Book(
                results.getInt(1),
                results.getInt(2),
                results.getString(3),
                BooksBookType.valueOf(results.getString(4)),
                results.getString(5),
                results.getInt(6),
                results.getObject(7, classOf[LocalDateTime]),
                results.getString(8)
            )
        if (results.next()) {
          throw SQLException("expected one row in result set, but got many")
        }
        ret
      }
    }
  }

  override def updateBook(
      title: String,
      tags: String,
      bookId: Int): Unit = {
    Using.resource(conn.prepareStatement(updateBookSQL)) { stmt =>
      stmt.setString(1, title)
          stmt.setString(2, tags)
          stmt.setInt(3, bookId)

      stmt.execute()
    }
  }

  override def updateBookISBN(
      title: String,
      tags: String,
      isbn: String,
      bookId: Int): Unit = {
    Using.resource(conn.prepareStatement(updateBookISBNSQL)) { stmt =>
      stmt.setString(1, title)
          stmt.setString(2, tags)
          stmt.setString(3, isbn)
          stmt.setInt(4, bookId)

      stmt.execute()
    }
  }

}


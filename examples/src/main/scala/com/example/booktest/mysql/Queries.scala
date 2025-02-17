// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.27.0

package com.example.booktest.mysql

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.time.LocalDateTime
import scala.util.Using

trait Queries {
  def booksByTags(tags: String): List[BooksByTagsRow]
  
  def booksByTitleYear(title: String, yr: Int): List[Book]
  
  def createAuthor(name: String): Long
  
  def createBook(
      authorId: Int,
      isbn: String,
      bookType: BooksBookType,
      title: String,
      yr: Int,
      available: LocalDateTime,
      tags: String): Long
  
  def deleteAuthorBeforeYear(yr: Int, authorId: Int): Unit
  
  def deleteBook(bookId: Int): Unit
  
  def getAuthor(authorId: Int): Option[Author]
  
  def getBook(bookId: Int): Option[Book]
  
  def updateBook(
      title: String,
      tags: String,
      bookId: Int): Unit
  
  def updateBookISBN(
      title: String,
      tags: String,
      isbn: String,
      bookId: Int): Unit
  
}


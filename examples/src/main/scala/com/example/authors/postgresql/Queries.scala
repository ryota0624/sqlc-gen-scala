// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.27.0

package com.example.authors.postgresql

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import scala.util.Using

trait Queries {
  def createAuthor(name: String, bio: Option[String]): Option[Author]
  
  def deleteAuthor(id: Long): Unit
  
  def getAuthor(id: Long): Option[Author]
  
  def listAuthors(): List[Author]
  
}


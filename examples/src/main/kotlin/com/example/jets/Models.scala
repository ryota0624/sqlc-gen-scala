// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.27.0

package com.example.jets

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import scala.util.Using

case class Jet (
  id: Int,
  pilotId: Int,
  age: Int,
  name: String,
  color: String
)

case class Language (
  id: Int,
  language: String
)

case class Pilot (
  id: Int,
  name: String
)

case class PilotLanguage (
  pilotId: Int,
  languageId: Int
)


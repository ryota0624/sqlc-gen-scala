// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.27.0

package com.example.manytypes.postgresql

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.UUID
import scala.util.Using

trait Queries {
  def insertExample(
      id: String,
      dates: List[LocalDate],
      timestamps: List[LocalDateTime],
      uuids: List[UUID],
      dollar5: List[String],
      date: LocalDate,
      timestamp: LocalDateTime,
      uuid: UUID,
      dollar9_2: String,
      offsettimestamp: OffsetDateTime,
      offsettimestampArray: List[OffsetDateTime]): Unit
  
  def selectExample(id: String): Option[ExampleTable]
  
}


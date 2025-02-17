// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.27.0

package com.example.ondeck.postgresql

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.sql.Types
import java.time.LocalDateTime
import scala.util.Using

val createCitySQL = """-- name: createCity :one
INSERT INTO city (
    name,
    slug
) VALUES (
    ?,
    ?
) RETURNING slug, name
"""

val createVenueSQL = """-- name: createVenue :one
INSERT INTO venue (
    slug,
    name,
    city,
    created_at,
    spotify_playlist,
    status,
    statuses,
    tags
) VALUES (
    ?,
    ?,
    ?,
    NOW(),
    ?,
    ?,
    ?,
    ?
) RETURNING id
"""

val deleteVenueSQL = """-- name: deleteVenue :exec
DELETE FROM venue
WHERE slug = ? AND slug = ?
"""

val getCitySQL = """-- name: getCity :one
SELECT slug, name
FROM city
WHERE slug = ?
"""

val getVenueSQL = """-- name: getVenue :one
SELECT id, status, statuses, slug, name, city, spotify_playlist, songkick_id, tags, created_at
FROM venue
WHERE slug = ? AND city = ?
"""

val listCitiesSQL = """-- name: listCities :many
SELECT slug, name
FROM city
ORDER BY name
"""

val listVenuesSQL = """-- name: listVenues :many
SELECT id, status, statuses, slug, name, city, spotify_playlist, songkick_id, tags, created_at
FROM venue
WHERE city = ?
ORDER BY name
"""

val updateCityNameSQL = """-- name: updateCityName :exec
UPDATE city
SET name = ?
WHERE slug = ?
"""

val updateVenueNameSQL = """-- name: updateVenueName :one
UPDATE venue
SET name = ?
WHERE slug = ?
RETURNING id
"""

val venueCountByCitySQL = """-- name: venueCountByCity :many
SELECT
    city,
    count(*)
FROM venue
GROUP BY 1
ORDER BY 1
"""

case class VenueCountByCityRow (
  city: String,
  count: Long
)

class QueriesImpl(private val conn: Connection) extends Queries {

// Create a new city. The slug must be unique.
// This is the second line of the comment
// This is the third line

  override def createCity(name: String, slug: String): Option[City] = {
    Using.resource(conn.prepareStatement(createCitySQL)) { stmt =>
      stmt.setString(1, name)
          stmt.setString(2, slug)

      val results = stmt.executeQuery()
      Option.when(results.next()) {
        val ret = City(
                results.getString(1),
                results.getString(2)
            )
        if (results.next()) {
          throw SQLException("expected one row in result set, but got many")
        }
        ret
      }
    }
  }

  override def createVenue(
      slug: String,
      name: String,
      city: String,
      spotifyPlaylist: String,
      status: Status,
      statuses: List[Status],
      tags: List[String]): Option[Int] = {
    Using.resource(conn.prepareStatement(createVenueSQL)) { stmt =>
      stmt.setString(1, slug)
          stmt.setString(2, name)
          stmt.setString(3, city)
          stmt.setString(4, spotifyPlaylist)
          stmt.setObject(5, status.value, Types.OTHER)
          stmt.setArray(6, conn.createArrayOf("status", statuses.map(_.value).toArray()))
          stmt.setArray(7, conn.createArrayOf("text", tags.toArray()))

      val results = stmt.executeQuery()
      Option.when(results.next()) {
        val ret = results.getInt(1)
        if (results.next()) {
          throw SQLException("expected one row in result set, but got many")
        }
        ret
      }
    }
  }

  override def deleteVenue(slug: String): Unit = {
    Using.resource(conn.prepareStatement(deleteVenueSQL)) { stmt =>
      stmt.setString(1, slug)
          stmt.setString(2, slug)

      stmt.execute()
    }
  }

  override def getCity(slug: String): Option[City] = {
    Using.resource(conn.prepareStatement(getCitySQL)) { stmt =>
      stmt.setString(1, slug)

      val results = stmt.executeQuery()
      Option.when(results.next()) {
        val ret = City(
                results.getString(1),
                results.getString(2)
            )
        if (results.next()) {
          throw SQLException("expected one row in result set, but got many")
        }
        ret
      }
    }
  }

  override def getVenue(slug: String, city: String): Option[Venue] = {
    Using.resource(conn.prepareStatement(getVenueSQL)) { stmt =>
      stmt.setString(1, slug)
          stmt.setString(2, city)

      val results = stmt.executeQuery()
      Option.when(results.next()) {
        val ret = Venue(
                results.getInt(1),
                Status.valueOf(results.getString(2)),
                results.getArray(3).getArray().asInstanceOf[Array[String]].map(Status.valueOf).toList,
                results.getString(4),
                results.getString(5),
                results.getString(6),
                results.getString(7),
                Option(results.getObject(8)).map { _ => results.getString(8) },
                results.getArray(9).getArray().asInstanceOf[Array[AnyRef]].map(_.asInstanceOf[String]).toList,
                results.getObject(10, classOf[LocalDateTime])
            )
        if (results.next()) {
          throw SQLException("expected one row in result set, but got many")
        }
        ret
      }
    }
  }

  override def listCities(): List[City] = {
    Using.resource(conn.prepareStatement(listCitiesSQL)) { stmt =>
      
      val results = stmt.executeQuery()
      val ret = scala.collection.mutable.ListBuffer.empty[City]
      while (results.next()) {
          ret += City(
                results.getString(1),
                results.getString(2)
            )
      }
      ret.toList
    }
  }

  override def listVenues(city: String): List[Venue] = {
    Using.resource(conn.prepareStatement(listVenuesSQL)) { stmt =>
      stmt.setString(1, city)

      val results = stmt.executeQuery()
      val ret = scala.collection.mutable.ListBuffer.empty[Venue]
      while (results.next()) {
          ret += Venue(
                results.getInt(1),
                Status.valueOf(results.getString(2)),
                results.getArray(3).getArray().asInstanceOf[Array[String]].map(Status.valueOf).toList,
                results.getString(4),
                results.getString(5),
                results.getString(6),
                results.getString(7),
                Option(results.getObject(8)).map { _ => results.getString(8) },
                results.getArray(9).getArray().asInstanceOf[Array[AnyRef]].map(_.asInstanceOf[String]).toList,
                results.getObject(10, classOf[LocalDateTime])
            )
      }
      ret.toList
    }
  }

  override def updateCityName(name: String, slug: String): Unit = {
    Using.resource(conn.prepareStatement(updateCityNameSQL)) { stmt =>
      stmt.setString(1, name)
          stmt.setString(2, slug)

      stmt.execute()
    }
  }

  override def updateVenueName(name: String, slug: String): Option[Int] = {
    Using.resource(conn.prepareStatement(updateVenueNameSQL)) { stmt =>
      stmt.setString(1, name)
          stmt.setString(2, slug)

      val results = stmt.executeQuery()
      Option.when(results.next()) {
        val ret = results.getInt(1)
        if (results.next()) {
          throw SQLException("expected one row in result set, but got many")
        }
        ret
      }
    }
  }

  override def venueCountByCity(): List[VenueCountByCityRow] = {
    Using.resource(conn.prepareStatement(venueCountByCitySQL)) { stmt =>
      
      val results = stmt.executeQuery()
      val ret = scala.collection.mutable.ListBuffer.empty[VenueCountByCityRow]
      while (results.next()) {
          ret += VenueCountByCityRow(
                results.getString(1),
                results.getLong(2)
            )
      }
      ret.toList
    }
  }

}


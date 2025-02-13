package com.example.manytypes.postgresql.postgresql

import com.dimafeng.testcontainers.PostgreSQLContainer
import com.dimafeng.testcontainers.scalatest.TestContainersForAll
import com.example.manytypes.postgresql.{ExampleTable, QueriesImpl}
import org.scalatest.OptionValues.convertOptionToValuable
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import org.testcontainers.utility.DockerImageName

import java.sql.DriverManager


class QueriesImplTest extends AnyFlatSpec with should.Matchers with TestContainersForAll {
  override type Containers = PostgreSQLContainer

  override def startContainers(): PostgreSQLContainer = {
    val postgres = PostgreSQLContainer(dockerImageNameOverride = DockerImageName.parse("postgres:15"))
    postgres.container.withInitScript("manytypes/postgresql/schema.sql")
    postgres.container.start()
    postgres
  }

  "array type values" should "insert DB Row" in withContainers {
    container =>
      Class.forName(container.driverClassName)
      val row = ExampleTable(
        "1",
        List("2022-01-01", "2022-01-02", "2022-01-03").map(java.time.LocalDate.parse),
        List("2022-01-01T00:00:00", "2022-01-02T00:00:00", "2022-01-03T00:00:00").map(java.time.LocalDateTime.parse),
        List("00000000-0000-0000-0000-000000000001", "00000000-0000-0000-0000-000000000002", "00000000-0000-0000-0000-000000000003").map(java.util.UUID.fromString),
        List("{}", "{}", "{}"),
        java.time.LocalDate.parse("2022-01-01"),
        java.time.LocalDateTime.parse("2022-01-01T00:00:00"),
        java.util.UUID.fromString("00000000-0000-0000-0000-000000000001"),
        "{}",
        java.time.OffsetDateTime.parse("2022-01-01T00:00:00Z"),
        List(
          java.time.OffsetDateTime.parse("2022-01-01T00:00:00Z"),
          java.time.OffsetDateTime.parse("2022-01-02T00:00:00Z"),
          java.time.OffsetDateTime.parse("2022-01-03T00:00:00Z"),
        )
      )
      val connection = DriverManager.getConnection(container.jdbcUrl, container.username, container.password)
      QueriesImpl(connection).insertExample(
        row.id,
        row.dates,
        row.timestamps,
        row.uuids,
        row.jsons,
        row.date,
        row.timestamp,
        row.uuid,
        row.json,
        row.offsettimestamp,
        row.offsettimestampArray
      )
      val fetchedRow = QueriesImpl(connection).selectExample(row.id)
      fetchedRow.value shouldEqual row
  }
}


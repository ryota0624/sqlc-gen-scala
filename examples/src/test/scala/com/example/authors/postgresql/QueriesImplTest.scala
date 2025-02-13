package com.example.authors.postgresql

import com.dimafeng.testcontainers.scalatest.TestContainersForAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import com.dimafeng.testcontainers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import org.scalatest.OptionValues.convertOptionToValuable
import java.sql.DriverManager


class QueriesImplTest extends AnyFlatSpec with should.Matchers with TestContainersForAll {
  override type Containers = PostgreSQLContainer

  override def startContainers(): PostgreSQLContainer = {
    val postgres = PostgreSQLContainer(dockerImageNameOverride = DockerImageName.parse("postgres:15"))
    postgres.container.withInitScript("authors/postgresql/schema.sql")
    postgres.container.start()
    postgres
  }

  "create author" should "insert DB Row" in withContainers {
    container =>
      Class.forName(container.driverClassName)
      val author = Author(1, "Brian Kernighan", Some("Co-author of The C Programming Language and The Go Programming Language"))
      val connection = DriverManager.getConnection(container.jdbcUrl, container.username, container.password)
      val result = QueriesImpl(connection).createAuthor(author.name, author.bio)
      result.value shouldEqual author
      val fetchedAuthor = QueriesImpl(connection).getAuthor(author.id)
      fetchedAuthor.value shouldEqual author
  }
}


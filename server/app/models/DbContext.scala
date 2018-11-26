package models

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import java.sql.Timestamp
import org.joda.time.DateTime
import org.joda.time.DateTimeZone.UTC

object DbContext {
  val amlConfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig("aml")
  val amlContext: JdbcProfile#Backend#Database = amlConfig.db

  val amlDocConfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig("amlDoc")
  val amlDocContext: JdbcProfile#Backend#Database = amlDocConfig.db
}
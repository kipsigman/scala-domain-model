package kipsigman.domain.repository.slick

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import play.api.db.slick.HasDatabaseConfig

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

import kipsigman.domain.entity._

trait TableDefinitions {
  protected val driver: JdbcProfile
  import driver.api._

  abstract class IdTable[T <: IdEntity](tag: Tag, tableName: String) extends Table[T](tag, tableName) {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  }
}

trait TableGroupConfig extends TableDefinitions with HasDatabaseConfig[JdbcProfile] {
  protected val dbConfig: DatabaseConfig[JdbcProfile]
}
package lib

import scala.util.Random

object DatabaseSupport {
  def createDatabase(): Database = {
    val db = new DatabaseImpl(Symbol("test" + Random.nextInt(Int.MaxValue)), 3)
    db.transaction { implicit session =>
      Migration.create
    }
    db
  }

  def destroyDatabase(db: Database) {
    try {
      db.transaction { implicit session =>
        Migration.drop
      }
    } finally {
      db.close()
    }
  }
}

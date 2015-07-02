package lib

import scalikejdbc.DBSession

import scala.util.Random

trait SessionSupport {
  def withSession(f: DBSession => Unit) {
    val db = new DatabaseImpl(Symbol("test" + Random.nextInt(Int.MaxValue)), 3)
    db.transaction { implicit session =>
      Migration.create
      f(session)
      Migration.drop
    }
  }
}

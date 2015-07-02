package lib

import scalikejdbc.{NoSession, DB, DBSession}

trait Database {
  def transaction[R](f: DBSession => R): R
}

class DatabaseImpl extends Database {
  def transaction[R](f: DBSession => R): R =
    DB.localTx(f)
}

class FakeDatabaseImpl extends Database {
  def transaction[R](f: DBSession => R): R =
    f(NoSession)
}

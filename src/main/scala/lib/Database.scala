package lib

import scalikejdbc.{ConnectionPool, NoSession, NamedDB, DBSession}

trait Database {
  def transaction[R](f: DBSession => R): R
}

class DatabaseImpl(name: Symbol, traceLevel: Int) extends Database {
  ConnectionPool.add(name, s"jdbc:h2:mem:$name;DB_CLOSE_DELAY=-1;TRACE_LEVEL_FILE=$traceLevel;MODE=PostgreSQL", "", "")

  def transaction[R](f: DBSession => R): R =
    NamedDB(name).localTx(f)
}

class FakeDatabaseImpl extends Database {
  def transaction[R](f: DBSession => R): R =
    f(NoSession)
}

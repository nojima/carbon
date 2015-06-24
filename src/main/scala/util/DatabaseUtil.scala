package util

import slick.driver.H2Driver.api._
import scala.concurrent.Await
import scala.concurrent.duration._

object DatabaseUtil {
  val QUERY_TIMEOUT = 4.seconds

  def runAction[R](db: Database, action: DBIOAction[R, NoStream, Nothing]): R =
    Await.result(db.run(action), QUERY_TIMEOUT)
}

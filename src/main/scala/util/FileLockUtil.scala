package util

import java.io.File

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.{ReentrantLock, Lock}

object FileLockUtil {

  /**
    *  lock objects
    */
  private val locks = new ConcurrentHashMap[String, Lock]()

  /**
    *  Returns the lock object for the specified repository.
    */
  private def getLockObject(key: String): Lock = synchronized {
    if(!locks.containsKey(key)){
      locks.put(key, new ReentrantLock())
    }
    locks.get(key)
  }

  def lockWith[T](key: File)(f: => T): T = {
    val lock = getLockObject(key.getAbsolutePath())
    try {
      lock.lock()
      f
    } finally {
      lock.unlock()
    }
  }

}

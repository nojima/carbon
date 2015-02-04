package services

object LockUtil {

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

  /**
    *  Synchronizes a given function which modifies the working copy of the wiki repository.
    */
  def lock[T](key: String)(f: => T): T = defining(getLockObject(key)){ lock =>
    try {
      lock.lock()
      f
    } finally {
      lock.unlock()
    }
  }

}


class FolderService(baseDir: String) {
  def ensureFolderExists(
      folderOwner: String, folderName: String, folderDescription: String) = {
    LockUtil.lock(s"${folderOwner}/${folderName}") {

  }
}

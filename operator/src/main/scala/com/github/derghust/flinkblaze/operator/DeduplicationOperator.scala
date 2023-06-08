package com.github.derghust.flinkblaze.operator

import com.github.blemale.scaffeine.{Cache, Scaffeine}
import org.apache.flink.api.common.functions.FilterFunction

import scala.concurrent.duration.FiniteDuration

/** Deduplication Operator used with filter operation that will filter out duplicated
  * entries.
  *
  * @param fun
  *   function used for selecting key from value.
  * @param maximumSize
  *   maximum cache size.
  * @param expiration
  *   Expiration time for entry in scaffeine cache.
  * @param countAccess
  *   Enable counting access and write operation for scaffeine cache.
  * @tparam K
  *   data type for key value.
  * @return
  *   the resulting stream containing the asynchronous results
  */
class DeduplicationOperator[T, K](
    fun: T => K,
    maximumSize: Int,
    expiration: Option[FiniteDuration],
    countAccess: Boolean = false
) extends FilterFunction[T] {

  @transient
  private lazy val cache: Cache[K, Long] = {
    val builder = Scaffeine()
      .recordStats()
      .maximumSize(maximumSize)
      .initialCapacity(maximumSize)
    expiration.map(exp => builder.expireAfterWrite(exp))
    builder.build[K, Long]()
  }

  override def filter(value: T): Boolean = {
    val key = fun(value)
    cache.getIfPresent(key) match {
      case Some(count) =>
        if (countAccess) {
          cache.put(key, count + 1)
        }
        false
      case None =>
        cache.put(key, 0L)
        true
    }
  }
}

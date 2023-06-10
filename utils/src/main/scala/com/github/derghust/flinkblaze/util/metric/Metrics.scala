package com.github.derghust.flinkblaze.util.metric

import com.github.derghust.flinkblaze.util.metric.flag.MetricFlag
import com.github.derghust.flinkblaze.util.status.{RSOK, RSWarn, ReturnStatus}
import org.apache.flink.metrics.Counter

/** Metrics wrapper for [[org.apache.flink.metrics]] metrics and counter.
  *
  * @param counters
  *   [[Counter]]
  */
class Metrics(@transient private val counters: Map[MetricFlag, Counter])
    extends Serializable {

  /** Increment the current count by the given value or by default value by 1.
    *
    * @param flag
    *   Metric flag name for give metric.
    * @param count
    *   value to increment the current count by.
    * @return
    */
  def inc(flag: MetricFlag, count: Int = 1): ReturnStatus = {
    counters.get(flag) match {
      case Some(value) =>
        value.inc(count)
        RSOK()
      case None => RSWarn(s"Metric flag was not registered! [flag=$flag]")
    }
  }
}

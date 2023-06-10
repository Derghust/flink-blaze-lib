package com.github.derghust.flinkblaze.util.metric

import com.github.derghust.flinkblaze.util.metric.flag.MetricFlag
import org.apache.flink.api.common.functions.RuntimeContext
import org.apache.flink.metrics.Counter

import scala.collection.mutable

/** Metrics Builder class for [[Metrics]] wrapper for [[org.apache.flink.metrics]]
  * metrics and counter.
  *
  * @param runtimeContext
  *   [[RuntimeContext]] used for registering metrics for [[org.apache.flink.metrics]].
  */
class MetricsBuilder(runtimeContext: RuntimeContext) {

  private val bufferCounter: mutable.Map[MetricFlag, Counter] = mutable.Map()

  /** Register [[MetricFlag]] to [[RuntimeContext]] as [[Counter]] metric.
    *
    * @param flag
    *   Used for register single flag as [[Counter]].
    * @return
    *   [[MetricsBuilder]]
    */
  def registerCounter(flag: MetricFlag): MetricsBuilder = {
    this.bufferCounter.put(flag, runtimeContext.getMetricGroup.counter(flag.toString))
    this
  }

  /** Register [[MetricFlag]] to [[RuntimeContext]] as [[Counter]] metric.
    *
    * @param flags
    *   Used for register list of flags as [[Counter]].
    * @return
    *   [[MetricsBuilder]]
    */
  def registerCounters(flags: List[MetricFlag]): MetricsBuilder = {
    flags.foreach { flag =>
      this.bufferCounter.put(flag, runtimeContext.getMetricGroup.counter(flag.toString))
    }
    this
  }

  /** Finalize and build [[Metrics]].
    *
    * @return
    *   [[Metrics]]
    */
  def build(): Metrics = {
    new Metrics(bufferCounter.toMap)
  }
}

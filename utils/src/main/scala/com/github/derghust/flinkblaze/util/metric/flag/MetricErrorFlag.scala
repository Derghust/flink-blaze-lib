package com.github.derghust.flinkblaze.util.metric.flag

case object Error extends MetricFlag
case object Warn  extends MetricFlag
case object Fatal extends MetricFlag
case object Retry extends MetricFlag

object MetricErrorFlag {
  val flags: List[MetricFlag] = List(Error, Warn, Fatal, Retry)
}

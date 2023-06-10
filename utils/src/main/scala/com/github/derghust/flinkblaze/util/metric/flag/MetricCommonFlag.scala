package com.github.derghust.flinkblaze.util.metric.flag

case object Missing extends MetricFlag
case object Null    extends MetricFlag
case object Some    extends MetricFlag
case object None    extends MetricFlag

object MetricCommonFlag {
  val flags: List[MetricFlag] = List(Missing, Null, Some, None)
}

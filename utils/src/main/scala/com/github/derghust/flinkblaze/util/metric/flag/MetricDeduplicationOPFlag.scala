package com.github.derghust.flinkblaze.util.metric.flag

case object Unique    extends MetricFlag
case object Duplicate extends MetricFlag

object MetricDeduplicationOPFlag {
  val flags: List[MetricFlag] = List(Unique, Duplicate)
}

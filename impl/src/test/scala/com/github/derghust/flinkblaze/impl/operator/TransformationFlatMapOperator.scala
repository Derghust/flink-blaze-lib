package com.github.derghust.flinkblaze.impl.operator

import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.util.Collector

class TransformationFlatMapOperator extends FlatMapFunction[Int, String] {
  override def flatMap(value: Int, out: Collector[String]): Unit =
    out.collect((value + 1).toString)

}

package com.github.derghust.flinkblaze.impl.operator

import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.util.Collector

class IncrementalFlatMapOperator extends FlatMapFunction[Int, Int] {
  override def flatMap(value: Int, out: Collector[Int]): Unit = out.collect(value + 1)
}

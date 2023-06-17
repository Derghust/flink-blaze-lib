package com.github.derghust.flinkblaze.impl.operator

import org.apache.flink.api.common.functions.MapFunction

class IncrementalMapOperator extends MapFunction[Int, Int] {
  override def map(value: Int): Int = value + 1
}

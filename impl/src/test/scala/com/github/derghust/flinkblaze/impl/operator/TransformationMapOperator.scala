package com.github.derghust.flinkblaze.impl.operator

import org.apache.flink.api.common.functions.MapFunction

class TransformationMapOperator extends MapFunction[Int, String] {
  override def map(value: Int): String = (value + 1).toString
}

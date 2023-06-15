package com.github.derghust.flinkblaze.operator

import org.apache.flink.api.common.functions.{FilterFunction, FlatMapFunction}
import org.apache.flink.util.Collector

/** Filter function operator for filtering [[Either]] for left [[L]] side and right
  * [[R]] side. Right side values will be retained and left side values will be filtered
  * out.
  *
  * @tparam L
  *   Either left side.
  * @tparam R
  *   Either right side
  */
class FilterEitherOperator[L, R] extends FlatMapFunction[Either[L, R], R] {

  override def flatMap(value: Either[L, R], out: Collector[R]): Unit = {
    value.map(r => out.collect(r))
  }
}

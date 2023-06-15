package com.github.derghust.flinkblaze.operator

import cats.implicits.toBifunctorOps
import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.util.Collector

/** Filter function operator for filtering [[Either]] for left [[L]] side and right
  * [[R]] side. [[Right]] side values will be retained and [[Left]] side values will be
  * filtered out.
  *
  * @tparam L
  *   Either left side.
  * @tparam R
  *   Either right side
  */
class FilterRightOperator[L, R] extends FlatMapFunction[Either[L, R], R] {

  override def flatMap(value: Either[L, R], out: Collector[R]): Unit = {
    value.map(out.collect)
  }
}

/** Filter function operator for filtering [[Either]] for left [[L]] side and right
  * [[R]] side. [[Left]] side values will be retained and [[Right]] side values will be
  * filtered out.
  *
  * @tparam L
  *   Either left side.
  * @tparam R
  *   Either right side
  */
class FilterLeftOperator[L, R] extends FlatMapFunction[Either[L, R], L] {

  override def flatMap(value: Either[L, R], out: Collector[L]): Unit = {
    value.leftMap(out.collect)
  }
}

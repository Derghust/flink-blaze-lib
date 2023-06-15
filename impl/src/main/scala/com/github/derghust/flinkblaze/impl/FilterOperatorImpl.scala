package com.github.derghust.flinkblaze.impl

import com.github.derghust.flinkblaze.operator.{DeduplicationOperator, FilterLeftOperator, FilterRightOperator}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.datastream.DataStream

import scala.concurrent.duration.FiniteDuration

object FilterOperatorImpl {

  implicit class FilterOperator[IN](ds: DataStream[IN]) {

    /** Wrap method [[DeduplicationOperator]] to [[DataStream]] filter operation that
      * will help you simplify and clean your code. Creates a new DataStream that
      * contains only the elements satisfying the given filter predicate with scaffeine
      * cache for deduplication.
      *
      * @param fun
      *   function used for selecting key from value.
      * @param maximumSize
      *   maximum cache size.
      * @param expiration
      *   Expiration time for entry in scaffeine cache.
      * @param countAccess
      *   Enable counting access and write operation for scaffeine cache.
      * @tparam K
      *   data type for key value.
      * @return
      *   the resulting stream containing the asynchronous results
      */
    def deduplicate[K](
        fun: IN => K,
        maximumSize: Int,
        expiration: Option[FiniteDuration],
        countAccess: Boolean = false
    ): DataStream[IN] = {
      ds.filter(
        new DeduplicationOperator[IN, K](fun, maximumSize, expiration, countAccess)
      )
    }
  }

  implicit class FilterEitherOperatorImpl[L: TypeInformation, R: TypeInformation](
      ds: DataStream[Either[L, R]]
  ) {

    /** Wrap method [[FilterRightOperator]] to [[DataStream]] with type of [[Either]]
      * [[L]] and [[R]] filter operation that will help you simplify and clean your
      * code. Filter function operator for filtering [[Either]] for left [[L]] side and
      * right [[R]] side. [[Right]] side values will be retained and [[Left]] side
      * values will be filtered out.
      *
      * @tparam L
      *   Either left side.
      * @tparam R
      *   Either right side
      */
    def right(): DataStream[R] = {
      ds.flatMap(new FilterRightOperator[L, R]())
    }

    /** Wrap method [[FilterLeftOperator]] to [[DataStream]] with type of [[Either]]
      * [[L]] and [[R]] filter operation that will help you simplify and clean your
      * code. Filter function operator for filtering [[Either]] for left [[L]] side and
      * right [[R]] side. [[Left]] side values will be retained and [[Right]] side
      * values will be filtered out.
      *
      * @tparam L
      *   Either left side.
      * @tparam R
      *   Either right side
      */
    def left(): DataStream[L] = {
      ds.flatMap(new FilterLeftOperator[L, R]())
    }
  }
}

package com.github.derghust.flinkblaze.impl

import com.github.derghust.flinkblaze.impl.AsyncOperatorImpl.{
  AsyncOperator,
  AsyncOrderType,
  AsyncOrderTypeWithRetry
}
import org.apache.flink.api.common.functions.{
  FilterFunction,
  FlatMapFunction,
  MapFunction
}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.functions.async.{
  AsyncFunction,
  AsyncRetryStrategy
}

import scala.concurrent.duration.TimeUnit

object IfOperatorImpl {
  implicit class IfOperator[IN](ds: DataStream[IN]) {

    /** Creates a new DataStream by applying the given function to every element of this
      * DataStream only if state is true.
      *
      * @param function
      *   to use
      * @param state
      *   State from which will be decided if
      * @return
      *   The resulting stream containing the asynchronous results, otherwise if state
      *   is false return unchanged [[DataStream]].
      */
    def mapIf(
        function: MapFunction[IN, IN],
        state: Boolean
    )(implicit ti: TypeInformation[IN]): DataStream[IN] = {
      if (state) {
        ds.map(function).returns(ti)
      } else {
        ds
      }
    }

    /** Creates a new DataStream by applying the given function to every element of this
      * DataStream only if state is true. Capital E in the name represent Either type.
      *
      * @param function
      *   to use.
      * @param state
      *   State from which will be decided if.
      * @tparam OUT
      *   Type of the output record.
      * @return
      *   The resulting stream containing the asynchronous results as [[Right]]
      *   [[DataStream]] side with [[OUT]] type information, otherwise if state is false
      *   return unchanged as [[Left]] [[DataStream]] with type information of [[OUT]].
      */
    def mapIfE[OUT: TypeInformation](
        function: MapFunction[IN, OUT],
        state: Boolean
    )(implicit ti: TypeInformation[OUT]): Either[DataStream[IN], DataStream[OUT]] = {
      if (state) {
        Right(ds.map(function).returns(ti))
      } else {
        Left(ds)
      }
    }

    /** Creates a new DataStream by applying the given function to every element and
      * flattening the results only if state is true.
      *
      * @param function
      *   to use.
      * @param state
      *   State from which will be decided if.
      * @return
      *   The resulting stream containing the asynchronous results, otherwise if state
      *   is false return unchanged [[DataStream]].
      */
    def flatMapIf(
        function: FlatMapFunction[IN, IN],
        state: Boolean
    )(implicit ti: TypeInformation[IN]): DataStream[IN] = {
      if (state) {
        ds.flatMap(function).returns(ti)
      } else {
        ds
      }
    }

    /** Creates a new DataStream by applying the given function to every element and
      * flattening the results only if state is true. Capital E in the name represent
      * Either type.
      *
      * @param function
      *   to use.
      * @param state
      *   State from which will be decided if.
      * @tparam OUT
      *   Type of the output record.
      * @return
      *   The resulting stream containing the asynchronous results as [[Right]]
      *   [[DataStream]] side with [[OUT]] type information, otherwise if state is false
      *   return unchanged as [[Left]] [[DataStream]] with type information of [[OUT]].
      */
    def flatMapIfE[OUT: TypeInformation](
        function: FlatMapFunction[IN, OUT],
        state: Boolean
    )(implicit ti: TypeInformation[OUT]): Either[DataStream[IN], DataStream[OUT]] = {
      if (state) {
        Right(ds.flatMap(function).returns(ti))
      } else {
        Left(ds)
      }
    }

    /** Creates a new DataStream that contains only the elements satisfying the given
      * filter predicate only if state is true.
      *
      * @param function
      *   to use.
      * @param state
      *   State from which will be decided if.
      * @return
      *   The resulting stream containing the asynchronous results, otherwise if state
      *   is false return unchanged [[DataStream]].
      */
    def filterIf(
        function: FilterFunction[IN],
        state: Boolean
    )(implicit ti: TypeInformation[IN]): DataStream[IN] = {
      if (state) {
        ds.filter(function).returns(ti)
      } else {
        ds
      }
    }

    /** Groups the elements of a DataStream by the given key positions (for tuple/array
      * types) to be used with grouped operators like grouped reduce or grouped
      * aggregations only if state is true.
      *
      * @param keySelector
      *   to use.
      * @param state
      *   State from which will be decided if.
      * @tparam K
      *   Type of the keyed record.
      * @return
      *   The resulting stream containing the asynchronous results, otherwise if state
      *   is false return unchanged [[DataStream]].
      */
    def keyByIf[K: TypeInformation](
        keySelector: KeySelector[IN, K],
        state: Boolean
    ): DataStream[IN] = {
      if (state) {
        ds.keyBy(keySelector)
      } else {
        ds
      }
    }

    /** Creates a new DataStream by applying the given function to every element of this
      * DataStream only if state is true. Wrapper method for AsyncDataStream that will
      * help you simplify and clean your code. Apply an asynchronous function on the
      * input data stream. The output order is only maintained with respect to
      * watermarks. Stream records which lie between the same two watermarks, can be
      * re-ordered.
      *
      * @param asyncFunction
      *   to use.
      * @param orderType
      *   control order of the records.
      *   [[https://nightlies.apache.org/flink/flink-docs-release-1.17/docs/dev/datastream/operators/asyncio/#order-of-results]].
      * @param timeout
      *   for the asynchronous operation to complete.
      * @param timeUnit
      *   of the timeout.
      * @param capacity
      *   of the operator which is equivalent to the number of concurrent asynchronous
      *   operations.
      * @param state
      *   State from which will be decided if.
      * @return
      *   the resulting stream containing the asynchronous results, otherwise if state
      *   is false return unchanged [[DataStream]].
      */
    def asyncIf(
        asyncFunction: AsyncFunction[IN, IN],
        orderType: AsyncOrderType,
        timeout: Long,
        timeUnit: TimeUnit,
        capacity: Int = 100,
        state: Boolean
    )(implicit ti: TypeInformation[IN]): DataStream[IN] = {
      if (state) {
        ds.async(asyncFunction, orderType, timeout, timeUnit, capacity)
      } else {
        ds
      }
    }

    /** Creates a new DataStream by applying the given function to every element of this
      * DataStream only if state is true. Wrapper method for AsyncDataStream that will
      * help you simplify and clean your code. Apply an asynchronous function on the
      * input data stream. The output order is only maintained with respect to
      * watermarks. Stream records which lie between the same two watermarks, can be
      * re-ordered. Capital E in the name represent Either type.
      *
      * @param asyncFunction
      *   to use.
      * @param orderType
      *   control order of the records.
      *   [[https://nightlies.apache.org/flink/flink-docs-release-1.17/docs/dev/datastream/operators/asyncio/#order-of-results]].
      * @param timeout
      *   for the asynchronous operation to complete.
      * @param timeUnit
      *   of the timeout.
      * @param capacity
      *   of the operator which is equivalent to the number of concurrent asynchronous
      *   operations.
      * @param state
      *   State from which will be decided if.
      * @tparam OUT
      *   Type of the output record.
      * @return
      *   The resulting stream containing the asynchronous results as [[Right]]
      *   [[DataStream]] side with [[OUT]] type information, otherwise if state is false
      *   return unchanged as [[Left]] [[DataStream]] with type information of [[OUT]].
      */
    def asyncIfE[OUT](
        asyncFunction: AsyncFunction[IN, OUT],
        orderType: AsyncOrderType,
        timeout: Long,
        timeUnit: TimeUnit,
        capacity: Int = 100,
        state: Boolean
    )(implicit ti: TypeInformation[OUT]): Either[DataStream[IN], DataStream[OUT]] = {
      if (state) {
        Right(ds.async(asyncFunction, orderType, timeout, timeUnit, capacity))
      } else {
        Left(ds)
      }
    }

    /** Creates a new DataStream by applying the given function to every element of this
      * DataStream only if state is true. Wrapper method for AsyncDataStream that will
      * help you simplify and clean your code. Apply an asynchronous function on the
      * input data stream. The output order is only maintained with respect to
      * watermarks. Stream records which lie between the same two watermarks, can be
      * re-ordered
      *
      * @param asyncFunction
      *   to use
      * @param orderType
      *   control order of the records.
      *   [[https://nightlies.apache.org/flink/flink-docs-release-1.17/docs/dev/datastream/operators/asyncio/#order-of-results]]
      * @param timeout
      *   for the asynchronous operation to complete.
      * @param timeUnit
      *   of the timeout.
      * @param capacity
      *   of the operator which is equivalent to the number of concurrent asynchronous
      *   operations.
      * @param asyncRetryStrategy
      *   The strategy of reattempt async i/o operation that can be triggered.
      * @param state
      *   State from which will be decided if.
      * @return
      *   the resulting stream containing the asynchronous results, otherwise if state
      *   is false return unchanged [[DataStream]].
      */
    def asyncWithRetryIf(
        asyncFunction: AsyncFunction[IN, IN],
        orderType: AsyncOrderTypeWithRetry,
        timeout: Long,
        timeUnit: TimeUnit,
        capacity: Int = 100,
        asyncRetryStrategy: AsyncRetryStrategy[IN],
        state: Boolean
    )(implicit ti: TypeInformation[IN]): DataStream[IN] = {
      if (state) {
        ds.asyncWithRetry(
          asyncFunction,
          orderType,
          timeout,
          timeUnit,
          capacity,
          asyncRetryStrategy
        )
      } else {
        ds
      }
    }

    /** Creates a new DataStream by applying the given function to every element of this
      * DataStream only if state is true. Wrapper method for AsyncDataStream that will
      * help you simplify and clean your code. Apply an asynchronous function on the
      * input data stream. The output order is only maintained with respect to
      * watermarks. Stream records which lie between the same two watermarks, can be
      * re-ordered. Capital E in the name represent Either type.
      *
      * @param asyncFunction
      *   to use.
      * @param orderType
      *   control order of the records.
      *   [[https://nightlies.apache.org/flink/flink-docs-release-1.17/docs/dev/datastream/operators/asyncio/#order-of-results]].
      * @param timeout
      *   for the asynchronous operation to complete.
      * @param timeUnit
      *   of the timeout.
      * @param capacity
      *   of the operator which is equivalent to the number of concurrent asynchronous
      *   operations.
      * @tparam OUT
      *   Type of the output record.
      * @param asyncRetryStrategy
      *   The strategy of reattempt async i/o operation that can be triggered.
      * @param state
      *   State from which will be decided if.
      * @return
      *   The resulting stream containing the asynchronous results as [[Right]]
      *   [[DataStream]] side with [[OUT]] type information, otherwise if state is false
      *   return unchanged as [[Left]] [[DataStream]] with type information of [[OUT]].
      */
    def asyncWithRetryIfE[OUT](
        asyncFunction: AsyncFunction[IN, OUT],
        orderType: AsyncOrderTypeWithRetry,
        timeout: Long,
        timeUnit: TimeUnit,
        capacity: Int = 100,
        asyncRetryStrategy: AsyncRetryStrategy[OUT],
        state: Boolean
    )(implicit ti: TypeInformation[OUT]): Either[DataStream[IN], DataStream[OUT]] = {
      if (state) {
        Right(
          ds.asyncWithRetry(
            asyncFunction,
            orderType,
            timeout,
            timeUnit,
            capacity,
            asyncRetryStrategy
          )
        )
      } else {
        Left(ds)
      }
    }
  }
}

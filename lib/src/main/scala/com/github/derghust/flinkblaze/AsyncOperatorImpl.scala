package com.github.derghust.flinkblaze

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.scala.async.{AsyncFunction, AsyncRetryStrategy}
import org.apache.flink.streaming.api.scala.{AsyncDataStream, DataStream}

import scala.concurrent.duration.TimeUnit

object AsyncOperatorImpl {

  sealed trait AsyncOrderType
  final case object Unordered extends AsyncOrderType
  final case object Ordered   extends AsyncOrderType

  sealed trait AsyncOrderTypeWithRetry
  final case object UnorderedWithRetry extends AsyncOrderTypeWithRetry
  final case object OrderedWithRetry   extends AsyncOrderTypeWithRetry

  implicit class AsyncOperator[IN](ds: DataStream[IN]) {

    /** Wrapper method for [[AsyncDataStream]] that will help you simplify and clean
      * your code. Apply an asynchronous function on the input data stream. The output
      * order is only maintained with respect to watermarks. Stream records which lie
      * between the same two watermarks, can be re-ordered.
      *
      * @param asyncFunction
      *   to use
      * @param orderType
      *   control order of the records.
      *   [[https://nightlies.apache.org/flink/flink-docs-release-1.17/docs/dev/datastream/operators/asyncio/#order-of-results]]
      * @param timeout
      *   for the asynchronous operation to complete
      * @param timeUnit
      *   of the timeout
      * @param capacity
      *   of the operator which is equivalent to the number of concurrent asynchronous
      *   operations
      * @tparam OUT
      *   Type of the output record
      * @return
      *   the resulting stream containing the asynchronous results
      */
    def async[OUT: TypeInformation](
        asyncFunction: AsyncFunction[IN, OUT],
        orderType: AsyncOrderType,
        timeout: Long,
        timeUnit: TimeUnit,
        capacity: Int = 100
    ): DataStream[OUT] = {
      orderType match {
        case Unordered =>
          AsyncDataStream.unorderedWait(ds, asyncFunction, timeout, timeUnit, capacity)
        case Ordered =>
          AsyncDataStream.orderedWait(ds, asyncFunction, timeout, timeUnit, capacity)
      }
    }

    /** Wrapper method for [[AsyncDataStream]] that will help you simplify and clean
      * your code. Apply an asynchronous function on the input data stream. The output
      * order is only maintained with respect to watermarks. Stream records which lie
      * between the same two watermarks, can be re-ordered.
      *
      * @param asyncFunction
      *   to use
      * @param orderType
      *   control order of the records.
      *   [[https://nightlies.apache.org/flink/flink-docs-release-1.17/docs/dev/datastream/operators/asyncio/#order-of-results]]
      * @param timeout
      *   for the asynchronous operation to complete
      * @param timeUnit
      *   of the timeout
      * @param capacity
      *   of the operator which is equivalent to the number of concurrent asynchronous
      *   operations
      * @tparam OUT
      *   Type of the output record
      * @param asyncRetryStrategy
      *   The strategy of reattempt async i/o operation that can be triggered
      * @return
      *   the resulting stream containing the asynchronous results
      */
    def asyncWithRetry[OUT: TypeInformation](
        asyncFunction: AsyncFunction[IN, OUT],
        orderType: AsyncOrderTypeWithRetry,
        timeout: Long,
        timeUnit: TimeUnit,
        capacity: Int = 100,
        asyncRetryStrategy: AsyncRetryStrategy[OUT]
    ): DataStream[OUT] = {
      orderType match {
        case UnorderedWithRetry =>
          AsyncDataStream.unorderedWaitWithRetry(
            ds,
            asyncFunction,
            timeout,
            timeUnit,
            capacity,
            asyncRetryStrategy
          )
        case OrderedWithRetry =>
          AsyncDataStream.orderedWaitWithRetry(
            ds,
            asyncFunction,
            timeout,
            timeUnit,
            capacity,
            asyncRetryStrategy
          )
      }
    }
  }
}

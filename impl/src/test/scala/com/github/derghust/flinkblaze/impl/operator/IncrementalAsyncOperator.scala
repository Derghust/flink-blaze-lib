package com.github.derghust.flinkblaze.impl.operator

import org.apache.flink.streaming.api.functions.async.{ResultFuture, RichAsyncFunction}

import java.util.Collections
import scala.concurrent.{ExecutionContext, Future}

class IncrementalAsyncOperator extends RichAsyncFunction[Int, Int] {

  implicit lazy val executor: ExecutionContext =
    ExecutionContext.fromExecutor(ExecutionContext.global)

  override def asyncInvoke(input: Int, resultFuture: ResultFuture[Int]): Unit = {
    Future(input + 1).map(value => resultFuture.complete(Collections.singleton(value)))
  }
}

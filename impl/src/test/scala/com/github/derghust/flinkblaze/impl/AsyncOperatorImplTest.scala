package com.github.derghust.flinkblaze.impl

import com.github.derghust.flinkblaze.impl.AsyncOperatorImpl.AsyncOperator
import com.github.derghust.flinkblaze.impl.operator.IncrementalAsyncOperator
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.scalatest.BeforeAndAfterAll
import org.scalatest.enablers.Containing
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.concurrent.TimeUnit
import scala.collection.convert.ImplicitConversions.`iterator asScala`


class AsyncOperatorImplTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

  var env: StreamExecutionEnvironment = _

  override def beforeAll(): Unit = {
    env = StreamExecutionEnvironment.getExecutionEnvironment
  }

  override def afterAll(): Unit = {}

  "IncrementAsyncFunction" should "increment the input by 1" in {
    val input: Int          = 10
    val expectedOutput: List[Int] = List(input + 1)

    val ds: DataStream[Int] = env.fromElements(input)

    val resultStream: DataStream[Int] = ds.async(
      new IncrementalAsyncOperator(),
      AsyncOperatorImpl.Ordered,
      10,
      TimeUnit.SECONDS
    )(TypeInformation.of(classOf[Int]), TypeInformation.of(classOf[Int]))

    resultStream.collectAsync().map { result =>
      expectedOutput should contain(result)
    }

    env.execute("Async Operator Test")
  }
}

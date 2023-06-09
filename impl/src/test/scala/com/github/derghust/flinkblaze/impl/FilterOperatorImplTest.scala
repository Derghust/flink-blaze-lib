package com.github.derghust.flinkblaze.impl

import com.github.derghust.flinkblaze.impl.FilterOperatorImpl.FilterEitherOperatorImpl
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.convert.ImplicitConversions.`iterator asScala`

class FilterOperatorImplTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

  var env: StreamExecutionEnvironment = _

  override def beforeAll(): Unit = {
    env = StreamExecutionEnvironment.getExecutionEnvironment
  }

  override def afterAll(): Unit = {}

  "RightEitherFilter" should "return right" in {
    val input: Either[Int, Int]   = Right(5)
    val expectedOutput: List[Int] = List(5)

    val ds: DataStream[Either[Int, Int]] = env.fromElements(input)

    val resultStream = ds.right()(TypeInformation.of(classOf[Int]))

    resultStream.collectAsync().map { result =>
      expectedOutput should contain(result)
    }

    env.execute("Operator Test")
  }

  "LeftEitherFilter" should "return right" in {
    val input: Either[Int, Int] = Left(5)
    val expectedOutput: List[Int] = List(5)

    val ds: DataStream[Either[Int, Int]] = env.fromElements(input)

    val resultStream = ds.left()(TypeInformation.of(classOf[Int]))

    resultStream.collectAsync().map { result =>
      expectedOutput should contain(result)
    }

    env.execute("Operator Test")
  }
}

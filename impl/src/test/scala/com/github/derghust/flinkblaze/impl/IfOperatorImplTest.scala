package com.github.derghust.flinkblaze.impl

import cats.implicits.toBifunctorOps
import com.github.derghust.flinkblaze.impl.IfOperatorImpl.IfOperator
import com.github.derghust.flinkblaze.impl.operator.IncrementalMapOperator
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.convert.ImplicitConversions.`iterator asScala`

class IfOperatorImplTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

  var env: StreamExecutionEnvironment = _

  override def beforeAll(): Unit = {
    env = StreamExecutionEnvironment.getExecutionEnvironment
  }

  override def afterAll(): Unit = {}

  "IncrementFunction" should "increment the input by 1" in {
    val input: Int                = 10
    val expectedOutput: List[Int] = List(input + 1)

    val ds: DataStream[Int] = env.fromElements(input)

    implicit val intTypeInformation: TypeInformation[Int] =
      TypeInformation.of(classOf[Int])

    val resultStream = ds.mapIf(new IncrementalMapOperator(), state = true)

    resultStream.collectAsync().map { result =>
      expectedOutput should contain(result)
    }

    env.execute("Operator Test")
  }

  "Disabled IncrementFunction" should "not increment the input by 1" in {
    val input: Int                = 10
    val expectedOutput: List[Int] = List(input + 1)

    val ds: DataStream[Int] = env.fromElements(input)

    implicit val intTypeInformation: TypeInformation[Int] =
      TypeInformation.of(classOf[Int])

    val resultStream = ds.mapIf(new IncrementalMapOperator(), state = false)

    resultStream.collectAsync().map { result =>
      expectedOutput should not contain (result)
    }

    env.execute("Operator Test")
  }

  "TransformationOperator" should "increment the input by 1 and return string as type" in {
    val input: Int                   = 10
    val expectedOutput: List[String] = List((input + 1).toString)

    val ds: DataStream[Int] = env.fromElements(input)

    implicit val intTypeInformation: TypeInformation[Int] =
      TypeInformation.of(classOf[Int])

    val resultStream = ds.mapIfE(new IncrementalMapOperator(), state = true)

    resultStream.map(_.collectAsync().map { result =>
      expectedOutput should contain(result)
    })

    env.execute("Operator Test")
  }

  "Disabled TransformationOperator" should "not increment the input by 1 and return string as type" in {
    val input: Int                = 10
    val expectedOutput: List[Int] = List(1)

    val ds: DataStream[Int] = env.fromElements(input)

    implicit val intTypeInformation: TypeInformation[Int] =
      TypeInformation.of(classOf[Int])

    val resultStream = ds.mapIfE(new IncrementalMapOperator(), state = false)

    resultStream.leftMap(_.collectAsync().map { result =>
      expectedOutput should contain(result)
    })

    env.execute("Operator Test")
  }
}

package com.github.derghust.flinkblaze.impl

import cats.implicits.toBifunctorOps
import com.github.derghust.flinkblaze.impl.IfOperatorImpl.IfOperator
import com.github.derghust.flinkblaze.impl.operator.{
  IncrementalFlatMapOperator,
  IncrementalMapOperator,
  TransformationFlatMapOperator,
  TransformationMapOperator
}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.convert.ImplicitConversions.`iterator asScala`

class IfOperatorImplTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

  var env: StreamExecutionEnvironment = _

  // Regular input and output

  val input: Int                = 10
  val expectedOutput: List[Int] = List(input + 1)

  implicit val intTypeInformation: TypeInformation[Int] =
    TypeInformation.of(classOf[Int])

  // Transformation input and output

  val expectedOutputTransformation: List[String] = List((input + 1).toString)

  implicit val stringTypeInformation: TypeInformation[String] =
    TypeInformation.of(classOf[String])

  // Test DataStream

  var ds: DataStream[Int] = _

  override def beforeAll(): Unit = {
    env = StreamExecutionEnvironment.getExecutionEnvironment

    ds = env.fromElements(input)
  }

  override def afterAll(): Unit = {}

  "MapIf" should "execute operator" in {
    val resultStream = ds.mapIf(new IncrementalMapOperator(), state = true)

    resultStream.collectAsync().map { result =>
      expectedOutput should contain(result)
    }

    env.execute("Operator Test")
  }

  "Disabled MapIf" should "not execute operator" in {
    val resultStream = ds.mapIf(new IncrementalMapOperator(), state = false)

    resultStream.collectAsync().map { result =>
      expectedOutput should not contain (result)
    }

    env.execute("Operator Test")
  }

  "MapIfE" should "execute operator" in {
    val resultStream = ds.mapIfE(new TransformationMapOperator(), state = true)

    resultStream.map(_.collectAsync().map { result =>
      expectedOutputTransformation should contain(result)
    })

    env.execute("Operator Test")
  }

  "Disabled MapIfE" should "not execute operator" in {
    val resultStream = ds.mapIfE(new TransformationMapOperator(), state = false)

    resultStream.leftMap(_.collectAsync().map { result =>
      expectedOutputTransformation should contain(result)
    })

    env.execute("Operator Test")
  }

  "FlatMapIf" should "execute operator" in {
    val resultStream = ds.flatMapIf(new IncrementalFlatMapOperator(), state = true)

    resultStream.collectAsync().map { result =>
      expectedOutput should contain(result)
    }

    env.execute("Operator Test")
  }

  "Disabled FlatMapIf" should "not execute operator" in {
    val resultStream = ds.flatMapIf(new IncrementalFlatMapOperator(), state = false)

    resultStream.collectAsync().map { result =>
      expectedOutput should not contain (result)
    }

    env.execute("Operator Test")
  }

  "FlatMapIfE" should "execute operator" in {
    val resultStream = ds.flatMapIfE(new TransformationFlatMapOperator(), state = true)

    resultStream.map(_.collectAsync().map { result =>
      expectedOutputTransformation should contain(result)
    })

    env.execute("Operator Test")
  }

  "Disabled FlatMapIfE" should "not execute operator" in {
    val resultStream = ds.flatMapIfE(new TransformationFlatMapOperator(), state = false)

    resultStream.leftMap(_.collectAsync().map { result =>
      expectedOutputTransformation should contain(result)
    })

    env.execute("Operator Test")
  }
}

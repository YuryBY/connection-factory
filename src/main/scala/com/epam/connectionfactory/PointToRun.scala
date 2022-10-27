package com.epam.connectionfactory

import com.databricks.spark.xml.XmlDataFrameReader
import org.apache.spark.sql.SparkSession
import org.slf4j.{Logger, LoggerFactory}

import java.util.Calendar

/**
 * @author ${user.name}
 */
object PointToRun {

  def main(args: Array[String]) {
    val log: Logger = LoggerFactory.getLogger(getClass)

    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("Connection-Factory-2.0")
      .getOrCreate()

    val path = if (args.nonEmpty) args(0) else getClass.getResource("/input_file.xml").toString

    log.info(s"xml file path: $path")

    val df = spark.read
      .option("rowTag", "x:books")
      .xml(path)

    df.printSchema()

    df.show(1, false)

    1 to 100 foreach { _ =>
      val now = Calendar.getInstance()
      val currentHour = now.get(Calendar.HOUR)
      val currentMinute = now.get(Calendar.MINUTE)
      val currentSecond = now.get(Calendar.SECOND)
      log.info(s"Current time - $currentHour:$currentMinute:$currentSecond")
      Thread.sleep(100000) // wait for 100000 millisecond
    }

  }

}

package com.epam.connectionfactory

import com.databricks.spark.xml.XmlDataFrameReader
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.slf4j.{Logger, LoggerFactory}

import java.util.Calendar

/**
 * @author ${user.name}
 */
object PointToRun {

  def main(args: Array[String]) {
    val log: Logger = LoggerFactory.getLogger(getClass)

    val spark: SparkSession = SparkSession.builder()
      //.master("local[1]")
      .appName("Connection-Factory-2.0")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val schema = StructType(
      List(
        StructField("RecordNumber", IntegerType, true),
        StructField("Zipcode", StringType, true),
        StructField("ZipCodeType", StringType, true),
        StructField("City", StringType, true),
        StructField("State", StringType, true),
        StructField("LocationType", StringType, true),
        StructField("Lat", StringType, true),
        StructField("Long", StringType, true),
        StructField("Xaxis", StringType, true),
        StructField("Yaxis", StringType, true),
        StructField("Zaxis", StringType, true),
        StructField("WorldRegion", StringType, true),
        StructField("Country", StringType, true),
        StructField("LocationText", StringType, true),
        StructField("Location", StringType, true),
        StructField("Decommisioned", StringType, true)
      )
    )

    val path = if (args.nonEmpty) args(0) else getClass.getResource("/input_file.xml").toString

    val df = spark.readStream
      .schema(schema)
      .json(path)

    df.printSchema()

    val groupDF = df.select("Zipcode")
      .groupBy("Zipcode").count()
    groupDF.printSchema()

    groupDF.writeStream
      .format("console")
      .outputMode("complete")
      .start()
      .awaitTermination()

    1 to 1 foreach { _ =>
      val now = Calendar.getInstance()
      val currentHour = now.get(Calendar.HOUR)
      val currentMinute = now.get(Calendar.MINUTE)
      val currentSecond = now.get(Calendar.SECOND)
      log.error(s"Current time - $currentHour:$currentMinute:$currentSecond")
      Thread.sleep(1000) // wait for 100000 millisecond
    }

  }

}

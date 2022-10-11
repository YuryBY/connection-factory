package com.epam.connectionfactory

import com.databricks.spark.xml.XmlDataFrameReader
import org.apache.spark.sql.SparkSession

/**
 * @author ${user.name}
 */
object App {

  def main(args: Array[String]) {

    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("sf20")
      .getOrCreate()

    val df = spark.read
      .option("rowTag", "x:books")
      .xml(getClass.getResource("/input_file.xml").toString)

    df.printSchema()

    df.show(1, false)
  }

}

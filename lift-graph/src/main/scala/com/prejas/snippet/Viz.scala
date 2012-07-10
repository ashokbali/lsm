package com.prejas.snippet

import scala.xml.NodeSeq
import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js._
import net.liftweb.widgets.flot._
import net.liftweb.common._
import JE._
import com.mongodb.casbah.MongoConnection._
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._

class Viz {

  val data = List(50,80) // range 0-100
  val bar_labels = List("Fred", "Bert")
  
  val mongoConn = MongoConnection()
    val mongoDB = mongoConn("casbah_test")("foo")
    val builder = MongoDBObject.newBuilder
    builder += "width" -> "500"
    builder += "height" -> "400"
   // builder += ("pie" -> 3.14)
    //builder += ("spam" -> "eggs", "mmm" -> "bacon")
    val newObj = builder.result
    mongoDB.insert(newObj)
    println("inserted") 
  
  
  val collection = mongoConn("casbah_test")("foo")
  var width = ""
  var height = ""
  var u = collection.find()
  for(x <- u){             
  	width = x.getOrElse("width","").asInstanceOf[String]
	height = x.getOrElse("height","").asInstanceOf[String]
	println("Width1 : "+width)
  } 
println("Show Records"+width+"  "+height)	 
  //val width = width1
  //val height = height1

  def googleUrl = "http://chart.apis.google.com/chart?" + List(
        "chxt=x,y", 
        "chxl=0:|" + bar_labels.mkString("|"), 
        "chs=%sx%s".format(width,height), 
        "cht=bvg", 
        "chco=A2C180", 
        "chd=t:"+data.mkString(",") ).mkString("&")

  def google(xhtml: NodeSeq) = <img src={googleUrl} width={width.toString} height={height.toString} alt="graph"/>
                                                 

  // -- FLOT EXAMPLE: -----------------------------------------------------

  def flot(xhtml: NodeSeq) = {
    
    // One FlotSerie for each bar
    val data_to_plot = for ( (y,x) <- data zipWithIndex ) yield new FlotSerie() {
        override val data : List[(Double,Double)] = (x.toDouble, y.toDouble) :: Nil
        override val label = Full( bar_labels(x) ) 
    } 

    val options:FlotOptions = new FlotOptions () {
        override val series = Full( Map( "bars" -> JsObj("show"->true, "barWidth"->0.5) ) )
       
        override val xaxis = Full( new FlotAxisOptions() {
            override def min = Some(0d)
            override def max = Some(data.length * 1d)
        })
        
        override def grid = Full( new FlotGridOptions() {
        	override def hoverable = Full(true)
        })
    }
  
    Flot.render("graph_area", data_to_plot, options, Flot.script(xhtml))
  }
}
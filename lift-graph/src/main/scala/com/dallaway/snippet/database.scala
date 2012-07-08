package com.dallaway.snippet

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



object database {
  def connectDb (String : NodeSeq)  = {
    val mongoConn = MongoConnection()
    val mongoDB = mongoConn("casbah_test")("foo")
    val builder = MongoDBObject.newBuilder
    builder += "foo" -> "bar"
    builder += "x" -> "y"
    builder += ("pie" -> 3.14)
    builder += ("spam" -> "eggs", "mmm" -> "bacon")
    val newObj = builder.result
    mongoDB.insert(newObj)
    println("inserted") 

  }


  def findrecords (String : NodeSeq) =  {
    val mongoConn = MongoConnection()
    val collection = mongoConn("casbah_test")("foo")

    /*for (dbo <- collection.find()) {    
      print("hi")
      println("Records :: "+dbo)
    }*/
    var u = collection.find()
     for(x <- u){        
	  
	println(x.getAs[String]("foo"))          
	println(x.getAs[String]("x"))
     } 
    println("Show Records")
  }
}


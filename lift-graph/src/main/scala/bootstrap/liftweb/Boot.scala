

package bootstrap.liftweb

import _root_.net.liftweb.http.{LiftRules, NotFoundAsTemplate, ParsePath}
import _root_.net.liftweb.sitemap.{SiteMap, Menu, Loc}
import _root_.net.liftweb.util.{ NamedPF }
import com.mongodb.casbah.MongoConnection._
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.global._

class Boot {
  def boot {
  
  
    // where to search snippet
    LiftRules.addToPackages("com.prejas")

    // build sitemap
    val entries = List(Menu("Home") / "index") :::
                  Nil
    
    LiftRules.uriNotFound.prepend(NamedPF("404handler"){
      case (req,failure) => NotFoundAsTemplate(
        ParsePath(List("exceptions","404"),"html",false,false))
    })

import net.liftweb.widgets.flot._
Flot.init
    
    LiftRules.setSiteMap(SiteMap(entries:_*))
    
    // set character encoding
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    
    
  }

  def connectDb {
    val mongoConn = MongoConnection()
    val mongoDB = mongoConn("casbah_test")("foo")
    val builder = MongoDBObject.newBuilder
    builder += "width" -> "50"
    builder += "height" -> "40"
   // builder += ("pie" -> 3.14)
    //builder += ("spam" -> "eggs", "mmm" -> "bacon")
    val newObj = builder.result
    mongoDB.insert(newObj)
    println("inserted") 

  }
}
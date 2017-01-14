package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by hungai on 1/14/17.
  */

case class Question(userId:Long,
                    title:String,
                    description:String,
                    hasAcceptedAnswer:Boolean = false,
                    questionId:Long = 0L)

object Question {

  /**
    * Serialization and Deserialization of the Question Model
    */

  implicit val questionReads:Reads[Question] = (
    (JsPath \ "userId").read[Long] and
      (JsPath \ "title").read[String] and
      (JsPath \ "description").read[String] and
      (JsPath \ "hasAcceptedAnswer").read[Boolean] and
      (JsPath \ "questionId").read[Long]
    )(Question.apply _)

  implicit val questionWrites: Writes[Question] = (
    (JsPath \ "userId").write[Long] and
      (JsPath \ "title").write[String] and
      (JsPath \ "description").write[String] and
      (JsPath \ "hasAcceptedAnswer").write[Boolean] and
      (JsPath \ "questionId").write[Long]
    )(unlift(Question.unapply))

}

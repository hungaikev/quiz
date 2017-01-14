package models


import play.api.libs.json._
import play.api.libs.functional.syntax._


/**
  * Created by hungai on 1/14/17.
  */
case class Answer(
                   userId:Long,
                   questionId:Long,
                   description:String,
                   isAccepted:Boolean = false,
                   answerId:Long = 0L)

object Answer {

  /**
    * Serialization and Deserialization of The Answer Model
    */

  implicit val answerReads:Reads[Answer] = (
    (JsPath \ "userId").read[Long] and
      (JsPath \ "questionId").read[Long] and
      (JsPath \ "description").read[String] and
      (JsPath \ "isAccepted").read[Boolean] and
      (JsPath \ "answerId").read[Long]
    )(Answer.apply _)

  implicit val answerWrites:Writes[Answer] = (
    (JsPath \ "userId").write[Long] and
      (JsPath \ "questionId").write[Long] and
      (JsPath \ "description").write[String] and
      (JsPath \ "isAccepted").write[Boolean] and
      (JsPath \ "answerId").write[Long]
    )(unlift(Answer.unapply))
}

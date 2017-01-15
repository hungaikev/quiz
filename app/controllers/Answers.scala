package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import javax.inject.Inject
import dao._
import models._
import play.api.libs.json._

/**
  * Created by hungai on 1/15/17.
  */
class Answers @Inject() (answersDao: AnswersDao) extends Controller {


  /**
    * Add an Answer to a question
    * @param userId
    * @param questionId
    * @param description
    * @return
    */
  def addAnswer(userId:Long,questionId:Long,description:String) = Action.async { implicit rs =>
    val answer = Answer(userId,questionId,description)
    answersDao.addAnswer(answer).map( ans => Ok(Json.obj("success" -> "Answer Created")))
      .recover { case e => InternalServerError(s"Error Creating Questions ->: ${e}") }

  }

  /**
    * Get all answers to a particular Question
    * @param id
    * @return
    */
  def answers(id:Long) = Action.async { implicit  rs =>
    answersDao.getAnswers(id).map( ans =>
      Ok(Json.toJson(ans)))
  }



  /**
    *  Get an answer to a particular Question
    * @param questionId
    * @param answerId
    * @return
    */
  def answer(questionId:Long,answerId:Long) = Action.async { implicit rs =>
    answersDao.findAnswerById(answerId).map(ans =>
      Ok(Json.toJson(ans)))
  }

  /**
    * Update an Answer
    * @param questionId
    * @param answerId
    * @return
    */
  def updateAnswer(questionId:Long,answerId:Long, description:String) = Action.async { implicit rs =>
    answersDao.updateAnswer(answerId,description).map(rs =>
      Ok(Json.obj("successfully updated" -> s"Question with id $questionId")))

  }


  /**
    * Get the number of Answers for a particular Question
    * Use Params
    * @param id
    * @return
    */
  def answersCount(id:Long)  = Action.async { implicit rs =>
    answersDao.getAnswersCount(id).map(rs =>
      Ok(Json.obj(s"The number of answers for question $id " -> rs)))
  }

  /**
    *  Accept an Answer to a particular Question
    *  Use Params
    * @param questionId
    * @param answerId
    * @return
    */
  def accept(questionId:Long,answerId:Long) = Action.async{ implicit rs =>
    answersDao.acceptAnswer(questionId,answerId).map( rs =>
      Ok(Json.obj("success" -> s"Question $questionId 's answers with id $answerId has been updated")))
  }



  /**
    * Get the accepted answer
    * @param questionId
    * @return
    */
  def acceptedAnswers(questionId:Long) = Action.async { implicit rs =>
    answersDao.getAcceptedAnswers(questionId).map(ans => Ok(Json.toJson(ans)))
  }

  /**
    * Delete an Answer
    * @param answerId
    * @return
    */
  def deleteAnswer(questionId:Long,answerId:Long) = Action.async { implicit rs =>
    for {
      _ <- answersDao.deleteAnswer(answerId)
    } yield Ok(Json.obj("success" -> "Deleted Answer"))

  }




}

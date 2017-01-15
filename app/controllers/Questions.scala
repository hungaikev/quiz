package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import javax.inject.Inject
import dao._
import models._
import play.api.libs.json._


/**
  * Created by hungai on 1/14/17.
  */
class Questions @Inject() (questionsDao: QuestionsDao) extends Controller {

  /**
    * Get all the questions
    * @return JSON array of all persisted questions
    */

  def questions = Action.async { implicit rs =>
    questionsDao.allQuestions.map( qs => Ok(Json.toJson(qs)))
      .recover { case e => InternalServerError(s"Error selecting all questions: ${e}") }
  }


  /**
    *  Get all the unanswered Questions
    *  @return JSON array of all persisted unanswered questions
    */
  def unanswered = Action.async { implicit rs =>
    questionsDao.getUnanswered.map(un => Ok(Json.toJson(un)))
  }

  /**
    * Get all the answered Questions
    * @return JSON array of all persisted answered questions
    */

  def answered = Action.async { implicit rs =>
    questionsDao.getAnswered.map( an => Ok(Json.toJson(an)))
  }

  /**
    * Ask a Question
    * @param userId
    * @param title
    * @param description
    * @return
    */
  def ask(userId:Long,title:String,description:String) = Action.async { implicit rs =>
    val question = Question(userId,title,description)
    questionsDao.addQuestion(question).map( an => Ok(Json.obj("success" -> "Question created")))
      .recover { case e => InternalServerError(s"Error Creating Questions -> ${question.questionId}: ${e}") }

  }

  /**
    * Get a Question
    * @param id
    * @return JSON representation of a question
    */
  def question(id:Long) = Action.async { implicit rs =>
    questionsDao.findQuestionById(id).map(q => Ok(Json.toJson(q)))
      .recover { case e => InternalServerError(s"Error selecting question with id -> ${id}: ${e}") }
  }


  /**
    * Update a Question
    * @param id
    * @return
    */
  def updateQuestion(id:Long) = TODO


  /**
    * Delete a Question
    * @param id
    * @return
    */
  def deleteQuestion(id:Long) = Action.async { implicit rs =>
    for {
      _   <- questionsDao.deleteQuestion(id)
    } yield  Ok(Json.obj("success" -> "Deleted Question"))
  }


}

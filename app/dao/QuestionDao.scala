package dao


import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
import models._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

/**
  * Created by hungai on 1/14/17.
  */


trait QuestionsComponent {

  self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  class Questions(tag: Tag) extends Table[Question](tag,"questions"){

    def questionId = column[Long]("question_id",O.PrimaryKey,O.AutoInc)
    def userId = column[Long]("user_id")
    def title = column[String]("title")
    def description = column[String]("description")
    def hasAcceptedAnswer = column[Boolean] ("has_accepted_answer")

    def * = (userId,title,description,hasAcceptedAnswer,questionId) <> ((Question.apply _).tupled,Question.unapply _)
  }
}


@Singleton
class QuestionsDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends QuestionsComponent with HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  lazy val questions = TableQuery[Questions]

  lazy val insertQuestion = questions returning questions.map(_.questionId)

  /**
    * Save a question to the database
    * @param question
    * @return
    */
  def addQuestion(question: Question) : Future[Long] = db.run(insertQuestion += question)

  /**
    * Save a sequence or list of questions to the database
    * @param questions
    * @return
    */
  def addQuestions(questions: Seq[Question]): Future[Unit] = db.run(this.questions ++= questions).map(_ => ())

  /**
    * Get all Unanswered Questions
    * Filter -> Has Accepted Answer  (False)
    * @return
    */
  def getUnanswered:Future[Seq[Question]] = db.run(questions.filter(_.hasAcceptedAnswer === false).result)

  /**
    * Get all Answered Questions
    * Filter -> Has Accepted Answer (True)
    * @return
    */
  def getAnswered : Future[Seq[Question]] = db.run(questions.filter(_.hasAcceptedAnswer === true).result)

  /**
    * Get a Question using a question Id
    * @param questionId
    * @return
    */
  def findQuestionById(questionId:Long): Future[Question] = db.run(questions.filter(_.questionId === questionId).result.head)

  /**
    * Get all questions stored in the database
    * @return
    */
  def allQuestions:Future[Seq[Question]] = db.run(questions.result)

  /**
    * Get the Number of Questions in the Database
    * @return
    */
  def getQuestionCount(): Future[Int] = db.run(questions.length.result)

  /**
    * Delete a Question from the Database
    * @param id
    * @return
    */
  def deleteQuestion(id:Long): Future[Unit] = db.run(questions.filter(_.questionId === id).delete).map(_ => ())




}

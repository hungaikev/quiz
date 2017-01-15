package dao

import javax.inject.{Inject, Singleton}
import models._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
  * Created by hungai on 1/14/17.
  */



@Singleton
class AnswersDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends QuestionsComponent with HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  class Answers(tag: Tag) extends Table[Answer](tag, "answers") {
    def answerId = column[Long]("answer_id", O.PrimaryKey, O.AutoInc)

    def userId = column[Long]("user_id")

    def questionId = column[Long]("question_id")

    def description = column[String]("description")

    def isAccepted = column[Boolean]("is_accepted")

    def question = foreignKey("fk_question", questionId, questions)(_.questionId)

    def * = (userId, questionId, description, isAccepted, answerId) <> ((Answer.apply _).tupled, Answer.unapply _)

  }


  lazy val questions = TableQuery[Questions]

  lazy val answers = TableQuery[Answers]

  lazy val insertAnswer = answers returning answers.map(_.questionId)


  /**
    * Saving an answer in the db
    * @param answer
    * @return
    */
  def addAnswer(answer: Answer): Future[Unit] = db.run(insertAnswer += answer).map(_ => ())

  def addAnswers(answers: Seq[Answer]):Future[Unit] = db.run(this.answers ++= answers).map(_ =>())

  def updateAnswer(id:Long,description:String): Future[Unit] =
    db.run(answers.filter(_.answerId === id).map(a => (a.description) ).update(description)).map(_ => ())

  def getAnswers(questionId:Long):Future[Seq[Answer]] = db.run(answers.filter(_.questionId === questionId).result)

  def getAcceptedAnswers(questionId:Long): Future[Seq[Answer]] =
    db.run(answers.filter(_.questionId === questionId).filter(_.isAccepted).result)

  def getAnswersCount(questionId:Long): Future[Int] = db.run(answers.filter(_.questionId === questionId).length.result)

  def findAnswerById(answerId:Long):Future[Seq[Answer]] = db.run(answers.filter( _.answerId === answerId).result)

  def acceptAnswersTable(answerId:Long): Future[Unit] =
    db.run(answers.filter(_.answerId === answerId).map(_.isAccepted).update(true)).map(_ => ())

  def acceptQuestionAnswer(questionId:Long):Future[Unit] =
    db.run(questions.filter(_.questionId === questionId).map(_.hasAcceptedAnswer).update(true)).map(_ => ())

  def acceptAnswer(questionId:Long,answerId:Long): Future[Unit] = for {
    _   <- acceptAnswersTable(answerId)
    _   <- acceptQuestionAnswer(questionId)
  } yield ()

  def deleteAnswer(id:Long): Future[Unit] = db.run(answers.filter(_.answerId === id).delete).map(_ => ())



}

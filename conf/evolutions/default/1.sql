# --- !Ups

CREATE TABLE "questions" (
  "question_id"  SERIAL NOT NULL PRIMARY KEY,
  "user_id" BIGINT NOT NULL,
  "title" VARCHAR NOT NULL,
  "description" VARCHAR  NOT NULL,
  "has_accepted_answer" BOOLEAN NOT NULL
);


CREATE TABLE "answers"(
 "answer_id" SERIAL NOT NULL PRIMARY KEY,
 "user_id" BIGINT NOT NULL,
 "question_id" BIGINT NOT NULL,
 "description" VARCHAR NOT NULL,
 "is_accepted" BOOLEAN NOT NULL
);

ALTER TABLE answers ADD FOREIGN KEY (question_id) REFERENCES questions (question_id)

# --- !Downs
DROP TABLE answers;
DROP TABLE questions;

## Quiz Service

### Create your Database

    CREATE DATABASE "learning" WITH ENCODING 'UTF8';
    CREATE USER "student" WITH PASSWORD 'trustNo1';
    GRANT ALL ON DATABASE "learning" TO student;


### Clone the repo
    git clone https://github.com/hungaikev/quiz.git
    cd quiz


### Build the code
You need to download and install sbt for this application to run. ( Might take a while if you have slow internet)
If this is your first time running SBT, you will be downloading the internet.

    cd quiz
    sbt compile


### Running
Once you have sbt installed, the following at the command prompt will start up Play in development mode:

```
sbt run
```

Play will start up on the HTTP port at http://localhost:9000/.   You don't need to reploy or reload anything -- changing any source code while the server is running will automatically recompile and hot-reload the application on the next HTTP request.

### Usage

If you call the same URL from the command line, you’ll see JSON. Using httpie, we can execute the command:

```
http --verbose http://localhost:9000/api/messages

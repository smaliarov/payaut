Homework assignment
-----

Create a Dockerized SpringBoot Application which run on Java 11 (we prefer Kotlin - with Java 11 target - but feel free to use pure Java if you more comfortable, but we would like to ask to use Kotlin for least one class)
use build tool Maven, Gradle, Bazel (we do prefer Maven but feel free to use your favorite)
use h2database as persistent storage or whatever you prefer but everything should packed in to a single docker image
please use Git as revision control and commit often as possible, we would like to see the progression of the project
send us the zip file contains the git repo+the working copy together (it is also good if you send us link to repository
Please send us your result even if you do not feel complete, we would like to see the progression and way of thinking, the final result is nice but not required.

Would like to kindly ask to keep in your mind:
- we prefer clean code (OOP and/or Functional programming - OOP most important for us)
- we prefer automated tested code

The application is a simple bookkeeping application, should provide the following functionality through REST interface
- create a new account
- debit on a specified account
- credit on a specified account
- transfer between two accounts
- balance of an account
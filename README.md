# OTUS-homework
Homework project for Java QA Engineer course

##Introduction
This project is created for practice of technology of Page Object.

## Set up
To run this project you need to set value to next properties:
* browserName, type of browser you're going to use, e.g."chrome"
* email, email, registered on the website
* password, password to log in

Use the next command in command line:

```bash
mvn clean test -Dbrowser="[browserName]" -Demail="[email]" -Dpassword="[password]"
```
This is a skeleton of Spring Boot application which should be used as a start point to create a working one.
The goal of this task is to create simple web application which allows users to create TODOs via REST API.

Below you may find a proposition of the DB model:

![DB model](DBModel.png)

To complete the exercices please implement all missing classes and functonalites in order to be able to store and retrieve information about tasks and their categories.
Once you are ready, please send it to me (ie link to your git repository) before  our interview.


TESTS

Find

http://localhost:8080/api/category

http://localhost:8080/api/task

Create

curl --location --request POST 'http://localhost:8080/api/task' --header 'Content-Type: application/json' --data-raw '{"name": "Flights", "description": "Buy London flights"}'

curl --location --request POST 'http://localhost:8080/api/category' --header 'Content-Type: application/json' --data-raw '{"name": "Urgent", "description": "Task is urgent" }'

Delete

curl -X DELETE http://localhost:8080/api/task/1

curl -X DELETE http://localhost:8080/api/category/1


Update

curl -X PUT http://localhost:8080/api/category/2 -H "Content-Type: application/json" -d '{"name":"Families"}' 

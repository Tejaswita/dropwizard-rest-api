dropwizard-rest-api
===================

About the Example:

The sample code demonstrates simple REST API using dropwizard.

**Stack**

1. Dropwizard
1. Gradle
1. Postgresql
1. Heroku


It uses Gradle for build and has config for deploying onto Heroku.
Its connects to Postgresql available as a Heroku add-on.


**REST API**

1. NameResource - takes the path parameter from the GET request and returns the same.
GET /display/somevaluetodisplay
HTTP/1.1
Host: localhost:8080
Content-Type: text/plain
Cache-Control: no-cache

2. LoginResource - accepts POST request with username and password, searches postgresql db and returns authenticated user object as response.
   
POST /login 
HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
Content-Type: application/x-www-form-urlencoded

username=xyz&password=password
 

 


Simple GraphQL Server
=====================

Pre-requisites
-----------------------

* Java 8
* Maven

Running the application
-----------------------

* Package the application:
    ```
    mvn clean install
    ```
* Run the Mock API:
    ```
    sh run_mock_api.sh
    ```
* Run the GraphQL Server:
    ```
    sh run_graphql.sh
    ```

Testing the application
-----------------------

* Testing the Mock API:
    ```
    http://localhost:8000/swagger
    ```
    ```
    http://localhost:8000/api/v1/book/0439708184
    http://localhost:8000/api/v1/author/2222
    http://localhost:8000/api/v1/review/0439708184
    ```
* Testing the GraphQL Server:
    ```
    http://localhost:3000/graphiql
    ```
    ```
    query {
      book (isbn:"0439708184") {
        id
        title
        author {
          id
          name
        }
        review {
          id
          title
          content
        }
      }
    }
    ```

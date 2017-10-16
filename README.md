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

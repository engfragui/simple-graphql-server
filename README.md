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
* Run the GraphQL Server:
    ```
    java -jar service/target/service-*-jar-with-dependencies.jar server service/app-config-local.yml
    ```

Testing the application
-----------------------

* Testing the GraphQL Server:
    ```
    http://localhost:3000/graphiql
    ```
    ```
    query {
      book(id:"11111"){
        id
        isbn
        title
        author {
          id
          name
        }
        review {
          id
          stars
          content
        }
      }
    }
    ```
    ```
    curl -X POST -H "Content-Type: application/json" -d '{"query":"query{\nbook(isbn:\"11111\"){\nid\nisbn\ntitle\nreview{\nid\nstars\ncontent\n}\nauthor{\nid\nname\n}\n}\n}"}' 'http://localhost:3000/graphql'  | json_pp
    ```
 
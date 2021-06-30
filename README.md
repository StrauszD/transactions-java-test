# Transactions API

This API lets you add transactions and get reports to mock a payments system.

1. [Built With](#built-with)
2. [Deployment](#deployment)
3. [Usage](#usage)
4. [Author](#author)

## Built With

- [Ehcache - Open source, standards-based cache](https://www.ehcache.org/)
- [Docker - Container Framework](https://www.docker.com/)
- [Jackson Databind - General data-binding functionality for Jackson](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)
- [Spring Boot - Easy to create stand-alone, production-grade Spring based Applications](https://spring.io/projects/spring-boot)


## Deployment
This API has been deployed to Heroku:

**Host:** `https://clip-transactions.herokuapp.com/`

To run the project locally with Docker you have to follow the following instructions

First run the following command to generate the .jar:

```shell script
$ ./gradlew build && java -jar build/libs/gs-spring-boot-docker-0.1.0.jar
```

Then build the image as this:

```shell script 
$ docker build --force-rm -t transactions .
```

Finally, run the image:

```shell script
$ docker run -d -p 8080:8080 --restart=always --name=transactions transactions
``` 


## Usage
The application already starts with some sample data for user id 1

This micro-service has four endpoints:

* **Endpoint `{{host}}/api/v1/transactions, [POST]`**
   
   This endpoint receives a new transaction and saves it to the cache.

   Payload:
   ````json
  {
      "user_id": 1,
      "amount": 22.25,
      "description": "test3",
      "date": "2018-10-11"
  }
    ````
   
   Response body:
    ```json
    {
        "amount": 22.25,
        "description": "test3",
        "date": "2018-10-11",
        "transaction_id": "c459e0c2-0582-4f5e-ad6e-2f4e41b8ae41",
        "user_id": 1
    }
    ```
  
    cURL:
    ```shell script
    curl --location --request POST '{{host}}/api/v1/transactions' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "user_id": 1,
        "amount": 22.25,
        "description": "test3",
        "date": "2018-10-11"
    }'
    ```

* **Endpoint `{{host}}/api/v1/transactions/:userId, [GET]`**
    
    This endpoint returns all transactions for the given user id.
    
    Response Body:
    ````json
    [
        {
            "amount": 22.2,
            "description": "test3",
            "date": "2018-10-11",
            "transaction_id": "903b4739-7c58-4b66-a871-1bb4a7c009df",
            "user_id": 1
        },
        {
          "amount": 24.2,
          "description": "test2",
          "date": "2018-10-11",
          "transaction_id": "903b4739-7c58-4b66-a871-1bb4a7c009df",
          "user_id": 1
        }
    ]
    ````
  
  cURL:
  ````shell script
    curl --location --request GET 'localhost:8080/api/v1/transactions/<user_id>'
    ````
  
* **Endpoint `{{host}}/api/v1/transactions/:userId/:transactionId, [GET]`**
    
    This endpoint returns a single transaction according to the given user id and transaction id.
    
    Response Body:
    ````json
    {
      "amount": 24.2,
      "description": "test2",
      "date": "2018-10-11",
      "transaction_id": "903b4739-7c58-4b66-a871-1bb4a7c009df",
      "user_id": 1
    }
    ````
  
  cURL:
  ````shell script
    curl --location --request GET 'localhost:8080/api/v1/transactions/<user_id>/<transaction_id>'
    ````
  
* **Endpoint `{{host}}/api/v1/transactions/sum/:userId, [GET]`**
    
    This endpoint returns the sum of all the transactions associated with the specified user_id.
    
    Response Body:
    ````json
    {
        "user_id": 1,
        "sum": 66.65
    }
    ````
  
  cURL:
  ````shell script
    curl --location --request GET 'localhost:8080/api/v1/transactions/sum/<user_id>'
    ````
* **Endpoint `{{host}}/api/v1/transactions/report/:userId, [GET]`**

    This endpoint returns the report of all the transactions associated with the specified user_id. 
    The report is grouped by weeks, where the week starts on a Friday and ends next Thursday.
        
    Response Body:
    ```
  [
       {
           "quantity": 3,
           "amount": 75.5,
           "user_id": 1,
           "total_amount": 0.0,
           "start_date": "2019-11-08",
           "end_date": "2019-11-14"
       },
       {
           "quantity": 3,
           "amount": 43.3,
           "user_id": 1,
           "total_amount": 75.5,
           "start_date": "2019-11-15",
           "end_date": "2019-11-21"
       },
       {
           "quantity": 3,
           "amount": 120.1,
           "user_id": 1,
           "total_amount": 118.8,
           "start_date": "2019-11-22",
           "end_date": "2019-11-28"
       },
       {
           "quantity": 1,
           "amount": 60.45,
           "user_id": 1,
           "total_amount": 238.89999999999998,
           "start_date": "2019-11-29",
           "end_date": "2019-11-30"
       },
       {
           "quantity": 1,
           "amount": 99.99,
           "user_id": 1,
           "total_amount": 299.34999999999997,
           "start_date": "2019-12-01",
           "end_date": "2019-12-05"
       },
       {
           "quantity": 1,
           "amount": 10.0,
           "user_id": 1,
           "total_amount": 399.34,
           "start_date": "2019-12-06",
           "end_date": "2019-12-12"
       }
    ]
  ```
      
  cURL:
      
    ````shell script
    curl --location --request GET 'localhost:8080/api/v1/transactions/report/<user_id>'
    ````
## Author
- Daniel Strausz - danst.1199@gmail.com


## Project Description
* This project consists of 6 microservices: 
  * API Gateway 
  * Discovery Service
  * Order Service
  * Product Service
  * Inventory Service
  * Notification Service

* The microservices are registered with a Eureka server and provide functionality related to placing an order, getting product details, and getting inventory details.


## Endpoints
as this project is still in development, the endpoints are not yet finalized. The following endpoints are currently available:
* `POST /api/orders` - place an order
* `GET /api/product` - Get all products


## Learning Outcomes:
With the help of this project, I learned:
* Implementation of microservices using Spring Boot.
* Utilizing the Eureka server for service registration and discovery.
* Implementing a basic API Gateway using Spring Cloud Gateway.
  * Using the API Gateway to route requests to the appropriate microservice.
  * Using the API Gateway to implement cross-cutting concerns such as authentication and authorization.
* Using Distributed Tracing to trace requests across microservices.
* Using Hystrix to implement circuit breakers.
* Using Docker to containerize the microservices.
* Using kafka to implement asynchronous communication between microservices.


## Future Enhancements:
* Define more use case specific endpoints.
* Define more entities and make it more useful for real world applications.
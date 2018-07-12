# Spring layered architecture demo
This example project implements shopping application based on below technologies:
- Spring Boot
- Spring JPA
- Spring & Thymeleaf integration
- Consuming RESTful JSON services (via. wiremock)
- Thymeleaf based web application
- Gradle dependency


# Layered architecture (Spring Application)
This sample project demonstrates one of the best various ways to organisation different application layer into their own packages. Giving you control over maintenance and also to understand whats in your project. Here are the layer in the project:
- data layer `Has entity models and repository implementation. And this is doesn't talk to any other layer`
- service layer `Business use case logic sits here. And is aware of data layers to all repositories to access data.`
- web layer `Web, RESTful controller go here and talks to Services to process Models (only, repositories shouldn't be accessed here). Also any DTO, web security and feature implementation can be implemented here. So feature slices (**without** service, repository & data access) can go here too.`

 
# Business requirements (Shopping Checkout)
Build a checkout system for a local supermarket that can be used to calculate the total cost of a basket which could contain any combination of items and promotions. Please bear in mind the following:
- Items can be scanned in any order.
- Promotions should always be applied if possible.
- The supermarket would like to show the customer how much they've saved.
- New types of prices/promotions may need to be added in the future.
- Existing service (a WireMock server) is provided with a few API mappings. Please consume this API to retrieve products for the shopping checkout application.

**Note:** Prices are expressed in pennies.


# STEPS: To run this example:

This is a Spring Boot web application (Java, Gradle project) and code can be found in 'shopping' directory.
To start with just run below steps on the terminal and details are displayed on screen. Or you can run on IDE.

1. Install wiremock

2. Start wiremock server and point to ./mapping folder.
  For example if you are running wirewock through jar, here is the command:
 ` $ java -jar wiremock-standalone-2.18.0.jar --port 8081 `

3. Now build & run the app with below:
  ` $ ./runApp.sh `

# code-review-cupid-website

This repository holds the code for the website. The backend of the website consists of a Java application using SparkJava. For the moment we'll be using SQLite but using DAO we can eventually replace it with any type of database. The Java provides a REST API that can be used by the front-end to retrieve data.

The front-end consists of Velocity and plain html pages which will use AJAX in JavaScript to pull information from the backend.

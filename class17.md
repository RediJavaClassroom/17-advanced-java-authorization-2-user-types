# 17-advanced-java-authorization-2-user-types

## Where are we at?

* We know how to create user
* We know how to login i.e. authenticate our users
* We know about different roles
----
## Agenda for today

* Recap: Authentication
* Live coding: 2 types of users : free, basic and premium
----

Requirements

Free users
* 10 short links per month 
* 1 month only

Basic 
* needs to be logged in 
* 100 short links 
* short links will be valid for 1 year

Premium 
* unlimited short links 
* valid for 10 years
* can view all their short links

Design

* User can specify which tier they want to belong to while registering.
* Users can upgrade their tiers if they want.
* we create a new field in user database table for each user, and say how many short urls
* they are permitted, we have different endpoints, which we can use to restrict different 
* types of users. 
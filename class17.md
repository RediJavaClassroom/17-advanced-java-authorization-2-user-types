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


## Using application

Start Postgres:

```
docker run --publish 5432:5432 --env POSTGRES_PASSWORD=mysecretpassword --detach postgres
```

Start application in IntelliJ IDEA

Create user:

```
curl -X POST http://localhost:8080/users --data '{"email": "test@example.com", "name": "Test", "password": "password", "userType": "FREE"}' -H "Content-Type: application/json" -v 
```

Login user:
```
curl -X POST http://localhost:8080/login --data '{"email": "test@example.com", "password": "password"}' -H "Content-Type: application/json" -v
```

Create link:

```
curl -X POST http://localhost:8080/links --data '{"originalUrl": "https://mkysoft.com"}' -H "Content-Type: application/json"  --cookie "JSESSIONID=9879245D1AE19616DF99A03D4CA1F6C5; Path=/; HttpOnly" -v
```

Expand link:

```
curl http://localhost:8080/l/65e5d8dd --cookie "JSESSIONID=52069F0C02B2EA5C3280BF57F3765BE5; Path=/; HttpOnly" -v
```


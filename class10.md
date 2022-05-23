# URL Shortner with Database

----

## Where are we at?

* We know hot to build URL Shortner service that stores links in memory (class 3)
* We know how to spin up a docker container with Postgres (class 8)
* We know how to read and write data to Postgres using Spring JPA (class 9)

----

## Agenda for today
* We will add database support to our URL shortner service

----

## Spin up database
```
docker run --name shortlinks-db --publish 5432:5432 --env POSTGRES_PASSWORD=mysecretpassword --detach postgres
docker exec -it shortlinks-db psql --host=localhost --username=postgres --file /opt/shortlinks-db/src/main/resources/init-db.sql
```

----

### Add JPA repo

* Add `spring-boot-starter-data-jpa` and `postgresql` dependencies to `pom.xml`
* Add configuration to `application.properties`
* Add entity classes
* Add repositories

----
#### Cascade Types
**PERSIST** create together  
**MERGE** update existing
**REFRESH** refresh together  
**REMOVE** delete together  
**ALL** all of the above
### Let's test it

```
curl -X POST http://localhost:8080/links --data '{"url": "http://example.org"}' -H "Content-Type: application/json"
curl http://localhost:8080/??? -v

```

## Home assignment
* Add `expired_at` field to `short_links` table and `ShortLink` object and skip links that older than 1 month in `expandShortLink`

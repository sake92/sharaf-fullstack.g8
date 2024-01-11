# $name$

This app is built with the [Sharaf](https://github.com/sake92/sharaf) mini framework.


## Development

```sh
# start a Postgres instance
docker compose up -d postgres

# run the server:
./mill app.run

# or run with restart on changes:
./mill -w app.runBackground
```

Then go to http://localhost:9001


---
---

## Tests

```sh
./mill app.test
```

Integration tests are written with help of [Testcontainers](https://testcontainers.com/guides/getting-started-with-testcontainers-for-java/).  
Every test suite starts a fresh docker container, executes migrations, tests, and kills the temporary container.  

### Writing integration tests
```sh
# run only once to download browsers for testing
./mill app.test.run install

# generate a new test
./mill app.test.runMain $package$.playwright.main
```
After going through a scenario, copy-paste the generated code in `PostsTests` and that's it! :)

### Architecture tests

Architecture of this app is layered: `web` -> `domain` -> `db`
See https://www.archunit.org/userguide/html/000_Index.html#_layer_checks

The ArchUnit tests make sure that:
- layers are respected
- there are no cycles between classes
- controllers are named `*Controller`

You can add more if you like.  
Or remove if you don't like the setup.


---
---

## Docker

Make a `.env` file (don't commit it!):
```
PORT=9011
BASE_URL=http://localhost:9011

# this is INSIDE DOCKER NETWORK, hence postgres:5432
DB_JDBC_URL=jdbc:postgresql://postgres:5432/blog
DB_USERNAME=blog_admin
DB_PASSWORD=blog_admin
```

```sh
# run postgres + build and run server image
docker compose up -d
```



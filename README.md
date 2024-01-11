# mill-scala3-library-starter.g8

A Giter8 template for a Sharaf fullstack web app.  
This template creates a simple blog web app with:
- simple, layered architecture, with [ArchUnit](https://www.archunit.org/) tests
- [squery](https://sake92.github.io/squery/) for database access
- integration test written with [Playwright](https://playwright.dev/java/)
- simple `Dockerfile`
- `docker-compose.yml` for local dev


## Quickstart

1. Create a new, empty GitHub repo, with `main` branch
1. Clone the repo, and run this inside it:
    ```sh
    mill -i init sake92/sharaf-fullstack.g8 -o .
    ```
1. Tell git that `mill` is executable:
    ```sh
    git update-index --chmod=+x ./mill
    ```
1. Develop, commit, push

Enjoy!

package $package$.main

import ba.sake.tupson.JsonRW

case class BlogConfig(
    port: Int,
    baseUrl: String,
    db: DatabaseConfig
) derives JsonRW

case class DatabaseConfig(
    jdbcUrl: String,
    username: String,
    password: String
) derives JsonRW

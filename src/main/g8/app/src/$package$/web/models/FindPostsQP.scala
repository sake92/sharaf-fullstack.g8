package $package$.web.models

import ba.sake.querson.QueryStringRW

case class FindPostsQP(
    q: Option[String],
    tag: Option[String],
    p: PageQP = PageQP.first
) derives QueryStringRW

package $package$.web.controllers

import io.scalaland.chimney.dsl.*
import ba.sake.validson.*
import ba.sake.sharaf.*, routing.*
import $package$.domain.models.*
import $package$.domain.services.*
import $package$.web.models.*
import $package$.web.views.post.*
import java.util.UUID

class PostController(postService: PostService) extends Controller {

  override def routes = Routes {

    case GET() -> Path("posts") =>
      val qp = Request.current.queryParamsValidated[FindPostsQP]
      val pageRes = postService.filter(qp.p.toPageRequest, qp.tag.getOrElse(""), qp.q.getOrElse(""))
      Response.withBody(PostsPage(qp, pageRes))

    case GET() -> Path("posts", param[UUID](postId)) =>
      val postOpt = postService.findById(postId)
      val htmlPageOpt = postOpt.map(PostViewPage(_))
      Response.withBodyOpt(htmlPageOpt, "Post")

    case GET() -> Path("posts", "new") =>
      Response.withBody(CreateOrEditPostPage(None, UpsertPostForm.empty, Seq.empty))

    case POST() -> Path("posts", "new") =>
      val formData = Request.current.bodyForm[UpsertPostForm]
      formData.validate match
        case Seq() =>
          val newPost = postService.insert(formData.toNewPost)
          Response.redirect(s"/posts/\${newPost.id}")
        case errors =>
          val htmlPage = CreateOrEditPostPage(None, formData, errors)
          Response.withBody(htmlPage).withStatus(400)

    case GET() -> Path("posts", param[UUID](postId), "edit") =>
      val postOpt = postService.findById(postId)
      val htmlPageOpt = postOpt.map { post =>
        val formData = UpsertPostForm.fromPost(post)
        CreateOrEditPostPage(Some(post.id), formData, Seq.empty)
      }
      Response.withBodyOpt(htmlPageOpt, "Post")

    case POST() -> Path("posts", param[UUID](postId), "edit") =>
      val postOpt = postService.findById(postId)
      postOpt match
        case Some(post) =>
          val formData = Request.current.bodyForm[UpsertPostForm]
          formData.validate match
            case Seq() =>
              val updatedPost = post.patchUsing(formData)
              postService.update(updatedPost)
              Response.redirect(s"/posts/\${postId}")
            case errors =>
              val htmlPage = CreateOrEditPostPage(Some(post.id), formData, errors)
              Response.withBody(htmlPage).withStatus(400)
        case None =>
          Response.withStatus(404)

  }
}

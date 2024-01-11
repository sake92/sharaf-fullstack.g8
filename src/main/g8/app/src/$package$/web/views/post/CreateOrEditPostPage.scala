package $package$.web.views
package post

import java.util.UUID
import ba.sake.validson.ValidationError
import $package$.web.models.UpsertPostForm
import Bundle.*, Tags.*

class CreateOrEditPostPage(
    postId: Option[UUID],
    formData: UpsertPostForm,
    errors: Seq[ValidationError]
) extends BlogPage {

  private val (pageTitle, actionLabel, formAction) = postId match
    case Some(id) => ("Edit Post", "Update", s"/posts/\${id}/edit")
    case None     => ("Create Post", "Create", "/posts/new")

  override def pageSettings = super.pageSettings
    .withTitle(pageTitle)

  override def pageContent: Frag = div(
    h1(pageTitle),
    Form.form(action := formAction, method := "POST")(
      withValueAndValidation("title", _.title) { case (fieldName, fieldValue, state, messages) =>
        Form.inputText(autofocus, value := fieldValue)(
          fieldName,
          "Title",
          _validationState = state,
          _messages = messages
        )
      },
      withValueAndValidation("mdContent", _.mdContent) { case (fieldName, fieldValue, state, messages) =>
        Form.inputTextArea(value := fieldValue, rows := "10")(
          fieldName,
          "Content",
          _validationState = state,
          _messages = messages
        )
      },
      br,
      Form.inputButton(tpe := "submit", Classes.btnPrimary)(actionLabel)
    )
  )

  // errors are returned as JSON Path, hence the \$. prefix below!
  private def withValueAndValidation(fieldName: String, extract: UpsertPostForm => String)(
      f: (String, String, Option[Form.ValidationState], Seq[String]) => Frag
  ) =
    val fieldErrors = errors.filter(_.path == s"\$\$.\$fieldName")
    val (state, errMsgs) =
      if fieldErrors.isEmpty then None -> Seq.empty
      else Some(Form.ValidationState.Error) -> fieldErrors.map(_.msg)
    f(fieldName, extract(formData), state, errMsgs)

}

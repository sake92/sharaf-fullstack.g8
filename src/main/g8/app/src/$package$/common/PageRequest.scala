package $package$.common

case class PageRequest(number: Int):
  def offset: Int = number * Page.size
  def limit: Int = Page.size

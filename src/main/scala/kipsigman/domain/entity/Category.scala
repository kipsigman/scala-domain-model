package kipsigman.domain.entity

import play.api.data.format.Formatter
import play.api.data.FormError
import play.api.data.Forms
import play.api.data.Forms._
import play.api.data.Mapping
import play.api.mvc.PathBindable
import play.api.mvc.QueryStringBindable

case class Category(
  id: Option[Int] = None,
  name: String,
  order: Int) extends IdEntity {
  
  def path: String = name
  
  override def toString: String = name
}

object Category {
  def sort(categories: Seq[Category]): Seq[Category] = categories.sortBy(c => (c.order, c.name))
  def sort(categories: Set[Category]): Seq[Category] = sort(categories.toSeq)
}

/**
 * Implement in end application
 */
trait CategoryOptions {
  
  def all: Set[Category]
  
  final def apply(name: String): Category = {
    all.find(s => s.name == name) match {
      case Some(category) => category
      case None => throw new IllegalArgumentException(s"No Category with name: $name")
    }
  }
  
  final def findByPath(path: String): Category = {
    all.find(s => s.path == path) match {
      case Some(category) => category
      case None => throw new IllegalArgumentException(s"No Category with path: $path")
    }
  }
  
  final def allSorted: Seq[Category] = Category.sort(all)
  
  implicit def formatter: Formatter[Category] = new Formatter[Category] {
    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], Category] = {
      //data.get(key).toRight(Seq(FormError(key, "error.required", Nil)))
      data.get(key) match {
        case Some(name) => try {
          Right(apply(name))
        } catch {
          case e: Exception =>Left(Seq(FormError(key, "error.category.invalid", Nil)))
        }
        case None => Left(Seq(FormError(key, "error.required", Nil)))
      }
    }
    override def unbind(key: String, value: Category): Map[String, String] = {
      Map(key -> value.name)
    }
  }
  
  val formMapping: Mapping[Category] = of[Category]
  
  implicit def queryStringBinder(implicit stringBinder: QueryStringBindable[String]) = new QueryStringBindable[Category] {
    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, Category]] = {
      for {
        nameEither <- stringBinder.bind(key, params)
      } yield {
        nameEither match {
          case Right(name) => try {
            Right(apply(name))
          } catch {
            case e: Exception =>Left(s"Invalid Category: $name")
          }
          case _ => Left("Unable to bind Category")
        }
      }
    }
    override def unbind(key: String, value: Category): String = stringBinder.unbind(key, value.name)
  }
  
  implicit def pathBinder(implicit stringBinder: PathBindable[String]) = new PathBindable[Category] {
    override def bind(key: String, value: String): Either[String, Category] = {
      stringBinder.bind(key, value) match {
          case Right(path) => Right(findByPath(path))
          case _ => Left("Unable to bind Category")
      }
    }
    override def unbind(key: String, value: Category): String = stringBinder.unbind(key, value.path)
  }
}
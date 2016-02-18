package kipsigman.domain.entity

trait CategorizedEntity {
  
  def categories: Seq[Category]
  
  final def category: Category = categories.head
  
  final def secondaryCategories: Seq[Category] = categories.tail
  
  final def isMemberOf(category: Category) = categories.contains(category)
}
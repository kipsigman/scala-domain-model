package kipsigman.domain.entity

trait CategorizedEntity {
  
  /**
   * Primary category
   */
  def category: Category
  
  /**
   * Optional additional Categories
   */
  def secondaryCategories: Set[Category] = Set[Category]()
  
  /**
   * All categories
   */
  def categories: Set[Category] = secondaryCategories + category
  
  /**
   * Primary category followed by sorted secondary categories
   */
  final def categoriesSorted: Seq[Category] = category +: Category.sort(secondaryCategories)
  
  /**
   * Primary followed by sorted secondary
   */
  final def isMemberOf(category: Category) = categories.contains(category)
}
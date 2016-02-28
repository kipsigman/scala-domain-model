package kipsigman.domain.guice

import com.google.inject.AbstractModule

import kipsigman.domain.repository.ImageRepository
import kipsigman.domain.repository.slick.ImageRepositorySlick

class DomainSlickModule() extends AbstractModule {
  def configure() = {
    bind(classOf[ImageRepository]).to(classOf[ImageRepositorySlick])
  }
}
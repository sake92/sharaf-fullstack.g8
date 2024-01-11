package $package$.archunit

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.core.importer.Location
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices
import $package$.web.controllers.Controller

class ArchitectureTests extends munit.FunSuite {

  val basePackage = "$package$"
  val ignoreTests = new ImportOption() {
    override def includes(location: Location): Boolean =
      !location.contains("/test/")
  }
  val allClasses = new ClassFileImporter()
    .withImportOption(ignoreTests)
    .importPackages(basePackage)

  // format: off
  test("web -> domain -> db") {
    // Main has to access all
    // in order to set up the whole app
    layeredArchitecture().consideringAllDependencies()
      .layer("Main").definedBy(s"\${basePackage}.main..")
      .layer("Web").definedBy(s"\${basePackage}.web..")
      .layer("Domain").definedBy(s"\${basePackage}.domain..")
      .layer("DB").definedBy(s"\${basePackage}.db..")
      .whereLayer("Web").mayOnlyBeAccessedByLayers("Main")
      .whereLayer("Domain").mayOnlyBeAccessedByLayers("Main", "Web")
      .whereLayer("DB").mayOnlyBeAccessedByLayers("Main","Domain")
      .check(allClasses)
  }

  test("no cycles") {
    // checks all subpackages for cycles
    slices().matching(s"\${basePackage}.(**)")
      .should().beFreeOfCycles()
      .check(allClasses)
  }

  test("controllers") {
    classes().that().implement(classOf[Controller])
      .should().haveSimpleNameEndingWith("Controller")
  }

}

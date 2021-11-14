package ar.edu.unq.desapp.grupoG.backenddesappapi.architecture

import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.springframework.stereotype.Service

@AnalyzeClasses(packages = ["ar/edu/unq/desapp/grupoG/backenddesappapi"])
class ArchitectureTest {

    @ArchTest
    final val applicationServicesShouldResideInServicePackage = ArchRuleDefinition.classes().that().areAnnotatedWith(Service::class.java)
        .should().resideInAPackage("services")
}
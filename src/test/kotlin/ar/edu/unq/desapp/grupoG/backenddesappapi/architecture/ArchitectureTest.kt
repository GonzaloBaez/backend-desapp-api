package ar.edu.unq.desapp.grupoG.backenddesappapi.architecture

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RestController
import java.io.PrintStream
import javax.persistence.Entity

@AnalyzeClasses(packages = ["ar/edu/unq/desapp/grupoG/backenddesappapi"])
class ArchitectureTest {

    @ArchTest
    val applicationServicesShouldResideInServicePackage =
        ArchRuleDefinition.classes().that().areAnnotatedWith(Service::class.java)
            .should().resideInAPackage("..services")!!

    @ArchTest
    val applicationControllersShouldResideInWebServicesPackage =
        ArchRuleDefinition.classes().that().areAnnotatedWith(RestController::class.java)
            .should().resideInAPackage("..webservices")!!

    @ArchTest
    val entityClassesShouldBeInModelPackage = ArchRuleDefinition.classes().that().areAnnotatedWith(Entity::class.java)
        .should().resideInAPackage("..model")!!

    @ArchTest
    val classesThatHasDTOInNameShouldBeInDtoPackage =
        ArchRuleDefinition.classes().that().haveSimpleNameContaining("DTO")
            .should().resideInAPackage("..dto")!!

    @ArchTest
    val classesShouldNoAccessToSystemOut = ArchRuleDefinition.noClasses().that().resideInAPackage("..backenddesappapi..")
        .should().accessClassesThat().belongToAnyOf(PrintStream::class.java)!!


}
package ar.edu.unq.desapp.grupoG.backenddesappapi.aspects

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.log

@Aspect
@Component
@Order(0)
class LogAuditAspectAnnotation {
    val logger: Logger = LoggerFactory.getLogger("aspect")

    @Around("@annotation(LogAudit)")
    fun logAuditAspectAnnotation(joinPoint: ProceedingJoinPoint): Any? {
        logger.debug("/////// AROUND START  logAuditAspect annotation //////")
        val start = System.currentTimeMillis()
        val timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        val controller = joinPoint.target.javaClass.simpleName
        val method = joinPoint.signature.name
        var valuesOfParameters = mutableListOf<String>()
        var params = (joinPoint.staticPart.signature as MethodSignature).method.parameters
        var args = joinPoint.args

        for(param in params){
            val index = params.indexOf(param)
            val value = args[index]
            valuesOfParameters.add("$param: $value")
        }
        val proceed = joinPoint.proceed()
        val executionTime = System.currentTimeMillis() - start

        logger.debug("TimeStamp = $timeStamp")
        logger.debug("Controller = $controller")
        logger.debug("Method = $method")
        logger.debug("Params = $valuesOfParameters")
        logger.debug("Executed in " + executionTime + "ms")

        logger.debug("/////// AROUND FINISH  logAuditAspect annotation //////")
        return proceed
    }
}
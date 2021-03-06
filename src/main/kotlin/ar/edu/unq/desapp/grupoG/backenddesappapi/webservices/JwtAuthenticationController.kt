package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.aspects.LogAudit
import ar.edu.unq.desapp.grupoG.backenddesappapi.securityConfig.JwtRequest
import ar.edu.unq.desapp.grupoG.backenddesappapi.securityConfig.JwtResponse
import ar.edu.unq.desapp.grupoG.backenddesappapi.securityConfig.JwtTokenUtil
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*


@RestController

class JwtAuthenticationController {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    lateinit var userDetailsService: JwtUserDetailsService

    @LogAudit
    @CrossOrigin(origins = ["http://localhost:3000"])
    @RequestMapping(value = ["/authenticate"], method = [RequestMethod.POST])
    @Throws(Exception::class)
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<*> {
        authenticate(authenticationRequest.username!!, authenticationRequest.password!!)
        val userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.username!!)
        val token = jwtTokenUtil!!.generateToken(userDetails)
        return ResponseEntity.ok<Any>(JwtResponse(token))
    }

    @LogAudit
    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager!!.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }
}
package ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions

class DuplicateUniqueException(override var message : String?) : Exception()
class NotFoundException(override var message : String?) : Exception()
class BadRequest(override var message : String?) : Exception()
class InvalidUserInformation(override var message: String?) : Exception()
package ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions

class DuplicateUniqueException(override var message : String?) : Exception()
class NotFoundException(override var message : String?) : Exception()
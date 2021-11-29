package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.annotation.PostConstruct

@Service
@Transactional
class InitServiceInMemory {
    val log : Logger = LoggerFactory.getLogger(javaClass)

    @Value("\${pring.datasource.driverClassName:NONE}")
    private val className: String? = null

    @Autowired
    lateinit var userService : UserService

    @Autowired
    lateinit var transactionService: TransactionService

    @PostConstruct
    fun initialize(){
        transactionService.clearDatabase()
        userService.clearDatabase()
        fireInitialData()
    }

    fun fireInitialData(){
        var user = User("example1","ex1","example1@gmail.com","C18 134334",
            "$2a$10" + "$" + "aI30CzTcAifI4PJLmRu8FuySBMfLMyxwYmutN3umQYP31fMnlG8Zy","8179869268014869597379","40605719",0,0)
        var user2 = User("example2","ex2","example2@gmail.com","C18 134334","$2a$10" + "$" + "aI30CzTcAifI4PJLmRu8FuySBMfLMyxwYmutN3umQYP31fMnlG8Zy","8179869268014869597379","40605720",0,0)
        var user3 = User("example3","ex3","example3@gmail.com","C18 134334","$2a$10" + "$" + "aI30CzTcAifI4PJLmRu8FuySBMfLMyxwYmutN3umQYP31fMnlG8Zy","8179869268014869597379","40605739",0,0)
        var user4 = User("example4","ex4","example4@gmail.com","C18 134334","$2a$10" + "$" + "aI30CzTcAifI4PJLmRu8FuySBMfLMyxwYmutN3umQYP31fMnlG8Zy","8179869268014869597379","40605740",0,0)
        var user5 = User("example5","ex5","example5@gmail.com","C58 134334","$2a$10" + "$" + "aI30CzTcAifI4PJLmRu8FuySBMfLMyxwYmutN3umQYP31fMnlG8Zy","8179869268014869597379","40605759",0,0)
        var user6 = User("example6","ex6","example6@gmail.com","C18 134334","$2a$10" + "$" + "aI30CzTcAifI4PJLmRu8FuySBMfLMyxwYmutN3umQYP31fMnlG8Zy","8179869268014869597379","40605760",0,0)
        var user7 = User("example7","ex7","example7@gmail.com","C18 134334","$2a$10" + "$" + "aI30CzTcAifI4PJLmRu8FuySBMfLMyxwYmutN3umQYP31fMnlG8Zy","8179869268014869597379","40605779",0,0)
        var user8 = User("example8","ex8","example8@gmail.com","C18 134334","$2a$10" + "$" + "aI30CzTcAifI4PJLmRu8FuySBMfLMyxwYmutN3umQYP31fMnlG8Zy","8179869268014869597379","40605780",0,0)
        var user9 = User("example9","ex9","example9@gmail.com","C18 134334","$2a$10" + "$" + "aI30CzTcAifI4PJLmRu8FuySBMfLMyxwYmutN3umQYP31fMnlG8Zy","8179869268014869597379","40605799",0,0)
        var user10 = User("examplx10","ex10","examplx10@gmail.com","C18 134334","$2a$10" + "$" + "aI30CzTcAifI4PJLmRu8FuySBMfLMyxwYmutN3umQYP31fMnlG8Zy","8179869268014869597379","40605710",0,0)
        var user11 = User("examplx11","exx11","examplex11@gmail.com","C18 134334","$2a$10" + "$" + "aI30CzTcAifI4PJLmRu8FuySBMfLMyxwYmutN3umQYP31fMnlG8Zy","8179869268014869597379","40605711",0,0)
        var user12 = User("example12","ex12","example12@gmail.com","C18 134334","$2a$10" + "$" + "aI30CzTcAifI4PJLmRu8FuySBMfLMyxwYmutN3umQYP31fMnlG8Zy","8179869268014869597379","40605712",0,0)
        var userList = mutableListOf(user,user2,user3,user4,user5,user6,user7,user8,user9,user10,user11,user12)

        userList.forEach { userService.save(it) }

        var hour = LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        var transaction1 = Transaction(user,hour,"BTCUSDT",57329.9,20.0,20.0,20.0,"Compra","Creada",null)
        var transaction2 = Transaction(user2,hour,"ETHUSDT",57329.9,20.0,20.0,20.0,"Venta","Creada",null)
        var transaction3 = Transaction(user3,hour,"BNBUSDT",57329.9,20.0,20.0,20.0,"Compra","Creada",null)
        var transaction4 = Transaction(user4,hour,"BTCUSDT",57329.9,20.0,20.0,20.0,"Venta","Creada",null)
        var transaction5 = Transaction(user5,hour,"NEOUSDT",57329.9,20.0,20.0,20.0,"Compra","Creada",null)
        var transaction6 = Transaction(user6,hour,"ADAUSDT",57329.9,20.0,20.0,20.0,"Venta","En progreso",user.email)
        var transaction7 = Transaction(user7,hour,"TRXUSDT",57329.9,20.0,20.0,20.0,"Venta","Creada",null)
        var transaction8 = Transaction(user8,hour,"BTCUSDT",57329.9,20.0,20.0,20.0,"Compra","Creada",null)
        var transaction9 = Transaction(user9,hour,"BTCUSDT",57329.9,20.0,20.0,20.0,"Compra","Creada",null)
        var transaction10 = Transaction(user10,hour,"BTCUSDT",57329.9,20.0,20.0,20.0,"Compra","Creada",null)
        var transaction11 = Transaction(user11,hour,"BTCUSDT",57329.9,20.0,20.0,20.0,"Compra","En progreso",user3.email)
        var transaction12 = Transaction(user12,hour,"BTCUSDT",57329.9,20.0,20.0,20.0,"Compra","En progreso",user5.email)



        userService.addTransactionTo(user,transaction1)
        userService.addTransactionTo(user2,transaction2)
        userService.addTransactionTo(user3,transaction3)
        userService.addTransactionTo(user4,transaction4)
        userService.addTransactionTo(user5,transaction5)
        userService.addTransactionTo(user6,transaction6)
        userService.addTransactionTo(user7,transaction7)
        userService.addTransactionTo(user8,transaction8)
        userService.addTransactionTo(user9,transaction9)
        userService.addTransactionTo(user10,transaction10)
        userService.addTransactionTo(user11,transaction11)
        userService.addTransactionTo(user12,transaction12)


    }
}
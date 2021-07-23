package lean.kotlin.tutorial.springboot

import database.UserMapper
import database.UserRecord
import database.selectByPrimaryKey
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SelectController {
    @GetMapping("/select")
    fun select() : UserRecord? {
        createSessionFactory().openSession().use { session ->
            val mapper = session.getMapper(UserMapper::class.java)
            return mapper.selectByPrimaryKey(1)
        }
    }
}
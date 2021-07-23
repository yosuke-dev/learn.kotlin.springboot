package lean.kotlin.tutorial.springboot

import database.UserDynamicSqlSupport.User.age
import database.UserMapper
import database.UserRecord
import database.select
import org.mybatis.dynamic.sql.util.kotlin.elements.isGreaterThanOrEqualTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SelectController {
    @GetMapping("/select")
    fun select() : List<UserRecord> {
        createSessionFactory().openSession().use { session ->
            val mapper = session.getMapper(UserMapper::class.java)
            return mapper.select {
                where(age, isGreaterThanOrEqualTo(25))
            }
        }
    }
}
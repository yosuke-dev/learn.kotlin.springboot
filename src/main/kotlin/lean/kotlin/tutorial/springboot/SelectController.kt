package lean.kotlin.tutorial.springboot

import database.UserDynamicSqlSupport.User.age
import database.UserMapper
import database.UserRecord
import database.count
import database.select
import org.mybatis.dynamic.sql.util.kotlin.elements.isGreaterThanOrEqualTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SelectController {
    @GetMapping("/select")
    fun select(): List<UserRecord> {
        createSessionFactory().openSession().use { session ->
            val mapper = session.getMapper(UserMapper::class.java)
            return mapper.select {
                where(age, isGreaterThanOrEqualTo(25))
            }
        }
    }

    @GetMapping("/count")
    fun count(): Long {
        createSessionFactory().openSession().use { session ->
            val mapper = session.getMapper(UserMapper::class.java)
            return mapper.count {
                where(age, isGreaterThanOrEqualTo(25))
            }
        }
    }

    @GetMapping("/allCount")
    fun allCount(): Long {
        createSessionFactory().openSession().use { session ->
            val mapper = session.getMapper(UserMapper::class.java)
            return mapper.count { allRows() }
        }
    }
}
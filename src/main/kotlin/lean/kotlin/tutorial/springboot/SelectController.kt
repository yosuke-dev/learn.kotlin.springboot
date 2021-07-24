package lean.kotlin.tutorial.springboot

import database.*
import database.UserDynamicSqlSupport.User.age
import org.mybatis.dynamic.sql.util.kotlin.elements.isGreaterThanOrEqualTo
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
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

    @PostMapping("/insert")
    fun insert(@RequestBody request: List<UserRecord>): String {
        createSessionFactory().openSession().use { session ->
            val mapper = session.getMapper(UserMapper::class.java)
            val count = mapper.insertMultiple(request)
            session.commit()
            return "$count 行のレコードを挿入しました"
        }
    }
}
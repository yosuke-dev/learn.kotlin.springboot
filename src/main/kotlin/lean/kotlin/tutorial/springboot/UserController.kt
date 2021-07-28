package lean.kotlin.tutorial.springboot

import database.*
import database.UserDynamicSqlSupport.User.age
import database.UserDynamicSqlSupport.User.name
import database.UserDynamicSqlSupport.User.profile
import org.mybatis.dynamic.sql.util.kotlin.elements.isEqualTo
import org.mybatis.dynamic.sql.util.kotlin.elements.isGreaterThanOrEqualTo
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController {
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

    @PatchMapping("/update")
    fun update(@RequestBody request: UserRecord): String {
        createSessionFactory().openSession().use { session ->
            val mapper = session.getMapper(UserMapper::class.java)
            // updateByPrimaryKeyだと値が設定されていない項目をNullで更新する
            val count = mapper.updateByPrimaryKeySelective(request)
            session.commit()
            return "$count 行のレコードを更新しました"
        }
    }

    @PatchMapping("/profileUpdateByName")
    fun profileUpdateByName(@RequestBody request: UserRecord): String {
        createSessionFactory().openSession().use { session ->
            val mapper = session.getMapper(UserMapper::class.java)
            val count = mapper.update {
                set(profile).equalTo(request.profile)
                where(name, isEqualTo(request.name.toString()))
            }
            session.commit()
            return "$count 行のレコードを更新しました"
        }
    }
}
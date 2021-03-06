package lean.kotlin.tutorial.springboot

import database.*
import database.UserDynamicSqlSupport.User.age
import database.UserDynamicSqlSupport.User.name
import database.UserDynamicSqlSupport.User.profile
import org.mybatis.dynamic.sql.util.kotlin.elements.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController() {
    @GetMapping("/select/{id}")
    fun select(@PathVariable("id") id: Int): UserRecord? {
        createSessionFactory().openSession().use { session ->
            val mapper = session.getMapper(UserMapper::class.java)
            return mapper.selectByPrimaryKey(id)
        }
    }

    @GetMapping("/index")
    fun index(): List<UserRecord> {
        createSessionFactory().openSession().use { session ->
            val mapper = session.getMapper(UserMapper::class.java)
            return mapper.select {  }
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

    @PatchMapping("/updateByName")
    fun updateByName(@RequestBody request: UserRecord): String {
        createSessionFactory().openSession().use { session ->
            val mapper = session.getMapper(UserMapper::class.java)
            val count = mapper.update {
                updateSelectiveColumns(request)
                where(name, isEqualTo(request.name.toString()))
            }
            session.commit()
            return "$count 行のレコードを更新しました"
        }
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Int): String {
        createSessionFactory().openSession().use { session ->
            val mapper = session.getMapper(UserMapper::class.java)
            val count = mapper.deleteByPrimaryKey(id)
            session.commit()
            return "$count 行のレコードを削除しました"
        }
    }
}
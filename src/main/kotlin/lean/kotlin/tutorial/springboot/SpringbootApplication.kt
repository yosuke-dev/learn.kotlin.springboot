package lean.kotlin.tutorial.springboot

import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringbootApplication

fun main(args: Array<String>) {
	runApplication<SpringbootApplication>(*args)
}

fun createSessionFactory(): SqlSessionFactory {
	val resource = "mybatis-config.xml"
	val inputStream = Resources.getResourceAsStream(resource)
	return SqlSessionFactoryBuilder().build(inputStream)
}

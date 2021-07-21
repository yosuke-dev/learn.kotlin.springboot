package lean.kotlin.tutorial.springboot

import org.springframework.stereotype.Component

interface Greeter {
    fun sayHello(name: String) :String
}

@Component
class GreeterImpl : Greeter {
    override fun sayHello(name: String) = "Hello $name"
}
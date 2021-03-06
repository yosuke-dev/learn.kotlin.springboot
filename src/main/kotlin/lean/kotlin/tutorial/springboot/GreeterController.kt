package lean.kotlin.tutorial.springboot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("greeter")
class GreeterController() {
    @Autowired
    private lateinit var greeter: Greeter

    @GetMapping("/hello")
    fun hello(@RequestParam("name") name: String): HelloResponse {
        return HelloResponse("Hello $name")
    }

    @GetMapping("/hello/{name}")
    fun helloPathValue(@PathVariable("name") name: String): HelloResponse {
        return HelloResponse("Hello $name")
    }

    @PostMapping("/hello")
    fun helloByPost(@RequestBody request: HelloRequest): HelloResponse {
        return HelloResponse("Hello ${request.name}")
    }

    @GetMapping("/hello/byService/{name}")
    fun helloByService(@PathVariable("name") name: String) :HelloResponse {
        val message = greeter.sayHello(name)
        return HelloResponse(message)
    }
}

data class HelloResponse(val message: String)

data class HelloRequest(val name: String)

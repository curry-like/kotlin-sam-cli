package helloworld

import org.junit.Assert
import org.junit.Test

class PostAppTest {
    @Test
    fun successfulResponse() {
        val app = PostApp()
        val request = Request()
        val firstName = "Taro"
        val lastName = "Test"
        request.body = "{\"firstName\": \"%s\", \"lastName\": \"%s\"}".format(firstName, lastName)
        val result = app.handleRequest(request, null) as GatewayResponse
        Assert.assertEquals(result.statusCode.toLong(), 200)
        Assert.assertEquals(result.headers["Content-Type"], "application/json")
        val content = result.body
        Assert.assertNotNull(content)
        Assert.assertTrue(content.contains("\"message\""))
        Assert.assertTrue(content.contains("\"hello %s\"".format(lastName)))
        Assert.assertTrue(content.contains("\"location\""))
    }
}
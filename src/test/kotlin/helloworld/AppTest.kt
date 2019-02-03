package helloworld

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AppTest {
    @Test
    fun successfulResponse() {
        val app = App()
        val result = app.handleRequest(null, null) as GatewayResponse
        assertEquals(result.statusCode.toLong(), 200)
        assertEquals(result.headers["Content-Type"], "application/json")
        val content = result.body
        assertNotNull(content)
        assertTrue(content.contains("\"message\""))
        assertTrue(content.contains("\"hello world\""))
        assertTrue(content.contains("\"location\""))
    }
}

package helloworld

import org.junit.Assert.*
import org.junit.Test

class GetAppTest {
    @Test
    fun successfulResponse() {
        val app = GetApp()
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

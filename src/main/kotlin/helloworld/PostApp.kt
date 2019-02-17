package helloworld

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.IOException
import java.net.URL
import java.util.HashMap
import java.util.stream.Collectors

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * Handler for requests to Lambda function.
 */
class PostApp : RequestHandler<Request, GatewayResponse> {

    override fun handleRequest(input: Request, context: Context?): GatewayResponse {
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        headers["X-Custom-Header"] = "application/json"

        val mapper = jacksonObjectMapper()
        val person = mapper.readValue<Person>(input.body)
        return try {
            val pageContents = this.getPageContents("https://checkip.amazonaws.com")
            val output = String.format("{ \"message\": \"hello %s\", \"location\": \"%s\" }", person.lastName, pageContents)
            GatewayResponse(output, headers, 200)
        } catch (e: IOException) {
            GatewayResponse("{}", headers, 500)
        }
    }

    @Throws(IOException::class)
    private fun getPageContents(address: String): String {
        val url = URL(address)
        BufferedReader(InputStreamReader(url.openStream())).use { br -> return br.lines().collect(Collectors.joining(System.lineSeparator())) }
    }
}

class Request {
    lateinit var body: String
}

data class Person(val firstName: String, val lastName: String)


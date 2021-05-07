package com.github.vitormbgoncalves.todolist.oauth.client

import com.github.kittinunf.fuel.core.Encoding
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.RequestFactory
import com.github.kittinunf.fuel.test.MockHttpTestCase
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

/**
 * Http client request testing
 *
 * @author Vitor Goncalves
 * @since 07.05.2021, sex, 10:47
 */

class HttpClientTest : MockHttpTestCase() {

    class RequestTest : Spek({

        val manager: FuelManager by lazy { FuelManager() }

        class PathStringConvertibleImpl(url: String) : RequestFactory.PathStringConvertible {
            override val path = url
        }

        class RequestConvertibleImpl(val method: Method, private val url: String) : RequestFactory.RequestConvertible {
            override val request = createRequest()

            private fun createRequest(): Request {
                val encoder = Encoding(
                    httpMethod = method,
                    urlString = url,
                    parameters = listOf("foo" to "bar")
                )
                return encoder.request
            }
        }

        describe("Http client request") {

            it("should be OK") {
                fun testResponseURLShouldSameWithRequestURL() {
                    mock.chain(
                        request = mock.request().withMethod(Method.GET.value).withPath("/request"),
                        response = mock.reflect()
                    )

                    val (request, response, result) = manager.request(Method.GET, mock.path("request")).response()
                    val (data, error) = result

                    assertThat(request, notNullValue())
                    assertThat(response, notNullValue())
                    assertThat(error, nullValue())
                    assertThat(data, notNullValue())

                    assertThat(request.url, notNullValue())
                    assertThat(response.url, notNullValue())
                    assertThat(request.url, equalTo(response.url))
                }
            }
        }
    })
}
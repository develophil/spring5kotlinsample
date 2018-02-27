package net.slipp.hkp.handler

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.RequestPredicates.contentType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap



/**
 * Created by hkpking on 2018. 2. 22..
 */

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RacingHandlerTest {

    @Autowired
    lateinit var client: WebTestClient

    @Test
    @Throws(Exception::class)
    fun givenLoginForm_whenPostValidToken_thenSuccess() {
        val formData = LinkedMultiValueMap<String, String>(1)
        formData.add("names", """{"name": "a", "name":"b"}""")

        client.post().uri("/racing/game/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .expectStatus().isOk()
    }
}

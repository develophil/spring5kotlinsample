package net.slipp.hkp.web

import junit.framework.Assert.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Test
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient

@RunWith(JUnitPlatform::class)
class Week4Test: Spek({
    describe("계산기") {
        val calculator = SampleCalculator()

        it("첫 번째 숫자와 두 번째 숫자를 더한 값") {
            val sum = calculator.sum(2, 4)
            assertEquals(6, sum)
        }

        it("첫 번째 숫자에 두 번째 숫자를 뺀 값") {
            val subtract = calculator.subtract(4, 2)
            assertEquals(2, subtract)
        }
    }
})

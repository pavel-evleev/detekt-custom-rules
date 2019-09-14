package io.example.detektRules.rules.style

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class CompanionObjectInEndClassTest {

    @Test
    fun companionObjectTest() {
        val findings = CompanionObjectInEndClass(Config.empty).lint(
            """
            class SomeClass {

                private lateinit var name: String

                override fun toString(): String = "SomeClassName"

                companion object {
                    private val n = 4
                    fun print() {
                        print(n)
                    }
                }

            }
        """.trimIndent()
        )

        assertThat(findings).hasSize(0)
    }

    @Test
    fun companionObjectIsNotEndOfClass() {
        val findings = CompanionObjectInEndClass(Config.empty).lint(
            """
            class SomeClass {

                companion object {
                    private val n = 4
                    fun print() {
                        print(n)
                    }
                }

                private lateinit var name: String

                override fun toString(): String = "SomeClassName"

            }
        """.trimIndent()
        )

        assertThat(findings).hasSize(1)
    }

}

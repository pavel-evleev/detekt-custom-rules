package io.example.detektRules.rules.naming

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class MemberNameContainsClassNameTest {


    @Test
    fun test() {

        val klass = """
            class SomeClass{

                fun getSomeClassAction(){
                    print("action)
                }
            }
        """.trimIndent()

        val findings = MemberNameContainsClassName(Config.empty).lint(klass)

        assertThat(findings).hasSize(1)
    }
}
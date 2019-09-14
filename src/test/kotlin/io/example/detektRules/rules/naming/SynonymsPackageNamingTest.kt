package io.example.detektRules.rules.naming

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class SynonymsPackageNamingTest {

    @Test
    fun shouldKetchSynonyms() {
        val finding = SynonymsPackageNaming(Config.empty).lint("package domain.foo.entity.books")
        assertThat(finding).hasSize(1)
    }

    @Test
    fun withoutSynonyms() {
        val finding = SynonymsPackageNaming(Config.empty).lint("package domain.foo.books")
        assertThat(finding).hasSize(0)
    }

}
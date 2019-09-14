package io.example.detektRules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider
import io.example.detektRules.rules.style.CompanionObjectInEndClass
import io.example.detektRules.rules.style.EmptyLineBeforeEndClass

class ZenStyleProvider : RuleSetProvider {

    override val ruleSetId: String = "zenStyle"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId, listOf(
                EmptyLineBeforeEndClass(config),
                CompanionObjectInEndClass(config)
            )
        )
    }

}
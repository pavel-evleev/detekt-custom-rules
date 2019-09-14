package io.example.detektRules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider
import io.example.detektRules.rules.naming.MemberNameContainsClassName
import io.example.detektRules.rules.naming.SynonymsPackageNaming

class ZenNamingProvider : RuleSetProvider {

    override val ruleSetId: String = "zenNaming"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId, listOf(
                MemberNameContainsClassName(config),
                SynonymsPackageNaming(config)
            )
        )
    }

}
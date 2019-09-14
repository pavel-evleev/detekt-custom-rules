package io.example.detektRules.rules.naming

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtPackageDirective

/**
 * Reports when package names contains synonyms name.
 *
 */
class SynonymsPackageNaming(config: Config = Config.empty) : Rule(config) {

    override val issue = Issue(
        javaClass.simpleName,
        Severity.Style,
        "Package names should not use synonyms.",
        debt = Debt.FIVE_MINS
    )

    override fun visitPackageDirective(directive: KtPackageDirective) {
        val name = directive.qualifiedName
        val packages = name.split('.')
        if (name.isNotEmpty() && packages.containsAll(PACKAGE_SYNONYMS)) {
            report(
                CodeSmell(
                    issue,
                    Entity.from(directive),
                    message = "Package naming should not use synonyms"
                )
            )
        }
    }

    companion object {
        val PACKAGE_SYNONYMS = listOf("domain", "entity")
    }
}

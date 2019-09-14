package io.example.detektRules.rules.style

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtClassBody

class EmptyLineBeforeEndClass(config: Config) : Rule(config) {

    private val nextLine = '\n'

    override val issue = Issue(
        javaClass.simpleName,
        Severity.Style,
        "Extra line before the end class",
        Debt.FIVE_MINS
    )

    override fun visitClassBody(classBody: KtClassBody) {
        val text = classBody.text
        val chars = arrayListOf(text[text.lastIndex - 1], text[text.lastIndex - 2])
        if (text.isNotEmpty() &&
            !chars.all { it == nextLine } &&
            text[text.lastIndex - 3] != nextLine
        ) {
            report(
                CodeSmell(
                    issue,
                    Entity.from(classBody.containingKtFile, text.length - 2),
                    "No empty line before closing class"
                )
            )
        }
    }
}
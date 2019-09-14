package io.example.detektRules.rules.style

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtClassBody
import org.jetbrains.kotlin.psi.psiUtil.endOffset

class CompanionObjectInEndClass(config: Config) : Rule(config) {

    private val lineTrashHold = 3

    override val issue = Issue(
        javaClass.simpleName,
        Severity.Style,
        "Companion object is not in the end of class",
        Debt.FIVE_MINS
    )

    override fun visitClassBody(classBody: KtClassBody) {
        val companionObject = classBody.allCompanionObjects.first()
        val endClass = classBody.endOffset
        val endCompanionObject = companionObject.endOffset
        if (endClass - endCompanionObject > lineTrashHold) {
            report(
                CodeSmell(
                    issue, Entity.from(classBody.containingKtFile),
                    "Companion object should be in the end of class with one extra line."
                )
            )
        }
    }

}

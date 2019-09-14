package io.example.detektRules.rules.naming

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.util.collectionUtils.concat

/**
 * This rule reports a member that has the same as the containing class or object.
 * The member should either be renamed or changed to a constructor.
 * Factory functions that create an instance of the class are exempt from this rule.
 *
 * <noncompliant>
 * class MethodNameEqualsClassName {
 *
 *     fun domethodNameEqualsClassName() { }
 * }
 *
 * class PropertyNameEqualsClassName {
 *
 *     val countPropertyEqualsClassName = 0
 * }
 * </noncompliant>
 *
 * <compliant>
 * class Manager {
 *
 *     companion object {
 *         // factory functions can have the same naming as the class
 *         fun manager(): Manager {
 *             return Manager()
 *         }
 *     }
 * }
 * </compliant>
 *
 * @configuration ignoreOverriddenFunction - if overridden functions should be ignored (default: true)
 *
 */
class MemberNameContainsClassName(config: Config = Config.empty) : Rule(config) {

    override val issue = Issue(
        javaClass.simpleName, Severity.Style,
        "A member should not be given the same naming as its parent class or object.",
        Debt.FIVE_MINS
    )

    private val classMessage = "A member is named after the class. This might result in confusion. " +
        "Either rename the member or change it to a constructor."
    private val objectMessage = "A member is named after the object. " +
        "This might result in confusion. Please rename the member."

    private val ignoreOverriddenFunction = valueOrDefault(IGNORE_OVERRIDDEN_FUNCTION, true)

    override fun visitClass(klass: KtClass) {
        if (!klass.isInterface()) {
            getMisnamedMembers(klass, klass.name)
                .concat(getMisnamedCompanionObjectMembers(klass))
                ?.forEach { report(CodeSmell(issue, Entity.from(it), classMessage)) }
        }
        super.visitClass(klass)
    }

    override fun visitObjectDeclaration(declaration: KtObjectDeclaration) {
        if (!declaration.isCompanion()) {
            getMisnamedMembers(declaration, declaration.name)
                .forEach { report(CodeSmell(issue, Entity.from(it), objectMessage)) }
        }
        super.visitObjectDeclaration(declaration)
    }

    private fun getMisnamedMembers(klassOrObject: KtClassOrObject, name: String?): List<KtNamedDeclaration> {
        val body = klassOrObject.body ?: return emptyList()
        val className = name?.let { StringBuilder(name) } ?: return emptyList()
        val declarations = getFunctions(body).concat(body.properties)
        return declarations?.filter { it.name?.contains(className, ignoreCase = true) == true }.orEmpty()
    }

    private fun getFunctions(body: KtClassBody): List<KtNamedDeclaration> {
        var functions = body.children.filterIsInstance<KtNamedFunction>()
        if (ignoreOverriddenFunction) {
            functions = functions.filter { !it.isOverride() }
        }
        return functions
    }

    private fun getMisnamedCompanionObjectMembers(klass: KtClass): List<KtNamedDeclaration> {
        return klass.companionObjects
            .flatMap { getMisnamedMembers(it, klass.name) }
            .filterNot { it is KtNamedFunction && isFactoryMethod(it, klass) }
    }

    private fun isFactoryMethod(function: KtNamedFunction, klass: KtClass): Boolean {
        val typeReference = function.typeReference
        return typeReference == null && function.bodyExpression !is KtBlockExpression ||
            typeReference?.text?.equals(klass.name) == true
    }

    private fun KtModifierListOwner.isOverride() = hasModifier(KtTokens.OVERRIDE_KEYWORD)

    companion object {
        const val IGNORE_OVERRIDDEN_FUNCTION = "ignoreOverriddenFunction"
    }
}


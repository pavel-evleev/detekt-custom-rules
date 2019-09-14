package io.example.detektRules.rules.style

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class EmptyLineBeforeEndClassTest {

    @Test
    fun shouldNotGenerateIssueCauseEmptyLineExist() {
        val findings = EmptyLineBeforeEndClass(Config.empty).lint(
            """
            package io.zensoft.detektRules.rules

            import io.gitlab.arturbosch.detekt.api.*
            import org.jetbrains.kotlin.psi.KtFile

            class EmptyLineBeforeEndClass(config: Config) : Rule(config) {
                override val issue = Issue(
                    javaClass.simpleName,
                    Severity.Style,
                    "Extra line in the end file",
                    Debt.FIVE_MINS
                )

                override fun visitKtFile(file: KtFile) {
                    val text = file.text
                    if (text.isNotEmpty() &&
                        text[text.lastIndex - 1] != '\n' ||
                        text[text.lastIndex - 1] != text[text.lastIndex - 2]
                    ) {
                        report(CodeSmell(issue, Entity.from(file, text.length - 2), "Have not found extra line"))
                    }
                }

            }
        """.trimIndent()
        )
        assertThat(findings).hasSize(0)
    }

    @Test
    fun shouldNotGenerateIssueCauseClassWithoutBody() {
        val findings = EmptyLineBeforeEndClass(Config.empty).lint(
            """
            package io.zensoft.detektRules.rules

            import io.gitlab.arturbosch.detekt.api.*
            import org.jetbrains.kotlin.psi.KtFile

            class EmptyLineBeforeEndClass(config: Config)
        """.trimIndent()
        )

        assertThat(findings).hasSize(0)
    }

    @Test
    fun shouldGenerateIssueCauseNoEmptyLineBeforeEndClass() {
        val findings = EmptyLineBeforeEndClass(Config.empty).lint(
            """
            package io.zensoft.detektRules.rules

            import io.gitlab.arturbosch.detekt.api.*
            import org.jetbrains.kotlin.psi.KtFile

            class EmptyLineBeforeEndClass(config: Config) : Rule(config) {
                override val issue = Issue(
                    javaClass.simpleName,
                    Severity.Style,
                    "Extra line in the end file",
                    Debt.FIVE_MINS
                )

                override fun visitKtFile(file: KtFile) {
                    val text = file.text
                    if (text.isNotEmpty() &&
                        text[text.lastIndex - 1] != '\n' ||
                        text[text.lastIndex - 1] != text[text.lastIndex - 2]
                    ) {
                        report(CodeSmell(issue, Entity.from(file, text.length - 2), "Have not found extra line"))
                    }
                }
            }
        """.trimIndent()
        )

        assertThat(findings).hasSize(1)
    }


    @Test
    fun shouldGenerateIssueCauseNoTabulationBeforeEndClass() {
        val findings = EmptyLineBeforeEndClass(Config.empty).lint(
            """
            package io.zensoft.detektRules.rules

            import io.gitlab.arturbosch.detekt.api.*
            import org.jetbrains.kotlin.psi.KtFile

            class EmptyLineBeforeEndClass(config: Config) : Rule(config) {
                override val issue = Issue(
                    javaClass.simpleName,
                    Severity.Style,
                    "Extra line in the end file",
                    Debt.FIVE_MINS
                )

                override fun visitKtFile(file: KtFile) {
                    val text = file.text
                    if (text.isNotEmpty() &&
                        text[text.lastIndex - 1] != '\n' ||
                        text[text.lastIndex - 1] != text[text.lastIndex - 2]
                    ) {
                        report(CodeSmell(issue, Entity.from(file, text.length - 2), "Have not found extra line"))
                    }
                }}
        """.trimIndent()
        )

        assertThat(findings).hasSize(1)
    }

    @Test
    fun shouldNotGenerateIssueCauseEmptyLineExistWithExtensionFile() {
        val findings = EmptyLineBeforeEndClass(Config.empty).lint(
            """
            package io.zensoft.detektRules.rules

            import io.gitlab.arturbosch.detekt.api.*
            import org.jetbrains.kotlin.psi.KtFile

            class EmptyLineBeforeEndClass(config: Config) : Rule(config) {
                override val issue = Issue(
                    javaClass.simpleName,
                    Severity.Style,
                    "Extra line in the end file",
                    Debt.FIVE_MINS
                )

                override fun visitKtFile(file: KtFile) {
                    val text = file.text
                    if (text.isNotEmpty() &&
                        text[text.lastIndex - 1] != '\n' ||
                        text[text.lastIndex - 1] != text[text.lastIndex - 2]
                    ) {
                        report(CodeSmell(issue, Entity.from(file, text.length - 2), "Have not found extra line"))
                    }
                }

            }

            fun <T : Message> ByteBuf.writeList(list: List<T>) {
                this.writeInt(list.size)
                list.forEach { it.write(this) }
            }

            inline fun <reified T : Message> ByteBuf.readSet(): MutableSet<T> {
                val size = this.readInt()
                val set = mutableSetOf<T>()
                for (index in 1..size) {
                    val instance = T::class.java.newInstance()
                    instance.read(this)
                    set.add(instance)
                }
                return set
            }

            fun <T : Message> ByteBuf.writeSet(set: Set<T>) {
                this.writeInt(set.size)
                set.forEach { it.write(this) }
            }
        """.trimIndent()
        )

        assertThat(findings).hasSize(0)
    }

    @Test
    fun shouldGenerateIssueCauseEmptyLineNotExistWithExtensionFile() {
        val findings = EmptyLineBeforeEndClass(Config.empty).lint(
            """
            package io.zensoft.detektRules.rules

            import io.gitlab.arturbosch.detekt.api.*
            import org.jetbrains.kotlin.psi.KtFile

            class EmptyLineBeforeEndClass(config: Config) : Rule(config) {
                override val issue = Issue(
                    javaClass.simpleName,
                    Severity.Style,
                    "Extra line in the end file",
                    Debt.FIVE_MINS
                )

                override fun visitKtFile(file: KtFile) {
                    val text = file.text
                    if (text.isNotEmpty() &&
                        text[text.lastIndex - 1] != '\n' ||
                        text[text.lastIndex - 1] != text[text.lastIndex - 2]
                    ) {
                        report(CodeSmell(issue, Entity.from(file, text.length - 2), "Have not found extra line"))
                    }
                }
            }

            fun <T : Message> ByteBuf.writeList(list: List<T>) {
                this.writeInt(list.size)
                list.forEach { it.write(this) }
            }

            inline fun <reified T : Message> ByteBuf.readSet(): MutableSet<T> {
                val size = this.readInt()
                val set = mutableSetOf<T>()
                for (index in 1..size) {
                    val instance = T::class.java.newInstance()
                    instance.read(this)
                    set.add(instance)
                }
                return set
            }

            fun <T : Message> ByteBuf.writeSet(set: Set<T>) {
                this.writeInt(set.size)
                set.forEach { it.write(this) }
            }
        """.trimIndent()
        )

        assertThat(findings).hasSize(1)
    }

}
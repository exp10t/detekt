package io.gitlab.arturbosch.detekt.rules.empty

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.rules.asBlockExpression
import org.jetbrains.kotlin.psi.KtExpression

/**
 * @author Artur Bosch
 */
abstract class EmptyRule(config: Config) : Rule(config) {

	override val issue = Issue(javaClass.simpleName,
			Severity.Minor,
			"Empty block of code detected. As they serve no purpose they should be removed.",
			Debt.FIVE_MINS)

	fun KtExpression.addFindingIfBlockExprIsEmpty() {
		this.asBlockExpression()?.statements?.let {
			if (it.isEmpty()) report(CodeSmell(issue, Entity.from(this)))
		}
	}
}

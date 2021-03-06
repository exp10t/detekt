package io.gitlab.arturbosch.detekt.cli.console

import io.gitlab.arturbosch.detekt.api.ConsoleReport
import io.gitlab.arturbosch.detekt.api.Detektion
import io.gitlab.arturbosch.detekt.api.PREFIX
import io.gitlab.arturbosch.detekt.api.format
import io.gitlab.arturbosch.detekt.core.processors.COMPLEXITY_KEY
import io.gitlab.arturbosch.detekt.core.processors.LLOC_KEY
import io.gitlab.arturbosch.detekt.core.processors.LOC_KEY
import io.gitlab.arturbosch.detekt.core.processors.NUMBER_OF_COMMENT_LINES_KEY
import io.gitlab.arturbosch.detekt.core.processors.SLOC_KEY

/**
 * @author Artur Bosch
 */
class ComplexityReport : ConsoleReport() {

	override val priority: Int = 20

	override fun render(detektion: Detektion): String? {
		val findings = detektion.findings
		val mcc = detektion.getData(COMPLEXITY_KEY)
		val loc = detektion.getData(LOC_KEY)
		val sloc = detektion.getData(SLOC_KEY)
		val lloc = detektion.getData(LLOC_KEY)
		val cloc = detektion.getData(NUMBER_OF_COMMENT_LINES_KEY)
		if (mcc != null && lloc != null && lloc > 0) {
			val numberOfSmells = findings.entries.sumBy { it.value.size }
			val smellPerThousandLines = numberOfSmells * ONE_THOUSAND / lloc
			val mccPerThousandLines = mcc * ONE_THOUSAND / lloc
			return with(StringBuilder()) {
				append("Complexity Report:".format())
				append("$loc lines of code (loc)".format(PREFIX))
				append("$sloc source lines of code (sloc)".format(PREFIX))
				append("$lloc logical lines of code (lloc)".format(PREFIX))
				append("$cloc comment lines of code (cloc)".format(PREFIX))
				append("$mcc McCabe complexity (mcc)".format(PREFIX))
				append("$numberOfSmells number of total code smells".format(PREFIX))
				append("$mccPerThousandLines mcc per 1000 lloc".format(PREFIX))
				append("$smellPerThousandLines code smells per 1000 lloc".format(PREFIX))
				toString()
			}
		} else {
			return null
		}
	}

	companion object {
		const val ONE_THOUSAND = 1000
	}
}

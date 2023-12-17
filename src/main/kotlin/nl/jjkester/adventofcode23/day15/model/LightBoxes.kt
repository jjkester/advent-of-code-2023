package nl.jjkester.adventofcode23.day15.model

/**
 * A collection of 256 light boxes that can contain lenses.
 */
class LightBoxes {

    private val data = List(256) { mutableListOf<Lens>() }

    /**
     * The total focusing power of the installed lenses.
     */
    val focusingPower: Long
        get() = data.asSequence()
            .mapIndexed { boxIndex, lenses ->
                lenses.asSequence()
                    .mapIndexed { lensIndex, lens ->
                        (boxIndex + 1L) * (lensIndex + 1L) * lens.focalLength
                    }
                    .sum()
            }
            .sum()

    /**
     * Performs the [step] to add or remove a lens. This changes the internal state of the light boxes.
     */
    fun performStep(step: InitializationStep) = HolidayAscii.hash(step.label).toInt().let { hash ->
        data[hash].run {
            when (step) {
                is InitializationStep.Add -> Lens(step.label, step.focalLength).let { lens ->
                    indexOfFirst { it.label == step.label }
                        .takeIf { it >= 0 }
                        ?.let { set(it, lens) }
                        ?: add(lens)
                }

                is InitializationStep.Remove -> removeIf { it.label == step.label }
            }
        }
    }
}

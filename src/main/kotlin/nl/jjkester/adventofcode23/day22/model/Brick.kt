package nl.jjkester.adventofcode23.day22.model

import nl.jjkester.adventofcode23.predef.space.RangeVolume
import nl.jjkester.adventofcode23.predef.space.Volume

/**
 * A falling brick.
 *
 * A [Brick] and its [volume] are immutable. Therefore, the [id] can be used to track a single brick in different stages
 * of the fall. When falling, the volume will change (the z range will shift down).
 *
 * @property id The assigned identifier to track the brick while falling.
 * @property volume The volume of the brick.
 */
data class Brick(val id: String, val volume: RangeVolume) : Volume by volume

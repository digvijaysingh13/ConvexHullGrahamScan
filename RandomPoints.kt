import java.util.*

internal fun IntRange.random(): Int = Random().nextInt((endInclusive + 1) - start) + start

fun getSampleDate(): MutableList<Point> {
    val rng = 10..670
    val ml = MutableList(100) { Point(0f, 0f) }
    for (e in 0..ml.lastIndex) {
        with(ml[e]) {
            x = rng.random().toFloat()
            y = rng.random().toFloat()
        }

    }
    return ml
}
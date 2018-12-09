import javax.swing.SwingUtilities

fun main(args: Array<String>) {

    val data = getSampleDate()

    val ch = ConvexHull(data)



    SwingUtilities.invokeLater {
        Graph(data, ch.chList)
    }

}


class ConvexHull(private val data: MutableList<Point>) {
    val chList by lazy { MutableList(data.size) { Point(data[it].x, data[it].y) } }

    init {
        sort()
        applyAlgo()
    }

    private fun sort() {
        chList.sortWith(compareByDescending<Point> { it.y }
                .thenByDescending { it.x })

        for (e in 1..chList.lastIndex) {
            chList[e].slopeWithRoot = (chList[e].y - chList[0].y) / (chList[e].x - chList[0].x)
        }
        val rootData = chList[0]
        chList.removeAt(0)
        val lMinus = mutableListOf<Point>()
        val lPlus = mutableListOf<Point>()
        for (e in chList) {
            if (e.slopeWithRoot < 0) {
                lMinus.add(e)
            } else {
                lPlus.add(e)
            }
        }

        lMinus.sortWith(compareByDescending { it.slopeWithRoot })
        lPlus.sortWith(compareByDescending { it.slopeWithRoot })

        chList.clear()

        chList.add(rootData)
        chList.addAll(lMinus)
        chList.addAll(lPlus)
    }

    private fun applyAlgo() {

        var index = 2
        while (index < chList.size) {
            val o = getOrientation(chList[index - 2], chList[index - 1], chList[index])
            when (o) {
                EOrientation.CLOCK_WISE -> {
                    chList.removeAt(index - 1) // remove the body and no increment
                    index--
                }
                else -> {  // In case of anti clockwise or collinear
                    index++
                }


            }
        }

    }

}


private fun getOrientation(tail: Point, body: Point, head: Point): EOrientation {
    val crossP = ((body.x - tail.x) * (head.y - tail.y)) - ((body.y - tail.y) * (head.x - tail.x))
    return when {
        crossP < 0f -> EOrientation.ANTI_CLOCK_WISE
        crossP > 0f -> EOrientation.CLOCK_WISE
        else -> EOrientation.COLLINEAR
    }
}


class Point(var x: Float, var y: Float) {
    var slopeWithRoot: Float = 0f
}


enum class EOrientation {
    CLOCK_WISE, ANTI_CLOCK_WISE, COLLINEAR
}
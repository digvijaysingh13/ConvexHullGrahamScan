import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel

class Graph(private val points:List<Point>,private val linePoints:List<Point>) : JFrame() {

    companion object {
        private val WIDTH = 800
        private val HEIGHT = 800
    }

    init {
        DrawGraph().also {
            it.preferredSize = Dimension(WIDTH, HEIGHT)
            this@Graph.contentPane.add(it)
        }

        defaultCloseOperation = EXIT_ON_CLOSE
        pack()
        title = "Convex Hull Experiment"
        isVisible = true
    }


    inner class DrawGraph() : JPanel() {

        override fun paintComponent(g: Graphics?) {
            super.paintComponents(g)
            background = Color.BLACK
            g?.run {
                color = Color.BLUE   // set the drawing color

                for (p in points){
                    fillOval(p.x.toInt(),p.y.toInt(),5,5)
                }

                var index = 0
                val last = linePoints.lastIndex
                color = Color.BLACK
                while (index<last){
                    drawLine(linePoints[index].x.toInt(),linePoints[index].y.toInt(),
                            linePoints[index+1].x.toInt(),linePoints[index+1].y.toInt())

                    index++
                }

                drawLine(linePoints[linePoints.lastIndex].x.toInt(),linePoints[linePoints.lastIndex].y.toInt(),
                        linePoints[0].x.toInt(),linePoints[0].y.toInt())

            }
        }

    }


}
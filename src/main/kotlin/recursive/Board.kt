package recursive

class Board(var cells: List<Cell>) {

    var lines = arrayListOf<Line>()
    var cols = arrayListOf<Col>()
    var squares = arrayListOf<Square>()
    var lastFoundCell: Cell? = null
    var DEBUG = false

    init {
        init()
    }

    private fun init() {

        for(index in 0..<9) {
            val lineCells = cells.filterIndexed { i, _ -> i / 9 == index}
            lines.add(Line("$index", lineCells.toTypedArray()))
        }

        for(index in 0..<9) {
            val colCells = cells.filterIndexed { i, _ -> i % 9 == index}
            cols.add(Col("$index", colCells.toTypedArray()))
        }

        for(squareY in 0..< 3) {
            for (squareX in 0..<3) {
                val initialXCell = squareX * 3
                val initialYCell = squareY * 3
                val currentSquareCells = arrayListOf<Cell>()
                for(deltaY in 0 ..< 3) {
                    for(deltaX in 0 ..< 3) {
                        currentSquareCells.add(get(initialXCell + deltaX, initialYCell+deltaY))
                    }
                }
                val currentSquare = Square("$squareX,$squareY", currentSquareCells.toTypedArray())
                squares.add(currentSquare)
            }
        }

    }

    fun clone(): Board {
        val clonedCells = cells.map { it.clone() }
        return Board(clonedCells)
    }

    fun isCompletelySolved() : Boolean {
        return nbFoundCells() == 81
    }



    fun nbFoundCells():Int {
        return cells.filter { it.foundValue != null }.size
    }

    // = cellAt()
    operator fun get(x:Int, y:Int): Cell {
        return cells[y*9 + x]
    }

    fun shapes(): Iterator<Shape> {
        val shapes = cols + lines + squares
        return shapes.iterator()
    }

    fun debug() {
        for(y in 0..<9) {
            if(y % 3 == 0) {
                println("------------")
            }
            for (x in 0..<9) {
                if(x % 3 == 0) {
                    print("|")
                }
                val cell = get(x, y)
                if(lastFoundCell == cell) {
                    print(green("" + cell))
                } else {
                    print(cell)
                }

            }
            println("|")
        }
        println("------------")
    }

    fun findFirstCellWithLessValuePossibilities(): Cell {

        return cells.filter { !it.isFound }.minBy { it.possibleValues.size }

    }


}

fun green(text:String): String {
    val greenColor = "\u001b[32m"
    val reset = "\u001b[0m"
    return "${greenColor}$text$reset"
}
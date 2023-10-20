package recursive

abstract class Shape(var name:String, var cells:Array<Cell>): Iterable<Cell>, CellListener {

    init {
        cells.forEach { it.addParentShape(this) }
    }


    override fun iterator(): Iterator<Cell> {
        return cells.iterator()
    }

    fun type(): String {
        return this.javaClass.simpleName
    }

    override fun valueFound(cell: Cell, newValue: Int) {
        // quand une veleur est trouvée, alors, on l'enleve de toutes les cellules des Shapes
        cells.forEach { cellInShape ->
           // val valueToRemove = requireNotNull()
            cellInShape.removeImpossibleValue(newValue)
        }
    }


    fun debug() {
        cells.forEach {
            println("${it} -- ${it.possibleValues}")
        }
        println()
    }
}


class Line(name:String, cells:Array<Cell>) : Shape(name, cells)
class Col(name:String,cells:Array<Cell>) : Shape(name, cells)
class Square(name:String, cells:Array<Cell>) : Shape(name, cells)

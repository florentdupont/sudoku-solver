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

    override fun toString(): String {
        return "${type()} $name"
    }

    override fun valueFound(cell: Cell, newValue: Int) {
        // quand une valeur est trouvée, alors, on l'enlève de toutes les cellules des Shapes
        cells.forEach { cellInShape ->
            cellInShape.removeImpossibleValue(newValue)
        }
    }


    fun debug() {
        cells.forEach {
            println("$it -- ${it.possibleValues}")
        }
        println()
    }
}


class Line(name:String, cells:Array<Cell>) : Shape(name, cells)
class Col(name:String,cells:Array<Cell>) : Shape(name, cells)
class Square(name:String, cells:Array<Cell>) : Shape(name, cells)
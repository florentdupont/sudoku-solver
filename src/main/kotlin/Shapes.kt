abstract class Shape(var name:String, var cells:Array<Cell>) {

    init {
        cells.forEach { it.addParentShape(this) }
    }

    fun type(): String {
        return this.javaClass.simpleName
    }

    fun removeFromPossibleValuesInShape(newlyFoundValue: Int) {
        // quand une valeur est trouvée, alors, elle est enlevée de toutes les cellules de la forme courant
        cells.forEach { cellInShape ->
            cellInShape.removeImpossibleValue(newlyFoundValue)
        }
    }
}


class Line(name:String, cells:Array<Cell>) : Shape(name, cells)
class Col(name:String,cells:Array<Cell>) : Shape(name, cells)
class Square(name:String, cells:Array<Cell>) : Shape(name, cells)

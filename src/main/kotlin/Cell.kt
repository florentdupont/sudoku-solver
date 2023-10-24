import exceptions.IllegalMove

class Cell(val x:Int, val y:Int, initialValue:Int?) {

    companion object {
        fun create(linearPosition: Int, value: Char): Cell {

            val y = linearPosition / 9
            val x = linearPosition % 9
            var initialValue:Int? = null
            if (value != ' ') {
                initialValue = value.digitToInt()
            }
            return Cell(x, y, initialValue)
        }
    }

    var owningShapes = arrayListOf<Shape>()
    lateinit var owningSolver: GameSolver
    private var _foundValue:Int? = null
    private var _possibleValues = ArrayList<Int>( (1..9).toList())

    init {
        _foundValue = initialValue
    }

    val possibleValues: ArrayList<Int>
        get()  {
            return if(isFound)
                arrayListOf()
            else
                ArrayList(_possibleValues)
        }

    fun addParentShape(shape: Shape) {
        owningShapes.add(shape)
    }

    fun removeImpossibleValue(value:Int) {
        if(isFound)
            return
        if(_possibleValues.contains(value)) {
            _possibleValues.remove(value)
            if (_possibleValues.size == 1) {
                // only 1 possible value left ! It has to be the value for this cell
                foundValue = _possibleValues[0]
                owningSolver.cellWasFound(this)
            }
        }
    }

    val isFound:Boolean
        get() = _foundValue != null

    var foundValue:Int?
        get() = _foundValue
        set(value) {
            if (isFound) {
                throw IllegalMove("Impossible d'affecter une valeur déjà positionnée")
            } else {
                _foundValue = value
                _possibleValues.clear()
            }
        }


    fun clone(): Cell {
        val clonedCell = Cell(x, y, foundValue)
        clonedCell._possibleValues = ArrayList(_possibleValues)
        return clonedCell
    }

    override fun toString(): String {
        return if (!isFound)
            "."
        else
            "$foundValue"
    }
}


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

    // en vrai, il serait plus logique que ce ne soit pas la cellule qui connaisse les autres shapes parentes
    // mais que la cellule "notifie" qu'une solution a été trouvée
    var parentShapes = arrayListOf<Shape>()
    lateinit var parentSolver: GameSolver
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
        parentShapes.add(shape)
    }


    fun removeImpossibleValue(value:Int) {
        if(isFound)
            return
        if(_possibleValues.contains(value)) {
            _possibleValues.remove(value)
            if (_possibleValues.size == 1) {
                println("Only 1 possible value left : ${_possibleValues[0]} is found for cell $x,$y")

                setFoundValueWithNoPropagation(_possibleValues[0])
                // ajoute dans la liste des items
                parentSolver.eventCellWasFound(this)

            }
        }
    }


    // TODO, peut-être que ça pourrait être remonté dans le gameSolver...
    fun notifyParentShapesValueWasFound() {
        parentShapes.forEach { parentShape ->
            val newValue = requireNotNull(foundValue)
            parentShape.valueFound(this, newValue)
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
                //notifyParentShapesValueWasFound()
            }
        }

    fun setFoundValueWithNoPropagation(value:Int) {
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

interface CellListener {

    fun valueFound(cell: Cell, newValue: Int)



}
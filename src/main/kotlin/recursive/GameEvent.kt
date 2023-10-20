package recursive

open class GameEvent

data class CellFoundEvent(var foundCell: Cell, var newValue:Int) : GameEvent() {

}

//class LastFoundValuePossible(var cell:Cell, var lastFoundValuePossible: Int): GameEvent()

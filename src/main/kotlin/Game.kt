import utils.println
import java.util.LinkedList
import java.util.Queue

class Game {

    var possibleAlterableBoards:Queue<Board> = LinkedList()

    lateinit var initialBoard: Board

    fun prepare(content:String) {
        val cells = arrayListOf<Cell>()
        var linearPosition = 0
        val authorizedChars = (1..9).map { it.digitToChar() } + ' '
        content.toCharArray().forEach { char ->
            if(authorizedChars.contains(char)) {
                val cell = Cell.create(linearPosition, char)
                cells.add(cell)
                linearPosition++
            }
        }
       initialBoard = Board(cells)
    }

    fun solve(): Board {

        lateinit var result: Board

        val initialSolver = GameSolver(initialBoard)

        initialSolver.eliminatePossibleValuesInOwningShapes()

        if(!initialSolver.board.isCompletelySolved()) {
            initialSolver.eliminateUniqueValuesFromShapes()
        }

        if(!initialSolver.board.isCompletelySolved()) {
            possibleAlterableBoards.add(initialSolver.board)
        } else {
            result = initialSolver.board
        }


        var maxIteration = 10
        var isSolved = false

        while(possibleAlterableBoards.isNotEmpty() && maxIteration > 0 && !isSolved) {

            val alternativeBoard = possibleAlterableBoards.poll()

            val firstCellWithFewPossibility = alternativeBoard.findFirstCellWithLessValuePossibilities()

            val alternativeValues = ArrayList(firstCellWithFewPossibility.possibleValues)
            for (alternativeValue in alternativeValues) {
                try {
                    val alternatedBoard = testAlternative(alternativeBoard, firstCellWithFewPossibility, alternativeValue)
                    if(alternatedBoard.isCompletelySolved()) {
                        // si le board est completé, alors, le jeu est fini.
                        result = alternatedBoard
                        isSolved = true
                        break
                    }

                    // a la fin de la tentative de résolution alternative, il est possible que le tableau ne soit toujours pas résolu.
                    // il faut donc tenter une nouvelle alternative sur le tableau restant.
                    possibleAlterableBoards.add(alternatedBoard)

                } catch (error: Exception) {
                    // could be IllegalMove or ImpossibleBoardState
                    println { "Cette alternative n'est pas possible ... ".red }
                }
            }

            maxIteration--
        }

       return result

    }


    private fun testAlternative(initialBoard: Board, cellAlternative: Cell, alternativeValue: Int ): Board {
        println("Testing alternative on Board")
        println("trying with alternative $alternativeValue for cell ${cellAlternative.x}, ${cellAlternative.y}")

        val alternativeSolver = GameSolver(initialBoard)
        val cellToAlternate = alternativeSolver.board[cellAlternative.x, cellAlternative.y]
        cellToAlternate.foundValue = alternativeValue
        alternativeSolver.cellWasFound(cellToAlternate)

        alternativeSolver.eliminatePossibleValuesInOwningShapes()
        alternativeSolver.eliminateUniqueValuesFromShapes()

        return alternativeSolver.board
    }




}

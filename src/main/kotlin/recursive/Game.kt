package recursive

import common.ImpossibleGame

class Game {

    lateinit var board:Board

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
       board = Board(cells)
    }

    fun solve() {

        val initialSolver = GameSolver(board)

        initialSolver.firstPass()

        if(!initialSolver.board.isCompletelySolved()) {
            initialSolver.secondPass()
        }

        var finallySolvedSolver: GameSolver = initialSolver
        var continueSolving = !finallySolvedSolver.board.isCompletelySolved()

        try {

            while(continueSolving) {

                // find the first
                val firstCellWithFewPossibility = finallySolvedSolver.board.findFirstCellWithLessValuePossibilities()
                println("$firstCellWithFewPossibility : ${firstCellWithFewPossibility.possibleValues}")

                val alternativeValues = ArrayList(firstCellWithFewPossibility.possibleValues)

                for (alternativeValue in alternativeValues) {
                    try {
                        finallySolvedSolver = testAlternative(finallySolvedSolver.board, firstCellWithFewPossibility, alternativeValue)
                        if(finallySolvedSolver.board.isCompletelySolved())
                            break
                    } catch (error: ImpossibleGame) {
                        error.printStackTrace()
                        println("Cette alternative n'est pas possible ... ")
                    }
                }

                continueSolving = !finallySolvedSolver.board.isCompletelySolved()

            }
        } catch (e:Exception) {
            e.printStackTrace()
            println("DEBUGGING")
            finallySolvedSolver.board.debug()
        }

        this.board = finallySolvedSolver.board
    }


    fun testAlternative(initialBoard: Board, cellAlternative: Cell, alternativeValue: Int ): GameSolver {
        println("trying with alternative $alternativeValue for cell ${cellAlternative.x}, ${cellAlternative.y}")

        val alternativeSolver = GameSolver(initialBoard)
        val cellToAlternate = alternativeSolver.board[cellAlternative.x, cellAlternative.y]
        cellToAlternate.foundValue = alternativeValue

        alternativeSolver.firstPass()
        alternativeSolver.secondPass()

        // faire un try harder ?
        return alternativeSolver
    }




}

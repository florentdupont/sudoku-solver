package recursive

fun main() {

    val game = Game()
    val initialBoard = game.prepare(mediumLevel())

    //game.board.debug()

   // val initialBoard = game.board

    val solver = GameSolver(initialBoard)
    solver.firstPass()
    val lastSolvedBoard = solver.board

    println("INITIAL BOARD")
    initialBoard.debug()

    println("FINAL BOARD")
    lastSolvedBoard.debug()

    if(!lastSolvedBoard.isCompletelySolved()) {
        // find the first
        val firstCellWithFewPossibility = lastSolvedBoard.findFirstCellWithLessValuePossibilities()

        val alternativeValues = ArrayList(firstCellWithFewPossibility.possibleValues)
        // on fait un hypothèse ici
        for (i in alternativeValues) {
            val alternativeSolver = GameSolver(lastSolvedBoard)
            val cellToAlternate = alternativeSolver.board[firstCellWithFewPossibility.x, firstCellWithFewPossibility.y]
            cellToAlternate.foundValue = i
            //cellToAlternate.foundValue = 9
            cellToAlternate.possibleValues.clear()
            cellToAlternate.possibleValues.add(i)
            alternativeSolver.firstPass()
            val finallySolved = alternativeSolver.board
            println("A LA FIN")
            finallySolved.debug()
        }
    }


   /*for (i in 0 ..<9) {
        println("Line $i")
        game.board.lineAt(i).debug()
    }

    for (i in 0 ..<9) {
        println("Col $i")
        game.board.colAt(i).debug()
    }*/

   /* println("ETAT FINAL")
    for (x in 0..<3) {
        for(y in 0..<3) {
            game.board.squareAt(x, y).debug()
        }
    }*/

    //game.board.debug()

   /* println("ETAT FINAL")
    for (x in 0..<3) {
        for(y in 0..<3) {
            game.board.squareAt(x, y).debug()
        }
    }
    */

}

fun mediumLevel() : String {
    return  " 3 | 59| 1 " +
            "12 |   |   " +
            "  5|   | 76" +
            //-------------
            "   |2 3|4  " +
            "8 3| 1 |  2" +
            "   |6 8|5  " +
            //-------------
            "  4|   | 63" +
            "31 |   |   " +
            " 7 | 82| 4 "
}


// en mode difficile, il faut faire des hypothèses
fun hardLevel() : String {
    return  "   | 46|   \n" +
            "21 |   |   \n" +
            "   |   |26 \n" +
            // "-----------\n" +
            "  1|8  |  3\n" +
            " 69|5 4|  7\n" +
            "  5|3  |  2\n" +
            //  "-----------\n" +
            "   |   |45 \n" +
            "73 |   |   \n" +
            "   | 18|   "
}

fun hardLevel2() : String {
    return  "  2|   | 9 \n" +
            "   |2  |3 7\n" +
            "7  |3 6| 12\n" +
            // "-----------\n" +
            " 2 |  3| 8 \n" +
            " 7 |41 | 6 \n" +
            "5  |   |   \n" +
            //  "-----------\n" +
            "  7|  1|   \n" +
            "984| 7 |  5\n" +
            " 1 | 4 |7  "
}














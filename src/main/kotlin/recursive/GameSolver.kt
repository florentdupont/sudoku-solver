package recursive

import java.util.NoSuchElementException


class GameSolver(boardToSolve: Board) : CellListener {

    var board: Board

    init {
        board = boardToSolve.clone()
    }


    /**
     * La 1ere passe itère sur toutes les cellules trouvée et les "reveille"
     * pour qu'elles notifient à leur tour leur formes parentes...
     * Les formes parentes vont réduire les valeurs possibles des leurs cases.
     * Ce qui pourra avoir comme conséquences de réduire à 1 seule valeur possible sur une case
     * => et de faire en sorte qu'une seule valeur unique deviennent la valeur trouvée.
     * et a son tour déclenche les formes parentes pour qu'elles se mettent à jour.
     */
    fun firstPass() {
        board.cells.forEach { cell ->
            if (cell.isFound) {
                cell.notifyValueWasFound()
            }
        }

    }

    fun secondPass() {

        val maxIteration = 12

        // run the game
        for(iteration in 0..<maxIteration) {
            var nbFoundInThisIteration = 0

            board.shapes().forEach { shape ->
                val nbFound = tryToFindUnicityInShape(shape)
                // des valeurs ont pu être positionnées sur des cellules
                // Mais elles n'ont pas relancée de mise à jour des shapes parentes
                // On relance donc une firstPass pour qu'elles soient remises à jour.
                firstPass()
                nbFoundInThisIteration += nbFound
                if (nbFound > 0)
                    println("$nbFound cell(s) found in $shape")
            }

            if(nbFoundInThisIteration == 0) {
                println("Breaking loop because no new solution can be found")
                break
            }
        }

        println("Found Cells : ${board.nbFoundCells()}")
        println("no more cells can be found")

    }

    private fun tryToFindUnicityInShape(shape: Shape):Int {

        var nbFoundCells = 0

        // fait une analyse en 2 étapes :
        // 1ere etape :
        // prend toutes les valeurs possibles de chaque cell de la Shape
        // et place dans une map {ValeurPossible-> Nb de fois ou cette valeur est possible au sein de la Shape}
        // on obtient par exemple
        //   1 : 2
        //   2 : 1
        //   4 : 3

        val occurencesByRemainingValue = hashMapOf<Int,Int>()
        shape.cells.filter { !it.isFound }.forEach{ cell ->
            cell.possibleValues.forEach { value ->
                if(occurencesByRemainingValue.containsKey(value)) {
                    occurencesByRemainingValue[value] = occurencesByRemainingValue[value]!! + 1
                } else {
                    occurencesByRemainingValue[value] = 1
                }
            }
        }

       // 2eme étape, on ne garde que les entrées qui ont 1 seule valeur possible, c'est à dire
       // qui sont de la forme {x->1}
       // ces valeurs, si elles sont uniques, ne peuvent être positionnées qu'à 1 seul endroit.
       // il suffit donc de placer cette valeur sur la seul case de la cellule qui est possible.
        occurencesByRemainingValue.entries.forEach { occ->
            if(occ.value == 1) {
                var foundCell:Cell
                try {
                    foundCell = shape.cells.first { it.possibleValues.contains(occ.key) }
                } catch (e:NoSuchElementException) {
                    e.printStackTrace()
                    // println(shape.cells.)
                    shape.cells.forEach {
                        println("$it : ${it.possibleValues}")
                    }
                    println("trying to find ${occ.key}")
                    throw  e
                }

                nbFoundCells++
                val foundValue = occ.key
                // TODO ATTENTION l'affectation ici a pu changer le contenu de la mapOccurence
                // qui n'est peut-être plus à jour !
                // peut être qu'il ne faut que positionner la valeur ici, mais ne PAS notifier les cellules parentes.
               // foundCell.foundValue = foundValue
                foundCell.setFoundValueWithNoPropagation(foundValue)
            }
        }
        return nbFoundCells
    }



    override fun valueFound(cell: Cell, newValue: Int) {

        board.lastFoundCell = cell

    }






}
import exceptions.ImpossibleBoardState
import java.util.*


class GameSolver(boardToSolve: Board) {

    var nextCellsToNotifyOwningShapes:Queue<Cell> = LinkedList()

    var board: Board

    init {
        board = boardToSolve.clone()
        board.cells.forEach { it.owningSolver = this }

        // Empile chaque cellule qui est déjà trouvée dans la file de traitement
        board.cells.forEach { cell ->
            if (cell.isFound) {
                nextCellsToNotifyOwningShapes.add(cell)
            }
        }
    }


    /**
     * La 1ere passe itère sur toutes les cellules trouvées et les "reveille"
     * pour qu'elles notifient à leur tour leur formes parentes...
     * Les formes parentes vont réduire les valeurs possibles de leurs cases.
     * Ce qui pourra avoir comme conséquences de réduire à 1 seule valeur possible sur une case
     * => et de faire en sorte qu'une seule valeur unique deviennent la valeur trouvée.
     * et a son tour déclenche les formes parentes pour qu'elles se mettent à jour.
     */
    fun eliminatePossibleValuesInOwningShapes() {
        // Dépile la liste de traitement des cellules trouvées
        while (!nextCellsToNotifyOwningShapes.isEmpty()) {
            val cell= nextCellsToNotifyOwningShapes.poll()

            cell.owningShapes.forEach { owningShape ->
                val foundValue = requireNotNull(cell.foundValue)
                owningShape.removeFromPossibleValuesInShape(foundValue)
            }
        }
    }


    fun cellWasFound(cell: Cell) {
        nextCellsToNotifyOwningShapes.add(cell)
    }

    /**
     * La seconde passe recherche non plus par cellule, mais par Shape.
     * S'il n'existe plus qu'une seule valeur possible pour une Shape, alors
     * il faudra affecter la case dessus.
     */
    fun eliminateUniqueValuesFromShapes() {
        board.shapes().forEach { shape ->
            tryToFindUnicityInShape(shape)

            // des valeurs ont pu être positionnées sur des cellules
            // Mais elles n'ont pas relancée de mise à jour des shapes parentes
            // On relance donc une firstPass pour qu'elles soient remises à jour.
            // avant de relancer une recherhe d'unicité sur une prochaine
            eliminatePossibleValuesInOwningShapes()
        }

    }

    private fun tryToFindUnicityInShape(shape: Shape) {

        // fait une analyse en 2 étapes :
        // 1ere etape :
        // prend toutes les valeurs possibles de chaque cell de la Shape
        // et place dans une map {ValeurPossible-> Nb de fois ou cette valeur est possible au sein de la Shape}
        // on obtient par exemple
        //   [ 1->2, 2->1, 4->3]
        val occurrencesByRemainingValue = hashMapOf<Int,Int>()

        // les boards alternatifs peuvent amener à des situations impossible.
        if(shape.cells.filter { !it.isFound }.size == 1) {
            val lastNotFoundCellInShape = shape.cells.first { !it.isFound }
            if(lastNotFoundCellInShape.possibleValues.size > 1)
                throw ImpossibleBoardState("La cell $lastNotFoundCellInShape ne peut pas encore avoir plusieurs " +
                        "solutions ${lastNotFoundCellInShape.possibleValues} car c'est normalement la " +
                        "dernière cellule non trouvée du Board")
        }

        shape.cells.filter { !it.isFound }.forEach{ cell ->
            cell.possibleValues.forEach { value ->
                if(occurrencesByRemainingValue.containsKey(value)) {
                    occurrencesByRemainingValue[value] = occurrencesByRemainingValue[value]!! + 1
                } else {
                    occurrencesByRemainingValue[value] = 1
                }
            }
        }

       // 2eme étape, on ne garde que les entrées qui ont 1 seule valeur possible, c'est à dire
       // qui sont de la forme {x->1}
       // ces valeurs, si elles sont uniques, ne peuvent être positionnées qu'à 1 seul endroit.
       // il suffit donc de placer cette valeur sur la seule case de la cellule qui est possible.
        occurrencesByRemainingValue.entries.filter { it.value == 1 }.forEach { occ->
            val foundCell = shape.cells.first { it.possibleValues.contains(occ.key) }
            val foundValue = occ.key
            foundCell.foundValue = foundValue
            cellWasFound(foundCell)
        }

    }

}
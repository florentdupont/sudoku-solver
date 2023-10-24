import utils.ColoredConsole
import utils.println
import java.util.*


class GameSolver(boardToSolve: Board) : CellListener {

    var nextCellsToNotifyParentsShapes:Queue<Cell> = LinkedList()

    var board: Board

    init {
        board = boardToSolve.clone()
        board.cells.forEach { it.parentSolver = this }

        // Empile chaque cellule qui est déjà trouvée dans la file de traitement
        board.cells.forEach { cell ->
            if (cell.isFound) {
                nextCellsToNotifyParentsShapes.add(cell)
            }
        }
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
        // Dépile la liste de traitement des cellules trouvées
        while (!nextCellsToNotifyParentsShapes.isEmpty()) {
            val cell= nextCellsToNotifyParentsShapes.poll() // Récupère et supprime le premier élément
            // force la cellule a notifier les shapes parents
            cell.notifyParentShapesValueWasFound()
        }
    }


    fun eventCellWasFound(cell: Cell) {
        nextCellsToNotifyParentsShapes.add(cell)
    }

    /**
     * La seconde passe recherche non plus par cellule, mais par Shape.
     * S'il n'existe plus qu'une seule valeur possible pour une Shape, alors
     * il faudra affecter la case dessus.
     */
    fun secondPass() {
       // board.debug()
        board.shapes().forEach { shape ->
            tryToFindUnicityInShape(shape)

            // des valeurs ont pu être positionnées sur des cellules
            // Mais elles n'ont pas relancée de mise à jour des shapes parentes
            // On relance donc une firstPass pour qu'elles soient remises à jour.
            // avant de relancer une recherhe d'unicité sur une prochaine
            firstPass()
        }

    }

    private fun tryToFindUnicityInShape(shape: Shape):Int {
        println("Try to find unicity in ${shape.type()} ${shape.name}")

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
       // il suffit donc de placer cette valeur sur la seule case de la cellule qui est possible.
        occurencesByRemainingValue.entries.filter { it.value == 1 }.forEach { occ->
            var foundCell = shape.cells.first { it.possibleValues.contains(occ.key) }
            nbFoundCells++
            val foundValue = occ.key
            foundCell.setFoundValueWithNoPropagation(foundValue)
            eventCellWasFound(foundCell)
        }
        return nbFoundCells
    }



    override fun valueFound(cell: Cell, newValue: Int) {

        board.lastFoundCell = cell

    }






}
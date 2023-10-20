import common.ImpossibleGame
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import recursive.*

class RecursiveBoardTest {

    @Test
    fun `medium level should pass`() {

        val game = Game()
        game.prepare(mediumLevel())
        game.solve()

        val solvedBoard = game.board

        assertThat(solvedBoard.isCompletelySolved()).isTrue()
        assertThat(solvedBoard.nbFoundCells()).isEqualTo(81)

    }


    @Test
    fun `hard level 1 should pass`() {

        val game = Game()
        game.prepare(hardLevel())
        game.solve()

        val solvedBoard = game.board

        assertThat(solvedBoard.isCompletelySolved()).isTrue()
        assertThat(solvedBoard.nbFoundCells()).isEqualTo(81)

    }

    @Test
    fun `hard level 2 should pass`() {

        val game = Game()
        game.prepare(hardLevel2())
        game.solve()

        val solvedBoard = game.board

        assertThat(solvedBoard.isCompletelySolved()).isTrue()
        assertThat(solvedBoard.nbFoundCells()).isEqualTo(81)

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

    fun mediumLevel3() : String {
        return  " 2 |  4|3  " +
                "9  | 2 |  8" +
                "   |6 9| 5 " +
                //-------------
                "   |   |  1" +
                " 72|5 3|68 " +
                "6  |   |   " +
                //-------------
                " 8 |2 5|   " +
                "1  | 9 |  3" +
                "  9|8  | 6 "
    }

    fun mediumLevelFinished() : String {
        return  "827|154|396" +
                "965|327|148" +
                "341|689|752" +
                //-------------
                "593|468|271" +
                "472|513|689" +
                "618|972|435" +
                //-------------
                "786|235|914" +
                "154|796|823" +
                "239|841|567"
    }

    fun hardLevel() : String {
        return  "   | 46|   " +
                "21 |   |   " +
                "   |   |26 " +
                // "-----------\n" +
                "  1|8  |  3" +
                " 69|5 4|  7" +
                "  5|3  |  2" +
                //  "-----------\n" +
                "   |   |45 " +
                "73 |   |   " +
                "   | 18|   "
    }

    fun expertLevel() : String {
        return  "   | 3 |   " +
                "   |  9|81 " +
                "6 3|   |5  " +
                // "-----------\n" +
                " 7 |9 2|   " +
                "  9|  5| 36" +
                "  1| 7 |   " +
                //  "-----------\n" +
                "   |   | 4 " +
                "  5|2 8|   " +
                "  2| 9 | 7 "
    }
}
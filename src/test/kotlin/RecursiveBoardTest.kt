import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RecursiveBoardTest {

    @Test
    fun `already finished level should return the same board`() {

        val game = Game()
        game.prepare(finishedBoard())

        val solvedBoard = game.solve()

        assertThat(solvedBoard.isCompletelySolved()).isTrue()
        assertThat(solvedBoard.nbFoundCells()).isEqualTo(81)

    }

    @Test
    fun `Super Easy level`() {
        val game = Game()
        game.prepare(superEasyBoard())
        val solvedBoard = game.solve()
        assertThat(solvedBoard.isCompletelySolved()).isTrue()
        assertThat(solvedBoard.nbFoundCells()).isEqualTo(81)
    }


    @Test
    fun `medium level should pass`() {

        val game = Game()
        game.prepare(mediumLevel())

        val solvedBoard = game.solve()
        assertThat(solvedBoard.isCompletelySolved()).isTrue()
        assertThat(solvedBoard.nbFoundCells()).isEqualTo(81)

    }

    @Test
    fun `medium level 2 should pass`() {

        val game = Game()
        game.prepare(mediumLevel2())

        val solvedBoard = game.solve()

        assertThat(solvedBoard.isCompletelySolved()).isTrue()
        assertThat(solvedBoard.nbFoundCells()).isEqualTo(81)

    }


    @Test
    fun `hard level 1 should pass`() {

        val game = Game()
        game.prepare(hardLevel())
        val solvedBoard = game.solve()

        solvedBoard.debug()
        assertThat(solvedBoard.isCompletelySolved()).isTrue()
        assertThat(solvedBoard.nbFoundCells()).isEqualTo(81)

    }


    @Test
    fun `expert level should pass`() {

        val game = Game()
        game.prepare(expertLevel())
        game.initialBoard.debug()

        val solvedBoard = game.solve()

        solvedBoard.debug()
        assertThat(solvedBoard.isCompletelySolved()).isTrue()
        assertThat(solvedBoard.nbFoundCells()).isEqualTo(81)

    }

    @Test
    fun `evil level should pass`() {

        val game = Game()
        game.prepare(evilLevel())
        game.initialBoard.debug()

        val solvedBoard = game.solve()

        solvedBoard.debug()
        assertThat(solvedBoard.isCompletelySolved()).isTrue()
        assertThat(solvedBoard.nbFoundCells()).isEqualTo(81)

    }

    @Test
    fun `evil level 2 should pass`() {

        val game = Game()
        game.prepare(evilLevel2())
        game.initialBoard.debug()

        val solvedBoard = game.solve()

        solvedBoard.debug()
        assertThat(solvedBoard.isCompletelySolved()).isTrue()
        assertThat(solvedBoard.nbFoundCells()).isEqualTo(81)

    }

    @Test
    fun `evil level 3 should pass`() {

        val game = Game()
        game.prepare(evilLevel3())
        game.initialBoard.debug()

        val solvedBoard = game.solve()

        solvedBoard.debug()
        assertThat(solvedBoard.isCompletelySolved()).isTrue()
        assertThat(solvedBoard.nbFoundCells()).isEqualTo(81)

    }



}
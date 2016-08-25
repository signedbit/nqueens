package net.signedbit.nqueens;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class NQueensProblemTest {
    @Test
    public void testSolveNQueens() throws Exception {
        // assert correct output
        assertEquals("Q \n\n", getOutputForNQueen(1));
        assertEquals(
                ". Q . . \n" +
                        ". . . Q \n" +
                        "Q . . . \n" +
                        ". . Q . \n" +
                        "\n",
                getOutputForNQueen(4));

        // assert illegal argument exception
        try {
            NQueensProblem.solveNQueens(-1);
            NQueensProblem.solveNQueens(0);
            NQueensProblem.solveNQueens(32);
            fail("Expected an illegal argument exception to be thrown");
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(), is("n should be between 0 and 32"));
        }

        // assert where n is 2 and 3 (no solutions)
        assertEquals(String.format("no solution for %d-queens%n", 2), getOutputForNQueen(2));
        assertEquals(String.format("no solution for %d-queens%n", 3), getOutputForNQueen(3));
    }

    /**
     * Gets the output of calling {@link NQueensProblem#solveNQueens(int)} as a String.
     * This is convoluted as the spec requires the output to be printed to
     * standard out rather than returned as a String.
     * It would be nicer if we could return the output instead of printing it.
     * <p/>
     * Note: this doesn't forward the output to the real standard out.
     *
     * @param n see {@link NQueensProblem#solveNQueens(int)} for an explanation
     * @return the printed chessboard as a String.
     * @throws UnsupportedEncodingException
     */
    private String getOutputForNQueen(final int n) throws UnsupportedEncodingException {
        // save the old standard out
        final PrintStream oldOut = System.out;

        // set the new standard out
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // solve the problem
        NQueensProblem.solveNQueens(n);
        final String output = out.toString(StandardCharsets.US_ASCII.name());

        // restore the old standard out
        System.setOut(oldOut);
        return output;
    }
}

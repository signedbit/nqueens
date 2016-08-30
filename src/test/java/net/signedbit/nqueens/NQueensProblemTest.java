package net.signedbit.nqueens;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class NQueensProblemTest {
    @Test
    public void testSolveNQueens() throws Exception {
        // assert correct output
        assertEquals("Q \n", NQueensProblem.solveNQueens(1));
        assertEquals(
                ". Q . . \n" +
                        ". . . Q \n" +
                        "Q . . . \n" +
                        ". . Q . \n",
                NQueensProblem.solveNQueens(4));

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
        assertEquals(String.format("no solution for %d-queens", 2), NQueensProblem.solveNQueens(2));
        assertEquals(String.format("no solution for %d-queens", 3), NQueensProblem.solveNQueens(3));
    }
}

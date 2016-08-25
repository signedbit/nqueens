package net.signedbit.nqueens;

/**
 * A simple class to compute any one solution to the N-Queens problem
 * and print the board showing where the queens can be placed.
 * <p>
 * When n is 2 or 3, there is no solution. Further, when n is 1, the solution is trivial.
 * <p>
 * Since it returns the same board for every n, we could just precompute them and store as String constants.
 * <p>
 * Spec for reference:
 * A single solution, for a board of size N where N is given as an input.
 * You can assume N less than (say) 32 for a backtracking solution, or 256 for one of the more advanced algorithms.
 * I don't mind which you use, we're not going for a product here.
 * Let's say the spec is that the program is a function which takes an integer argument,
 * and prints, using System.out.println, an NxN ascii grid representing any single solution.
 * Keep it as simple as you need to.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Eight_queens_puzzle">Wikipedia page on the 8-Queens puzzle</a>
 */
public class NQueensProblem {
    /**
     * Solve for one solution of the N-Queens problem and print the chessboard to standard out.
     * Note that when n is 2 or 3, there is no solution.
     * Further, when n is 1, the solution is trivial.
     *
     * @param n the length of one side of a standard chessboard
     * @throws IllegalArgumentException if n is not between 0 and 32
     */
    public static void solveNQueens(final int n) {
        if (n <= 0 || n >= 32) {
            throw new IllegalArgumentException("n should be between 0 and 32");
        }

        if (n == 2 || n == 3) {
            System.out.printf("no solution for %d-queens%n", n);
            return;
        }

        final boolean[][] board = new boolean[n][n];

        NQueensProblem.solveNQueensInternal(n, board, 0);

        final String asciiBoard = NQueensProblem.convertBoardToAsciiString(n, board);
        System.out.println(asciiBoard);
    }

    /**
     * Perform the actual solving of the N-Queens problem.
     * This uses a backtracking algorithm which tries to place one queen in each row at a time.
     *
     * @param n     the length of one side of a standard chessboard
     * @param board the n-by-n chessboard represented as a 2D boolean array
     *              where true means a queen has been placed in that square and false otherwise
     * @param row   the current 0-indexed row that we are trying to place a queen on
     * @return true if a solution is found and we should stop or false to continue
     */
    private static boolean solveNQueensInternal(final int n, final boolean[][] board, final int row) {
        if (row == n) {
            // we've placed all n queens and so we've arrived at a solution :-)
            return true;
        }

        for (int i = 0; i < n; i++) {
            if (!NQueensProblem.isAttackable(n, board, row, i)) {
                board[row][i] = true; // place a queen here

                if (NQueensProblem.solveNQueensInternal(n, board, row + 1)) {
                    return true;
                }

                board[row][i] = false; // remove the queen here since there is no solution that has a queen here
            }
        }
        return false;
    }

    /**
     * Queens may attack any piece in the same row, column, or diagonal.
     *
     * @param n     the length of one side of a standard chessboard
     * @param board the n-by-n chessboard represented as a 2D boolean array
     *              where true means a queen has been placed in that square and false otherwise
     * @param row   the row where we are considering placing the queen
     * @param col   the column where we are considering placing the queen
     * @return whether the given square can be attacked by an already placed queen
     */
    private static boolean isAttackable(final int n, boolean[][] board, final int row, final int col) {
        return NQueensProblem.isRowAttackable(board, row) ||
                NQueensProblem.isColumnAttackable(board, col) ||
                NQueensProblem.isDiagonallyAttackable(n, board, row, col);
    }

    /**
     * Determine if the given row can be attacked by a previously placed queen going horizontally
     *
     * @param board the n-by-n chessboard represented as a 2D boolean array
     *              where true means a queen has been placed in that square and false otherwise
     * @param row   the 0-indexed row to check
     * @return whether the row is attackable by a previously placed queen
     */
    private static boolean isRowAttackable(final boolean[][] board, final int row) {
        for (final boolean square : board[row]) {
            if (square) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if the given column can be attacked by a previously placed queen going vertically
     *
     * @param board the n-by-n chessboard represented as a 2D boolean array
     *              where true means a queen has been placed in that square and false otherwise
     * @param col   the 0-indexed column to check
     * @return whether the column is attackable by a previously placed queen
     */
    private static boolean isColumnAttackable(final boolean[][] board, final int col) {
        for (final boolean[] row : board) {
            if (row[col]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if the given square can be attacked by a previously placed queen going diagonally
     *
     * @param n     the length of one side of a standard chessboard
     * @param board the n-by-n chessboard represented as a 2D boolean array
     *              where true means a queen has been placed in that square and false otherwise
     * @param row   the 0-indexed row of the queen we're considering placing
     * @param col   the 0-indexed column of the queen we're considering placing
     * @return whether the square is attackable by a previously placed queen
     */
    private static boolean isDiagonallyAttackable(final int n,
                                                  final boolean[][] board,
                                                  final int row,
                                                  final int col) {
        return NQueensProblem.isNegSlopeDiagonalAttackable(n, board, row, col) ||
                NQueensProblem.isPosSlopeDiagonalAttackable(n, board, row, col);
    }

    /**
     * Determine if the given square can be attacked by a previously placed queen on a negative slope diagonal
     *
     * @param n     the length of one side of a standard chessboard
     * @param board the n-by-n chessboard represented as a 2D boolean array
     *              where true means a queen has been placed in that square and false otherwise
     * @param row   the 0-indexed row of the queen we're considering placing
     * @param col   the 0-indexed column of the queen we're considering placing
     * @return whether the square is attackable by a previously placed queen
     */
    private static boolean isNegSlopeDiagonalAttackable(final int n,
                                                        final boolean[][] board,
                                                        final int row,
                                                        final int col) {
        // yes, this and isPosSlopeDiagonalAttackable are both basically duplicate code
        // but they're both kept for simplicity and clarity
        for (int i = -n; i < n; i++) {
            final int nrow = row + i;
            final int ncol = col + i;
            if (isSquareTaken(n, board, nrow, ncol)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Determine if the given square can be attacked by a previously placed queen on a positive slope diagonal
     *
     * @param n     the length of one side of a standard chessboard
     * @param board the n-by-n chessboard represented as a 2D boolean array
     *              where true means a queen has been placed in that square and false otherwise
     * @param row   the 0-indexed row of the queen we're considering placing
     * @param col   the 0-indexed column of the queen we're considering placing
     * @return whether the square is attackable by a previously placed queen
     */
    private static boolean isPosSlopeDiagonalAttackable(final int n,
                                                        final boolean[][] board,
                                                        final int row,
                                                        final int col) {
        // yes, this and isNegSlopeDiagonalAttackable are both basically duplicate code
        // but they're both kept for simplicity and clarity
        for (int i = -n; i < n; i++) {
            final int nrow = row - i;
            final int ncol = col + i;
            if (isSquareTaken(n, board, nrow, ncol)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if the given square is occupied by a previously placed queen
     *
     * @param n     the length of one side of a standard chessboard
     * @param board the n-by-n chessboard represented as a 2D boolean array
     *              where true means a queen has been placed in that square and false otherwise
     * @param row   the 0-indexed row of the queen we're considering placing
     * @param col   the 0-indexed column of the queen we're considering placing
     * @return whether the square is already occupied by a queen, returns false if the given square are out of bounds
     */
    private static boolean isSquareTaken(final int n, final boolean[][] board, final int row, final int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            return false;
        }
        return board[row][col];
    }

    /**
     * Convert a given chessboard to an ASCII String where the queens are represented as the English letter 'Q' and
     * empty squares are denoted by the full stop character '.'
     * <p>
     * Note: each line as a trailing space after it and the last line as a trailing new line after it.
     *
     * @param n     the length of one side of a standard chessboard
     * @param board the n-by-n chessboard represented as a 2D boolean array
     *              where true means a queen has been placed in that square and false otherwise
     * @return The board as a String
     */
    private static String convertBoardToAsciiString(final int n, final boolean[][] board) {
        final String newLine = System.lineSeparator();
        // one char for each piece, one char for the space, and the newline
        final int charsPerRow = 2 * n + newLine.length();
        final int totalCharsInBoard = charsPerRow * n;
        // This is just to prevent slow internal array copying.
        final StringBuilder sb = new StringBuilder(totalCharsInBoard);
        for (final boolean[] row : board) {
            for (final boolean square : row) {
                if (square) {
                    sb.append('Q');
                } else {
                    sb.append('.');
                }
                sb.append(' ');
            }
            sb.append(newLine);
        }
        return sb.toString();
    }
}

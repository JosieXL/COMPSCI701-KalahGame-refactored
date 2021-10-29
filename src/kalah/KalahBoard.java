package kalah;

import com.qualitascorpus.testsupport.IO;

/**
 * This class is the KalahBoard class.  
 * It contains two methods, one is an override of 
 * toString() method, the other is printKalahBoard 
 * method, which is to io.println the kalah board we want.
 */

public class KalahBoard {   
    public void printKalahBoard(IO io, int[][] numberArray) {
        String[][] kalahBoard = createKalahBoard(numberArray);
        printBoard(io, kalahBoard);
    }

    public void printBoard(IO io, String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                io.print(board[i][j] + "");
            }
            io.println("");
        }
    }

    private static String toString(int num) {
        if (num >= 0 && num < 10) {
            return " " + num;
        }
        else {
            return Integer.toString(num);
        }
    }

    public String[] kalahBoardZeroOrLastLine() {
        String[] kalahBoardZeroOrLastLine = {"+----+-------+-------+-------+-------+-------+-------+----+"};
        return kalahBoardZeroOrLastLine;
    }

    public String[] kalahBoardOneLine(int[][] numberArray) {
        String[] kalahBoardOneLine = {"| P2 | 6[" + toString(numberArray[1][5]) + "] | 5[" + toString(numberArray[1][4]) + "] | 4[" + toString(numberArray[1][3]) + "] | 3[" + toString(numberArray[1][2]) + "] | 2[" + toString(numberArray[1][1]) + "] | 1[" + toString(numberArray[1][0]) + "] | " + toString(numberArray[0][6]) + " |"};
        return kalahBoardOneLine;
    }

    public String[] kalahBoardTwoLine() {
        String[] kalahBoardTwoLine = {"|    |-------+-------+-------+-------+-------+-------|    |"};
        return kalahBoardTwoLine;
    }

    public String[] kalahBoardThreeLine(int[][] numberArray) {
        String[] kalahBoardThreeLine = {"| " + toString(numberArray[1][6]) + " | 1[" + toString(numberArray[0][0]) + "] | 2[" + toString(numberArray[0][1]) + "] | 3[" + toString(numberArray[0][2]) + "] | 4[" + toString(numberArray[0][3]) + "] | 5[" + toString(numberArray[0][4]) + "] | 6[" + toString(numberArray[0][5]) + "] | P1 |"};
        return kalahBoardThreeLine;
    }

    public String[][] createKalahBoard(int[][] numberArray) {
        String[][] kalahBoard = new String[5][60];
        kalahBoard[0] = kalahBoardZeroOrLastLine();
        kalahBoard[1] = kalahBoardOneLine(numberArray);
        kalahBoard[2] = kalahBoardTwoLine();
        kalahBoard[3] = kalahBoardThreeLine(numberArray);
        kalahBoard[4] = kalahBoardZeroOrLastLine();
        return kalahBoard;
    }

}


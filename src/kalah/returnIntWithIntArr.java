package kalah;

/**
 * This class is the returnIntWithIntArr class.  
 * It is a type that can be used to return 
 * {(int)finalMoveIndex, (int[][])NumberArray}.
 */

public class returnIntWithIntArr {
    int finalMoveIndex;
    int[][] NumArray;
    int[] numArrayOneD;
    
    public returnIntWithIntArr(int finalMoveNum, int[][] NumberArray) {
        finalMoveIndex = finalMoveNum;
        NumArray = NumberArray;
    }

    public returnIntWithIntArr(int finalMoveNum, int[] numArray1D) {
        finalMoveIndex = finalMoveNum;
        numArrayOneD = numArray1D;
    }
}
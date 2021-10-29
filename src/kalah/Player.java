package kalah;

import com.qualitascorpus.testsupport.IO;

/**
 * This class is the Player class.  
 * Methods return the corresponding array, includes an integer 
 * lastMoveIndex and two int[] p1NumberArray and p2NumberArray.
 */

public class Player {
    public returnIntWithIntArr playerOneRound(IO io, int[][] numberArray, char inputChar) {
        Move p1Move = new Move();
        int inputInt = inputChar - '0';
        returnIntWithIntArr intElementsP1Array = p1Move.moveStepP1RoundReturnFinalMoveIndex(io, numberArray, inputInt);
        return intElementsP1Array;
    }

    public returnIntWithIntArr playerTwoRound(IO io, int[][] numberArray, char inputChar) {
        Move p2Move = new Move();
        int inputInt = inputChar - '0';
        returnIntWithIntArr intElementsP2Array = p2Move.moveStepP2RoundReturnFinalMoveIndex(io, numberArray, inputInt);
        return intElementsP2Array;
    }
    
}


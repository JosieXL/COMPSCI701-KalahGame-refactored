package kalah;

import com.qualitascorpus.testsupport.IO;

/**
 * This class is the Move class, contains moveStepP1RoundReturnFinalMoveIndex 
 * method and moveStepP2RoundReturnFinalMoveIndex method to do the moving 
 * in the back of the kalah board.
 * The methods return an integer lastIndexMove, two int[] p1NumberArray 
 * and p2NumberArray.
 */

public class Move {
    public returnIntWithIntArr moveStepP1RoundReturnFinalMoveIndex(IO io, int[][] numberArray, int inputInt) {
        int p2Store = numberArray[1][numberArray[1].length-1];
        int[] reactiveArray = createReactiveArr(numberArray, "P1");
        int collectSeedFromHouse = reactiveArray[inputInt-1];
        // Collect all the seed from the inputInt corresponding house => set the seed in the house to be 0
        reactiveArray[inputInt-1] = 0;         
        returnIntWithIntArr legalMoveArray = legalMove(collectSeedFromHouse, inputInt, reactiveArray);
        int lastMoveIndex = legalMoveArray.finalMoveIndex;
        reactiveArray = legalMoveArray.numArrayOneD;
        numberArray = convertOneDArrToTwoDArr(reactiveArray, numberArray, "P2");
        numberArray[1][numberArray[1].length-1]= p2Store; 
        numberArray = specialCaseKalahP1(io, lastMoveIndex, numberArray, collectSeedFromHouse);
        return new returnIntWithIntArr(lastMoveIndex, numberArray);
    }

    public returnIntWithIntArr moveStepP2RoundReturnFinalMoveIndex(IO io, int[][] numberArray, int inputInt) {
        int p1Store = numberArray[0][numberArray[0].length-1];
        int[] reactiveArray = createReactiveArr(numberArray, "P2");
        int collectSeedFromHouse = reactiveArray[inputInt-1];
        reactiveArray[inputInt-1] = 0;
        returnIntWithIntArr legalMoveArray = legalMove(collectSeedFromHouse, inputInt, reactiveArray);
        int lastMoveIndex = legalMoveArray.finalMoveIndex;
        reactiveArray = legalMoveArray.numArrayOneD;
        numberArray = convertOneDArrToTwoDArr(reactiveArray, numberArray, "P1");
        numberArray[0][numberArray[0].length-1]= p1Store;
        numberArray = specialCaseKalahP2(io, lastMoveIndex, numberArray, collectSeedFromHouse);
        return new returnIntWithIntArr(lastMoveIndex, numberArray);
    }

    public int[] createReactiveArr(int[][] numberArray, String playerNum) {
        int[] reactiveArray = new int[13];
        if (playerNum == "P1") {
            reactiveArray = new int[]{numberArray[0][0], numberArray[0][1], numberArray[0][2], numberArray[0][3], numberArray[0][4], numberArray[0][5], numberArray[0][6], numberArray[1][0], numberArray[1][1], numberArray[1][2], numberArray[1][3], numberArray[1][4], numberArray[1][5]};
            //initial1 [4,4,4,4,4,4,0,4,4,4,4,4,4] length=13
        }
        else if (playerNum == "P2") {
            reactiveArray = new int[]{numberArray[1][0], numberArray[1][1], numberArray[1][2], numberArray[1][3], numberArray[1][4], numberArray[1][5], numberArray[1][6], numberArray[0][0], numberArray[0][1], numberArray[0][2], numberArray[0][3], numberArray[0][4], numberArray[0][5]};
        }
        return reactiveArray;
    }

    // while the collected seed is not 0, plant 1 each house anti-clockwise, including the player's store (p1 houses + p1 store + p2 house; if out of index then start again at the P1's house 1) 
    public returnIntWithIntArr legalMove(int collectSeedFromHouse, int inputInt, int[] reactiveArray) {
        int lastMoveIndex = 0;
        if ((collectSeedFromHouse+inputInt)<=reactiveArray.length){ //if inputInt=6, collectedSeed<=7
            for (int i=0; i<collectSeedFromHouse; i++){
                reactiveArray[inputInt+i]++;
                lastMoveIndex = inputInt+i;
            }
        }
        else if ((reactiveArray.length<(collectSeedFromHouse+inputInt)) && ((collectSeedFromHouse+inputInt)<=(reactiveArray.length*2))){ // if inputInt=6, 8<=collectSeed<=20
            for (int i=0; i<reactiveArray.length-inputInt; i++){
                reactiveArray[inputInt+i]++;
            }
            for (int j=0; j<collectSeedFromHouse-(reactiveArray.length-inputInt); j++){
                reactiveArray[j]++;
                lastMoveIndex = j;
            }
        }
        else if ((reactiveArray.length*2<(collectSeedFromHouse+inputInt)) && ((collectSeedFromHouse+inputInt)<=(reactiveArray.length*3))){ // if inputInt=6, 21<=collectSeed<=33
            for (int i=0; i<reactiveArray.length-inputInt; i++){
                reactiveArray[inputInt+i]++;
            }
            for (int j=0; j<reactiveArray.length; j++){
                reactiveArray[j]++;
            }
            for (int k=0; k<collectSeedFromHouse-(reactiveArray.length*2-inputInt); k++){
                reactiveArray[k]++;
                lastMoveIndex = k;
            }
        }
        return new returnIntWithIntArr(lastMoveIndex, reactiveArray);
    }

    // Convert reactiveArray (int[]) back into numberArray (int[][])
    public int[][] convertOneDArrToTwoDArr(int[] reactiveArray, int[][] numberArray, String pStore) {
        if (pStore == "P1") {
            for (int position=0; position<reactiveArray.length; position++) {
                if (position < numberArray[0].length) {
                    numberArray[1][position] = reactiveArray[position];
                }
                else {
                    numberArray[0][position-numberArray[0].length] = reactiveArray[position];
                }
            }
        }
        else if (pStore == "P2") {
            for (int position=0; position<reactiveArray.length; position++) {
                if (position < numberArray[0].length) {
                    numberArray[0][position] = reactiveArray[position];
                }
                else {
                    numberArray[1][position-numberArray[0].length] = reactiveArray[position];
                }
            }
        }
        return numberArray;
    }

    // Special case - if a move ends in player's house that is empty, 
    // the player gets that seed and all seeds in the opposing house.
    private static int[][] specialCaseKalahP1(IO io, int lastMoveIndex, int[][] numberArray, int collectSeedFromHouse) {
        KalahBoard kalahBoard = new KalahBoard();
        if ((lastMoveIndex != 6) && (lastMoveIndex < numberArray[0].length) && (numberArray[0][lastMoveIndex] == 1)) {
            if (numberArray[1][numberArray[1].length-2-lastMoveIndex] > 0) {
                int p1Seed = numberArray[0][lastMoveIndex];
                int p2Seed = numberArray[1][numberArray[1].length-2-lastMoveIndex];
                numberArray[0][lastMoveIndex] = 0;
                numberArray[1][numberArray[1].length-2-lastMoveIndex] = 0;
                numberArray[0][numberArray[0].length-1] = numberArray[0][numberArray[0].length-1] + p1Seed + p2Seed;
                kalahBoard.printKalahBoard(io, numberArray);
            }
            else {
                kalahBoard.printKalahBoard(io, numberArray);
            } 
        }
        // In case of empty seed, straight print "House empty. Move again." and then print the existing kalah board.
        else if (collectSeedFromHouse != 0) {
            kalahBoard.printKalahBoard(io, numberArray);
        }
        return numberArray;
    }

    private static int[][] specialCaseKalahP2(IO io, int lastMoveIndex, int[][] numberArray, int collectSeedFromHouse) {
        KalahBoard kalahBoard = new KalahBoard();
        if ((lastMoveIndex != 6) && (lastMoveIndex < numberArray[1].length) && (numberArray[1][lastMoveIndex] == 1)) {
            if (numberArray[0][numberArray[0].length-2-lastMoveIndex] > 0) {
                int p2Seed = numberArray[1][lastMoveIndex];
                int p1Seed = numberArray[0][numberArray[0].length-2-lastMoveIndex];
                numberArray[1][lastMoveIndex] = 0;
                numberArray[0][numberArray[0].length-2-lastMoveIndex] = 0;
                numberArray[1][numberArray[1].length-1] = numberArray[1][numberArray[0].length-1] + p1Seed + p2Seed;                
                kalahBoard.printKalahBoard(io, numberArray);
            }
            else {
                kalahBoard.printKalahBoard(io, numberArray);
            } 
        }
        else if (collectSeedFromHouse != 0) {
            kalahBoard.printKalahBoard(io, numberArray);
        }
        return numberArray;
    }
}


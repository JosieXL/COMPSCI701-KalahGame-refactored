package kalah;



import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure. 
 * This class calls 6 objects from 4 classes.
 * This class is the one that runs for the codes.
 */

public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public int[][] createNumberArray() {
        // This is the initial state of Kalah board but in array
        int p1HouseOneSeedNumber = 4, p1HouseTwoSeedNumber = 4, p1HouseThreeSeedNumber = 4, p1HouseFourSeedNumber = 4, p1HouseFiveSeedNumber = 4, p1HouseSixSeedNumber = 4;
        int p2HouseOneSeedNumber = 4, p2HouseTwoSeedNumber = 4, p2HouseThreeSeedNumber = 4, p2HouseFourSeedNumber = 4, p2HouseFiveSeedNumber = 4, p2HouseSixSeedNumber = 4;
        int p1StoreNumber = 0, p2StoreNumber = 0;
        int[] p1NumberArray = {p1HouseOneSeedNumber, p1HouseTwoSeedNumber, p1HouseThreeSeedNumber, p1HouseFourSeedNumber, p1HouseFiveSeedNumber, p1HouseSixSeedNumber, p1StoreNumber};
        int[] p2NumberArray = {p2HouseOneSeedNumber, p2HouseTwoSeedNumber, p2HouseThreeSeedNumber, p2HouseFourSeedNumber, p2HouseFiveSeedNumber, p2HouseSixSeedNumber, p2StoreNumber};
		int[][] numberArray = {p1NumberArray, p2NumberArray};
		return numberArray;
	}

	public boolean collectSeedZeroCaseBooleanOne(IO io, int collectSeedFromHouse, char inputChar, int[][] numberArray) {
		boolean finished = false;
		KalahCases kalahCases = new KalahCases();
		if (collectSeedFromHouse == 0) {
			finished = kalahCases.seedIsZeroPlayerOne(io, inputChar, numberArray, collectSeedFromHouse);
		}
		return finished;
	}

	public boolean collectSeedZeroCaseBooleanTwo(IO io, int collectSeedFromHouse, char inputChar, int[][] numberArray) {
		boolean finished = false;
		KalahCases kalahCases = new KalahCases();
		if (collectSeedFromHouse == 0) {
			finished = kalahCases.seedIsZeroPlayerTwo(io, inputChar, numberArray, collectSeedFromHouse);
		}
		return finished;
	}

	public int getCollectSeedFromInputChar (char inputChar, int[] pNumberArray) {
		// Convert char to int
		int inputInt = inputChar - '0';
        int collectSeedFromHouse = pNumberArray[inputInt-1];
		return collectSeedFromHouse;
	}
	
	public void play(IO io) {
		// Replace what's below with your implementation
		//io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		//io.println("| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |");
		//io.println("|    |-------+-------+-------+-------+-------+-------|    |");
		//io.println("|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |");
		//io.println("+----+-------+-------+-------+-------+-------+-------+----+");

		KalahBoard kalahBoard = new KalahBoard();
		
		kalahPromptGetInputCondition p1Prompt = new kalahPromptGetInputCondition();
		kalahPromptGetInputCondition p2Prompt = new kalahPromptGetInputCondition();
		Player playerOne = new Player();
		Player playerTwo = new Player();
		KalahCases kalahCases = new KalahCases();
		
		
		int[][] numberArray = createNumberArray();
		/*
		for (int i=0; i<numberArray.length; i++) {
				System.out.println(Arrays.toString(numberArray[i]));
		}
		*/
		//[4, 4, 4, 4, 4, 4, 0]
        //[4, 4, 4, 4, 4, 4, 0]
		
		// Print kalah board (initial one)
		kalahBoard.printKalahBoard(io, numberArray);

        // round%2==0 => P1 round; round%2==1 => P2 round
        // special case for one more round, round not add 1
		boolean finished = false; // Use with break; to exist for loop when within a while loop
		for (int round=0; round < 100000 && !finished; round++) {
			if (round % 2 == 0) { // P1 round
				int[] storeTotalNumberArr = kalahCases.gameFinishRoundOne(io, numberArray);
				if ((storeTotalNumberArr[0] != 0) && (storeTotalNumberArr[1] != 0) ){
					break;
				}
				char inputChar = p1Prompt.p1Prompt(io);
				if (inputChar == 'q') {
					kalahCases.printGameOverAndBoard(io, kalahBoard, numberArray);
					break;
				}
				if (inputChar == '1' || inputChar == '2' || inputChar == '3' || inputChar == '4' || inputChar == '5' || inputChar == '6') {
					// do the move
        			int collectSeedFromHouse = getCollectSeedFromInputChar(inputChar, numberArray[0]);
					finished = collectSeedZeroCaseBooleanOne(io, collectSeedFromHouse, inputChar, numberArray);
					if (collectSeedFromHouse != 0) {
						returnIntWithIntArr intElementsP1Array = playerOne.playerOneRound(io, numberArray, inputChar);
						numberArray = intElementsP1Array.NumArray;
						int finalMoveIndex = intElementsP1Array.finalMoveIndex;
						if (finalMoveIndex == 6) {
							while (finalMoveIndex == 6) {
								storeTotalNumberArr = kalahCases.gameFinishRoundOne(io, numberArray);
								if ((storeTotalNumberArr[0] != 0) && (storeTotalNumberArr[1] != 0) ){
									finished = true; // Use to exist the outer for loop
									break; // Use to exist the inner while loop
								}
								inputChar = p1Prompt.p1Prompt(io);
								if (inputChar == 'q') {
									kalahCases.printGameOverAndBoard(io, kalahBoard, numberArray);
									finished = true;
									break;
								}
								collectSeedFromHouse = getCollectSeedFromInputChar(inputChar, numberArray[0]);
								intElementsP1Array = playerOne.playerOneRound(io, numberArray, inputChar);
								numberArray = intElementsP1Array.NumArray;
								finalMoveIndex = intElementsP1Array.finalMoveIndex;
								finished = collectSeedZeroCaseBooleanOne(io, collectSeedFromHouse, inputChar, numberArray);
							}
						}
					}
				}
			}
			else if (round % 2 == 1) { //P2 round
				int[] storeTotalNumberArr = kalahCases.gameFinishRoundTwo(io, numberArray);
				if ((storeTotalNumberArr[0] != 0) && (storeTotalNumberArr[1] != 0) ){
					break;
				}
				char inputChar = p2Prompt.p2Prompt(io);
				if (inputChar == 'q') {
					kalahCases.printGameOverAndBoard(io, kalahBoard, numberArray);
					break;
				}
				if (inputChar == '1' || inputChar == '2' || inputChar == '3' || inputChar == '4' || inputChar == '5' || inputChar == '6') {
					// do the move
					int collectSeedFromHouse = getCollectSeedFromInputChar(inputChar, numberArray[1]);
					finished = collectSeedZeroCaseBooleanTwo(io, collectSeedFromHouse, inputChar, numberArray);
					if (collectSeedFromHouse != 0) {
						returnIntWithIntArr intElementsP2Array = playerTwo.playerTwoRound(io, numberArray, inputChar);
						numberArray = intElementsP2Array.NumArray;
						int finalMoveIndex = intElementsP2Array.finalMoveIndex;
						if (finalMoveIndex == 6) {
							while (finalMoveIndex == 6) {
								storeTotalNumberArr = kalahCases.gameFinishRoundTwo(io, numberArray);
								if ((storeTotalNumberArr[0] != 0) && (storeTotalNumberArr[1] != 0) ){
									finished = true; 
									break; 
								}
								inputChar = p2Prompt.p2Prompt(io);
								if (inputChar == 'q') {
									kalahCases.printGameOverAndBoard(io, kalahBoard, numberArray);
									finished = true;
									break;
								}
								collectSeedFromHouse = getCollectSeedFromInputChar(inputChar, numberArray[1]);
								intElementsP2Array = playerTwo.playerTwoRound(io, numberArray, inputChar);
								numberArray = intElementsP2Array.NumArray;
								finalMoveIndex = intElementsP2Array.finalMoveIndex;
								finished = collectSeedZeroCaseBooleanTwo(io, collectSeedFromHouse, inputChar, numberArray);
							}
						}
					}
				}
			}
        }
	}
	
}

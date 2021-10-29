package kalah;

import com.qualitascorpus.testsupport.IO;

/**
 * This class is the KalahCases class, contains 2 gameFinish functions 
 * and 2 seedIsZero functions for player1 round and player2. 
 */
// p1NumberArray = numberArray[0], p2NumberArray = numberArray[1]
public class KalahCases {
    public int[] gameFinishRoundOne(IO io, int[][] numberArray) {
		KalahBoard kalahBoard = new KalahBoard(); 
		int[] storeTotalNumberArr = new int[2];
		// storeTotalNumberArr = {p1StoreTotalNumber, p1StoreTotalNumber} = {0, 0} for initialize
		if (numberArray[0][0]==0 && numberArray[0][1]==0 && numberArray[0][2]==0 && numberArray[0][3]==0 && numberArray[0][4]==0 && numberArray[0][5]==0) {
			printGameOverAndBoard(io, kalahBoard, numberArray);
			storeTotalNumberArr[0] = storeNumberOne(numberArray[0]);
			storeTotalNumberArr[1] = storeNumberTwo(numberArray[1]);
			printStoreNumber(io, storeTotalNumberArr);
		}
		printWinPlayer(io, storeTotalNumberArr);
		return storeTotalNumberArr;
	}

	public int[] gameFinishRoundTwo(IO io, int[][] numberArray) {
		KalahBoard kalahBoard = new KalahBoard(); 
		int[] storeTotalNumberArr = new int[2];
		// storeTotalNumberArr = {p1StoreTotalNumber, p1StoreTotalNumber} = {0, 0} for initialize
		if (numberArray[1][0]==0 && numberArray[1][1]==0 && numberArray[1][2]==0 && numberArray[1][3]==0 && numberArray[1][4]==0 && numberArray[1][5]==0) {
			printGameOverAndBoard(io, kalahBoard, numberArray);
			storeTotalNumberArr[1] = storeNumberOne(numberArray[1]);
			storeTotalNumberArr[0] = storeNumberTwo(numberArray[0]);	
			printStoreNumber(io, storeTotalNumberArr);
		}
		printWinPlayer(io, storeTotalNumberArr);
		return storeTotalNumberArr;
	}

	public boolean seedIsZeroPlayerOne(IO io, char inputChar, int[][] numberArray, int collectSeedFromHouse) {
		Player playerOne = new Player();
		KalahBoard kalahBoard = new KalahBoard();
		kalahPromptGetInputCondition prompt = new kalahPromptGetInputCondition();
		boolean finished = false;
		while (collectSeedFromHouse == 0) {
			printHouseEmptyAndBoard(io, kalahBoard, numberArray);
			// one more round
			inputChar = prompt.p1Prompt(io);
			if (inputChar == 'q') {
				printGameOverAndBoard(io, kalahBoard, numberArray);
				finished = true;					break;
			}
			collectSeedFromHouse = getCollectSeedFromHouse(inputChar, numberArray, "P1");
			returnIntWithIntArr intElementsP1Array = playerOne.playerOneRound(io, numberArray, inputChar);
			assignNumberArray(numberArray, intElementsP1Array);
		}
        return finished;
	}

	public boolean seedIsZeroPlayerTwo(IO io, char inputChar, int[][] numberArray, int collectSeedFromHouse) {
		Player playerTwo = new Player();
		KalahBoard kalahBoard = new KalahBoard();
		kalahPromptGetInputCondition prompt = new kalahPromptGetInputCondition();
		boolean finished = false;
		while (collectSeedFromHouse == 0) {
			printHouseEmptyAndBoard(io, kalahBoard, numberArray);
			// one more round
			inputChar = prompt.p2Prompt(io);
			collectSeedFromHouse = getCollectSeedFromHouse(inputChar, numberArray, "P2");
			if (inputChar == 'q') {
				printGameOverAndBoard(io, kalahBoard, numberArray);
				finished = true;
				break;
			}
			returnIntWithIntArr intElementsP2Array = playerTwo.playerTwoRound(io, numberArray, inputChar);
			assignNumberArray(numberArray, intElementsP2Array);
		}
        return finished;
	}
	
	public static int storeNumberOne(int[] arrayFirst) {
		int storeTotalNumber = 0;
		storeTotalNumber = arrayFirst[6];
		return storeTotalNumber;
	}

	public static int storeNumberTwo(int[] arraySecond) {
		int storeTotalNumber = 0;
		storeTotalNumber = arraySecond[6] + arraySecond[0] + arraySecond[1] + arraySecond[2] + arraySecond[3] + arraySecond[4] + arraySecond[5];
		return storeTotalNumber;
	}

	private void printStoreNumber(IO io, int[] storeTotalNumberArr) {
		io.println("	player 1:" + storeTotalNumberArr[0]);
		io.println("	player 2:" + storeTotalNumberArr[1]);
	}

	public void printGameOverAndBoard(IO io, KalahBoard kalahBoard, int[][] numberArray) {
		io.println("Game over");
		kalahBoard.printKalahBoard(io, numberArray);
	}

	public void printHouseEmptyAndBoard(IO io, KalahBoard kalahBoard, int[][] numberArray) {
		io.println("House is empty. Move again.");
		kalahBoard.printKalahBoard(io, numberArray);
	}

	private void printWinPlayer(IO io, int[] storeTotalNumberArr) {
		if (storeTotalNumberArr[1] > storeTotalNumberArr[0]) {
			io.println("Player 2 wins!");
		}
		else if (storeTotalNumberArr[0] > storeTotalNumberArr[1]) {
			io.println("Player 1 wins!");
		}
		else if ((storeTotalNumberArr[0] == storeTotalNumberArr[1]) && (storeTotalNumberArr[0] != 0)) {
			io.println("A tie!");
		}
	}

	private static void assignNumberArray(int[][] numberArray, returnIntWithIntArr intElementsP1Array){
		numberArray[0] = intElementsP1Array.NumArray[0];
		numberArray[1] = intElementsP1Array.NumArray[1];
	}

	public int getCollectSeedFromHouse(char inputChar, int[][] numberArray, String round) {
		int inputInt = inputChar - '0';
		int collectSeedFromHouse = 0;
		if (round == "P1") {
			collectSeedFromHouse = numberArray[0][inputInt-1];
		}
		else if (round == "P2") {
			collectSeedFromHouse = numberArray[1][inputInt-1];
		}
		return collectSeedFromHouse;
	}

}


package pt314.blocks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import pt314.blocks.game.GameBoard;
import pt314.blocks.game.HorizontalBlock;
import pt314.blocks.game.TargetBlock;
import pt314.blocks.game.VerticalBlock;
import pt314.blocks.gui.SimpleGUI;

public class NewGame {
	
	private static GameBoard newBoard;
	
	private static int NUMBER_OF_PUZZLES = 4;
		
	private static String PUZZLE_PATH_ONE = "C:\\Users\\Yaswanth\\Documents\\GitHub\\blocks\\app\\res\\puzzles\\puzzle-001.txt";
	private static String PUZZLE_PATH_TWO = "C:\\Users\\Yaswanth\\Documents\\GitHub\\blocks\\app\\res\\puzzles\\puzzle-002.txt";
	private static String PUZZLE_PATH_THREE = "C:\\Users\\Yaswanth\\Documents\\GitHub\\blocks\\app\\res\\puzzles\\puzzle-003.txt";
	private static String PUZZLE_PATH_FOUR = "C:\\Users\\Yaswanth\\Documents\\GitHub\\blocks\\app\\res\\puzzles\\puzzle-004.txt";
	
	private static String reloadGameFilePath;
	
	public static int randomPuzzleNumberGenerator(){
		Random randomPuzzle = new Random();	
		int randomPuzzleNumber = randomPuzzle.nextInt(NUMBER_OF_PUZZLES) + 1;		
		return randomPuzzleNumber;
	}

	public GameBoard startNewGame(String loadGame, int randomPuzzleNumber) {
		
		try {				
			
			String filePath = "";
			
			if(loadGame.equals("newGame")){				
				switch (randomPuzzleNumber) {
					case 1:
						filePath = PUZZLE_PATH_ONE;
						break;
					case 2:
						filePath = PUZZLE_PATH_TWO;
						break;
					case 3:
						filePath = PUZZLE_PATH_THREE;
						break;
					case 4:
						filePath = PUZZLE_PATH_FOUR;
						break;
				}
				System.out.println(filePath);
				
			} else if (loadGame.equals("reloadGame")){
				filePath = reloadGameFilePath;
			}
			
			BufferedReader newPuzzleReader = new BufferedReader(new FileReader(filePath));
			reloadGameFilePath = filePath;
			
			String matrixSize = newPuzzleReader.readLine();
			int rows = Integer.parseInt((matrixSize.split(" "))[0]);
			int cols = Integer.parseInt((matrixSize.split(" "))[1]);
			
			if(rows >= 1 && cols >= 1){
				newBoard = createGameBoard(rows,cols,newPuzzleReader);//creates a new board with given rows, columns and blocks
			} else {
				System.out.println("Invalid Board Size. Rows and columns must be greater than 1.");
				System.exit(0);
			}
			
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
		return newBoard;
	}

	private GameBoard createGameBoard(int rows, int cols, BufferedReader puzzleReader) throws IOException {
		
		newBoard = new GameBoard(rows,cols);
		
		int targetBlockCount = 0;
		
		BufferedReader puzzle = puzzleReader;
		
		for(int currentRow = 0; currentRow < rows; currentRow++){
			
			String puzzleInputLine = puzzle.readLine();
			Boolean targetBlockPresent = false;
			
			for(int currentCol = 0; currentCol < cols; currentCol++){
				
				char inputBlock = puzzleInputLine.charAt(currentCol);
				
				if(inputBlock == 'H'){	
					
					if(!targetBlockPresent){//for each row we are checking if target block is already present or not
						newBoard.placeBlockAt(new HorizontalBlock(), currentRow, currentCol);						
					}else{
						System.out.println("Invalid Input. Target Block Must be the right most horizantal block in its row.");
						System.exit(0);//if present then the given puzzle is invalid
					}
					
				} else if(inputBlock == 'V') {
					
					newBoard.placeBlockAt(new VerticalBlock(), currentRow, currentCol);	
					
				} else if(inputBlock == 'T'){
					
					if(targetBlockCount == 0){
						newBoard.placeBlockAt(new TargetBlock(), currentRow, currentCol);
						targetBlockCount++;
						targetBlockPresent = true;
					} else{
						System.out.println("Invalid Input. A puzzle must have only ONE Target Block.");
						System.exit(0);
					}
				}
			}						
		}
		
		return newBoard;		
	}
	
	public static void main(String[] args){		
		NewGame game = new NewGame();
		int puzzleNumber = randomPuzzleNumberGenerator();
		newBoard = game.startNewGame("newGame",puzzleNumber);	
		new SimpleGUI(newBoard);
	}

}

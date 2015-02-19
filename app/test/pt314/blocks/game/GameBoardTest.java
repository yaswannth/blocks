package pt314.blocks.game;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Before;
import org.junit.Test;

import pt314.blocks.NewGame;

public class GameBoardTest {
	
	private GameBoard testBoard;
	private NewGame testGame;

	@Before
	public void initializeSampleBoard() throws FileNotFoundException {
		BufferedReader inputReader = new BufferedReader(new FileReader("C:\\Users\\Yaswanth\\Documents\\GitHub\\blocks\\app\\res\\puzzles\\puzzle-001.txt"));
		testGame = new NewGame();
		testBoard = testGame.startNewGame("newGame", 1);				
	}

	@Test
	public void testHorizantalBlockConstraintDown(){
		//we know the block at (0,0) is a horizontal block
		assertFalse(testBoard.moveBlock(0, 0, Direction.DOWN, 1));
	}
	
	@Test
	public void testHorizantalBlockConstraintUp(){
		//we know the block at (0,0) is a horizontal block
		assertFalse(testBoard.moveBlock(0, 0, Direction.UP, 1));
	}
	
	@Test
	public void testVerticalBlockConstraintLeft(){
		//we know the block at (3,1) is a vertical block
		assertFalse(testBoard.moveBlock(3, 1, Direction.LEFT, 1));		
	}
	
	@Test
	public void testVerticalBlockConstraintRight(){
		//we know the block at (3,1) is a vertical block
		assertFalse(testBoard.moveBlock(3, 1, Direction.RIGHT, 1));		
	}
	
	@Test
	public void testTargetBlockConstraint(){
		//target block can move only along horizantal direction
		//we know the block at (2,2) is the target block
		assertFalse(testBoard.moveBlock(2, 2, Direction.UP, 1));		
	}
	
	@Test
	public void testOverlappingOfBlocks(){
		testBoard.moveBlock(0, 0, Direction.RIGHT, 3);//moves horizontal block present in position (0,0) to (0,3)
		//in the following test we assert that we cannot move vertical block in (1,3) to (0,3)
		assertFalse(testBoard.moveBlock(0, 3, Direction.UP, 1));
	}
	
	@Test
	public void testHorizantalJumpingOverVertical(){
		testBoard.moveBlock(1, 3, Direction.UP, 1);//moves vertical block present in position (1,3) to (0,3)
		//in the following test we assert that we cannot move 
		//Horizontal block from (0,0) to (0,4)
		//when it has a vertical block in position (0,3) blocking its path
		assertFalse(testBoard.moveBlock(0, 0, Direction.RIGHT, 4));		
	}
	
	@Test
	public void testVerticalJumpingOverHorizantal(){
		testBoard.moveBlock(2,2, Direction.LEFT, 1);//moves horizontal (Target) block present in position (2,2) to (2,1)
		//in the following test we assert that we cannot move 
		//Vertical block from (3,1) to (1,1)
		//when it has a horizontal block in position (2,1) blocking its path
		assertFalse(testBoard.moveBlock(3, 1, Direction.UP, 2));		
	}
	
	@Test
	public void testVerticalBlockBoundaryConstraintUp(){
		assertFalse(testBoard.moveBlock(3, 1, Direction.UP, 4));	
	}
	
	@Test
	public void testVerticalBlockBoundaryConstraintDown(){
		assertFalse(testBoard.moveBlock(3, 1, Direction.DOWN, 2));	
	}
	
	@Test
	public void testHorizantalBlockBoundaryConstraintRight(){
		assertFalse(testBoard.moveBlock(0, 0, Direction.RIGHT, 5));	
	}
	
	@Test
	public void testHorizantalBlockBoundaryConstraintLeft(){
		assertFalse(testBoard.moveBlock(4, 4, Direction.LEFT, 5));	
	}
	
}

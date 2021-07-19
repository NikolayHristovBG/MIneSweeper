package com.minesweeper;

import java.util.concurrent.ThreadLocalRandom;

public class GameBoard {
	private static int rows = 0;      
	private static int columns = 0;
	private static int mineCount = 0;
	private static int hiddenCellCount = 0;
	private static boolean exploded = false;
	private static int turns = 0;
	private static int difficulty = -1;
	private int row = -1;
	private int column = -1;
	
	
	Cell cells[][] = new Cell[rows][columns];
	static Cell mines[];
	
	// Initialize empty GameBoard
	public GameBoard() {
		for (int i = 0; i < rows; i++) {
	         for (int j = 0; j < columns; j++) {
	            cells[i][j] = new Cell(i, j);
	            if ((i == 0) && (j == 0)) {
	            	cells[0][0].setValue("    ");
	            } else {
		            if ((i == 0) || (j == 0)){
		            	if (i == 0) {
		            		if (j < 10) {
		            			cells[0][j].setValue(String.valueOf(j - 1) + "   ");
		            		}else {
		            			cells[0][j].setValue(String.valueOf(j - 1) + "  ");
		            		}
		            	}	
						if (j == 0) {
							if (i <= 10) {
								cells[i][0].setValue(String.valueOf(i - 1) + "   ");
							} else {
								cells[i][0].setValue(String.valueOf(i - 1) + "  ");
							}	
						}
					}
	            }    
	         }
	      }
	}
	
	// Calculate number of all cells, mines and hidden safe cells  
	public static void calculateCells(int difficulty) {
		
		switch (difficulty) {
				
				case 0:
					rows = 10;
					columns = 10;
					mineCount = 10;
					break;
				case 1:
					rows = 17;
					columns = 17;
					mineCount = 40;	
					break;
				case 2:
					rows = 25;
					columns = 25;
					mineCount = 99;
					break;	
			}
			hiddenCellCount = ((rows - 1) * (columns -1));
			mines = new Cell[mineCount];
	}
	
	// Show GameBoard
	public void showGameBoard() {
		for (int i = 0; i < rows; i++) {
	         for (int j = 0; j < columns; j++) {
	        	 System.out.print(cells[i][j].getValue());
	         }
	         System.out.println();
	    }
	}
	
	// Check if selected cell is a mine 
	public boolean selectCell(int row, int column) {

		if (cells[row][column].getIsAMine()) {
			exploded = true;
			return false;
		} else {
			cells[row][column].setHidden(false);
			hiddenCellCount--;
			return true;
		}
	}
	
	// Calculate recursively number of adjacent mines
	public void setNumberOfMines(int row, int column) {
		int numberOfMines = 0;
		
		// Check N
		if (row > 1) {
			if (cells[row-1][column].getIsAMine()) {
				numberOfMines += 1;
			}
		}
		
		// Check NE
		if (row > 1 && column < columns - 1) {
			if (cells[row-1][column+1].getIsAMine()) {
				numberOfMines += 1;
			}
		}
		
		// Check E
		if (column < columns - 1) {
			if (cells[row][column+1].getIsAMine()) {
				numberOfMines += 1;
			}
		}
		
		// Check SE
		if (row < rows - 1 && column < columns - 1) {
			if (cells[row+1][column+1].getIsAMine()) {
				numberOfMines += 1;
			}
		}
				
		// Check S
		if (row < rows - 1) {
			if (cells[row+1][column].getIsAMine()) {
				numberOfMines += 1;
			}
		}
				
		// Check SW
		if (row < rows - 1 && column > 1) {
			if (cells[row+1][column-1].getIsAMine()) {
				numberOfMines += 1;
			}
		}
				
		// Check W
		if (column > 1) {
			if (cells[row][column-1].getIsAMine()) {
				numberOfMines += 1;
			}
		}
				
		// Check NW
		if (row > 1 && column > 1) {
			if (cells[row-1][column-1].getIsAMine()) {
				numberOfMines += 1;
			}
		}
		
		cells[row][column].setValue(Integer.toString(numberOfMines) + "   ");
		
		if (numberOfMines == 0) {
			// Check N
			if (row > 1 && (cells[row - 1][column].isHidden() == true)){
				selectCell(row - 1, column);
				setNumberOfMines(row - 1, column);
			}
			
			//Check NE 
			if (row > 1 && column < columns - 1 && (cells[row - 1][column + 1].isHidden() == true)) { 
				selectCell(row - 1, column + 1);
				setNumberOfMines(row - 1, column + 1);
			}
			
			//Check E
			if (column < columns - 1 && (cells[row][column + 1].isHidden() == true)) {
				selectCell(row, column + 1);
				setNumberOfMines(row, column + 1);
			}
			
			//SE
			if (row < rows - 1 && column < columns - 1 && (cells[row + 1][column + 1].isHidden() == true)) {
				selectCell(row + 1, column + 1);
				setNumberOfMines(row + 1, column + 1);
			}
			
			//S
			if (row < rows - 1 && (cells[row + 1][column].isHidden() == true)) {
				selectCell(row + 1, column);
				setNumberOfMines(row + 1, column);
			}
			
			//SW
			if (row < rows - 1 && column > 1 && (cells[row + 1][column - 1].isHidden() == true)) {
				selectCell(row + 1, column - 1);
				setNumberOfMines(row + 1, column - 1);
			}
			
			//W
			if (column > 1 && (cells[row][column - 1].isHidden() == true)) {
				selectCell(row, column - 1);
				setNumberOfMines(row, column - 1);
			}
			
			//NW
			if (row > 1 && column > 1 && (cells[row - 1][column - 1].isHidden() == true)) {
				selectCell(row - 1, column - 1);
				setNumberOfMines(row - 1, column - 1);
			}
		}	
	}
		
	public void placeMines(int row, int column) {
		int counter = 0;
		int mineRow = - 1;
		int mineColumn = -1;
		while (counter < mineCount) {
			mineRow = ThreadLocalRandom.current().nextInt(2, rows);
			mineColumn = ThreadLocalRandom.current().nextInt(2, columns);
			if ((cells[mineRow][mineColumn].isHidden() == true) && (cells[mineRow][mineColumn].getIsAMine() == false) && (mineRow != row) && (mineColumn != column)) {
				cells[mineRow][mineColumn].setIsAMine(true);
				mines[counter] = cells[mineRow][mineColumn];
				counter += 1;
			}
		}
	}
	
	public static void revealMines() {
		for (int i=0; i < mineCount; i++) {
			mines[i].setValue("*   ");
		}
	}
	
	public static boolean hasExploded(){
		if (exploded == true) {
			System.out.println("You lost!");
			revealMines();
		}	
		return exploded;
	}
	
	public static boolean hasWon() {
		if (hiddenCellCount == mineCount) {
			System.out.println("You won in " + turns + " turns!");
			revealMines();
			return true;
		}
		System.out.println();
		System.out.println("Only " + (hiddenCellCount - mineCount) + " safe cells to go!");
		return false;
	}
	
	public static void showMenu() {
		System.out.println("Enter the Difficulty Level");
		System.out.println("Press 0 for BEGINNER (9 * 9 Cells and 10 Mines)");
		System.out.println("Press 1 for INTERMEDIATE (16 * 16 Cells and 40 Mines)");
		System.out.println("Press 2 for ADVANCED (24 * 24 Cells and 99 Mines)");
	}
		
	public static boolean isValidDifficulty(String tempStringDifficulty) {
		String regexDifficulty = "0|1|2";
		if (tempStringDifficulty.matches(regexDifficulty)) {
			difficulty = Integer.parseInt(tempStringDifficulty);
        	return true;
        } else {
        	if (turns > 0) {
        		System.out.println("\n\nInvalid input. Please enter a number in the 0-2 range.");
        	}
        	return false;
        }
	}
		
	public boolean isValidCoordinates(String tempStringCoordinates) {
		String regexCoordinates = "\\d+\\s\\d+";
		if (tempStringCoordinates.matches(regexCoordinates)) {
        	return true;
        } else {
        	if (tempStringCoordinates != "") {
        		System.out.println("\n\nInvalid cell coordinates. Please enter valid row and column values separated by a single space.");
        	}	
        	return false;
        }
	}
	
	public boolean isValidCell(String tempStringCoordinates ) {
		String[] strs = tempStringCoordinates.trim().split("\\s");
	    row = Integer.parseInt(strs[0]) + 1;
	    column = Integer.parseInt(strs[1]) + 1;
	    if ((row < 0) || (row > rows - 1) || (column < 0) || (column > columns - 1)) {
			System.out.println("Entered number is out of bounds.");
			return false;
		} else {
			if (cells[row][column].isHidden() == false) {
				System.out.println("Cell already opened.");
				return false;
			} else {
				turns++;
				return true;
			}
		}
		
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public int getTurns() {
		return turns;
	}
	
	public static int getDifficulty() {
		return difficulty;
	}
}

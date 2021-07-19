package com.minesweeper;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		String tempStringDifficulty = "";
		String tempStringCoordinates = "";
		
		Scanner sc = new Scanner(System.in);
				
		while (!GameBoard.isValidDifficulty(tempStringDifficulty)) {
			GameBoard.showMenu();
			tempStringDifficulty = sc.nextLine();
		}
				
		GameBoard.calculateCells(GameBoard.getDifficulty());
		GameBoard gb = new GameBoard();		

		while (!GameBoard.hasExploded() && !GameBoard.hasWon()) {
			while (!gb.isValidCoordinates(tempStringCoordinates)) {
				gb.showGameBoard();
				System.out.println("Enter your move (row, column) \n->");
				tempStringCoordinates = sc.nextLine();
			}   

			if (gb.isValidCell(tempStringCoordinates)) {
				if (gb.selectCell(gb.getRow(), gb.getColumn())) {
					if (gb.getTurns() == 1) {
						gb.placeMines(gb.getRow(), gb.getColumn());
					}
					gb.setNumberOfMines(gb.getRow(), gb.getColumn());
				};
				System.out.println();
			}
			tempStringCoordinates = "";
		}    
		
		gb.showGameBoard();
		sc.close();
	}
	
}

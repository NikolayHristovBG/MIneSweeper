package com.minesweeper;

public class Cell {
	int row;
	int column;
	String value = "-   ";
	boolean isHidden = true;
	boolean isAMine = false;
	
	public Cell(int row, int column) {	
		this.row = row;
		this.column = column;
	}
	
	public void init() {
		
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
		
	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public boolean getIsAMine() {
		return isAMine;
	}

	public void setIsAMine(boolean isAMine) {
		this.isAMine = isAMine;
	}

	@Override
	public String toString() {
		return this.value;
	}

}

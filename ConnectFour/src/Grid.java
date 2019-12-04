import java.io.Serializable;


//Creating object for Grid, which will operate actions on it. Grid needs to be able to:
//create grid, display grid, update grid

public class Grid implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8624313722515405144L;
	private String grid[][];
	private int rows;
	private int column;

	//Main Grid class method with following arguments: rows and columns
	public Grid(int rows, int columns) {
		this.rows = rows;
		this.column = columns;

		//Grid will have numbers of rows and columns as in input
		this.grid = new String[rows][columns];
	}//end of Grid

	//Getter for grid
	public String[][] getGrid() {
		return this.grid;
	}//end of getGrid

	//Method which will create grid
	public void startGrid() {

		for (int rows = 0; rows < this.grid.length; rows++) {
			for (int columns = 0; columns < this.grid[rows].length; columns++) {
				//Filling each place in grid with plain black square with ANSI code 0x25A0
				this.grid[rows][columns] = String.valueOf(Character.toChars(0x25A0));
			}//end of nester for loop
		}//end of for loop
	}//end of startGrid

	//Method which will allow each player to place their disc in selected place on bottom line
	public void updateGrid (int column, String player) {

		for (int row = this.grid.length - 1; row >= 0; row--) {
			if (this.grid[row][column - 1].equals(String.valueOf(Character.toChars(0x25A0)))) {
				this.grid[row][column - 1] = player;
				break;
			}//end of if statement
		}//end of for loop
	}//end of updateGrid

	//Method with will return whole grid as String with help lines to including number of columns
	public String displayGrid () {

		//Help lines with numbers
		String output = "";
		for (int displayHelp = 0; displayHelp <= this.grid.length; displayHelp++) {
			output += displayHelp+1 + " | ";
		}//end of for loop
		output += "\n";

		//Display of grid
		for (int row = 0; row < this.grid.length; row++) {
			for (int column = 0; column < this.grid[row].length; column++) {
				output += this.grid[row][column] + "  ";
			}//end of nester for loop

			output += "\n";
		}//end of for loop
		return output;
	}

	//Getters and setters for Rows and Columns
	public int getRows() {
		return this.rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumn() {
		return this.column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	//end of getters and setter
}//end of class Grid
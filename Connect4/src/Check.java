import javax.swing.*;
import java.io.Serializable;

//Creating object Check, which will process all necessary operations. This object is responsible for:
//1. Checking if one of the players has four disc in same colour in row: horizontally, vertically, diagonally or opposite diagonally.
//2. Verifying if there is a draw.
//3. Check if game is finished.

public class Check implements Serializable {
    private Grid grid;
    public boolean finish = false;

    private String mainGrid[][];

    //Main class method Check with Grid object parameter
    public Check(Grid aGrid) {

        this.grid = aGrid;

        this.mainGrid = this.grid.getGrid();
    }//end of main method Check


    //This methods will check if player hit four disc in a row horizontally, vertically, diagonally or opposite diagonally.
    // In order to do it I check each row or column. If desired player type is found, variable count is updated with 1.
    // For each time loop is running, method checks in the end, by using another method checkResult, if count if equals 4.
    // This will mean that one of the players had 4 discs in row.

    public void checkingIfConnectedHorizontal(int userInput, String player, String playerName) {

        for (int rows = 0; rows < this.mainGrid.length; rows++) {

            int count = 0;
            int userColumn = userInput;

            //Checking on right side of user's column.
            while (userColumn < grid.getColumn() && mainGrid[rows][userColumn].equals(player)) {
                count++;
                userColumn++;
            }//end of while loop

            //Checking on left side from input and excluding already counted user's column
            userColumn = userInput - 1;

            while (userColumn >= 0 && mainGrid[rows][userColumn].equals(player)) {
                count++;
                userColumn--;
            }//end of while loop

            checkResult(count, playerName);
        }//end of for loop
    }//end of method checkingIfConnectedHorizontal

    public void checkingIfConnectedVertical(int userInput, String player, String playerName) {
        for (int rows = 0; rows < this.mainGrid.length; rows++) {

            int count = 0;
            int rowsForLoop = rows;

            //Checking each row in selected column
            while (rowsForLoop < grid.getRows() && this.mainGrid[rowsForLoop][userInput - 1].equals(player)) {
                count++;
                rowsForLoop++;
            }//end of while loop

            checkResult(count, playerName);
        }//end of for loop
    }//end of checkingIfConnectedVertical method

    public void checkingIfConnectedDiagonal(int userInput, String player, String playerName) {
        for (int rows = 0; rows < this.mainGrid.length; rows++) {

            int count = 0;
            int rowForLoop = rows;
            int userColumn = userInput - 1;

            //Checking left side from user input. With each iteration increasing row(going down on grid) and decreasing column.
            while (rowForLoop < grid.getRows() && userColumn >= 0 && this.mainGrid[rowForLoop][userColumn].equals(player)) {
                count++;
                rowForLoop++;
                userColumn--;
            }//end of while loop

            //Setting counting on one position further then the one already counted and decreasing row(going up on grid)
            //and increasing column.
            rowForLoop = rows - 1;
            userColumn = (userInput - 1) + 1;
            while (rowForLoop >= 0 && userColumn < grid.getColumn() && this.mainGrid[rowForLoop][userColumn].equals(player)) {
                count++;
                rowForLoop--;
                userColumn++;
            }//end of while loop

            checkResult(count, playerName);
        }//end of for loop
    }//end of checkingIfConnectedDiagonal method

    public void checkingIfConnectedOppositeDiagonal(int userInput, String player, String playerName) {
        for (int rows = 0; rows < this.mainGrid.length; rows++) {

            int count = 0;
            int rowForLoop = rows;
            int userColumn = userInput;

            //Checking right side from user input. With each iteration increasing row(going down on grid) and increasing column.
            while (rowForLoop < grid.getRows() && userColumn < grid.getColumn() && this.mainGrid[rowForLoop][userColumn].equals(player)) {
                count++;
                rowForLoop++;
                userColumn++;
            }//end of while loop

            //Setting counting on one position before then the one already counted.
            //Checking left side from user input. With each iteration decreasing row(going up on grid) and decreasing column.
            rowForLoop = rows - 1;
            userColumn = userInput - 1;

            while (rowForLoop >= 0 && userColumn >= 0 && this.mainGrid[rowForLoop][userColumn].equals(player)) {
                count++;
                rowForLoop--;
                userColumn--;
            }//end of while loop

            checkResult(count,playerName);
        } // end for loop
    }// end of checkingIfConnectedOppositeDiagonal method

    //This method will check weather one of four checking if disc are connected results is true. If one of this methods
    //will return count = 4, then game is finished and finish message is displayed including final grid.
    //We need to change here variable finish to true to let know program that game is finished.

    public void checkResult(int count, String player) {

        int total = 4;

        if (count == total) {
            print(player + " wins!\n\nGame Over!\n\n\n" + this.grid.displayGrid());
            finish = true;
        }//end of if statement
    }//end of method checkResult


    //This method checks if there is a draw. In order to be draw, the grid has to be full without having count = 4 in any
    //possible way to check. Each time player is playing the variable totalMoves is updated +1. Once totalMoves = rows x columns
    //it means that grid is full and there is no winner. Method will display message.
    //We need to change finis to true as well here, as game is finished.

    public void verifyDraw(int totalMoves) {

        int total = this.grid.getColumn() * this.grid.getRows();

        if (totalMoves == total) {
            print(grid.displayGrid() + "\n\n\nNo winner!\n\nGame Over");
            this.finish = true;
        }//end of if statement
    }//end of verifyDraw method

    //Method where we check if selected by user column is not full.
    public boolean invalidColumn(int column) {

        int count = 0;

        for (int row = this.mainGrid.length - 1; row >= 0; row--) {

            if (!this.mainGrid[row][column - 1].equals(String.valueOf(Character.toChars(0x25A0)))) {
                count = count + 1;
            }//end of if
        }//end of for loop

        if (count == this.mainGrid.length) {
            return true;
        }//end of if

        return false;
    }//end of invalidColumn

    //Getter for finish
    public boolean getFinish (){
        return this.finish;
    }

    //Helpful print statement including JOptionPane displaying message
    public void print(String output) {
        JOptionPane.showMessageDialog(null,output,"Player name", JOptionPane.PLAIN_MESSAGE);
    }
}

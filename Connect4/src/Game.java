import javax.swing.*;
import java.io.*;

//  This object is responsible for merging all previous object into one working game. We need to create few variables here:
//      - object grid
//      - Array object users
//      - User input
//      - object check
//      - rows, columns and total number of moves
//  In this class we will need to initialize players, allow player to place disc in grid, run game,
//  allow user to save and load game and create small menu.

public class Game implements Serializable{
    private Grid grid;
    private User[] users;
    private int userInput;
    private Check check;
    private boolean runGame = true;
    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private int totalMoves = 0;

    //Main class method Game, which is creating grid and initializing 2 players.
    public Game() {
        users = new User[2];
        grid = new Grid(this.ROWS, this.COLUMNS);
        check = new Check(grid);
        for (int player = 0; player < users.length; player++) {

            users[player] = new User();
        }//end of for loop
    }//end of Game

    //Menu method. Here I will create small menu with few small buttons using JOptionPane showOptionDialog.
    //User will have chance to choose 3 options: new game, load game or exit game.
    public void menu() throws IOException, ClassNotFoundException {

        int mainMenu; //variable responsible for holding value if player wants to come back to main menu (0 - yes, 1 - no)
        int userChoice; //variable responsible for holding value of user choice in main menu(0 - play, 1 - load, 2 - exit)

        String output = "Welcome to Connect Four game\n\n\n";

        //Using JLabel to place welcome message in the middle of window.
        JLabel newOutput = new JLabel(output,SwingConstants.CENTER);

        //Image of Connect Four board
        ImageIcon icon = new ImageIcon(Game.class.getResource("/connectFour.png"));

        //Object of buttons for main menu with user choice variable holding JOptionPane
        Object[] options = {"Play game", "Load game", "Exit game"};
        userChoice = JOptionPane.showOptionDialog(null,newOutput,"Connect Four",
                        JOptionPane.PLAIN_MESSAGE,JOptionPane.PLAIN_MESSAGE,icon,options,options[0]);

        //Validation of user choice
        if (userChoice==0){
            //Running game if user choose to click play button
            run();
            //end of game

            //Options to choose once game is finished hold in object to display in JOptionPane dialog.
            //End the game or come back to main menu.
            Object [] endOptions = {"Main Menu", "Exit game"};
            mainMenu = JOptionPane.showOptionDialog(null,"Select one of options: ", "Select",
                        JOptionPane.PLAIN_MESSAGE,JOptionPane.PLAIN_MESSAGE,icon,endOptions,endOptions[0]);
            if (mainMenu==0){
                check.finish = false; //We have to change boolean value finish to false in order to play again.

                //Opening menu if player choose this option.
                menu();
            }//end of coming back to main menu option

            //If player choose "exit", game finishes.
            else {
                System.exit(0);
            }//end of exit game option
        }
        //Loading game if player press "Load game" (userChoice == 1)
        else if (userChoice==1){
            loadGameDataFromFile();
        }//end of Main Menu Load Game option

        //If player choose "Exit Game" game finishes.
        else {
            System.exit(0);
        }//end of Main Menu Exit Game else statement
    }//end of Menu method

    //Method run is responsible for running few other methods like: initializing players, starting a grid,
    //placing disc in grid and adding new move to total moves.
    public void run() throws IOException, ClassNotFoundException {

        //Initialize of the game
        initPlayers();
        grid.startGrid();
        grid.displayGrid();


        while (runGame) {

            processChecks();
            userInputTurn(users[0].getName(),users[0].getDisplayColour());//Player 1 is placing disc.
            this.totalMoves = this.totalMoves + 1;//Adding new move to total number of moves.

            //This process runs as long as value of finish from class grid is changed to true.
            if (check.getFinish()){
                break;
            }

            //Same process for player 2.
            processChecks();
            userInputTurn(users[1].getName(),users[1].getDisplayColour());
            this.totalMoves = this.totalMoves + 1;

            //Same for second player.
            if (check.getFinish()){
                break;
            }
        }//end of while loop
    }//end of run


    //This method checks if there is a draw.
    public void processChecks() {
        check.verifyDraw(this.totalMoves);
    }//end of processChecks

    //Method responsible for placing disc in grid. It will be display as JOptionPane OptionDialog. User will have 7
    //column buttons and button to save game, as well as exit to main menu. Each column button will place disc
    //in chosen by player place.
    private void userInputTurn(String playerName, String gridInput) throws IOException, ClassNotFoundException {

        while (true) {
            String input = String.format("Player: %s\n%s\n\nPlease enter a column number between 1 to %s" +
                    "\nin which you want to place the disc.", playerName, grid.displayGrid(), COLUMNS);

            //Object holding possible options to display and variable userInput as integer with choice of user.
            Object[] choicesOfColumns = {1,2,3,4,5,6,7,"Save game", "Exit"};
            userInput = JOptionPane.showOptionDialog(null,input,"Connect Four", JOptionPane.PLAIN_MESSAGE,JOptionPane.PLAIN_MESSAGE,null,choicesOfColumns,choicesOfColumns[0]);
            userInput++;//Adding one to user input as OptionDialog starts with 0.

            //Player selects exit - main menu.
            if(userInput==9){
                menu();
            }
            //Player selects save game.
            else if(userInput==8) {
                saveGameDataToFile();
            }
            //Player selects one of columns from 1 to 7.
            else {
                //Checks if column is not full using invalidColumn method from Check class.
                if (check.invalidColumn(userInput)){
                    print(String.format("%s\n\n\nColumn %s is full. Please select other column.",grid.displayGrid(), userInput));
                }
                else{
                    grid.updateGrid(userInput, gridInput);//updating grid with user choice.
                    //Checking each possible option if user hits 4 discs in row.
                    check.checkingIfConnectedHorizontal(userInput, gridInput, playerName);
                    check.checkingIfConnectedVertical(userInput, gridInput, playerName);
                    check.checkingIfConnectedDiagonal(userInput, gridInput, playerName);
                    check.checkingIfConnectedOppositeDiagonal(userInput, gridInput, playerName);
                    break;
                }

            }//end of if statement
        }//end of while loop
    }//end of userInputTurn


    //Method responsible for choosing player names and selecting which disc colour they want - Yellow or Red.
    private void initPlayers() {

        String red = "R";
        java.awt.Color.getColor(red);
        String yellow = "Y";
        while (true) {
            //Object which holds possible option to select - Yellow or Red.
            Object [] colours = {"Yellow", "Red"};

            //Player 1 inputs his name and selects disc's colour.
            users[0].setName(inputStr("Player 1 please enter your name?"));
            int player_one = JOptionPane.showOptionDialog(null,users[0].getName() + ", which colour do you want to play with?","Choose colour", JOptionPane.PLAIN_MESSAGE,JOptionPane.PLAIN_MESSAGE,null, colours,colours[0]);

            //Player 2 selects name.
            users[1].setName(inputStr("Player 2 please enter your name?"));

            //Depending on what disc's colour player 2 selects, player 1 will get opposite colour.
            //Here we are selecting type:
            // 1. "Y" will be displayed in grid for colour Yellow.
            // 2. "R" will be displayed in grid for colour Red.
            if(player_one == 0){
                users[0].setDisplayColour(yellow);
                users[1].setDisplayColour(red);
                print(users[1].getName() + ", you will play with colour " + colours[1]);
            }
            else{
                users[0].setDisplayColour(yellow);
                users[1].setDisplayColour(red);
                print(users[1].getName() + ", you will play with colour " + colours[0]);
            }

            //
            if (users[0].getName().length() > 0 && users[1].getName().length() > 0) {
                break;
            }
        }//end of while loop
    }//end of initPlayers method.


    //Method to save game. Save game status will be saved in txt file "savegame.txt".
    private void saveGameDataToFile() throws IOException {
        ObjectOutputStream fileStream = new ObjectOutputStream(new FileOutputStream("savegame.txt"));
        fileStream.writeObject(grid);
        fileStream.close();
        System.out.println("Done writing");
    }

    //Method to load game. This method will read from file "savegame.txt".
    private void loadGameDataFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream file = new ObjectInputStream(new FileInputStream("savegame.txt"));
        Grid g = (Grid) file.readObject();
        System.out.println(file.toString());
        System.out.println("done loading");
        file.close();

    }
    //Helping method to input String.
    public String inputStr(String input) {
        return JOptionPane.showInputDialog(null,input,"Player name", JOptionPane.PLAIN_MESSAGE);
    }

    //Helping method with print as JOptionPane message.
    public void print(String output) {
        JOptionPane.showMessageDialog(null,output,"Player name", JOptionPane.PLAIN_MESSAGE);
    }
}
import java.io.IOException;


//Main class Run, where we start game.
public class Run{

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //Creating new object game and starting menu.
        Game game = new Game();
        game.menu();
    }
}


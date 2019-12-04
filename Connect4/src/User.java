import java.io.Serializable;

//Creating object for Player. Each player will have name and type. Type will represent symbol displayed on grid.

public class User implements Serializable {
    private String name;
    private String displayColour;

    //Main class method
    public User() {
        setName(name);
        setDisplayColour(displayColour);
    }

    //Getters and setters for variables: name and type
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayColour() {
        return this.displayColour;
    }

    public void setDisplayColour(String displayColour) {
        this.displayColour = displayColour;
    }

    //To string
    public String toString() {
        return getClass().getName()+" Name:"+getName()+" Type:"+ getDisplayColour();
    }
}

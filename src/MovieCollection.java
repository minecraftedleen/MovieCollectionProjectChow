import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class MovieCollection {
    private Scanner scan = new Scanner(System.in);
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    private void importData() {
        try {
            File myFile = new File("src//movies_data.csv");
            Scanner fileScanner = new Scanner(myFile);
            fileScanner.nextLine();
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                String[] splitData = data.split(",");
                movies.add(new Movie(splitData[0], splitData[1], splitData[2], splitData[3], Integer.parseInt(splitData[4]), Double.parseDouble(splitData[5])));
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

    }


    public void mainMenu() {
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scan.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }

    }

    public void searchTitles() {
        int count = 0;

    }

    public void searchCast() {
        int count = 0;
    }
}

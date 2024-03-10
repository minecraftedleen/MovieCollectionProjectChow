import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
public class MovieCollection {
    private Scanner scan = new Scanner(System.in);
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    public MovieCollection() {
        importData();
        mainMenu();
    }
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
        ArrayList<Movie> titles = new ArrayList<Movie>();
        System.out.print("Enter a title search term: ");
        String titlePart = scan.nextLine();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getTitle().toLowerCase().contains(titlePart.toLowerCase())) {
                titles.add(movies.get(i));
            }
        }
        if (titles.size() > 0) {
            insertionSortTitle(titles);
            for (int i = 0; i < titles.size(); i++) {
                System.out.println(i + 1 +". " + titles.get(i).getTitle());
            }
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number (Type 0 to skip): ");
            int idx = scan.nextInt() - 1;
            scan.nextLine();
            System.out.println();
            if (idx != -1) {
                Movie chosen = titles.get(idx);
                System.out.println("Title: " + chosen.getTitle());
                System.out.println("Runtime: " + chosen.getRuntime());
                System.out.println("Directed by: " + chosen.getDirector());
                System.out.println("Cast: " + chosen.getCast());
                System.out.println("Overview: " + chosen.getOverview());
                System.out.println("User rating: " + chosen.getUserRating());
            }
        } else {
            System.out.println();
            System.out.println("No movie titles match that search term!");
        }
    }

    public void searchCast() {
        int count = 0;
        ArrayList<Movie> castsMovies = new ArrayList<Movie>();
        ArrayList<String> cast = new ArrayList<String>();
        System.out.print("Enter a person to search for (first or last name): ");
        String name = scan.nextLine();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getCast().toLowerCase().contains(name.toLowerCase())) {
                castsMovies.add(movies.get(i));
                String[] actorList = movies.get(i).getCast().split("\\|");
                for (int j = 0; j < actorList.length; j++) {
                    if (actorList[j].toLowerCase().contains(name.toLowerCase())) {
                        cast.add(actorList[j]);
                    }
                }
            }
        }
        if (cast.size() > 0) {
            insertionSortCast(cast);
            removeDupes(cast);
            for (int i = 0; i < cast.size(); i++) {
                System.out.println(i + 1 +". " + cast.get(i));
            }
            System.out.println("Which would you like to see all movies for?");
            System.out.print("Enter number (Type 0 to skip): ");
            int idx = scan.nextInt() - 1;
            scan.nextLine();
            System.out.println();
            if (idx != -1) {
                ArrayList<Movie> inMovies = new ArrayList<Movie>();
                for (int i = 0; i < castsMovies.size(); i++) {
                    if (castsMovies.get(i).getCast().contains(cast.get(idx))) {
                        inMovies.add(castsMovies.get(i));
                    }
                }
                insertionSortTitle(inMovies);
                for (int i = 0; i < inMovies.size(); i++) {
                    System.out.println(i + 1 +". " + inMovies.get(i).getTitle());
                }
                System.out.println("Which movie would you like to learn more about?");
                System.out.print("Enter number (Type 0 to skip): ");
                idx = scan.nextInt() - 1;
                scan.nextLine();
                System.out.println();
                if (idx != -1) {
                    Movie chosen = inMovies.get(idx);
                    System.out.println("Title: " + chosen.getTitle());
                    System.out.println("Runtime: " + chosen.getRuntime());
                    System.out.println("Directed by: " + chosen.getDirector());
                    System.out.println("Cast: " + chosen.getCast());
                    System.out.println("Overview: " + chosen.getOverview());
                    System.out.println("User rating: " + chosen.getUserRating());
                }
            }
        } else {
            System.out.println();
            System.out.println("No results match your search");
        }
    }

    public static void insertionSortTitle(ArrayList<Movie> arrList) {
        int counter = 0;
        for (int i = 1; i < arrList.size(); i++) {
            int swapIdx = i;
            Movie temp = arrList.get(i);
            while (swapIdx > 0 && temp.getTitle().compareTo(arrList.get(swapIdx - 1).getTitle()) < 0) {
                counter++;
                arrList.set(swapIdx, arrList.get(swapIdx - 1));
                swapIdx--;
            }
            arrList.set(swapIdx, temp);
        }
    }

    private static void removeDupes(ArrayList<String> arrList) {
        for (int i = 0; i < arrList.size() - 1; i++) {
            if (arrList.get(i + 1).equals(arrList.get(i))) {
                arrList.remove(i + 1);
                i--;
            }
        }
    }

    private static void insertionSortCast(ArrayList<String> arrList) {
        int counter = 0;
        for (int i = 1; i < arrList.size(); i++) {
            int swapIdx = i;
            String temp = arrList.get(i);
            while (swapIdx > 0 && temp.compareTo(arrList.get(swapIdx - 1)) < 0) {
                counter++;
                arrList.set(swapIdx, arrList.get(swapIdx - 1));
                swapIdx--;
            }
            arrList.set(swapIdx, temp);
        }
    }


}

import java.io.IOException;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        mangaAPI manga = new mangaAPI();

        while (true) {
            System.out.print("Search for Manga: ");
            Scanner sc = new Scanner(System.in);
            String searchtitle = sc.nextLine();

            manga.searchManga(searchtitle);
            System.out.println("\n\n");
        }

    }
}
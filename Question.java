import java.util.Scanner;

public class Question
{
    public static Scanner sc;
    
    /**
     * Quasi singleton. I'm sure there are cleaner ways to do this. But I needed to do something to make sure that the scanner could be referenced continuously, because closing it once causes problems for later classes.
     * @return
     */
    public static Scanner getScanner()
    {
        if(sc == null)
        {
            sc = new Scanner(System.in);
        }
        return sc;
    }

    public static void setScanner(Scanner scanner)
    {
        sc = scanner;
    }
}

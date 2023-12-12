public class QuestionTwo extends Question
{

    /**
     * Checks that a given day can belong to a given month.
     * Does not account for non leap years wrt february
     * @param day   The int value of the day part of the date 
     * @param month The int value of the month part of the date
     * @return      Returns true if the date is valid, false otherwise.
     */
    public static boolean isDateValid(int day, int month)
    {
        // september april june november
        switch(month)
        {
            case 9: case 4: case 6: case 11:
                return day < 31;
            case 2:
                return day < 30;
            default:
                return day < 32;
        }
    }

    /**
     * Get the int part of a date
     * @param type  month for month, day for day, this is just a helper for the printing
     * @param max   The maximum value, useful for initial boundary setting
     * @return      The int value of the date part
     */
    public static int getDatePart(String type, int max)
    {
        int datePart = -1;
        try
        {
            // Ask for input
            System.out.print(
                String.format("Please enter a %s: ", type)
            );
            // get input
            String dayAsString = sc.nextLine();
            // parse as int
            datePart = Integer.parseInt(dayAsString);
            // make sure that it's within the bounds
            if(datePart < 1 || datePart > max)
            {
                System.out.println(String.format("Invalid %s entered", type));
                return -1;
            }
        }
        catch(Exception e)
        {
            System.out.println(String.format("Invalid %s entered", type));
        }
        return datePart;
    }
    
    /**
     * A method to print the date in the required format
     * @param day   The day of the date
     * @param month The month of the date
     */
    public static void printDate(int day, int month)
    {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        System.out.println(String.format("You selected the %s of %s", ordinal(day) ,monthNames[month - 1]));
    }

    /**
     * Code cribbed from https://stackoverflow.com/questions/20258569/date-format-with-20th-suffix
     * A method to return the ordinal of a number
     * @param i
     * @return
     */
    public static String ordinal(int i)
    {
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th","th", "th", "th", "th" };
        switch (i % 100)
        {
            case 11: case 12: case 13: return i + "th";
            default: return i + sufixes[i % 10];
        }
    }

    public static void main(String[] args)
    {
        sc = getScanner();
        System.out.print("Please enter the day (1-31): ");
        // get the day part
        int day = getDatePart("day", 31);
        // only proceed if it's valid
        if(day == -1)
        {
            return;
        }
        System.out.println("Please enter the month (1-12)");
        // same for the month
        int month = getDatePart("month", 12);
        if(month == -1)
        {
            return;
        }

        // if the date is valid print it
        if(isDateValid(day, month))
        {
            printDate(day, month);
        }
        // otherwise print an error
        else
        {
            System.out.println("Invalid date entered");
        }
        return;
    }
}

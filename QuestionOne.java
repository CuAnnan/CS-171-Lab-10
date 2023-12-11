public class QuestionOne extends Question
{
    
    /**
     * Pick a shape from input
     */
    public static boolean processShape()
    {
        System.out.print("Enter a shape type (q to quit): ");
        String in = sc.nextLine();
        System.out.println();
        if(in.equals("q"))
        {
            return false;
        }
        switch(in)
        {
            case "square":
                getSquareDetails();
                break;
            case "rectangle":
                getRectangleDetails();
                break;
            case "circle":
                getCircleArea();
                break;
        }
        return true;
    }

    /**
     * A method to print the same message to the screen
     * with the calculated details of a given shape
     * @param shape     The name of the shape to print
     * @param perimeter The calculated perimeter
     * @param area      The calculated area
     */
    public static void printArea(String shape, double perimeter, double area)
    {
        System.out.println(
            String.format(
                "The perimeter of your %s has a %s of %.2f and an area of %.2f",
                shape,
                shape.equals("circle")?"circumference":"perimeter",
                perimeter,
                area
            )
        );
    }

    /**
     * Get the width and height of a rectangle, calculate its perimeter and area and
     * pass those params to the print method
     */
    public static void getRectangleDetails()
    {
        System.out.print("Please enter the width of the rectangle: ");
        double width = sc.nextDouble();
        System.out.println();
        System.out.print("Please enter the height of the rectangle: ");
        double height = sc.nextDouble();
        System.out.println();
        // clearing the scanner cursor to a new line
        sc.nextLine();
        printArea("rectangle", (width + height)*2, width*height);
    }

    /**
     * Get the side length of a square and print its perimeter and area to the screen
     */
    public static void getSquareDetails()
    {
        System.out.print("Please enter the side length of the square: ");
        double length = sc.nextDouble();
        System.out.println();
        // clearing the scanner cursor to a new line
        sc.nextLine();
        printArea("square", length*4, length*length);
    }

    /**
     * Get a circle's radius and print its perimeter and area to the screen
     */
    public static void getCircleArea()
    {
        System.out.print("Please enter the radius of the circle: ");
        double radius = sc.nextDouble();
        System.out.println();
        // clearing the scanner cursor to a new line
        sc.nextLine();
        printArea("circle", Math.PI*2*radius, Math.PI*radius*radius);
    }

    public static void main(String[] args)
    {
        boolean working = true;
        while(working)
        {
            working = processShape();
        };
        return;
    }
}

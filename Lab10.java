import java.util.Scanner;
public class Lab10
{
    public static void main(String[] args)
    {
        Question.sc = new Scanner(System.in);
        System.out.println("Question One:");
        QuestionOne.main(args);
        System.out.println("Question Two:");
        QuestionTwo.main(args);
        System.out.println("Question Three:");
        QuestionThree.main(args);
        System.out.println("Question Four:");
        QuestionFour.main(args);
        Question.sc.close();
    }
}

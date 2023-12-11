import java.util.Scanner;

public class QuestionThree
{
    private static PrimeChecker pc;
    public static void main(String[] args)
    {
        pc = new PrimeChecker();
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter an integer n: ");
        int n = sc.nextInt();
        if(n > 2)
        {
            printPrimes(n);
        }
        else
        {
            System.out.println("There are no positive prime numbers less than 2");
        }
        sc.close();
    }

    public static void printPrimes(int n)
    {
        int primes = 0;
        for(int i = 1; i < n; i++)
        {
            boolean isPrime = pc.isPrime(i);
            if(isPrime)
            {
                System.out.println(i+" is prime");
                primes++;
            }
        }
        System.out.println(primes);
    }

}

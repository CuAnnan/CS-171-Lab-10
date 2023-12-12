public class QuestionThree extends Question
{
    private static PrimeChecker pc;
    public static void main(String[] args)
    {
        sc = getScanner();
        pc = new PrimeChecker();
        
        System.out.print("Enter how many numbers you want to check for primality: ");
        int n = sc.nextInt();
        if(n > 2)
        {
            printPrimes(n);
        }
        else
        {
            System.out.println("There are no positive prime numbers less than 2");
        }
        return;
    }

    public static void printPrimes(int n)
    {
        int primes = 0;
        for(int i = 1; i < n; i++)
        {
            boolean isPrime = pc.isPrime(i);
            if(isPrime)
            {
                primes++;
            }
        }
        System.out.println("There are "+primes+" primes between 0 and "+n);
    }

}

/**
 * A Prime checker built on the Seive of Eratosthenes
 */
public class PrimeChecker
{
    /**
     * An array to hold all currently known primes
     */
    protected int[] primes;

    // constructor
    public PrimeChecker()
    {
        // prime the primes (sorry not sorry)
        this.primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19};
    }

    /**
     * Get the list of known primes, used to verify functionality but may come in handy in future. This may well be reusable code.
     * @return  The array of primed primes
     */
    public int[] getPrimes()
    {
        return this.primes;
    }

    /**
     * A method to check if an integer is prime. Only works within the int range. Could be expanded to work on longs.
     * @param n A number to check for primeness
     * @return  True if prime, otherwise false.
     */
    public boolean isPrime(int n)
    {
        if(n == 1)
        {
            return false;
        }
        
        int p = 0;
        int largestPrime = 0;
        while(p < this.primes.length)
        {
            largestPrime = this.primes[p];
            // check if it's a stored prime
            if(n == largestPrime)
            {
                return true;
            }
            // check if largest prime is a factor of n
            if(n % largestPrime == 0)
            {
                return false;
            }
            p++;
        }

        // I'm not sure if this is actually an improvement on the i*i < j, but I contend that it's easier to read. A lot of this is going to be subjective.
        int sqrtRoundedDown = (int)Math.sqrt(n);

        // passed the seive now need to make sure that there are no factors beyond the range of stored primes.
        // this phase is not strictly needed for the question
        for(int i = largestPrime; i < sqrtRoundedDown; i++)
        {
            // recursively add any new prime factors to the array of primes
            isPrime(i);
            if(n % i == 0)
            {
                return false;
            }
        }
        
        // Add this number to the list of primes so that we don't have to calculate it in future.
        // pretty sure this is an optimisation, but my understanding of data structures and algorithms gets a bit hazy because I haven't had to look at this in a while.
        // But it strikes me that this is probably an improvement
        int[] newPrimes = new int[primes.length + 1];
        System.arraycopy(this.primes, 0, newPrimes, 0, primes.length);
        newPrimes[primes.length] = n;
        this.primes = newPrimes;

        return true;
    }

}
public class PrimeChecker
{
    protected int[] primes;
    // constructor
    public PrimeChecker()
    {
        // prime the primes (sorry not sorry)
        this.primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19};
    }

    public boolean isPrime(int n)
    {
        if(n == 1)
        {
            return false;
        }
        boolean prime = true;
        int i = 0;
        while(prime && i < this.primes.length)
        {
            int p = this.primes[i];
            if(n == p)
            {
                return true;
            }
            if(n % p == 0)
            {
                prime = false;
            }
            i++;
        }
        if(prime)
        {
            int[] newPrimes = new int[primes.length + 1];
            System.arraycopy(this.primes, 0, newPrimes, 0, primes.length);
            newPrimes[primes.length] = n;
            this.primes = newPrimes;
        }
        return prime;
    }

}
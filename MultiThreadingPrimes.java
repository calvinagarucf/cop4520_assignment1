import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MultiThreadingPrimes {

    public static void main(final String[] args) throws InterruptedException 
	{    
		PrimeThread[] threads = new PrimeThread[8];

		// Start threads
        long start = System.nanoTime();
        for (int i = 0; i < threads.length; i++) 
		{
            threads[i] = new PrimeThread();
            threads[i].start();
        }

		// Wait to finish
		for (int i = 0; i < threads.length; i++)
			threads[i].join();
        long end = System.nanoTime();

		long executionTime = (end - start) / 1000000L;

		// Create output
		String formattedTime = executionTime + "ms";
		String output = formattedTime + " " + PrimeThread.totalCount.get() + " " + PrimeThread.totalSum.get() + "\n";

		Arrays.sort(PrimeThread.primes);
		for (int i = PrimeThread.primes.length - 10; i < PrimeThread.primes.length; i++)
			output += PrimeThread.primes[i] + "\n";

		// Write to primes.txt
		PrintWriter writer;
		try
		{
			writer = new PrintWriter("primes.txt", "UTF-8");
			writer.print(output);
			writer.close();
		}
		catch (FileNotFoundException f) {}
		catch (UnsupportedEncodingException u) {}

	}
}

class PrimeThread extends Thread {

    public final static int UPPER_BOUND = (int)Math.pow(10, 8);

    public static int[] primes = new int[10000000]; 

	// Initialized to start at 3
    public static AtomicInteger totalCount = new AtomicInteger(1);
	public static AtomicLong totalSum = new AtomicLong(2);
    public static AtomicInteger nextNum = new AtomicInteger(3);

	PrimeThread() 
	{
		primes[0] = 2;
	}

    public void run() 
	{
        int test;
        while ((test = nextNum.getAndAdd(2)) <= UPPER_BOUND)
		{
            if (isPrime(test)) 
			{
                primes[totalCount.incrementAndGet()] = test;
				totalSum.addAndGet(test);
            }
		}
	}

    public boolean isPrime(int n) 
	{
        int max = (int)Math.sqrt(n);

        for (int i = 3; i <= max; i += 2)
            if (n % i == 0) 
				return false;

        return true;
    }
}
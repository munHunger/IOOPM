import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


public class LargestNum extends RecursiveTask<Double>
{
	int start;
	int end;
	double[] rng;
	public LargestNum()
	{
		rng = new double[100000000];
		end = rng.length;
		for(int i = 0; i < rng.length; i++)
			rng[i] = Math.random()*99999999.0;
		
		long time = System.currentTimeMillis();
		double largest = findLargestDirectly();
		System.out.println("Largest value:" + largest + "\tCalculation time for non fork:" + (System.currentTimeMillis()-time));
		
		time = System.currentTimeMillis();
		ForkJoinPool pool = new ForkJoinPool(6);
		LargestNum largestNum = new LargestNum(0, rng.length, rng);
		System.out.println("Largest value:" + pool.invoke(largestNum) + "\tCalculation time for fork:" + (System.currentTimeMillis()-time));
	}
	
	public LargestNum(int start, int end, double[] rng)
	{
		this.start = start;
		this.end = end;
		this.rng = rng;
	}
	
	public double findLargestDirectly()
	{
		double largest = 0;
		for(int i = start; i < end; i++)
			if(rng[i] > largest)
				largest = rng[i];
		return largest;
	}
	
	public static void main(String args[])
	{
		new LargestNum();
	}

	protected Double compute() 
	{
		int length = end-start;
		if(length < 100000)
			return findLargestDirectly();
		
		int split = length/2;
		LargestNum left = new LargestNum(start, end-split, rng);
		left.fork();
		LargestNum right = new LargestNum(start+split, end, rng);
		
		return Math.max(right.compute(), left.join());
	}
}

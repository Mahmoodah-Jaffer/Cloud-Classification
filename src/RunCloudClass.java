import java.util.Scanner;
import java.lang.Math;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.RecursiveAction;

public class RunCloudClass{
	static int t;
	static int rows;
	static int columns;
	static int total;

	static float sumX = 0;
	static float sumY = 0;
	static float avgX = 0;
	static float avgY = 0;

	static float wind;
	static float[] con;
	static float[] adv;

	static int lines = 0;

	static float localAvg;

	static long startTime = 0;

	public static void main(String[] args){
		CloudData obj = new CloudData();
		Vector vec = new Vector();
		CloudClassSeq seq = new CloudClassSeq();

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Filename");
		String filename = sc.nextLine();

		obj.readData(filename);

		t = obj.getDimt();
		rows = obj.getDimx();
		columns = obj.getDimy();
		total = obj.dim();

		System.out.println("Sequential");
		System.out.println(t+" "+rows+" "+columns);
		System.gc();
		tick();
		seq.totAvg(t, rows, columns, total, vec, obj);
		con = new float[total];
		con = seq.getCon();
		float time = tock();
		System.out.println("Time taken to find total average = "+ time +" seconds");
		System.gc();
		tick();
		seq.localAvg(t, rows, columns, total, vec, obj);
		adv = new float[total];
		adv = seq.getAd();
		seq.classify(con,adv,total, rows, columns);

		time = tock();
		System.out.println("Time taken to do classification = "+ time +" seconds");

		System.out.println("Parallel");
		System.gc();
		tick();
		Vector avgVec = avg(total, vec, obj);
		float xval = (avgVec.getX())/total;
		float yval = (avgVec.getY())/total;
		System.out.println(xval + " " + yval);

		time = tock();
		System.out.println("Time taken to find total average = "+ time +" seconds");

		System.gc();
		tick();
		classify(total, rows, columns,vec, obj);

		time = tock();
		System.out.println("Time taken to classify= "+ time +" seconds");

		sc.close();

	}
	/**
	Method totalAvg returns the x and y components for the total prevailing wind for all cloud elements
	@param total int
	@param vec Vector
	@param obj CloudData
	*/
	static final ForkJoinPool fjPool = new ForkJoinPool();
	static Vector avg(int total, Vector vec, CloudData obj){
	  return fjPool.invoke(new ParallelAvg(0, total, vec,obj)); 
	}
	/**
	Method classify takes in the value of the advection and convection of each element in the cloud and classfies it as either cumulus(0), striated stratus(1)
	or amorphous stratus(2)
	@param total int
	@param rows int
	@param columns int
	@param vec Vector
	@param obj CloudData

	*/
	static void classify(int total, int rows, int columns, Vector vec, CloudData obj){
	  fjPool.invoke(new ParallelClassify(0, total, rows, columns, vec,obj)); 
	}
	/**
	Method tick starts a timer. Must be run with tock method
	*/
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	/**
	Method tock returns the duration of time since the tick() method ran. Must be run with tock method
	*/
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
}
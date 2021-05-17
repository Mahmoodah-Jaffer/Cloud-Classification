import java.util.concurrent.RecursiveTask;
import java.lang.Math;
import java.util.Scanner;

public class ParallelAvg extends RecursiveTask<Vector>{

	int lo;
	int hi;
	static int t, rows, columns;
	Vector vec = new Vector();
	CloudData obj = new CloudData();
	Vector ans; // result 
	static final int SEQUENTIAL_CUTOFF = 50000;

	public ParallelAvg(){}
	/**
	Constructor with arguments for Parallel Avg class
	@param l int
	@param h int
	@param obj CloudData 
	@param vec Vector
	*/
	public ParallelAvg(int l, int h, Vector vec, CloudData obj){
		lo = l;
		hi = h;
		this.obj = obj;
		this.vec = vec; 
	}

	protected Vector compute(){// return answer - instead of run
		  if((hi-lo) < SEQUENTIAL_CUTOFF) {

		  	ans = new Vector();
		  	int[] ind = {0,0,0};

			  
		    for(int i=lo; i < hi; i++){
		    	obj.locate(i, ind);
		    	ans = vec.sum(ans, obj.advection[ind[0]][ind[1]][ind[2]]);
		    }

		     return ans;

		  }
		  else {
			  ParallelAvg left = new ParallelAvg(lo,(hi+lo)/2, vec, obj);
			  ParallelAvg right= new ParallelAvg((hi+lo)/2,hi, vec, obj);
			  
			  // order of next 4 lines
			  // essential – why?
			  left.fork();
			  Vector rightAns = right.compute();
			  Vector leftAns  = left.join();
			  return  vec.sum(rightAns,leftAns); 
		  }
	}


}
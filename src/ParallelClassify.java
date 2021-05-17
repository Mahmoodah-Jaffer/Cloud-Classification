import java.util.concurrent.RecursiveAction;
import java.lang.Math;
import java.util.Scanner;

public class ParallelClassify extends RecursiveAction{

	int lo;
	int hi;
	int rows;
	int columns;
	Vector vec = new Vector();
	CloudData obj = new CloudData();
	static final int SEQUENTIAL_CUTOFF = 2500000;

	public ParallelClassify(){}
	/**
	Constructor with arguments for Parallel Avg class
	@param l int
	@param h int
	@param obj CloudData 
	@param vec Vector
	*/
	public ParallelClassify(int l, int h, int rows, int columns, Vector vec, CloudData obj){
		lo = l;
		hi = h;
		this.rows = rows;
		this.columns = columns;
		this.obj = obj;
		this.vec = vec; 
	}

	protected void compute(){// return answer - instead of run
		  int count = 0;
		  if((hi-lo) < SEQUENTIAL_CUTOFF) {
		  	
		  	int[] ind = {0,0,0};
		    for(int i=lo; i < hi; i++){
		    	obj.locate(i, ind);
		    	System.out.print(localAvg(ind[0], ind[1], ind[2],rows, columns, vec,obj) + " ");
		    	count++;
		    	if (count ==(rows*columns)){
		    		System.out.print("\n");
		    		count =0;
		    	}
		    }

		  }
		  else {
			  ParallelClassify left = new ParallelClassify(lo,(hi+lo)/2, rows, columns, vec, obj);
			  ParallelClassify right= new ParallelClassify((hi+lo)/2,hi, rows, columns,vec, obj);
			  
			  // order of next 4 lines
			  // essential – why?
			  left.fork();
			  right.compute();
			  left.join();
		  }
	}
	/**
	Method localAvg returns the local prevailing wind for each cloud element and classifies all the elements
	@param i int
	@param rows int
	@param columns int
	@param j int
	@param k int
	@param vec Vector
	@param obj CloudData
	*/
	public static int localAvg(int i, int j, int k , int rows, int columns, Vector vec, CloudData obj){
		float w = 0;
		int numNeighbours = 0;
		float localX = 0.0f;
		float localY = 0.0f;
		float u = obj.convection[i][j][k];

		for (int x = -1; x < 2;x++){
			for (int y = -1; y < 2; y++){

				if (j==0 && x ==-1)
					continue;
				if (k==0 && y ==-1)
					continue;
				if (j == (rows-1) && x ==1)
					continue;
				if (k == (columns-1) && y ==1)
					continue;

				localX += obj.advection[i][j+x][k+y].x;
				localY += obj.advection[i][j+x][k+y].y;
				numNeighbours++;
			}
		}

		float xAvg = localX/numNeighbours;
		float yAvg = localY/numNeighbours;

		w = vec.len(xAvg,yAvg);

		float uAbs = Math.abs(u);

		if(uAbs>w){
			return 0;
		}
		else if((w>0.2) && (w>=uAbs)){
			return 1;
		}
		else{
			return 2;
		}
	}




}
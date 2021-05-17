import java.lang.Math;
import java.util.Scanner;

public class CloudClassSeq{

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

	/**
	Method classify takes in the value of the advection and convection of each element in the cloud and classfies it as either cumulus(0), striated stratus(1)
	or amorphous stratus(2)
	@param con float[]
	@param adv float[]
	@param total int
	@param x int
	@param y int
	*/
	public static void classify(float[] con, float[] adv, int total, int x, int y){
		int count = 0;
		for (int f = 0; f <total;f++){
			
			float uAbs = Math.abs(con[f]);
			float w = adv[f];

			if(uAbs>w){
			 System.out.print("0 ");
			 count++;
			}
			else if((w>0.2) && (w>=uAbs)){
				System.out.print("1 ");
				count++;
			}
			else{
				System.out.print("2 ");
				count++;
			}

			if (count==(x*y)){
				count = 0;
				System.out.print("\n");
			}



		    
		}

	}
	/**
	Method totalAvg returns the x and y components for the total prevailing wind for all cloud elements
	@param t int
	@param rows int
	@param columns int
	@param tot int
	@param vec Vector
	@param obj CloudData
	*/
	public static void totAvg(int t, int rows, int columns, int tot, Vector vec, CloudData obj){
		int counter = 0;
		con = new float[tot];
		for (int i =0; i<t; i++){
			for (int j = 0; j<rows;j++){
				for (int k = 0; k<columns;k++){
					sumX = sumX + obj.advection[i][j][k].x;
					sumY = sumY + obj.advection[i][j][k].y;
					wind = vec.len(obj.advection[i][j][k].x, obj.advection[i][j][k].y);

					if(counter<tot){
						con[counter] = obj.convection[i][j][k];
						counter++;
					}

				}
			}
		}

		avgX = sumX/tot ;
		avgY = sumY/tot;

		System.out.println(avgX + " " + avgY);
	}
	/**
	Method localAvg returns the local prevailing wind for each cloud element and this is used to classify all the elements
	@param t int
	@param rows int
	@param columns int
	@param tot int
	@param vec Vector
	@param obj CloudData
	*/
	public static void localAvg(int t, int rows, int columns, int tot, Vector vec, CloudData obj){
		int counter = 0;
		adv = new float[tot];
		for (int i =0; i<t; i++){
			for (int j = 0; j<rows;j++){
				for (int k = 0; k<columns;k++){
					localAvg = 0;
					int numNeighbours = 0;
					float localX = 0.0f;
					float localY = 0.0f;

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

					localAvg = vec.len(xAvg,yAvg);
					
					adv[counter] = localAvg;
					counter++;
						

				}
			}
		}

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
	/**
	Method getAd returns the advection array
	@return float[]
	*/
	public float[] getAd(){
		return adv;
	}
	/**
	Method getCon returns the advection array
	@return float[]
	*/
	public float[] getCon(){
		return con;
	}
}
import java.lang.Math;

public class Vector{

	float x;
	float y;

	Vector(){
		this.x = 0.0f;
		this.y = 0.0f;
	}

	public void Vector(float xval, float yval){
		this.x = xval;
		this.y = yval;
	}
	/**
	Method getX returns value of the x co-ordinate
	@return float
	*/
	public float getX (){
		return x;
	}
	/**
	Method getY returns value of the y co-ordinate
	@return float
	*/
	public float getY (){
		return y;
	}
	/**
	Method setY sets value of the y co-ordinate
	@param yval float
	*/
	public void setY (float yval){
		y = yval;
	}
	/**
	Method setX sets value of the x co-ordinate
	@param xval float
	*/
	public void setX (float xval){
		x = xval;
	}
	/**
	Method len calculates the magnitude of a given set of x and y co-ordinates
	@param xval float
	@param yval float
	@return float magnitude of co-ordinate set
	*/
	public float len(float xval, float yval){
		float sqr = (xval*xval) + (yval*yval);
		float len_w = (float) (Math.sqrt(sqr));

		return len_w;
	}
	/**
	Method sum adds two vectors together
	@param vec1 Vector
	@param vec2 Vector
	@return float magnitude of co-ordinate set
	*/
	public Vector sum(Vector vec1, Vector vec2){
		float valX = vec1.getX() + vec2.getX();
		float valY = vec1.getY() + vec2.getY();
		Vector vec = new Vector();
		vec.setX(valX);
		vec.setY(valY);
		return vec;

	}

}
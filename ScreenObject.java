import java.awt.Color;

class ScreenObject
{
	public Color color;
	public int x;
	public int y;
	public int width;
	public int height;
	
	public ScreenObject(Color color, int x, int y, int width, int height) 
	{
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public String toString() 
	{
		return String.format("[%s,%s] @ [%s,%s]", width, height, x, y) ;
	}
}
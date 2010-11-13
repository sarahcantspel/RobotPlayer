import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ObjectFinder
{
	public static final Point SOUTH = new Point(0, 1); 
	public static final Point EAST = new Point(1, 0);
	
	private BufferedImage image;

	public void setImage(BufferedImage image) 
	{
		this.image = image;
	}

	public List<ScreenObject> findObjects(Color objectColor) 
	{
		List<Point> possibleLocations = findPossibleLocations(objectColor);
		return findObjectsAt(possibleLocations, objectColor);
	}
	
	public List<Point> findPossibleLocations(Color targetColor)
	{
		ArrayList<Point> list = new ArrayList<Point>();
		int height = image.getHeight();
		int width = image.getWidth();
		boolean hitIt = false;
		
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				int color = image.getRGB(x, y);
				
				if(hitIt && color != targetColor.getRGB())
				{
					hitIt = false;
				}
				else if(!hitIt && color == targetColor.getRGB())
				{
					hitIt = true;
					list.add(new Point(x, y));
				}
			}
		}

		return list;
	}
	public ArrayList<ScreenObject> findObjectsAt(List<Point> locations, Color targetColor)
	{
		ArrayList<ScreenObject> objectsFound = new ArrayList<ScreenObject>();
		
		while(!locations.isEmpty())
		{
			Point currentLocation = locations.remove(0);
			ScreenObject object = findObjectCenter(currentLocation, targetColor);
			objectsFound.add(object);
			removeOverlappingPoints(object, locations);
		}
		
		return objectsFound;
	}
	
	public ScreenObject findObjectCenter(Point corner, Color pixelColor)
	{
		int width = countPixels(corner, pixelColor, EAST);
		int centerX = (width / 2) + corner.x;

		Point middle = new Point(centerX, corner.y);
		int height = countPixels(middle, pixelColor, SOUTH);
		int centerY = (height / 2) + corner.y;
		
		return new ScreenObject(pixelColor, centerX, centerY, width, height);
	}
	
	private void removeOverlappingPoints(ScreenObject object, List<Point> points)
	{
		int halfWidth = object.width / 2;
		int startX = object.x - halfWidth;
		int endX = object.x + halfWidth;
		
		int halfHeight = object.height / 2;
		int startY = object.y - halfHeight;
		int endY = object.y + halfHeight;
		
		for(int i = points.size() - 1; i >= 0; i--)
		{
			Point point = points.get(i);
			
			if(point.x >= startX && point.x <= endX &&
			   point.y >= startY && point.y <= endY)
			{
				points.remove(i);
			}
		}
	}
	
	private int countPixels(Point start, Color pixelColor, Point increment)
	{
		int targetColor = pixelColor.getRGB();
		int pixelCount = 0;
		int x = start.x;
		int y = start.y;
		
		while(image.getRGB(x, y) == targetColor)
		{
			x += increment.x;
			y += increment.y;
			pixelCount++;
		}
		
		return pixelCount;
	}	
}
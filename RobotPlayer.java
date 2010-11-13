import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;

public class RobotPlayer 
{
	private static final int POSITION_OFFSET = 30;
	private static final int CAPTURE_DELAY = 100;
	private static final Color TARGET_SQUARE = Color.decode("#2222ff");
	private static final Point GAME_SCREEN_POSITION = new Point(320, 122);
	private static final Dimension GAME_SCREEN_SIZE = new Dimension(640, 480);
	private static final Rectangle GAME_CAPTURE_AREA = new Rectangle(GAME_SCREEN_POSITION, 
																	 GAME_SCREEN_SIZE);
	public static void main(String[] args) 
	{
		try 
		{			
			ObjectFinder objectFinder = new ObjectFinder();
			Robot robot = new Robot();

			while(true)
			{
				objectFinder.setImage(robot.createScreenCapture(GAME_CAPTURE_AREA));
				
				for(ScreenObject object : objectFinder.findObjects(TARGET_SQUARE))
				{
					robot.mouseMove(GAME_SCREEN_POSITION.x + object.x + POSITION_OFFSET,
									GAME_SCREEN_POSITION.y + object.y);
				}
				
				robot.delay(CAPTURE_DELAY);
			}
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
}
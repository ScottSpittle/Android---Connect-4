package scottspittle.connect4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

//Display class to extend the View class (Canvas)
public class Display extends View
{
	//Properties
	
	//Globals
	private Main info; //declare game info object
	//Constructor, View class expects a context. (essentially the application (resources, etc..))
	public Display(Context context) {
		super(context);
		info  = new Main(); // Initialise game info object
	}
	
	public void onDraw(Canvas canvas)
	{
		//screen dimentions
		int width = getWidth();
		int height = getHeight();
		
		int hw = width / 2;
		int padding = hw / 12;
		int boardSize = hw - (2 * padding);
		int slotdx = boardSize / info.BOARD_WIDTH;;
		int slotdy = slotdx;
		
		//int minBoxSize = width < height ? width : height;
		//int minBoxSize = boardSize / info.BOARD_WIDTH;
		
		//board dimentions
		//int boardWidth = info.BOARD_WIDTH;
		//int boardHeight = info.BOARD_HEIGHT;
		//int maxSlots = info.BOARD_WIDTH > info.BOARD_HEIGHT ? info.BOARD_WIDTH : info.BOARD_HEIGHT;
		// min box size, x and y
		//slotSize = minBoxSize / maxSlots;
		
		
		Paint paint = new Paint();
		
		paint.setColor(Color.rgb(204, 204, 204));
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(1);
		canvas.drawRect(0, 0, width, height, paint);
		
		for(int i = 0; i < info.BOARD_WIDTH; i++)
		{
			for(int j = 0; j < info.BOARD_HEIGHT; j++)
			{
				int x = padding + (i * slotdx);
				int y = padding + (j * slotdy);
				
				int type = info.getSlotInfo(i, j);
				
				switch(type)
				{
				case Main.EMPTY_CELL:
					paint.setColor(Color.YELLOW);
					break;
				case Main.P1:
					paint.setColor(Color.RED);
					break;
				case Main.P2:
					paint.setColor(Color.BLUE);
					break;
				}
				
				//draw squares
				paint.setStyle(Paint.Style.FILL_AND_STROKE);
				canvas.drawRect(x, y, x + slotdx, y + slotdy, paint);
				
				paint.setStyle(Paint.Style.STROKE);
				paint.setColor(Color.WHITE);
				canvas.drawRect(x, y, x + slotdx, y + slotdy, paint);
			}
		}
		
		String text;
		
		paint.setStyle(Paint.Style.FILL);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(50);
		paint.setColor(Color.BLACK);
		
		text = info.getWhosTurn() == info.player1.getCell() ? info.player1.getName() : info.player2.getName(); //display player turn.
		
		text += "'s turn";
		canvas.drawText(text, hw + (hw / 2), (padding * 2) - paint.getFontMetrics().ascent, paint);
		
//		byte winner = info.GetWinner();
//		
//		if(winner == info.P1_CELL)
//			canvas.drawText("Player 1 Wins", 100, 100, paint);
//		else if(winner == info.P2_CELL)
//			canvas.drawText("Player 2 Wins", 100, 100, paint);
		
		return;
	}
	
	//override from base class
//	public synchronized boolean onTouchEvent(MotionEvent e)
//	{
//		int action = (e.getAction() & MotionEvent.ACTION_MASK);
//		
//		if(action == MotionEvent.ACTION_UP)
//		{
//			//get x and y Coordinate of the touch event
//			int actionSlotX = (int) e.getX() / slotSize;
//			int actionSlotY = (int) e.getY();
//			
//			if(actionSlotX < info.BOARD_WIDTH && (actionSlotY < slotSize * info.BOARD_HEIGHT))
//			{
//				//initiate the drop counter on that slot.
//				info.DropCounter(actionSlotX);
//				//redraw screen
//				invalidate();
//			}		
//		}
//		
//		return true;
//	}
}

package mmt.connect4;

import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.FontMetrics;
import android.view.MotionEvent;
import android.view.View;

public class Display extends View {
	private GameInfo gameinfo;
	private Bitmap counters[], boardslot;

	// Globals used to remember where and how big the board is along with slot
	// sizes
	private int boardwidth, padding, slotdx, slotdy;

	private static final int DROP_TIMER = 1;
	private long timerstart;
	private Timer timer;
	private int dropcol, droprow, dropcounter;

	// variables for OnDraw - better not to create objects for each call
	private Paint paint;
	private Rect dstrect;

	public Display(Context context) {
		super(context);

		// Create our GameInfo object that will hold the game logic and
		// structure
		gameinfo = new GameInfo();

		// Create various other objects and initialise variables
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);

		timer = null;

		counters = new Bitmap[2];
		counters[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.red, null);
		counters[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.green, null);
		boardslot = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardslot, null);

		dstrect = new Rect();

	}

	// override method which is called when the screen needs redrawing
	public void onDraw(Canvas canvas) {
		// Gets the pixel width and height of the screen space we can draw to
		int width = getWidth();
		int height = getHeight();

		int hw = width / 2;
		int qw = hw / 4;
		padding = hw / 12;
		boardwidth = hw + qw - padding;
		slotdx = boardwidth / GameInfo.SLOTS_ACROSS;
		slotdy = slotdx;

		// draw the board in the top left square of the screen so determine what
		// size that square is
		// int minsize=(width<height?width:height);

		// Set the paint properities to be used while drawing
		// stuff like colour, pen width, etc
		paint.setColor(0xFFF2429D);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(1);

		// draw a rectangle to the drawing area using the above paint properties
		// (colour) that is as big as the screen
		canvas.drawRect(0, 0, width, height, paint);

		// what are the maximum number of playable slots in either direction
		// that need to be drawn
		// int
		// maxslots=(GameInfo.SLOTS_ACROSS>GameInfo.SLOTS_DOWN?GameInfo.SLOTS_ACROSS:GameInfo.SLOTS_DOWN);

		// the size of each slot is defined as the pixel width/height of our
		// drawable board portion of the screen
		// divided by the number of slots we need to draw
		// slotsize=minsize/maxslots;

		// Draw a background board colour
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(padding, padding, padding + GameInfo.SLOTS_ACROSS
				* slotdx, padding + GameInfo.SLOTS_DOWN * slotdy, paint);

		paint.setStyle(Paint.Style.FILL_AND_STROKE);

		if (timer != null) {
			// Draw the falling counter
			long dt = System.currentTimeMillis() - timerstart;

			int pos = (int) (-slotdx + (slotdy * dt) / 150);
			int landing = padding + droprow * slotdy;

			if (pos > landing) {
				timer.Kill();
				timer = null;
			} else {
				dstrect.left = padding + dropcol * slotdx;
				dstrect.top = pos;

				dstrect.right = dstrect.left + slotdx;
				dstrect.bottom = dstrect.top + slotdy;

				canvas.drawBitmap(dropcounter == GameInfo.Player1.GetCell() ? counters[0]
						: counters[1], null, dstrect, paint);
			}
		}

		// Loop around the 2D grid structure and draw a slot for each position
		for (int i = 0; i < GameInfo.SLOTS_ACROSS; i++) {
			for (int j = 0; j < GameInfo.SLOTS_DOWN; j++) {
				// Calculate the x and y coordinate of the top-left corner of
				// the (i, j)th slot
				dstrect.left = padding + i * slotdx;// slotsize;
				dstrect.top = padding + j * slotdy;// slotsize;

				dstrect.right = dstrect.left + slotdx;
				dstrect.bottom = dstrect.top + slotdy;

				if (timer == null || i != dropcol || j != droprow) {
					// what colour does it need to be based on the "counter" in
					// that position
					Bitmap counter = null;// boardslot;
					int type = gameinfo.GetSlotInfo(i, j);
					if (type == GameInfo.Player1.GetCell())
						counter = counters[0];
					else if (type == GameInfo.Player2.GetCell())
						counter = counters[1];

					if (counter != null)
						canvas.drawBitmap(counter, null, dstrect, paint);
				}
				canvas.drawBitmap(boardslot, null, dstrect, paint);
				/*
				 * // Draw a solid square using the current paint properties
				 * paint.setStyle(Paint.Style.FILL_AND_STROKE);
				 * canvas.drawRect(x, y, x+slotdx, y+slotdy, paint);
				 * 
				 * // Draw an outline (Stroke) around the same square to
				 * highlight the grid paint.setStyle(Paint.Style.STROKE);
				 * paint.setColor(Color.BLACK); canvas.drawRect(x, y, x+slotdx,
				 * y+slotdy, paint);
				 */
			}
		}

		// Display the game info screen
		String text = (gameinfo.GetWhoseTurn() == GameInfo.Player1.GetCell() ? GameInfo.Player1.GetName(): GameInfo.Player2.GetName());

		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setColor(Color.BLACK);
		paint.setTextSize(hw / 10);
		paint.setTextAlign(Paint.Align.LEFT);

		FontMetrics metrics = paint.getFontMetrics();
		int fontheight = (int) (metrics.descent - metrics.ascent);

		int x = 2 * padding + boardwidth;
		int y = padding - (int) metrics.ascent;
		canvas.drawText("Turn", x, y, paint);
		y += fontheight;
		canvas.drawText(text, x, y, paint);

		byte gameState = gameinfo.GetGameState();
		byte state = (byte) (gameState & GameInfo.GAMESTATEMASK);
		if (gameState == GameInfo.Player1.GetCell())
			canvas.drawText("Player 1 wins", width / 2, height / 2, paint);
		else if (gameState == GameInfo.Player2.GetCell())
			canvas.drawText("Player 2 wins", width / 2, height / 2, paint);
		else if (state == GameInfo)
	}

	// Deal with touch events to place the counter
	// a counter is dropped into place by pressing anywhere within the column
	public synchronized boolean onTouchEvent(MotionEvent e) {
		int action = (e.getAction() & MotionEvent.ACTION_MASK);

		// Only respond to actions when the user lifts off the touch screen
		if (timer == null && action == MotionEvent.ACTION_UP) {
			int x = (int) e.getX();
			int y = (int) e.getY();

			// reverse engineering the display code shows that for a given pixel
			// value
			int col = (x - padding) / slotdx;

			// check the touch was actually on the board area
			if (col < GameInfo.SLOTS_ACROSS && y >= padding
					&& y < padding + slotdy * GameInfo.SLOTS_DOWN) {
				// ask the game class to drop a counter in column col
				dropcounter = gameinfo.GetWhoseTurn(); // need to know this
														// beforehand for
														// animation
				droprow = gameinfo.DropCounter(col);
				if (droprow != -1) {
					dropcol = col;
					timerstart = System.currentTimeMillis();
					timer = new Timer(DROP_TIMER, 50, this);
				}

				// regardless of whether there was space, redraw the entire
				// screen
				// the game class is responsible to dealing with turns, etc so
				// all this class needs to do is keep refreshing it's view into
				// the
				// underlying data structure
				invalidate();
			}

		}

		return true;
	}

	public void OnTimerCallback(int id) {
		if (id == DROP_TIMER) {
			postInvalidate();
		}
	}
}

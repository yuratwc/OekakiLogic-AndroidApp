package bi.logic.drawing.oekakilogic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    private final int WIDTH_COUNT = 10;

    private final Paint painter;
    private final OekakiLogic logic;

    private final boolean[][] logicBuffer;

    private boolean isGameOver;

    public GameView(Context context) {
        super(context);
        painter = new Paint();
        logic = new OekakiLogic();
        logicBuffer = new boolean[WIDTH_COUNT][WIDTH_COUNT];
        logic.generateRandom();
        logic.generateNumbers();
    }

    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if(isGameOver)
            return false;

        if(evt.getAction() == MotionEvent.ACTION_DOWN) {
            int w = (getWidth() - 20) * 4 / 6;

            int x = (int)(evt.getX() - w / WIDTH_COUNT * 5) / (w / WIDTH_COUNT);
            int y = (int)(evt.getY() - w / WIDTH_COUNT * 5) / (w / WIDTH_COUNT);
            System.out.println(x + "," + y);
            if(x >= 0 && x < WIDTH_COUNT && y >= 0 && y < WIDTH_COUNT) {
                logicBuffer[y][x] = !logicBuffer[y][x];
                checkLogic();
                invalidate();
                return true;
            }
        }
        return false;
    }

    private void checkLogic() {
        boolean isEqual = true;
        for(int y = 0; y < WIDTH_COUNT; y++) {
            for(int x = 0; x < WIDTH_COUNT; x++) {
                if((logic.getAt(x, y) == 1) != logicBuffer[y][x]) {
                    isEqual = false;
                    break;
                }
            }
            if(!isEqual) break;
        }

        if(isEqual) {
            isGameOver = true;
            System.out.println("GAME END");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawCircle(10f, 10f, 10f, painter);
        int beginY = 20;
        int w = (getWidth() - 20) * 4 / 6;
        //canvas.translate(100,100);

        canvas.translate(w / WIDTH_COUNT * 5, 0);
        painter.setTextSize((float)w / WIDTH_COUNT - 2f);
        for(int x = 0; x < WIDTH_COUNT; x++) {
            int drawY = w / WIDTH_COUNT * 5 - 5;
            for(int y = WIDTH_COUNT / 2 - 1; y >= 0; y--) {
                int n = logic.getYZeroOneCount(x, y);
                if(n == 0)
                  continue;

                canvas.drawText("" + n, x * w / WIDTH_COUNT, drawY, painter);
                drawY -= w / WIDTH_COUNT;

            }
        }
        canvas.translate(-w / WIDTH_COUNT * 5,  w / WIDTH_COUNT * 6);

        for(int x = 0; x < WIDTH_COUNT; x++) {
            int drawX = w / WIDTH_COUNT * 4;
            for(int y = WIDTH_COUNT / 2 - 1; y >= 0; y--) {
                int n = logic.getXZeroOneCount(x, y);
                if(n == 0)
                    continue;

                canvas.drawText("" + n, drawX, x * w / WIDTH_COUNT, painter);
                drawX -= w / WIDTH_COUNT;

            }
        }

        canvas.translate(w / WIDTH_COUNT * 5, -w / WIDTH_COUNT);
        for(int x = 0; x < WIDTH_COUNT + 1; x++) {
            int _x = w / WIDTH_COUNT * x;
            canvas.drawLine(_x, beginY, _x, w, painter);
        }

        for(int y = 0; y < WIDTH_COUNT + 1; y++) {
            int _y = w / WIDTH_COUNT * y;
            canvas.drawLine(beginY, _y, w, _y, painter);
        }

        painter.setStyle(Paint.Style.FILL);
        for(int y = 0; y < WIDTH_COUNT; y++) {
            for(int x = 0; x < WIDTH_COUNT; x++) {
                int _x = w / WIDTH_COUNT * x;
                int _y = w / WIDTH_COUNT * y;
                if( logicBuffer[y][x]/*logic.getAt(x, y) == 1*/) {
                    canvas.drawRect(_x, _y, _x + w / WIDTH_COUNT, _y + w / WIDTH_COUNT, painter);
                }
            }
        }
        super.onDraw(canvas);
    }
}
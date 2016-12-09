package jmelab.ch.pixelpainter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jmelab.ch.pixelpainter.R;
import jmelab.ch.pixelpainter.model.Pixel;
import jmelab.ch.pixelpainter.view.listener.Square_OnTouch_Listener;

public class Square_View extends View {
    private static final Paint PAINT = new Paint();

    private int actualColor = R.color.white;
    private Pixel[][] pixelGrid;

    public int fullSquareSize;
    public int singleSquareSize;
    private RelativeLayout mainLayout;

    public void setActualColor(int color) {
        this.actualColor = color;
    }

    public int getActualColor() {
        return this.actualColor;
    }

    public void addPixelObject(Pixel pixelObject) {
        if (pixelGrid == null) {
            this.pixelGrid = new Pixel[13][13];
        }

        Log.i(getClass().toString(), "Adding pixel with color " + pixelObject.getColorAsString() + " to coordinates (" + pixelObject.getX() + "/" + pixelObject.getY() + ").");

        pixelGrid[pixelObject.getX()][pixelObject.getY()] = pixelObject;

        updateView();
    }

    public Square_View(Point screenSize, RelativeLayout superLayout, Context context) {
        super(context);

        this.fullSquareSize = calculateSquareSize(screenSize);
        this.singleSquareSize = fullSquareSize / 14;
        this.mainLayout = superLayout;

        this.setOnTouchListener(new Square_OnTouch_Listener(this));
    }

    private int calculateSquareSize(Point screenSize) {
        if (screenSize.x < screenSize.y) {
            return screenSize.x;
        }

        return screenSize.y;
    }

    private void updateView() {
        if (this.pixelGrid == null) {
            Log.e(getClass().toString(), "Pixel-Grid was not initialized correctly!");
            return;
        }

        Log.i(getClass().toString(), "Redrawing pixel-grid..");

        for (int x = 0; x < pixelGrid.length; x++) {
            for (int y = 0; y < pixelGrid[x].length; y++) {
                if (this.pixelGrid[x][y] == null) {
                    Log.w(getClass().toString(), "Pixel at (" + x + "/" + y + ") is missing!");
                } else {

                    TextView pixel = new TextView(this.getContext());
                    pixel.setHeight(singleSquareSize - 2);
                    pixel.setWidth(singleSquareSize - 2);
                    pixel.setX(singleSquareSize * pixelGrid[x][y].getX());
                    pixel.setY(singleSquareSize * pixelGrid[x][y].getY());
                    pixel.setBackgroundResource(pixelGrid[x][y].getColor());

                    mainLayout.addView(pixel);
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (int x = 0; x <= fullSquareSize; x += singleSquareSize) {
            for (int y = 0; y <= fullSquareSize; y += singleSquareSize) {
                // Vertical
                canvas.drawLine(x, 0, x, fullSquareSize, PAINT);

                // Horizontal
                canvas.drawLine(0, y, fullSquareSize, y, PAINT);
            }
        }
    }
}
package jmelab.ch.pixelpainter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jmelab.ch.pixelpainter.R;
import jmelab.ch.pixelpainter.model.Pixel;
import jmelab.ch.pixelpainter.view.listener.Square_OnTouch_Listener;

public class Square_View extends View {
    private static final Paint PAINT = new Paint();

    private int currentSelectedColor = R.color.white;
    private Pixel[][] pixelGrid;

    public int fullSquareSize;
    public int singleSquareSize;
    private RelativeLayout mainLayout;

    public void setCurrentSelectedColor(int color) {
        this.currentSelectedColor = color;
    }

    public int getCurrentSelectedColor() {
        return this.currentSelectedColor;
    }

    public void addPixelObject(Pixel pixelObject) {
        Log.i(getClass().toString(), "Adding pixel with color " + pixelObject.getColor() + " to coordinates (" + pixelObject.getX() + "/" + pixelObject.getY() + ").");

        pixelGrid[pixelObject.getX()][pixelObject.getY()] = pixelObject;

        updateView();
    }

    public Pixel[][] getPixelGrid() {
        return this.pixelGrid;
    }

    public Square_View(Point screenSize, RelativeLayout superLayout, Context context) {
        super(context);

        createDefaultPixelGrid();

        this.fullSquareSize = calculateSquareSize(screenSize);
        this.singleSquareSize = fullSquareSize / 13;
        this.mainLayout = superLayout;

        this.setOnTouchListener(new Square_OnTouch_Listener(this));
    }

    private void createDefaultPixelGrid() {
        pixelGrid = new Pixel[13][13];
        for (int x = 0; x < pixelGrid.length; x++) {
            for (int y = 0; y < pixelGrid.length; y++) {
                pixelGrid[x][y] = new Pixel(x, y, currentSelectedColor);
            }
        }
    }

    private int calculateSquareSize(Point screenSize) {
        // Convert 32dp to pixel (padding) using display metrics
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(16 * 3 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        if (screenSize.x < screenSize.y) {
            return screenSize.x - px;
        }

        return screenSize.y - px;
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
                    // -2 in size and +1 in location is to create a border
                    TextView pixel = new TextView(this.getContext());
                    pixel.setHeight(singleSquareSize - 2);
                    pixel.setWidth(singleSquareSize - 2);
                    pixel.setX(singleSquareSize * pixelGrid[x][y].getX() + 1);
                    pixel.setY(singleSquareSize * pixelGrid[x][y].getY() + 1);
                    pixel.setBackgroundResource(pixelGrid[x][y].getColor());

                    mainLayout.addView(pixel);
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (int i = 0; i <= fullSquareSize; i += singleSquareSize) {
            Log.d(getClass().toString(), "Line number: " + String.valueOf(i / singleSquareSize));

            // Vertical
            canvas.drawLine(i, 0, i, fullSquareSize, PAINT);

            // Horizontal
            canvas.drawLine(0, i, fullSquareSize, i, PAINT);
        }
    }
}
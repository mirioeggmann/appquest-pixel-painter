package jmelab.ch.pixelpainter.view.listener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import jmelab.ch.pixelpainter.model.Pixel;
import jmelab.ch.pixelpainter.view.Square_View;

public class Square_OnTouch_Listener implements View.OnTouchListener {
    private Square_View squareView;

    public Square_OnTouch_Listener(Square_View view) {
        this.squareView = view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Coordinates clickedCoordinates = new Coordinates(event);

        Log.d(getClass().toString(), "Click registered at " + clickedCoordinates.getX() + ":" + clickedCoordinates.getY());

        if (clickedCoordinates.getX() <= 12 && clickedCoordinates.getY() <= 12) {
            squareView.addPixelObject(new Pixel(clickedCoordinates.getX(), clickedCoordinates.getY(), squareView.getActualColor()));
        } else {
            Log.w(getClass().toString(), "Clicked out of range!");
        }

        return false;
    }

    private class Coordinates {
        private int x;
        private int y;

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public Coordinates(MotionEvent event) {
            this.x = (int) ((event.getX() - 16) / (squareView.fullSquareSize / 14));
            this.y = (int) ((event.getY() - 16) / (squareView.fullSquareSize / 14));
        }
    }
}
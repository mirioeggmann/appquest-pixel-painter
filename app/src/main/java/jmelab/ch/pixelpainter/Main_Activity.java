package jmelab.ch.pixelpainter;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import jmelab.ch.pixelpainter.view.Square_View;

public class Main_Activity extends AppCompatActivity {
    private static Point screenSize = new Point();
    private static RelativeLayout mainLayout;
    private static Square_View squareView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindowManager().getDefaultDisplay().getSize(screenSize);

        mainLayout = (RelativeLayout) findViewById(R.id.content_main);
        squareView = new Square_View(screenSize, mainLayout, this);
        mainLayout.addView(squareView);

        Log.d(getClass().toString(), "View successfully initialized!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.color_black:
                squareView.setActualColor(R.color.black);
                break;
            case R.id.color_blue:
                squareView.setActualColor(R.color.blue);
                break;
            case R.id.color_green:
                squareView.setActualColor(R.color.green);
                break;
            case R.id.color_grey:
                squareView.setActualColor(R.color.grey);
                break;
            case R.id.color_red:
                squareView.setActualColor(R.color.red);
                break;
            case R.id.color_white:
                squareView.setActualColor(R.color.white);
                break;
            case R.id.color_yellow:
                squareView.setActualColor(R.color.yellow);
                break;
            default:
                return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
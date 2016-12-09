package jmelab.ch.pixelpainter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import jmelab.ch.pixelpainter.utils.Logbook_Factory;
import jmelab.ch.pixelpainter.view.Square_View;

public class Main_Activity extends AppCompatActivity {
    private static Point screenSize = new Point();
    private static RelativeLayout mainLayout;
    private static Square_View squareView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ((FloatingActionButton) findViewById(R.id.save_picture)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportGridToLogbook();
            }
        });

        getWindowManager().getDefaultDisplay().getSize(screenSize);

        mainLayout = (RelativeLayout) findViewById(R.id.content_main);
        squareView = new Square_View(screenSize, mainLayout, this);
        mainLayout.addView(squareView);

        Log.d(getClass().toString(), "View successfully initialized!");
    }

    private void exportGridToLogbook() {
        Intent logbookIntent = new Logbook_Factory(squareView.getPixelGrid(), this).getLoogbookIntent();
        if (getPackageManager().queryIntentActivities(logbookIntent, PackageManager.MATCH_DEFAULT_ONLY).isEmpty()) {
            Toast.makeText(this, "Logbook app not installed", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(logbookIntent);

        Toast.makeText(this, "Data exported successfully!", Toast.LENGTH_SHORT).show();
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
                squareView.setCurrentSelectedColor(R.color.black);
                break;
            case R.id.color_blue:
                squareView.setCurrentSelectedColor(R.color.blue);
                break;
            case R.id.color_green:
                squareView.setCurrentSelectedColor(R.color.green);
                break;
            case R.id.color_grey:
                squareView.setCurrentSelectedColor(R.color.grey);
                break;
            case R.id.color_red:
                squareView.setCurrentSelectedColor(R.color.red);
                break;
            case R.id.color_white:
                squareView.setCurrentSelectedColor(R.color.white);
                break;
            case R.id.color_yellow:
                squareView.setCurrentSelectedColor(R.color.yellow);
                break;
            default:
                return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
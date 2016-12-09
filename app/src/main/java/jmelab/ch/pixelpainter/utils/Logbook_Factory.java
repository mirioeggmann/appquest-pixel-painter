package jmelab.ch.pixelpainter.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jmelab.ch.pixelpainter.model.Pixel;

/**
 * Created by User on 09.12.2016.
 */

public class Logbook_Factory {
    private Intent logbookIntent;

    public Intent getLoogbookIntent() {
        return this.logbookIntent;
    }

    public Logbook_Factory(Pixel[][] pixelGrid, Context context) {
        logbookIntent = new Intent("ch.appquest.intent.LOG");

        try {
            createLogMessage(pixelGrid);
        } catch (JSONException e) {
            Log.e(getClass().toString(), e.getLocalizedMessage());
            Toast.makeText(context, "Failed to create Logbook message (" + e.getClass().toString() + "): " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        Log.i(getClass().toString(), "Successfully created log intent.");
    }

    private void createLogMessage(Pixel[][] pixelGrid) throws JSONException {
        JSONObject logInformation = new JSONObject();
        logInformation.put("task", "Pixelmaler");

        JSONArray pixelArray = new JSONArray();
        for (int x = 0; x < pixelGrid.length; x++) {
            for (int y = 0; y < pixelGrid[x].length; y++) {
                JSONObject pixel = new JSONObject();
                pixel.put("y", String.valueOf(y));
                pixel.put("x", String.valueOf(x));
                pixel.put("color", pixelGrid[x][y].getColorAsString());
            }
        }
        logInformation.put("pixels", pixelArray);

        logbookIntent.putExtra("ch.appquest.logmessage", logInformation.toString());
    }
}
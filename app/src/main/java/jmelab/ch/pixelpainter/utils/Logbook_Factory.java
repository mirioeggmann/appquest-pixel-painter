package jmelab.ch.pixelpainter.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jmelab.ch.pixelpainter.R;
import jmelab.ch.pixelpainter.model.Pixel;

public class Logbook_Factory {
    private static Context context;

    private Intent logbookIntent;

    public Intent getLoogbookIntent() {
        return this.logbookIntent;
    }

    public Logbook_Factory(Pixel[][] pixelGrid, Context context) {
        this.context = context;

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
                if (!detectPixelColor(pixelGrid[x][y]).equals("#FFFFFFFF")) {
                    JSONObject pixel = new JSONObject();
                    pixel.put("y", String.valueOf(y));
                    pixel.put("x", String.valueOf(x));
                    pixel.put("color", detectPixelColor(pixelGrid[x][y]));
                    pixelArray.put(pixel);
                }
            }
        }

        Log.d(getClass().toString(), "Exporting " + pixelArray.length() + " pixels to logbook.");
        logInformation.put("pixels", pixelArray);

        Log.d(getClass().toString(), "Final message ist: " + logInformation.toString());
        logbookIntent.putExtra("ch.appquest.logmessage", logInformation.toString());
    }

    private String detectPixelColor(Pixel pixel) {
        switch (pixel.getColor()) {
            case 2131427338:
                return context.getResources().getString(R.string.black_hex);
            case 2131427339:
                return context.getResources().getString(R.string.blue_hex);
            case 2131427367:
                return context.getResources().getString(R.string.green_hex);
            case 2131427368:
                return context.getResources().getString(R.string.grey_hex);
            case 2131427393:
                return context.getResources().getString(R.string.red_hex);
            case 2131427405:
                return context.getResources().getString(R.string.yellow_hex);
            default:
                return context.getResources().getString(R.string.white_hext);
        }
    }
}
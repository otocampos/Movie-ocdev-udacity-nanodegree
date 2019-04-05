package ocdev.com.br.moview.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by Oto on 29/11/2017.
 */

public class NetworkUtils {
    private static final String apikey="xxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static final String key ="api_key";
    private static final String URL_BASE="http://api.themoviedb.org/3/movie/";



    public static URL buildUrl(String Moviequery) {
        Uri builtUri = Uri.parse(URL_BASE).buildUpon()
                .appendPath(Moviequery)
                .appendQueryParameter(key,apikey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}

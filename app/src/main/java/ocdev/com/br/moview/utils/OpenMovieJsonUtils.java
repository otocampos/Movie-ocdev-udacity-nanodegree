/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ocdev.com.br.moview.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class OpenMovieJsonUtils {


    public static String[] getSimpleMovieStringsFromJson(Context context, String moviesJsonStr)
            throws JSONException {





        String[] parsedMovieData = null;

        JSONObject MovieJson = new JSONObject(moviesJsonStr);



        JSONArray MovieArray = MovieJson.getJSONArray("results");

        parsedMovieData = new String[MovieArray.length()];



        for (int i = 0; i < MovieArray.length(); i++) {

            JSONObject c = MovieArray.getJSONObject(i);
            String imgurl = c.getString("poster_path");

            parsedMovieData[i] =   imgurl;


        }

        return parsedMovieData;
    }

}
package com.ubits.payflow.payflow_network;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.widget.EditText;

import com.ubits.payflow.payflow_network.model.PlaceAutocomplete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vikas on 8/7/2018.
 */

public class Http {
    static String placeApiUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json";

    public static List<PlaceAutocomplete> getPrdications(Context context, String query) {
        List<PlaceAutocomplete> autocompletes = new ArrayList<>();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            query = query.replaceAll(" ", "+");
            String httpUrl = placeApiUrl + "?key=" + context.getString(R.string.google_api_ley) + "&query=" + query;
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJSON = buffer.toString();
            JSONObject parentObject = new JSONObject(finalJSON);
            JSONArray array = parentObject.optJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.optJSONObject(i);
                PlaceAutocomplete autocomplete = new PlaceAutocomplete();
                autocomplete.setDescription(object.optString("formatted_address"));

                JSONObject geometryObject = object.optJSONObject("geometry");
                JSONObject locationObject = geometryObject.optJSONObject("location");
                autocomplete.setLat(locationObject.optDouble("lat"));
                autocomplete.setLng(locationObject.optDouble("lng"));
                autocompletes.add(autocomplete);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return autocompletes;
    }

    public static void get(final String httpUrl, final IOnResultLisnter onResultLisnter) {
        new AsyncTask<Void, Void, Void>() {
            private String result = null;

            @Override
            protected Void doInBackground(Void... voids) {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(httpUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    result = buffer.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (onResultLisnter != null) {
                    onResultLisnter.onResult(result);
                }
            }
        }.execute();
    }

    public static void post(final String url, final String params, final IOnResultLisnter onResultLisnter) {
        new AsyncTask<Void, Void, Void>() {
            private String result;

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL u = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
                    dataOutputStream.write(params.getBytes("UTF-8"));
                    try {
                        dataOutputStream.flush();
                        dataOutputStream.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    result = convertToResult(in);
                    in.close();
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (onResultLisnter != null) {
                    onResultLisnter.onResult(result);
                }
            }
        }.execute();
    }

    private static String convertToResult(InputStream it) throws Exception {
        InputStreamReader read = new InputStreamReader(it);
        BufferedReader buff = new BufferedReader(read);
        StringBuilder dta = new StringBuilder();
        String chunks;
        while ((chunks = buff.readLine()) != null) {
            dta.append(chunks);
        }
        return dta.toString();
    }

    public static void findAddress(final Context context, final PlaceAutocomplete place, final EditText txtSuburbs, final EditText etCityName, final EditText txtPostalCode) {
        new AsyncTask<Void, Void, Void>() {
            private ProgressDialog mDialog;
            private Geocoder mGeocoder;
            private List<Address> addresses;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mGeocoder = new Geocoder(context, Locale.getDefault());
                mDialog = ProgressDialog.show(context, "", "Loading..");
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    addresses = mGeocoder.getFromLocation(place.getLat(), place.getLng(), 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    if (addresses != null && addresses.size() > 0) {
                        txtSuburbs.setText(addresses.get(0).getLocality());
                        etCityName.setText(addresses.get(0).getLocality());
                        txtPostalCode.setText(addresses.get(0).getPostalCode());
                    }
                } catch (Exception e) {

                }
                mDialog.dismiss();
            }
        }.execute();
    }

    public interface IOnResultLisnter {

        void onResult(String result);
    }
}

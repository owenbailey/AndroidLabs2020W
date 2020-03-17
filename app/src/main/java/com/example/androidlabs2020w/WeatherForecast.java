package com.example.androidlabs2020w;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class WeatherForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


//        ProgressBar progressBar = new ProgressBar(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);

    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        public String uv;
        public String min;
        public String max;
        public String currentTemp;
        public Bitmap bitMapCurrentWeather;
        String iconName;
        public Bitmap image;




        protected String doInBackground(String... args){
//            try {
//
//                //create a URL object of what server to contact:
//                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");//args[0]);
//
//                //open the connection
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                //wait for data:
//                InputStream response = urlConnection.getInputStream();
//
//
//
//                //From part 3: slide 19
//                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//                factory.setNamespaceAware(false);
//                XmlPullParser xpp = factory.newPullParser();
//                xpp.setInput( response  , "UTF-8");
//
//
//                //From part 3, slide 20
//                String parameter = null;
//
//                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT
//
//                while(eventType != XmlPullParser.END_DOCUMENT)
//                {
//
//                    if(eventType == XmlPullParser.START_TAG)
//                    {
//                        //If you get here, then you are pointing at a start tag
//                        if(xpp.getName().equals("temperature"))
//                        {
//                            //If you get here, then you are pointing to a <Weather> start tag
//                            min = xpp.getAttributeValue(null,    "min");
//                            Log.i("Weather Query AsyncTask", "Found parameter temperature: " + min);
//                            publishProgress(25);
//                            max = xpp.getAttributeValue(null, "max");
//                            Log.i("Weather Query AsyncTask", "Found parameter temperature: " + max);
//                            publishProgress(50);
//                            currentTemp = xpp.getAttributeValue(null,    "value");
//                            Log.i("Weather Query AsyncTask", "Found parameter temperature: " + currentTemp);
//                            publishProgress(75);
//
//                        }
//
//                        else if(xpp.getName().equals("AMessage"))
//                        {
//                            parameter = xpp.getAttributeValue(null, "message"); // this will run for <AMessage message="parameter" >
//                        }
//                        else if(xpp.getName().equals("weather"))
//                        {
//                            iconName = xpp.getAttributeValue(null, "icon"); //this will run for <Weather outlook="parameter"
//                            parameter = xpp.getAttributeValue(null, "windy"); //this will run for <Weather windy="paramter"  >
//                        }
//                        else
//                        {
//                            String weatherURL = "http://openweathermap.org/img/w/" + iconName + ".png";
//                            URL urlWeather = new URL(weatherURL);
//                            URLConnection connection = (HttpURLConnection) urlWeather.openConnection();
//                            connection.connect();
//                            int responseCode = ((HttpURLConnection) connection).getResponseCode();
//                            if (responseCode == 200) {
//                                image = BitmapFactory.decodeStream(connection.getInputStream());
//                                FileOutputStream outputStream = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
//                                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
//                                outputStream.flush();
//                                outputStream.close();
//
//                            }
//                                publishProgress(100);
//
//
////                            xpp.next(); //move the pointer from the opening tag to the TEXT event
////                            parameter = xpp.getText(); // this will return  20
//                        }
//                    }
//                    eventType = xpp.next(); //move to the next xml event and store it in a variable
//                }
//
//
//
//
//            }catch(Exception e){
//                {
//                    Log.e("Error", e.getMessage());
//                }
//
//            }
//            return "Done";
//
//        }

            String ret = null;
            String queryURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";

            try {       // Connect to the server:
                URL url = new URL(queryURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                //Set up the XML parser:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( inStream  , "UTF-8");

                //Iterate over the XML tags:
                int EVENT_TYPE;         //While not the end of the document:
                while((EVENT_TYPE = xpp.getEventType()) != XmlPullParser.END_DOCUMENT)
                {
                    switch(EVENT_TYPE)
                    {
                        case START_TAG:         //This is a start tag < ... >
                            String tagName = xpp.getName(); // What kind of tag?
                            if(tagName.equals("temperature"))
                            {
                                min = xpp.getAttributeValue(null,    "min");
                                Log.i("Weather Query AsyncTask", "Found parameter temperature: " + min);
                                publishProgress(25);

                                max = xpp.getAttributeValue(null, "max");
                                Log.i("Weather Query AsyncTask", "Found parameter temperature: " + max);
                                publishProgress(50);

                                currentTemp = xpp.getAttributeValue(null, "value");
                                Log.i("Weather Query AsyncTask", "Found parameter temperature: " + currentTemp);
                                publishProgress(75);

                            }
                            break;
                        case END_TAG:           //This is an end tag: </ ... >
                            break;
                        case TEXT:              //This is text between tags < ... > Hello world </ ... >
                            break;
                    }
                    xpp.next(); // move the pointer to next XML element
                }
            }
            catch(MalformedURLException mfe){ ret = "Malformed URL exception"; }
            catch(IOException ioe)          { ret = "IO Exception. Is the Wifi connected?";}
            catch(XmlPullParserException pe){ ret = "XML Pull exception. The XML is not properly formed" ;}
            //What is returned here will be passed as a parameter to onPostExecute:
            return ret;
        }

        public void onProgressUpdate(Integer ... args)
        {
//            int i = 0;
//            ProgressBar progressBar = new ProgressBar(R.id.progressBar);
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(i);

        }

        protected void onPostExecute(String fromDoInBackground) {
            super.onPostExecute(fromDoInBackground);
            TextView tvMin = findViewById(R.id.textMinTemp);
            tvMin.setText("Min Temp: \t" + min);


            TextView tvMax = findViewById(R.id.textMaxTemp);
            tvMax.setText("Max Temp: \t" + max);

            TextView tvCurrent = findViewById(R.id.textCurrentTemp);
            tvCurrent.setText("Current Temp: \t" + currentTemp);

//            ProgressBar progressBar = new ProgressBar(R.id.progressBar);
//            progressBar.setVisibility(View.INVISIBLE);
        }

//        public boolean fileExistance(){
//            String name;
//            File file = getBaseContext().getFileStreamPath(name);
//            return file.exists();
//
//            FileInputStream fis = null;
//            try{
//                fis =openFileInput(imagefile);
//            }catch(FileNotFoundException e){
//                e.printStackTrace();
//            }
//            Bitmap bm = BitmapFactory.decodeStream(fis);
//        }
        
    }
}


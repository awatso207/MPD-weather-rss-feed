// Alexander Watson - S1623408
package gcu.mpd.bbcweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    WeatherInfo info;
    Location location = Location.GLASGOW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new RetrieveWeatherData().execute();

        // Dropdown with location options
        Spinner dropdown = findViewById(R.id.locations);
        String[] items = new String[]{"Glasgow", "London", "New York", "Oman", "Mauritius", "Bangladesh"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        location = Location.GLASGOW;
                        break;
                    case 1:
                        location = Location.LONDON;
                        break;
                    case 2:
                        location = Location.NEWYORK;
                        break;
                    case 3:
                        location = Location.OMAN;
                        break;
                    case 4:
                        location = Location.MAURITIUS;
                        break;
                    case 5:
                        location = Location.BANGLADESH;
                        break;
                }
                        new RetrieveWeatherData().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can not be empty as location is set with default.
            }
        });
    }

    class RetrieveWeatherData extends AsyncTask<Object,Void, ArrayAdapter>
    {
        @Override
        protected ArrayAdapter doInBackground(Object[] params)
        {
            info = new WeatherInfo();
            try
            {
                System.out.println("Called doInBackground");
                URL url = new URL(Location.getURL(location));
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                boolean insideChannel = false;
                boolean insideImage = false;

                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        System.out.println(xpp.getName());
                        if (xpp.getName().equalsIgnoreCase("channel"))
                        {
                            insideChannel = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("image"))
                        {
                            insideImage = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title" ) && !insideItem) {
                            if(insideChannel && !insideImage ) {

                                info.setTitle(xpp.nextText().replace("BBC Weather - ", ""));

                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title") && insideItem)
                        {
                            if(info.today == null) {
                                info.setToday(xpp.nextText().split(",")[0]);
                            } else if(info.tomorrow == null ) {
                                info.setTomorrow(xpp.nextText().split(",")[0]);
                            } else if(info.dayAfterTomorrow == null) {
                                info.setDayAfterTomorrow(xpp.nextText().split(",")[0]);
                            }

                        } else if (xpp.getName().equalsIgnoreCase("description") && insideItem) {
                            if (info.todayDetails == null) {
                                info.setTodayDetails(xpp.nextText());
                            } else if (info.tomorrowDetails == null) {
                                info.setTomorrowDetails(xpp.nextText());
                            } else if (info.dayAfterTomorrowDetails == null) {
                                info.setDayAfterTomorrowDetails(xpp.nextText());
                            }

                        }
                        else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                            if (insideChannel && !insideItem)
                                info.setPubDate(xpp.nextText());
                        }
                    }
                    else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem=false;
                    }
                    else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("image"))
                    {
                        insideImage=false;
                    }
                    else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("channel"))
                    {
                        insideChannel=false;
                    }
                    eventType = xpp.next();
                }

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (XmlPullParserException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ArrayAdapter adapter)
        {
            System.out.println(info.toString());

            // title
            TextView title = findViewById(R.id.title);
            title.setText(info.title);

            // pub date
            TextView pubDate = findViewById(R.id.pubDate);
            pubDate.setText(info.pubDate);

            //today
            TextView today = findViewById(R.id.today);
            today.setText(info.today);
            TextView todayDetails = findViewById(R.id.todayDetails);
            todayDetails.setText(info.todayDetails);

            //tomorrow
            TextView tomorrow = findViewById(R.id.tomorrow);
            tomorrow.setText(info.tomorrow);
            TextView tomorrowDetails = findViewById(R.id.tomorrowDetails);
            tomorrowDetails.setText(info.tomorrowDetails);

            //dayAfterTomorrow
            TextView dayAfterTomorrow = findViewById(R.id.dayAfterTomorrow);
            dayAfterTomorrow.setText(info.dayAfterTomorrow);
            TextView dayAfterTomorrowDetails = findViewById(R.id.dayAfterTomorrowDetails);
            dayAfterTomorrowDetails.setText(info.dayAfterTomorrowDetails);
        }
    }

    public InputStream getInputStream(URL url)
    {
        try
        {
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }


}
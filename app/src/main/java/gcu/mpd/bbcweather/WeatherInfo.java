// Alexander Watson - S1623408
package gcu.mpd.bbcweather;

public class WeatherInfo {

    public String title;
    public String pubDate;
    public String today;
    public String todayDetails;
    public String tomorrow;
    public String tomorrowDetails;
    public String dayAfterTomorrow;
    public String dayAfterTomorrowDetails;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public void setTodayDetails(String todayDetails) {
        this.todayDetails = todayDetails;
    }

    public void setTomorrow(String tomorrow) {
        this.tomorrow = tomorrow;
    }

    public void setTomorrowDetails(String tomorrowDetails) {
        this.tomorrowDetails = tomorrowDetails;
    }

    public void setDayAfterTomorrow(String dayAfterTomorrow) {
        this.dayAfterTomorrow = dayAfterTomorrow;
    }

    public void setDayAfterTomorrowDetails(String dayAfterTomorrowDetails) {
        this.dayAfterTomorrowDetails = dayAfterTomorrowDetails;
    }

    @Override
    public String toString() {
        return title + ", " + pubDate + ", " + today + ", " + todayDetails + "," + tomorrow + ", " + tomorrowDetails + ", " + dayAfterTomorrow + "," + dayAfterTomorrowDetails;
    }
}

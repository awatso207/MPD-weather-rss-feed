// Alexander Watson - S1623408
package gcu.mpd.bbcweather;

public enum Location {
    GLASGOW, LONDON, NEWYORK, OMAN, MAURITIUS, BANGLADESH;

    public static String getURL(Location location) {
        switch (location) {
            case GLASGOW:
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";
            case LONDON:
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743";
            case NEWYORK:
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581";
            case OMAN:
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286";
            case MAURITIUS:
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154";
            case BANGLADESH:
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241";
            default: return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";
        }
    }
}

package services;


import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * Class for finding address from latitude and longitude using Google Maps GeoCoding API.
 */
public class LocationDecoder {

    private static volatile LocationDecoder instance;
    private static volatile GeoApiContext api;
    private static final String LOGTAG = "Telegraph Account: ";

    private LocationDecoder() {
        api = new GeoApiContext().setApiKey(BotConfig.GEO_API);
    }

    public static LocationDecoder getInstance() {
        final LocationDecoder currentInstance;
        if (instance == null) {
            synchronized (LocationDecoder.class) {
                if (instance == null) {
                    instance = new LocationDecoder();
                }
                currentInstance = instance;
            }
        } else {
            currentInstance = instance;
        }
        return currentInstance;
    }

    //Copyright: https://stackoverflow.com/questions/39633328/google-reverse-geo-coding-to-get-address-from-latitude-and-longitude
    public static String getAddress(Float lat, Float lng) {
        try {
            GeocodingResult[] gResp = GeocodingApi.newRequest(getInstance().api).latlng(new LatLng(lat, lng)).await();
            return gResp[0].formattedAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.EMPTY_LINE;
    }
}

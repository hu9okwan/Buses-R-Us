package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A parser for the data returned by Translink stops query
 */
public class StopParser {

    private String filename;

    public StopParser(String filename) {
        this.filename = filename;
    }
    /**
     * Parse stop data from the file and add all stops to stop manager.
     *
     */
    public void parse() throws IOException, StopDataMissingException, JSONException{
        DataProvider dataProvider = new FileDataProvider(filename);

        parseStops(dataProvider.dataSourceToString());
    }

    /**
     * Parse stop information from JSON response produced by Translink.
     * Stores all stops and routes found in the StopManager and RouteManager.
     *
     * @param  jsonResponse    string encoding JSON data to be parsed
     * @throws JSONException when
     *     JSON data does not have expected format
     *     JSON data is not an array
     * @throws StopDataMissingException when
     *     JSON data is missing Name, StopNo, Routes or location (Latitude or Longitude) elements for any stop
     */

    public void parseStops(String jsonResponse)
            throws JSONException, StopDataMissingException {


        JSONArray stops = new JSONArray(jsonResponse);


        for (int i = 0; i < stops.length(); i++) {

            JSONObject stop = stops.getJSONObject(i);


            if (!(stop.has("StopNo")) || !(stop.has("Name")) || !(stop.has("Latitude")) || !(stop.has("Longitude")) || !(stop.has("Routes")))
                throw new StopDataMissingException();



            String stopName = stop.getString("Name");
            double lat = stop.getDouble("Latitude");
            double lon = stop.getDouble("Longitude");
            Integer stopNumber = stop.getInt("StopNo");

            LatLon latLon = new LatLon(lat, lon);

           // Stop s = new Stop(stopNumber, stopName, latLon);

            Stop s = StopManager.getInstance().getStopWithId(stopNumber, stopName, latLon);




            String route = stop.getString("Routes");
            if (route.contains(", ")){

                String[] routes = route.split(", ");

                for (int j = 0; j < routes.length; j++){

                    // Route r = new Route(routes[j]);

                    Route r = RouteManager.getInstance().getRouteWithNumber(routes[j]);

                    r.addStop(s);

                   //  RouteManager.getInstance().getRouteWithNumber(routes[j]);


                }

            }
            else {

                //Route r = new Route(route);
                Route r = RouteManager.getInstance().getRouteWithNumber(route);
                r.addStop(s);

               // RouteManager.getInstance().getRouteWithNumber(route);


            }

           //  StopManager.getInstance().getStopWithId(stopNumber, stopName, latLon);



        }
    }
}

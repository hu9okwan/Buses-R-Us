package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A parser for the data returned by the Translink arrivals at a stop query
 */
public class ArrivalsParser {

    /**
     * Parse arrivals from JSON response produced by TransLink query.  All parsed arrivals are
     * added to the given stop assuming that corresponding JSON object has a RouteNo: and an
     * array of Schedules:
     * Each schedule must have an ExpectedCountdown, ScheduleStatus, and Destination.  If
     * any of the aforementioned elements is missing, the arrival is not added to the stop.
     *
     * @param stop             stop to which parsed arrivals are to be added
     * @param jsonResponse    the JSON response produced by Translink
     * @throws JSONException  when JSON response does not have expected format
     * @throws ArrivalsDataMissingException  when no arrivals are found in the reply
     */
    public static void parseArrivals(Stop stop, String jsonResponse)
            throws JSONException, ArrivalsDataMissingException {


        JSONArray arrivals = new JSONArray(jsonResponse);

        for (int i = 0; i < arrivals.length(); i++) {

            JSONObject route = arrivals.getJSONObject(i);


            if (!(route.has("RouteNo")) || !(route.has("RouteName")))
                throw new ArrivalsDataMissingException();



            String routeNo = route.getString("RouteNo");
            String routeName = route.getString("RouteName");
            String direction = route.getString("Direction");



            // Route r = new Route(routeNo);
            // r.setName(routeName);
            Route r = RouteManager.getInstance().getRouteWithNumber(routeNo, routeName);


            JSONArray schedules = route.getJSONArray("Schedules");

            int numErrors = 0;

            for (int j = 0; j < schedules.length(); j++) {

                JSONObject schedule = schedules.getJSONObject(j);



                if (!(schedule.has("ExpectedCountdown")) || !(schedule.has("Destination")) || !(schedule.has("ScheduleStatus"))){

                    numErrors = numErrors + 1;
                    continue;
                }

                Integer expectedCountDown = schedule.getInt("ExpectedCountdown");
                String destination = schedule.getString("Destination");
                String scheduleStatus = schedule.getString("ScheduleStatus");
                String patternName = schedule.getString("Pattern");

                Arrival arrival = new Arrival(expectedCountDown, destination, r);
                arrival.setStatus(scheduleStatus);

                r.getPattern(patternName, destination, direction);

                stop.addArrival(arrival);

            }


            if (numErrors == schedules.length())
                throw new ArrivalsDataMissingException();


        }

    }

}

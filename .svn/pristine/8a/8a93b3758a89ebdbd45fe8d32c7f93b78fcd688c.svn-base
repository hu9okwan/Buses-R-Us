package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser for routes stored in a compact format in a txt file
 */
public class RouteMapParser {
    private String fileName;

    public RouteMapParser(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Parse the route map txt file
     */
    public void parse() {
        DataProvider dataProvider = new FileDataProvider(fileName);
        try {
            String c = dataProvider.dataSourceToString();
            if (!c.equals("")) {
                int posn = 0;
                while (posn < c.length()) {
                    int endposn = c.indexOf('\n', posn);
                    String line = c.substring(posn, endposn);
                    parseOnePattern(line);
                    posn = endposn + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse one route pattern, adding it to the route that is named within it
     * @param str
     */
    private void parseOnePattern(String str) {


        List<LatLon> elements = new ArrayList<LatLon>();

        //split semicolons
        String[] splitSemicolon = str.split(";");

        //split N and etc in index 0, ex: N43-ABC to N 43-ABC
        String removeN = splitSemicolon[0].substring(1);

        //split number and name in removeN, ex: 43-ABC to 43 ABC
        String[] splitDash = removeN.split("-");

        //index 0 in splitDash is number, index 1 is name
        String routeNumber = splitDash[0];
        String patternName = splitDash[1];


        // if (splitSemicolon[1].equals(""))


        //for every 2 indexes, create a new latLon and add it to elements list
        for (int i = 1; i < splitSemicolon.length; i++)
        {
            double latToD = Double.parseDouble(splitSemicolon[i]);
            double lonToD = Double.parseDouble(splitSemicolon[i+=1]);

            LatLon latLon = new LatLon(latToD, lonToD);

            // if route does not contain any lat lons
            if (splitSemicolon[i].equals(""))
                break;

            elements.add(latLon);



        }


        storeRouteMap(routeNumber, patternName, elements);




    }

    /**
     * Store the parsed pattern into the named route
     * Your parser should call this method to insert each route pattern into the corresponding route object
     * There should be no need to change this method
     *
     * @param routeNumber       the number of the route
     * @param patternName       the name of the pattern
     * @param elements          the coordinate list of the pattern
     */
    private void storeRouteMap(String routeNumber, String patternName, List<LatLon> elements) {
        Route r = RouteManager.getInstance().getRouteWithNumber(routeNumber);
        RoutePattern rp = r.getPattern(patternName);
        rp.setPath(elements);
    }
}

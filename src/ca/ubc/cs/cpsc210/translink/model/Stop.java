package ca.ubc.cs.cpsc210.translink.model;

import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.util.*;

/**
 * Represents a bus stop with an number, name, location (lat/lon)
 * set of routes which stop at this stop and a list of arrivals.
 */

public class Stop implements Iterable<Arrival> {
    private List<Arrival> arrivals;

    private Set<Route> routes;

    private int number;
    private String name;
    private LatLon locn;


    /**
     * Constructs a stop with given number, name and location.
     * Set of routes and list of arrivals are empty.
     *
     * @param number the number of this stop
     * @param name   name of this stop
     * @param locn   location of this stop
     */
    public Stop(int number, String name, LatLon locn) {

        this.number = number;
        this.name = name;
        this.locn = locn;

        arrivals = new ArrayList<Arrival>();
        routes = new HashSet<Route>();


    }

    /**
     * getter for name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for locn
     *
     * @return the location
     */
    public LatLon getLocn() {
        return locn;
    }

    /**
     * getter for number
     *
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * getter for set of routes
     *
     * @return the set of routes using this stop
     */
    public Set<Route> getRoutes() {
        return Collections.unmodifiableSet(routes);
    }

    /**
     * Add route to set of routes with stops at this stop.
     *
     * @param route the route to add
     */
    public void addRoute(Route route) {


        if (!(route.getStops().contains(this)))
            route.addStop(this);

        routes.add(route);

    }

    /**
     * Remove route from set of routes with stops at this stop
     *
     * @param route the route to remove
     */
    public void removeRoute(Route route) {

        if (route.getStops().contains(this))
            route.removeStop(this);

        routes.remove(route);

    }

    /**
     * Determine if this stop is on a given route
     *
     * @param route the route
     * @return true if this stop is on given route
     */
    public boolean onRoute(Route route) {
        if (routes.contains(route))
            return true;
        return false;
    }

    /**
     * Add bus arrival travelling on a particular route at this stop.
     * Arrivals are to be sorted in order by arrival time
     *
     * @param arrival the bus arrival to add to stop
     */
    public void addArrival(Arrival arrival) {

        arrivals.add(arrival);

        int nextIndex = 1;

        if (arrivals.size() != 1) {


            for (Arrival a : arrivals) {

                if (arrivals.size() == nextIndex)
                    break;

                Arrival nextArrival = arrivals.get(nextIndex);

                if (a.compareTo(nextArrival) > 0) {

                    arrivals.set(arrivals.indexOf(a), nextArrival);
                    arrivals.set(nextIndex, a);

                }

                nextIndex = nextIndex + 1;
            }
        }
    }

    /**
     * Remove all arrivals from this stop
     */
    public void clearArrivals() {
        arrivals.clear();

    }

    /**
     * Two stops are equal if their ids are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stop arrivals = (Stop) o;

        return number == arrivals.number;

    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public Iterator<Arrival> iterator() {
        // Do not modify the implementation of this method!
        return arrivals.iterator();
    }

    /**
     * setter for name
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;

    }

    /**
     * setter for location
     *
     * @param locn the new location
     */
    public void setLocn(LatLon locn) {
        this.locn = locn;
    }
}
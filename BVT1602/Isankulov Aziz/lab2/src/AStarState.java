import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;

    private Map<Location, Waypoint> openWay = new HashMap<Location, Waypoint>();
    private Map<Location, Waypoint> closedWay = new HashMap<Location, Waypoint>();

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if(openWay.size() == 0)return null;

        Set oWayKey = openWay.keySet();
        Iterator i = oWayKey.iterator();
        Waypoint res = null;
        float min = Float.MAX_VALUE;
        while(i.hasNext()){
            Location loc = (Location)i.next();
            Waypoint way = openWay.get(loc);

            if(way.getTotalCost() < min){
                min = way.getTotalCost();
                res = way;
            }
        }
        return res;
    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one <em>only
     * if</em> the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        Location location = newWP.getLocation();

        if(openWay.containsKey(location)){
            Waypoint current = openWay.get(location);
            if(newWP.getPreviousCost() < current.getPreviousCost()){
                openWay.put(location, newWP);
                return true;
            }
            return false;
        }
        openWay.put(location, newWP);
        return true;
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return openWay.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint waypoint = openWay.remove(loc);
        closedWay.put(loc, waypoint);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closedWay.containsKey(loc);
    }
}


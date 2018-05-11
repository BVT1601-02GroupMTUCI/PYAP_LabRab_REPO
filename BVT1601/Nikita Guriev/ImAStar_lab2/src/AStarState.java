import java.util.*;


/**
 * This class stores the basic state necessary for the A* algorithm to compute
 * a path across a map.  This state includes a collection of "open waypoints"
 * and another collection of "closed waypoints."  In addition, this class
 * provides the basic operations that the A* pathfinding algorithm needs to
 * perform its processing.
 **/
public class AStarState
{
    /**
     * This is a reference to the map that the A* algorithm
     * is navigating.
     **/
    private Map2D map;

    private Map<Location, Waypoint> OpenWaypoints = new HashMap<> ();
    private Map<Location, Waypoint> ClosedWaypoints = new HashMap<> ();

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
     * with the minimum total cost.  If there are no open waypoints, this
     * method returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if (OpenWaypoints.size() == 0){
            return  null;
        }

        Iterator<Map.Entry<Location, Waypoint>> entries = OpenWaypoints.entrySet().iterator();
        Map.Entry<Location, Waypoint> entry = entries.next();
        float minCost = entry.getValue().getTotalCost();
        Location minKey = entry.getKey();
        while(entries.hasNext()) {
            entry = entries.next();
            if(entry.getValue().getTotalCost() < minCost) {
                minCost = entry.getValue().getTotalCost();
                minKey = entry.getKey();
            }
        }
        return OpenWaypoints.get(minKey);
    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint
     * already in) the "open waypoints" collection.  If there is not already
     * an open waypoint at the new waypoint's location then the new waypoint
     * is simply added to the collection.  However, if there is already a
     * waypoint at the new waypoint's location, the new waypoint replaces
     * the old one <em>only if</em> the new waypoint's "previous cost" value
     * is less than the current waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        if(OpenWaypoints.get(newWP.getLocation()) == null )
            OpenWaypoints.put(newWP.getLocation(), newWP);
        else {
            Waypoint oldWP;
            oldWP = OpenWaypoints.get(newWP.getLocation());
            if(newWP.getPreviousCost() < oldWP.getPreviousCost()) {
                OpenWaypoints.put(newWP.getLocation(), newWP);
                return true;
            }
        }

        return false;

    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return OpenWaypoints.size();
    }

    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint waypoint = OpenWaypoints.get(loc);
        OpenWaypoints.remove(loc);
        ClosedWaypoints.put(loc, waypoint);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        if(ClosedWaypoints.get(loc) != null)
            return true;

        return false;
    }
}
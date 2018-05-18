import java.util.HashMap;
import java.util.Map;
public class AStarState {
    private Map2D map;
    private HashMap<Location, Waypoint> openWaypoints = new HashMap<>();
    private HashMap<Location, Waypoint> closedWaypoints = new HashMap<>();

    /**
     * Инициализируем новый объект для  A* pathfinding алгоритм для использования.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** возвращаем карту the A* pathfinder is navigating. **/
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
        // TODO:  Implement.
        Waypoint min = null;
        for (Map.Entry<Location,Waypoint> entry: openWaypoints.entrySet()){
            if (min == null) min = entry.getValue();
            else{
                Waypoint waypoint = entry.getValue();;
                if (waypoint.getTotalCost()<min.getTotalCost()) min = waypoint;
            }
        }
        return min;
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
        // TODO:  Implement.
        Location key = newWP.getLocation();
        Waypoint value = newWP;
        if (openWaypoints.containsKey(key) && value.getPreviousCost()<openWaypoints.get(key).getPreviousCost()){
            openWaypoints.replace(key,value);
            return true;
        }
        else if (!openWaypoints.containsKey(key)){ openWaypoints.put(key,value); return true; }
        return false;
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return openWaypoints.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        closedWaypoints.put(loc,openWaypoints.get(loc));
        openWaypoints.remove(loc);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        if (closedWaypoints.containsKey(loc)) return true;
        return false;
    }
}


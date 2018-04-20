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

    /** Initialize a map of all open waypoints and their locations. **/
    private Map<Location, Waypoint> OpenedWAypoint
            = new HashMap<Location, Waypoint> ();

    /** Initialize a map of all closed waypoints and their locations. **/
    private Map<Location, Waypoint> closed_waypoints
            = new HashMap<Location, Waypoint> ();

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
        //если коллекция "Открытых точек пуста"
        if (OpenedWAypoint.isEmpty()){return  null;}

        // Инициализирум коллекция ключ-значеие(entrySet) для всех точек пути, и создаем счетчик(iterator)
        // для перебора всех значений в коллекции.
        Iterator entries = OpenedWAypoint.entrySet().iterator();
        Waypoint NextPoint = null;//следующая точка, от которой ближе к финишу
        float Next_cost = Float.MAX_VALUE; //устанавливаем начальное значение кратчайшего пути

        // перебираем в цикле все точки в коллекции (до тех пор, пока есть следующий элемент относительно текущего)
        for (;entries.hasNext();)
        {
           // Записываем значение текущей точки(waypoint).
            Map.Entry next=(Map.Entry)entries.next();
            Waypoint waypoint = OpenedWAypoint.get(next.getKey());
            // записываем общее минимальное расстояние от текущей точки до финиша
            float waypoint_total_cost = waypoint.getTotalCost();

            // если расстояние от текущей точки меньше, чем расстояние от предыдущей
            // перезаписываем ближайую к финишу точку значением текущей точки
            if (waypoint_total_cost < Next_cost)
            {
                NextPoint = OpenedWAypoint.get(next.getKey());
                Next_cost = waypoint_total_cost;
            }

        }
        // Returns the waypoint with the minimum total cost.
        return NextPoint;
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
        // Находим расположение новой точки.
        Location location = newWP.getLocation();


        // Если такая точка уже есть в коллекции открытых точек.
        if (OpenedWAypoint.containsKey(location))
        {
            Waypoint current_waypoint = OpenedWAypoint.get(location);
            if (newWP.getPreviousCost() < current_waypoint.getPreviousCost())
            {
                // Если "previous cost" новой точки меньше "previous cost" текущей точки новая точка заменяет значение предыдущей точки
                // и возвращает true.
                OpenedWAypoint.put(location, newWP);
                return true;
            }
            // иначе
            return false;
        }
        // Если новая точка отсутствует в списке открытых точек, добавляем и return true
        OpenedWAypoint.put(location, newWP);
        return true;
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return OpenedWAypoint.size();
    }

    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint waypoint = OpenedWAypoint.remove(loc);
        closed_waypoints.put(loc, waypoint);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closed_waypoints.containsKey(loc);
    }
}
import java.util.*;

public class AStarState
{
    private Map2D map;

    private Map<Location, Waypoint> OpenedWAypoint
            = new HashMap<Location, Waypoint> ();

    private Map<Location, Waypoint> closed_waypoints
            = new HashMap<Location, Waypoint> ();

    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("Error: map = null");
        this.map = map;
    }

    public Map2D getMap()
    {
        return map;
    }

    public Waypoint getMinOpenWaypoint()
    {
        //если коллекция "Открытых точек пуста"
        if (OpenedWAypoint.isEmpty()){return  null;}

        // Cоздаем счетчик для перебора всех значений в коллекции.
        Iterator entries = OpenedWAypoint.entrySet().iterator();
        Waypoint NextPoint = null;          //следующая точка, от которой ближе к финишу
        float Next_cost = Float.MAX_VALUE;  //устанавливаем стартовое значение кратчайшего пути

        // перебираем в цикле все точки в коллекции (до тех пор, пока есть следующий элемент относительно текущего)
        for (;entries.hasNext();)
        {
           // Записываем значение текущей точки(waypoint).
            Map.Entry next=(Map.Entry)entries.next();
            Waypoint waypoint = OpenedWAypoint.get(next.getKey());
            // записываем общее минимальное расстояние от текущей точки до финиша
            float waypoint_total_cost = waypoint.getTotalCost();

            // если расстояние от текущей точки меньше, чем расстояние от предыдущей
            // перезаписываем ближайую к финишу точку на значение текущей
            if (waypoint_total_cost < Next_cost)
            {
                NextPoint = OpenedWAypoint.get(next.getKey());
                Next_cost = waypoint_total_cost;
            }

        }
        return NextPoint;
    }

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
                // Если true - новая точка заменяет значение предыдущей
                OpenedWAypoint.put(location, newWP);
                return true;
            }
            return false;
        }
        // Если новая точка отсутствует в списке открытых - добавляем туда
        OpenedWAypoint.put(location, newWP);
        return true;
    }

    public int numOpenWaypoints()
    {
        return OpenedWAypoint.size();
    }

    public void closeWaypoint(Location loc)
    {
        Waypoint waypoint = OpenedWAypoint.remove(loc);
        closed_waypoints.put(loc, waypoint);
    }

    public boolean isLocationClosed(Location loc)
    {
        return closed_waypoints.containsKey(loc);
    }
}
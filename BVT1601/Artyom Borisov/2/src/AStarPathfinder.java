public class AStarPathfinder
{

    public static final float COST_LIMIT = 1e6f;

    public static Waypoint computePath(Map2D map)
    {
        // Переменные, необходимые для нахождения А*.
        AStarState state = new AStarState(map);
        Location finishLoc = map.getFinish();

        // Задаём начальную точку для пропуска поиска А*.
        Waypoint start = new Waypoint(map.getStart(), null);
        start.setCosts(0, Weight_checking(start.getLocation(), finishLoc));
        state.addOpenWaypoint(start);

        Waypoint finalWaypoint = null;
        boolean foundPath = false;
        
        while (!foundPath && state.numOpenWaypoints() > 0)
        {
            // Поиск оптимальной точки.
            Waypoint Optimal = state.getMinOpenWaypoint();
            
            // Проверка, финиш или нет.
            if (Optimal.getLocation().equals(finishLoc))
            {
                finalWaypoint = Optimal;
                foundPath = true;
            }
            takeNextStep(Optimal, state);
            state.closeWaypoint(Optimal.getLocation());
        }
        
        return finalWaypoint;
    }

    private static void takeNextStep(Waypoint currWP, AStarState state)
    {
        Location loc = currWP.getLocation();
        Map2D map = state.getMap();
        
        for (int y = loc.y_Coord - 1; y <= loc.y_Coord + 1; y++)
        {
            for (int x = loc.x_Coord - 1; x <= loc.x_Coord + 1; x++)
            {
                Location nextLoc = new Location(x, y);

                // Если остаётся на месте, уже закрыто или выходит за рамки поля - пропускаем.
                if (nextLoc == loc || !map.contains(nextLoc) || state.isLocationClosed(nextLoc))
                    continue;

                Waypoint nextWP = new Waypoint(nextLoc, currWP);

                float prevCost = currWP.getPreviousCost() + Weight_checking(currWP.getLocation(),
                        nextWP.getLocation()); prevCost += map.getCellValue(nextLoc);

                if (prevCost >= COST_LIMIT)
                    continue;
                
                nextWP.setCosts(prevCost,
                    Weight_checking(nextLoc, map.getFinish()));
                state.addOpenWaypoint(nextWP);
            }
        }
    }

    private static float Weight_checking(Location currLoc, Location destLoc)
    {
        int dx = destLoc.x_Coord - currLoc.x_Coord;
        int dy = destLoc.y_Coord - currLoc.y_Coord;
        
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}

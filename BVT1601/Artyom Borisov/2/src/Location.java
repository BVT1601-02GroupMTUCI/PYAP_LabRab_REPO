
public class Location
{
    public int x_Coord, y_Coord;
    public Location(int x, int y)
    {
        x_Coord = x;
        y_Coord = y;
    }
    public Location()
    {
        this(0, 0);
    }

    public boolean equals(Object obj) {

        // Если это объект lOCATION
        if (obj instanceof Location) {
            // сравниваем координаты
            if (x_Coord == ((Location) obj).x_Coord && y_Coord == ((Location) obj).y_Coord) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + x_Coord;
        result = 37 * result + y_Coord;
        return result;
    }
}

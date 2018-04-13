/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
public class Location
{
    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;


    /** Creates a new location with the specified integer coordinates. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Creates a new location with coordinates (0, 0). **/
    public Location()
    {
        this(0, 0);
    }

    /** Compares this Location to another. **/
    public boolean equals(Object obj) {

        // Если это объект типа lOCATION
        if (obj instanceof Location) {

            // сравниваем координаты текущей точки и координаты точек объекта с которым сравниваем.
            //ежели равны
            if (xCoord == ((Location) obj).xCoord && yCoord == ((Location) obj).yCoord) {
                return true;
            }
        }

        // если объекты не равны.  Return false.
        return false;
    }

    /** Provides a hashCode for each Location. **/
    public int hashCode() {
        int result = 17;


        result = 37 * result + xCoord;
        result = 37 * result + yCoord;
        return result;
    }
}

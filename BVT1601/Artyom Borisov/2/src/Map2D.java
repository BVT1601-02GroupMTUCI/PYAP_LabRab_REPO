public class Map2D
{
    private int width;
    private int height;
    private int[][] cells;
    private Location start;
    private Location finish;
    public Map2D(int width, int height)
    {
        if (width <= 0 || height <= 0)
        {
            throw new IllegalArgumentException(
                    "Высота и ширина должны быть положительны; got " + width +
                    "x" + height);
        }
        
        this.width = width;
        this.height = height;
        
        cells = new int[width][height];
        start = new Location(0, height / 2);
        finish = new Location(width - 1, height / 2);
    }

    private void checkCoords(int x, int y)
    {
        if (x < 0 || x > width)
        {
            throw new IllegalArgumentException("x должен входить в отрезок [0, " +
                    width + "), х= " + x);
        }
        
        if (y < 0 || y > height)
        {
            throw new IllegalArgumentException("y должен входить в отрезок [0, " +
                    height + "), у= " + y);
        }
    }    
    
    //Возвращает ширину
    public int Ret_Width()
    {
        return width;
    }
    
    //Возвращает высоту
    public int Ret_Height()
    {
        return height;
    }

    public boolean contains(int x, int y)
    {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public boolean contains(Location Loc)
    {
        return contains(Loc.x_Coord, Loc.y_Coord);
    }

    public int getCellValue(int x, int y)
    {
        checkCoords(x, y);
        return cells[x][y];
    }

    public int getCellValue(Location Loc)
    {
        return getCellValue(Loc.x_Coord, Loc.y_Coord);
    }

    public void setCellValue(int x, int y, int value)
    {
        checkCoords(x, y);
        cells[x][y] = value;
    }

    public Location getStart()
    {
        return start;
    }

    public void setStart(Location Loc)
    {
        if (Loc == null)
            throw new NullPointerException("Ошибка! Loc = null");
        start = Loc;
    }

    public Location getFinish()
    {
        return finish;
    }

    public void setFinish(Location Loc)
    {
        if (Loc == null)
            throw new NullPointerException("Ошибка! Loc = null");
        
        finish = Loc;
    }
}

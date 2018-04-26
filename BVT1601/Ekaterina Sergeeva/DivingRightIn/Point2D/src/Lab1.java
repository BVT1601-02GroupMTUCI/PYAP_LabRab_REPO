import java.io.*;

public class Lab1 {
    public static void main(String[] args) {
        Point3D[] points = new Point3D[3];

        for (int i = 0; i < points.length; i++ ) {
            System.out.println("Введите точку: ");
            points[i] = new Point3D(getDouble(),getDouble(),getDouble());
        }

        if (points[0].equals(points[1]) || points[1].equals(points[2]) || points[0].equals(points[2])) {
            System.out.println("Невозможно посчитать площадь - существуют одинаковые точки!");
        } else {
            double a = points[0].distanceTo(points[1]),
                    b = points[1].distanceTo(points[2]),
                    c = points[0].distanceTo(points[2]);
            System.out.println(computeArea(a, b, c));
        }
    }

    public static double computeArea(double a, double b, double c) {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    /**
     * Obtains one double-precision floating point number from
     * standard input.
     *
     * @return return the inputted double, or 0 on error.
     */
    public static double getDouble() {

        // There's potential for the input operation to "fail"; hard with a
        // keyboard, though!
        try {
            // Set up a reader tied to standard input.
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));

            // Read in a whole line of text.
            String s = br.readLine();

            // Conversion is more likely to fail, of course, if there's a typo.
            try {
                double d = Double.parseDouble(s);

                //Return the inputted double.
                return d;
            } catch (NumberFormatException e) {
                // Bail with a 0.  (Simple solution for now.)
                return 0.0;
            }
        } catch (IOException e) {
            // This should never happen with the keyboard, but we'll
            // conform to the other exception case and return 0 here,
            // too.
            return 0.0;
        }
    }
}

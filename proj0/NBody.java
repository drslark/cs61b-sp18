public class NBody{
    
    /**
    * Read the radius of the universe from the file.
    *
    * @param file The file to read from.
    * @return the radius of the universe.
    */
    public static double readRadius(String file){
        In in = new In(file);
        int num = in.readInt();
        double radius = in.readDouble();
        in.close();
        return radius;
    }
    
    /**
    * Read the planets of the universe from the file.
    *
    * @param file The file to read from.
    * @return the planets of the universe.
    */
    public static Planet[] readPlanets(String file) {
        In in = new In(file);
        int num = in.readInt();
        double radius = in.readDouble();
        
        Planet[] planets = new Planet[num];
        for (int i = 0; i < planets.length; i++) {
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        in.close();
        
        return planets;
    }
    
    /**
    * Draw the background.
    *
    * @param radius The radius of the painting universe.
    * @param backgroundFig The Figure of the background.
    */
    private static void drawBackground(double radius, String backgroundFig) {
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0.0, 0.0, backgroundFig);
    }
    
    /**
    * Draw all planets.
    *
    * @param planets All planets in the current universe.
    */
    private static void drawPlanets(Planet[] planets) {
        for (Planet planet: planets) {
            planet.draw();
        }
    }
    
    /**
    * Calculate net forces in X direction exerted by other planets for each planet.
    *
    * @param xForces The net forces in direction X for each planet.
    * @param planets All planets in the current universe.
    */
    private static void calcXForces (double[] xForces, Planet[] planets) {
        for (int i = 0; i < planets.length; i++) {
            xForces[i] = planets[i].calcNetForceExertedByX(planets);
        }
    }
    
    /**
    * Calculate net forces in Y direction exerted by other planets for each planet.
    *
    * @param yForces The net forces in direction Y for each planet.
    * @param planets All planets in the current universe.
    */
    private static void calcYForces (double[] yForces, Planet[] planets) {
        for (int i = 0; i < planets.length; i++) {
            yForces[i] = planets[i].calcNetForceExertedByY(planets);
        }
    }
    
    /**
    * Update the NBody System.
    * 
    * @param planets All planets in the current universe.
    * @param xForces The net forces in direction X for each planet.
    * @param yForces The net forces in direction Y for each planet.
    * @param dt A small period of time.
    */
    private static void update (Planet[] planets, double[] xForces, double[] yForces, double dt) {
        for (int i = 0; i < planets.length; i++) {
            planets[i].update(dt, xForces[i], yForces[i]);
        }
    }
    
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        double[] xForces = new double[planets.length];
        double[] yForces = new double[planets.length];
        
        StdDraw.enableDoubleBuffering();
        
        for (int t = 0; t < T; t += dt) {
            calcXForces(xForces, planets);
            calcYForces(yForces, planets);
            update(planets, xForces, yForces, dt);
            
            drawBackground(radius, "images/starfield.jpg");
            drawPlanets(planets);
            StdDraw.show();
            StdDraw.pause(10);
        }
        
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
        
    }
    
}
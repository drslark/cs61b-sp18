public class TestPlanet {
	
	public static final double EPS = 0.01;
	
	/**
	* Test two planets' velocities and positions.
	*/
	public static void main(String[] args) {
		Planet planet1 = new Planet(0.0, 0.0, 0.0, 0.0, 1e10, "jupiter.gif");
		Planet planet2 = new Planet(100.0, 100.0, 0.0, 0.0, 1e10, "jupiter.gif");
		checkWithSimulation(planet1, planet2, 10);
	}
	
	/**
	* Run a simulation to test the program.
	*
	* @param planet1 The first planet.
	* @param planet2 The second planet.
	* @param round The round to simulate.
	*/
	public static void checkWithSimulation(Planet planet1, Planet planet2, int round) {
		Planet[] planets = {planet1, planet2};
		for (int i = 0; i < round; i++) {
			double fx2to1 = planet1.calcNetForceExertedByX(planets);
			double fy2to1 = planet1.calcNetForceExertedByY(planets);
			planet1.update(1, fx2to1, fy2to1);
			
			double fx1to2 = planet2.calcNetForceExertedByX(planets);
			double fy1to2 = planet2.calcNetForceExertedByY(planets);
			planet2.update(1, fx1to2, fy1to2);
			
			checkSymmetry(planet1, planet2);
		}
	}
	
	/**
	* Check if the planets' status are symmetrical.
	*
	* @param planet1 The first planet.
	* @param planet2 The second planet.
	*/
	public static void checkSymmetry(Planet planet1, Planet planet2) {
		if (
			(planet1.xxVel + planet2.xxVel) / (Math.abs(planet1.xxVel) + Math.abs(planet2.xxVel)) <= EPS && 
			(planet1.yyVel + planet2.yyVel) / (Math.abs(planet1.yyVel) + Math.abs(planet2.yyVel)) <= EPS
		) {
			System.out.printf("PASS: planet1's velocity is (%f, %f), planet1's velocity is (%f, %f).\n", planet1.xxVel, planet1.yyVel, planet2.xxVel, planet2.yyVel);
		} else {
			System.out.printf("FAIL: planet1's velocity is (%f, %f), planet1's velocity is (%f, %f).\n", planet1.xxVel, planet1.yyVel, planet2.xxVel, planet2.yyVel);
		}
	}
}
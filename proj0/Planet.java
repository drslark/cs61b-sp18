public class Planet {
	
	private static final double G = 6.67e-11;
	
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	
	public double mass;
	public String imgFileName;
	
	public Planet(double xP, double yP, double xV,
				double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	
	public Planet(Planet b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}
	
	/** 
	* Calculate the distance between this planet and another planet.
	*
	* @param another The another planet.
	* @return The distance to another planet.
	*/
	public double calcDistance(Planet another) {
		double dx = this.xxPos - another.xxPos;
		double dy = this.yyPos - another.yyPos;
		double distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}
	
	/**
	* Calculate the force to this planet exerted by another planet.
	*
	* @param another The another planet.
	* @return The force exerted by another planet.
	*/
	public double calcForceExertedBy(Planet another) {
		double distance = calcDistance(another);
		double force = G * this.mass * another.mass / (distance * distance);
		return force;
	}
	
	/**
	* Calculate the force to this planet exerted by another planet in the X direction.
	*
	* @param another The another planet.
	* @return The force exerted by another planet in direction X.
	*/
	public double calcForceExertedByX(Planet another) {
		double distance = calcDistance(another);
		double force = calcForceExertedBy(another);
		double dx = another.xxPos - this.xxPos;
		double forceInX = force * dx / distance;
		return forceInX;
	}
	
	/**
	* Calculate the force to this planet exerted by another planet in the Y direction.
	*
	* @param another The another planet.
	* @return The force exerted by another planet in direction Y.
	*/
	public double calcForceExertedByY(Planet another) {
		double distance = calcDistance(another);
		double force = calcForceExertedBy(another);
		double dy = another.yyPos - this.yyPos;
		double forceInY = force * dy / distance;
		return forceInY;
	}
	
	/**
	* Calculate the force to this planet exerted by all other planets in the X direction.
	*
	* @param all All planets.
	* @return The net force exerted by another planet in direction X.
	*/
	public double calcNetForceExertedByX(Planet[] all) {
		double netForceInX = 0.0;
		for (Planet planet: all) {
			if (this.equals(planet)) {
				continue;
			}
			netForceInX += calcForceExertedByX(planet);
		}
		return netForceInX;
	}
	
	/**
	* Calculate the force to this planet exerted by all other planets in the Y direction.
	*
	* @param all All planets.
	* @return The net force exerted by another planet in direction Y.
	*/
	public double calcNetForceExertedByY(Planet[] all) {
		double netForceInY = 0.0;
		for (Planet planet: all) {
			if (this.equals(planet)) {
				continue;
			}
			netForceInY += calcForceExertedByY(planet);
		}
		return netForceInY;
	}
	
	/**
	* Update the status of current planet.
	*
	* @param dt A small period of time.
	* @param fX The force in direction X.
	* @param fY The force in direction Y.
	*/
	public void update(double dt, double fX, double fY) {
		double accelInX = fX / this.mass;
		double accelInY = fY / this.mass;
		this.xxVel += accelInX * dt;
		this.yyVel += accelInY * dt;
		this.xxPos += this.xxVel * dt;
		this.yyPos += this.yyVel * dt;
	}
	
	/** 
	* Draw the planet on the canvas.
	*/
	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}
	
}
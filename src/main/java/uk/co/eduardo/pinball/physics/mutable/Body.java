package uk.co.eduardo.pinball.physics.mutable;

/**
 * A body is defined as a mass with a velocity. It is assumed all bodies are spherical but exist on a 2D plane.
 *
 * @author Ed
 */
public class Body
{
   /** The mass of the object in kilograms. */
   public double mass;

   /** The density of the body in kilograms/metres<sup>2</sup>. */
   public double density;

   /** Radius of the body in metres. */
   public double radius;

   /** The position of the body in space in metres. */
   public Vector position;

   /** The velocity of the object in metres/second. */
   public Vector velocity;

   /**
    * Initializes a new Body object.
    *
    * @param mass the mass of the object in kilograms.
    * @param density the density of the body in kilograms/metre<sup>2</sup>
    * @param position the position of the body in space in metres.
    * @param velocity the velocity of the object in metres.
    */
   public Body( final double mass, final double density, final Vector position, final Vector velocity )
   {
      this.mass = mass;
      this.density = density;
      this.radius = getRadius( mass, density );
      this.position = position;
      this.velocity = velocity;
   }

   /**
    * Creates a new body that is the result of accelerating this body by applying the specified force for the given number of
    * seconds.
    *
    * @param force the force to apply.
    * @param seconds the period for which the force should be applied.
    * @return a new body with the resultant position and velocity.
    */
   public Body applyForce( final Vector force, final double seconds )
   {
      final Vector acceleration = new Vector( force ).div( this.mass );
      final Vector deltaVelocity = acceleration.mul( seconds );

      this.velocity.add( deltaVelocity.mul( seconds ) );
      this.position.add( this.velocity );
      return this;
   }

   /**
    * Creates a new body that is the result of accelerating this body by the given amount for the given number of seconds.
    *
    * @param acceleration the acceleration in m/s<sup>2</sup>
    * @param seconds the number of seconds for which to accelerate the object.
    * @return the body with a new velocity and position as the result of the acceleration.
    */
   public Body applyAcceleration( final Vector acceleration, final double seconds )
   {
      final Vector deltaVelocity = new Vector( acceleration ).mul( seconds );
      this.velocity.add( deltaVelocity.mul( seconds ) );
      this.position.add( this.velocity );
      return this;
   }

   private static double getRadius( final double mass, final double density )
   {
      final double volume = mass / density;
      final double r3 = volume / ( ( 4.0 / 3.0 ) * Math.PI );
      final double r = Math.cbrt( r3 );
      return r;
      //
      // // Assuming an even distribution of mass within a spherical body.
      // final double area = mass / density;
      // final double radius = Math.sqrt( area / Math.PI );
      // return radius;
   }
}

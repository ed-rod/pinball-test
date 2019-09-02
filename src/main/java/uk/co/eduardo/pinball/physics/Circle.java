/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.pinball.physics;

import uk.co.eduardo.pinball.physics.mutable.Vector;

/**
 * TODO Insert description sentence here.
 *
 * @author erodri02
 */
public class Circle extends AbstractPhysicalObject implements MoveableObject
{
   protected final double radius;

   protected final Vector velocity;

   public Circle( final double mass, final double density, final Vector centre )
   {
      this( mass, density, centre, new Vector( 0, 0 ) );
   }

   public Circle( final double mass, final double density, final Vector centre, final Vector velocity )
   {
      super( mass, density, centre, null );
      this.radius = getRadius( mass, density );
      this.velocity = velocity;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Bounds getBounds()
   {
      return Bounds.create( new Vector( this.position.x - this.radius, this.position.y - this.radius ),
                            new Vector( this.position.x + this.radius, this.position.y + this.radius ) );
   }

   /**
    * Gets the radius.
    *
    * @return the radius.
    */
   public double getRadius()
   {
      return this.radius;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Vector getVelocity()
   {
      return this.velocity;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void applyAcceleration( final Vector acceleration, final double duration )
   {
      final Vector deltaVelocity = new Vector( acceleration ).mul( duration );
      this.velocity.add( deltaVelocity );
      this.position.add( new Vector( this.velocity ).mul( duration ) );
   }

   private static double getRadius( final double mass, final double density )
   {
      final double volume = mass / density;
      final double r3 = volume / ( ( 4.0 / 3.0 ) * Math.PI );
      final double r = Math.cbrt( r3 );
      return r;
   }
}

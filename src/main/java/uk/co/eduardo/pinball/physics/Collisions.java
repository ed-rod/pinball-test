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
public class Collisions
{
   // 1mm
   private static final double EPSILON = 1e-3;

   public static void checkInteraction( final MoveableObject body, final Vector oldPos, final PhysicalObject other )
   {
      if( ( body instanceof Circle ) && ( other instanceof Line ) )
      {
         final Circle circle = (Circle) body;
         final Line line = (Line) other;

         // There are two ways in which a circle may intersect a line.
         // 1. The current position overlaps the line
         if( !processOverlap( circle, line ) )
         {
            // 2. The old position was entirely one side of the line and the new position is entirely the other side of the line.
            // __ In this scenario, we need to move the ball back to the correct side of the line
            processClippedThroughLine( oldPos, circle, line );
         }
      }
   }

   private static boolean processOverlap( final Circle circle, final Line line )
   {
      final Vector E = line.start;
      final Vector C = circle.getPosition();
      final double r = circle.getRadius();

      final Vector d = line.direction;
      final Vector f = new Vector( E.x - C.x, E.y - C.y );
      final double a = d.dot( d );
      final double b = 2 * f.dot( d );
      final double c = f.dot( f ) - ( r * r );

      double discriminant = ( b * b ) - ( 4 * a * c );
      if( discriminant < 0 )
      {
         return false;
      }

      discriminant = Math.sqrt( discriminant );
      final double t1 = ( -b - discriminant ) / ( 2 * a );
      final double t2 = ( -b + discriminant ) / ( 2 * a );

      final boolean firstIntersection = ( t1 >= 0 ) && ( t1 <= 1 );
      final boolean secondIntersection = ( t2 >= 0 ) && ( t2 <= 1 );
      Vector i1 = null;
      Vector i2 = null;
      if( firstIntersection )
      {
         i1 = new Vector( E.x + ( d.x * t1 ), E.y + ( d.y * t1 ) );
      }
      if( secondIntersection )
      {
         i2 = new Vector( E.x + ( d.x * t2 ), E.y + ( d.y * t2 ) );
      }
      if( firstIntersection || secondIntersection )
      {
         // Get the midpoint of the two
         final double x = i1 == null ? i2.x * 2 : i1.x + ( i2 == null ? i1.x : i2.x );
         final double y = i1 == null ? i2.y * 2 : i1.y + ( i2 == null ? i1.y : i2.y );

         // Now the circle needs to touch this point and be centred in the direction of the normal
         C.x = ( x / 2 ) + ( line.normal.x * r );
         C.y = ( y / 2 ) + ( line.normal.y * r );

         // Update the velocity so it bounces off the line.
         updateVelocity( circle, line );

         return true;
      }
      return false;
   }

   private static void processClippedThroughLine( final Vector oldPos, final Circle circle, final Line line )
   {
      final Vector p1 = new Vector( oldPos );
      final Vector p2 = new Vector( circle.getPosition() );

      final int h1 = getHalfspace( line, p1 );
      final int h2 = getHalfspace( line, p2 );

      if( h1 != h2 )
      {
         final Vector position = circle.getPosition();

         final Vector p = new Vector( oldPos.x, oldPos.y );
         final Vector r = new Vector( position.x - oldPos.x, position.y - oldPos.y );
         final Vector q = new Vector( line.start.x, line.start.y );
         final Vector s = new Vector( line.direction.x, line.direction.y );

         final double u = new Vector( q ).sub( p ).cross( new Vector( r ).div( r.cross( s ) ) );
         final Vector intersection = new Vector( q ).add( new Vector( s ).mul( u ) );

         // Check to see if the intersection occurs along the line segment.
         if( ( u >= 0 ) && ( u <= 1 ) )
         {
            // Set the circle position so that it is touching the intersection and move it in the direction of the normal
            position.x = intersection.x + ( line.normal.x * circle.getRadius() );
            position.y = intersection.y + ( line.normal.y * circle.getRadius() );

            // Update the velocity so it bounces off the line.
            updateVelocity( circle, line );
         }
      }
   }

   private static void updateVelocity( final Circle circle, final Line line )
   {
      // Calculate distance to normal and add it twice to get mirror image in normal.
      final Vector v = circle.getVelocity();
      final double ns = new Vector( v ).dot( line.normal );
      final Vector p = new Vector( line.normal ).mul( Math.abs( ns ) );
      v.add( p ).add( p );

      // Damping
      v.mul( 0.9 );
      if( v.l2norm() < EPSILON )
      {
         v.x = 0;
         v.y = 0;
      }
   }

   /**
    * Performs a halfspace test on the line.
    * <p>
    * Points whose halfspace are 1 are all on one side of the line.
    * </p>
    * <p>
    * Points whose halfspace are -1 are all on the other side of the line.
    * </p>
    * <p>
    * Points whose halfspace are 0 lie on the line.
    * </p>
    *
    * @param line the line
    * @param testPoint the point to test.
    * @return the halfspace of the line as 1, -1 or 0.
    */
   private static int getHalfspace( final Line line, final Vector testPoint )
   {
      // v = testPoint - start
      final double vx = testPoint.x - line.start.x;
      final double vy = testPoint.y - line.start.y;

      // v = normal to d
      final double nx = line.normal.x;
      final double ny = line.normal.y;

      // dot product v with normal
      final double dp = ( nx * vx ) + ( ny * vy );

      if( dp > EPSILON )
      {
         return 1;
      }
      if( dp < EPSILON )
      {
         return -1;
      }
      return 0;
   }
}

/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.pinball.physics;

import uk.co.eduardo.pinball.physics.mutable.Vector;

/**
 * Rectangular bounds object.
 *
 * @author erodri02
 */
public class Bounds
{
   /** Empty bounding box. */
   public static final Bounds EMPTY = new Bounds();

   private final boolean isEmpty;

   private final Vector topLeft;

   private final Vector bottomRight;

   /**
    * Initializes a new Bounds object. Creates an empty bounds object.
    */
   private Bounds()
   {
      this.isEmpty = true;
      this.topLeft = null;
      this.bottomRight = null;
   }

   /**
    * Initializes a new Bounds object.
    *
    * @param topLeft the top-left point of the bounds object.
    * @param bottomRight the bottom-right point of the bounds object.
    */
   private Bounds( final Vector topLeft, final Vector bottomRight )
   {
      final double tlx = Math.min( topLeft.x, bottomRight.x );
      final double tly = Math.min( topLeft.y, bottomRight.y );
      final double brx = Math.max( topLeft.x, bottomRight.x );
      final double bry = Math.max( topLeft.y, bottomRight.y );

      this.isEmpty = false;
      this.topLeft = new Vector( tlx, tly );
      this.bottomRight = new Vector( brx, bry );
   }

   /**
    * Add a point to the bounds. The returned bounds object is the union of the current bounds and the newly added point.
    *
    * @param newPoint the point to add.
    * @return a bounds object that fully contains the new point, too.
    */
   public Bounds union( final Vector newPoint )
   {
      if( this.isEmpty )
      {
         return new Bounds( newPoint, newPoint );
      }
      final double tlx = Math.min( this.topLeft.x, newPoint.x );
      final double tly = Math.min( this.topLeft.y, newPoint.y );
      final double brx = Math.max( this.bottomRight.x, newPoint.x );
      final double bry = Math.max( this.bottomRight.y, newPoint.y );

      return new Bounds( new Vector( tlx, tly ), new Vector( brx, bry ) );
   }

   /**
    * Test to see if a point is contained within the bounds object.
    *
    * @param point the point to test.
    * @return whether or not the point is contained within the bounds object.
    */
   public boolean contains( final Vector point )
   {
      if( this.isEmpty )
      {
         return false;
      }
      return ( point.x >= this.topLeft.x ) && ( point.x <= this.bottomRight.x ) && //
             ( point.y >= this.topLeft.y ) && ( point.y <= this.bottomRight.y );
   }

   /**
    * Tests whether or not this bounds and the other bounds overlap.
    * <p>
    * Bounds are bounding boxes and it simply tests for overlap between the two rectangles.
    * </p>
    *
    *
    * @param other the other bounds to test.
    * @return whether or not they intersect.
    */
   public boolean intersects( final Bounds other )
   {
      if( this.isEmpty || other.isEmpty )
      {
         return false;
      }

      if( ( this.topLeft.x > other.bottomRight.x ) || ( other.topLeft.x > this.bottomRight.x ) )
      {
         return false;
      }
      if( ( this.topLeft.y < other.bottomRight.y ) || ( other.topLeft.y < this.bottomRight.y ) )
      {
         return false;
      }

      return true;
   }

   /**
    * Creates a bounding box for the given points.
    *
    * @param positions the points from which to construct a bounding box.
    * @return the bounding box.
    */
   public static Bounds create( final Vector... positions )
   {
      Bounds bounds = Bounds.EMPTY;
      for( final Vector p : positions )
      {
         bounds = bounds.union( p );
      }
      return bounds;
   }
}

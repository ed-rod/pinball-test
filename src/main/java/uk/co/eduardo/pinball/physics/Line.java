/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.pinball.physics;

import uk.co.eduardo.pinball.physics.mutable.Vector;

/**
 * Lines are static objects.
 *
 * @author erodri02
 */
public class Line extends AbstractPhysicalObject
{
   // Lines are static objects and don't have a mass.
   private static final double DEFAULT_LINE_MASS = 0;

   // Lines don't have a density.
   private static final double DEFAULT_LINE_DENSITY = 0;

   /** Start point of the line */
   protected final Vector start;

   /** End point of the line */
   protected final Vector end;

   /** Line direction vector (not normalized). */
   protected final Vector direction;

   /** Normal vector (normalized) */
   protected final Vector normal;

   /**
    * Initializes a new Line object.
    *
    * @param start one endpoint to the line.
    * @param end the other endpoint to the line.
    */
   public Line( final Vector start, final Vector end )
   {
      super( DEFAULT_LINE_MASS,
             DEFAULT_LINE_DENSITY,
             new Vector( end.x - start.x, end.y - start.y ).div( 2 ),
             Bounds.create( start, end ) );

      this.start = start;
      this.end = end;
      this.direction = new Vector( end ).sub( start );
      this.normal = new Vector( -this.direction.y, this.direction.x ).normalize();
   }

   /**
    * Gets the start of the line.
    *
    * @return the start.
    */
   public Vector getStart()
   {
      return this.start;
   }

   /**
    * Gets the end of the line.
    *
    * @return the end.
    */
   public Vector getEnd()
   {
      return this.end;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return this.start.toString() + " -> " + this.end.toString(); //$NON-NLS-1$
   }
}

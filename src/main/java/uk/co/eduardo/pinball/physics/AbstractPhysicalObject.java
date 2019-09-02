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
public class AbstractPhysicalObject implements PhysicalObject
{
   protected final double mass;

   protected final double density;

   protected final Vector position;

   private final Bounds bounds;

   public AbstractPhysicalObject( final double mass, final double density, final Vector position, final Bounds bounds )
   {
      this.mass = mass;
      this.density = density;
      this.position = position;
      this.bounds = bounds;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public double getMass()
   {
      return this.mass;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public double getDensity()
   {
      return this.density;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Vector getPosition()
   {
      return this.position;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Vector getCentroid()
   {
      return this.position;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Bounds getBounds()
   {
      return this.bounds;
   }
}

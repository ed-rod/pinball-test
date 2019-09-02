/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.pinball.physics;

import uk.co.eduardo.pinball.physics.mutable.Vector;

/**
 * A Physical object in the scene.
 *
 * @author erodri02
 */
public interface PhysicalObject
{
   /**
    * Gets the mass of the object in kilograms.
    *
    * @return the mass in kg.
    */
   double getMass();

   /**
    * Gets the density of the object in kg/m<sup>3</sup>
    *
    * @return the density of the object.
    */
   double getDensity();

   /**
    * Gets the position of the objec in metres.
    *
    * @return the position in metres.
    */
   Vector getPosition();

   /**
    * Gets the centroid of the object.
    *
    * @return the centroid of the object.
    */
   Vector getCentroid();

   /**
    * Gets the rectangular bounding box of the object.
    *
    * @return the bounds of the object.
    */
   Bounds getBounds();
}

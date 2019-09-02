/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.pinball.physics;

import uk.co.eduardo.pinball.physics.mutable.Vector;

/**
 * A physical object that can be moved.
 *
 * @author erodri02
 */
public interface MoveableObject extends PhysicalObject
{
   /**
    * Gets the velocity of the object in metres per second.
    *
    * @return the velocity in m/s.
    */
   Vector getVelocity();

   /**
    * Updates the object's velocity and position as the result of accelerating this object by the given amount for the given number
    * of seconds.
    *
    * @param acceleration the acceleration in m/s<sup>2</sup>
    * @param duration the number of seconds for which to accelerate the object.
    */
   void applyAcceleration( Vector acceleration, double duration );
}

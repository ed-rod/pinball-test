/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.pinball;

import java.util.List;

import uk.co.eduardo.pinball.physics.MoveableObject;
import uk.co.eduardo.pinball.physics.PhysicalObject;

/**
 * TODO Insert description sentence here.
 *
 * @author erodri02
 */
public interface PhysicalObjectGenerator
{
   /**
    * Create a list of static objects for a scene.
    *
    * @param settings the global settings.
    * @return a list of objects.
    */
   List< PhysicalObject > createStaticObjects( final Settings settings );

   /**
    * Create a list of moveable objects for a scene.
    *
    * @param settings the global settings.
    * @return a list of objects.
    */
   List< MoveableObject > createMoveableObjects( final Settings settings );
}

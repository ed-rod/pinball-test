/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.pinball;

import java.util.Arrays;
import java.util.List;

import uk.co.eduardo.pinball.physics.Circle;
import uk.co.eduardo.pinball.physics.Line;
import uk.co.eduardo.pinball.physics.MoveableObject;
import uk.co.eduardo.pinball.physics.PhysicalObject;
import uk.co.eduardo.pinball.physics.mutable.Vector;

/**
 * TODO Insert description sentence here.
 *
 * @author erodri02
 */
public class DiamondObjectGenerator implements PhysicalObjectGenerator
{
   /**
    * {@inheritDoc}
    */
   @Override
   public List< PhysicalObject > createStaticObjects( final Settings settings )
   {
      final double w = settings.getTableWidth();
      final double h = settings.getTableHeight();
      final Vector tl = new Vector( 0.5 * w, 0 );
      final Vector tr = new Vector( w, 0.5 * h );
      final Vector br = new Vector( 0.5 * w, h );
      final Vector bl = new Vector( 0, 0.5 * h );

      return Arrays.asList( new Line( tl, tr ), //
                            new Line( tr, br ), //
                            new Line( br, bl ), //
                            new Line( bl, tl ) );
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public List< MoveableObject > createMoveableObjects( final Settings settings )
   {
      final double w = settings.getTableWidth();
      final double h = settings.getTableHeight();
      final Vector c = new Vector( w, h ).div( 2 );
      return Arrays.asList( new Circle( settings.getBallMass(), settings.getBallDensity(), c, new Vector( 4, -0.02 ) ) );
   }
}

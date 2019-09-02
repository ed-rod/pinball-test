/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.pinball;

import java.util.ArrayList;
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
public class RoundTopObjectGenerator implements PhysicalObjectGenerator
{
   /**
    * {@inheritDoc}
    */
   @Override
   public List< PhysicalObject > createStaticObjects( final Settings settings )
   {
      final double w = settings.getTableWidth();
      final double h = settings.getTableHeight();

      final List< PhysicalObject > objects = new ArrayList<>();

      final double top = h * 0.3;
      final double xCentre = w / 2;
      final double yCentre = top;
      final double yRadius = top;
      final double xRadius = w / 2;

      Vector last = new Vector( 0, top );
      for( int i = 180; i <= 360; i += 1 )
      {
         final double x = xCentre + ( xRadius * Math.cos( Math.toRadians( i ) ) );
         final double y = yCentre + ( yRadius * Math.sin( Math.toRadians( i ) ) );
         final Vector current = new Vector( x, y );

         objects.add( new Line( last, current ) );
         last = current;
      }
      final Vector br = new Vector( w, h );
      final Vector bl = new Vector( 0, h );
      final Vector tl = new Vector( 0, top );
      final Vector tr = new Vector( w, top );

      objects.add( new Line( tr, br ) );
      objects.add( new Line( br, bl ) );
      objects.add( new Line( bl, tl ) );

      return objects;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public List< MoveableObject > createMoveableObjects( final Settings settings )
   {
      final double w = settings.getTableWidth();
      final double h = settings.getTableHeight();
      final double mass = settings.getBallMass();
      final double density = settings.getBallDensity();
      final double r = getRadius( mass, density ) * 1.001; // off the edge slightly
      final Vector c = new Vector( w - r, h - r );

      return Arrays.asList( new Circle( mass, density, c, new Vector( 0, -3 ) ) );
   }

   private static double getRadius( final double mass, final double density )
   {
      final double volume = mass / density;
      final double r3 = volume / ( ( 4.0 / 3.0 ) * Math.PI );
      final double r = Math.cbrt( r3 );
      return r;
   }
}

/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.pinball;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import uk.co.eduardo.pinball.physics.Collisions;
import uk.co.eduardo.pinball.physics.MoveableObject;
import uk.co.eduardo.pinball.physics.PhysicalObject;
import uk.co.eduardo.pinball.physics.mutable.Vector;

/**
 * TODO Insert description sentence here.
 *
 * @author erodri02
 */
public class PinballTest
{
   public static void main( final String[] args ) throws InvocationTargetException, InterruptedException
   {
      final AtomicReference< GraphicsComponent > graphicsPlaceholder = new AtomicReference<>();
      // final Settings settings = new Settings().setFps( 50 ).setTableSlope( 6.5 ).setBallMass( 0.3 );
      final Settings settings = new Settings().setFps( 50 ).setTableSlope( 6.5 );

      SwingUtilities.invokeAndWait( () -> {
         setLaf();
         final GraphicsComponent graphicsComp = new GraphicsComponent( settings );
         createFrame( graphicsComp );
         graphicsPlaceholder.set( graphicsComp );
      } );

      startLoop( graphicsPlaceholder.get(), settings );
   }

   private static JFrame createFrame( final JComponent graphicsComp )
   {
      final JPanel withBorder = new JPanel( new BorderLayout() );
      withBorder.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
      withBorder.add( graphicsComp );
      graphicsComp.setPreferredSize( new Dimension( 500, 500 ) );

      final JFrame frame = new JFrame( "Pinball Test" );
      frame.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
      frame.setContentPane( withBorder );
      frame.pack();
      frame.setLocationRelativeTo( null );
      frame.setVisible( true );
      return frame;
   }

   private static void setLaf()
   {
      try
      {
         UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
      }
      catch( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception )
      {
         // Ignore and use default LAF
      }
   }

   private static void startLoop( final GraphicsComponent graphicsComp, final Settings settings )
   {
      final PhysicalObjectGenerator generator = new RoundTopObjectGenerator();
      final List< PhysicalObject > staticObjects = generator.createStaticObjects( settings );
      final List< MoveableObject > moveableObjects = generator.createMoveableObjects( settings );
      final List< PhysicalObject > allObjects = new ArrayList<>( staticObjects );
      allObjects.addAll( moveableObjects );

      graphicsComp.setObjects( allObjects );

      final long targetFrameDuration = 1_000_000_000 / settings.getFps();
      boolean loop = true;

      while( loop )
      {
         final long frameStart = System.nanoTime();
         // Update state
         updatePositions( staticObjects, moveableObjects, settings );

         // paint
         SwingUtilities.invokeLater( () -> graphicsComp.repaint() );

         // Check to see how long to pause to maintain target framerate.
         maintainFramerate( targetFrameDuration, frameStart );

         // Check to see if the window has been disposed
         loop = graphicsComp.isDisplayable();
      }
   }

   private static void maintainFramerate( final long targetFrameDuration, final long frameStart )
   {
      final long frameDuration = System.nanoTime() - frameStart;
      final long remainder = targetFrameDuration - frameDuration;
      final long millisecondPause = remainder / 1_000_000;
      try
      {
         // System.out.printf( "Took: %d Remaining: %d ms Pause: %d\n", frameDuration, remainder, millisecondPause );
         if( millisecondPause > 0 )
         {
            Thread.sleep( millisecondPause );
         }
      }
      catch( final InterruptedException exception )
      {
         exception.printStackTrace();
      }
   }

   private static void updatePositions( final List< PhysicalObject > staticObjects,
                                        final List< MoveableObject > moveableObjects,
                                        final Settings settings )
   {
      final double time = 1.0 / settings.getFps();
      final double slopeFactor = Math.sin( Math.toRadians( settings.getTableSlope() ) );
      final double gn = 9.80665;
      final double a = gn * slopeFactor;
      final Vector accel = new Vector( 0, 1 ).mul( a );

      for( final MoveableObject moveable : moveableObjects )
      {
         final Vector oldPos = new Vector( moveable.getPosition() );
         moveable.applyAcceleration( accel, time );

         // Check for collisions with all other objects
         for( final PhysicalObject staticObject : staticObjects )
         {
            // Check for collisions
            Collisions.checkInteraction( moveable, oldPos, staticObject );
         }
      }
   }
}

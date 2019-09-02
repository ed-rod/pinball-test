/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.pinball;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import uk.co.eduardo.pinball.physics.Circle;
import uk.co.eduardo.pinball.physics.Line;
import uk.co.eduardo.pinball.physics.PhysicalObject;
import uk.co.eduardo.pinball.physics.mutable.Vector;

/**
 * TODO Insert description sentence here.
 *
 * @author erodri02
 */
class GraphicsComponent extends JComponent
{
   private static final int FRAME_WINDOW_SIZE = 30;

   private final Settings settings;

   private List< PhysicalObject > objects;

   private final LinkedList< Long > frameTimes = new LinkedList<>();

   GraphicsComponent( final Settings settings )
   {
      this.settings = settings;
   }

   void setObjects( final List< PhysicalObject > objects )
   {
      this.objects = objects;
   }

   @Override
   protected void paintComponent( final Graphics g )
   {
      this.frameTimes.add( System.nanoTime() );
      if( this.frameTimes.size() > FRAME_WINDOW_SIZE )
      {
         this.frameTimes.removeFirst();
      }

      final Graphics2D g2 = (Graphics2D) g;

      final Vector table = new Vector( this.settings.getTableWidth(), this.settings.getTableHeight() );
      final double xZoom = ( getWidth() - 2 ) / table.x;
      final double yZoom = ( getHeight() - 2 ) / table.y;
      final double zoom = Math.min( xZoom, yZoom );
      final int translatex = (int) ( ( getWidth() - ( table.x * zoom ) ) / 2 );
      final int translatey = (int) ( ( getHeight() - ( table.y * zoom ) ) / 2 );

      if( ( this.objects != null ) && ( this.objects.size() > 0 ) )
      {
         for( final PhysicalObject object : this.objects )
         {
            renderObject( g2, object, translatex, translatey, zoom );
         }
      }

      final int frameCount = this.frameTimes.size();
      final long start = this.frameTimes.getFirst();
      final long end = this.frameTimes.getLast();
      final long difference = end - start;
      if( difference != 0 )
      {
         final double fps = frameCount / ( difference / 1_000_000_000d );
         System.out.printf( "%d %.2f fps\n", frameCount, fps );
      }
   }

   private void renderObject( final Graphics2D g2,
                              final PhysicalObject object,
                              final int translatex,
                              final int translatey,
                              final double zoom )
   {
      if( object instanceof Line )
      {
         final Line line = (Line) object;
         final Vector p1 = line.getStart();
         final Vector p2 = line.getEnd();

         final int p1x = (int) ( p1.x * zoom ) + translatex;
         final int p1y = (int) ( p1.y * zoom ) + translatey;
         final int p2x = (int) ( p2.x * zoom ) + translatex;
         final int p2y = (int) ( p2.y * zoom ) + translatey;

         g2.drawLine( p1x, p1y, p2x, p2y );
      }
      else if( object instanceof Circle )
      {
         final Circle circle = (Circle) object;
         final Vector position = circle.getPosition();
         final int x = (int) ( position.x * zoom );
         final int y = (int) ( position.y * zoom );
         final int radius = (int) ( circle.getRadius() * zoom );

         g2.setColor( Color.BLACK );
         g2.drawOval( ( x - radius ) + translatex, ( y - radius ) + translatey, 2 * radius, 2 * radius );

         final Vector v = circle.getVelocity();
         final Vector vNorm = new Vector( v ).normalize();

         // To test whether they collide
         // We test three points: old centre, new centre and point on circumference in the direction of travel.
         final Vector p2 = new Vector( circle.getPosition() );
         final Vector p3 = new Vector( vNorm ).mul( circle.getRadius() ).add( p2 );

         final int p2x = (int) ( p2.x * zoom ) + translatex;
         final int p2y = (int) ( p2.y * zoom ) + translatey;
         final int p3x = (int) ( p3.x * zoom ) + translatex;
         final int p3y = (int) ( p3.y * zoom ) + translatey;

         g2.setColor( Color.BLUE );
         g2.drawRect( p2x, p2y, 1, 1 );
         g2.setColor( Color.RED );
         g2.drawRect( p3x, p3y, 1, 1 );
      }
   }
}
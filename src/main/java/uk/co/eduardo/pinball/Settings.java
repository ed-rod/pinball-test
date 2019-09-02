package uk.co.eduardo.pinball;

/**
 * Program settings.
 *
 * @author Ed
 */
public class Settings
{
   private static final int DEFAULT_FPS = 24;

   private static final double DEFAULT_BALL_DENSITY = 8050;

   private static final double DEFAULT_BALL_MASS = 0.08;

   private static final double DEFAULT_TABLE_WIDTH = 0.56;

   private static final double DEFAULT_TABLE_HEIGHT = 1.30;

   private static final double DEFAULT_TABLE_SLOPE = 6.5;

   private final int fps;

   private final double ballDensity;

   private final double ballMass;

   private final double tableWidth;

   private final double tableHeight;

   private final double tableSlope;

   /**
    * Initializes a new Settings object. Default settings.
    */
   public Settings()
   {
      this( DEFAULT_FPS, DEFAULT_BALL_DENSITY, DEFAULT_BALL_MASS, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_HEIGHT, DEFAULT_TABLE_SLOPE );
   }

   /**
    * Initializes a new Settings object.
    *
    * @param fps frames per second.
    * @param ballDensity ball density in kg/m<sup>3</sup>
    * @param ballMass ball mass in kg.
    * @param tableWidth the width of the table in metres.
    * @param tableHeight the height of the tablein metres.
    * @param tableSlope the angle of the table from horizontal in degrees. 0 = flat, 90 = completely vertical.
    */
   public Settings( final int fps,
                    final double ballDensity,
                    final double ballMass,
                    final double tableWidth,
                    final double tableHeight,
                    final double tableSlope )
   {
      this.fps = fps;
      this.ballDensity = ballDensity;
      this.ballMass = ballMass;
      this.tableWidth = tableWidth;
      this.tableHeight = tableHeight;
      this.tableSlope = tableSlope;
   }

   Settings setFps( final int fps )
   {
      return new Settings( fps, this.ballDensity, this.ballMass, this.tableWidth, this.tableHeight, this.tableSlope );
   }

   Settings setBallDensity( final double ballDensity )
   {
      return new Settings( this.fps, ballDensity, this.ballMass, this.tableWidth, this.tableHeight, this.tableSlope );
   }

   Settings setBallMass( final double ballMass )
   {
      return new Settings( this.fps, this.ballDensity, ballMass, this.tableWidth, this.tableHeight, this.tableSlope );
   }

   Settings setTableWidth( final double tableWidth )
   {
      return new Settings( this.fps, this.ballDensity, this.ballMass, tableWidth, this.tableHeight, this.tableSlope );
   }

   Settings setTableHeight( final double tableHeight )
   {
      return new Settings( this.fps, this.ballDensity, this.ballMass, this.tableWidth, tableHeight, this.tableSlope );
   }

   Settings setTableSlope( final double tableSlope )
   {
      return new Settings( this.fps, this.ballDensity, this.ballMass, this.tableWidth, this.tableHeight, tableSlope );
   }

   int getFps()
   {
      return this.fps;
   }

   double getBallDensity()
   {
      return this.ballDensity;
   }

   double getBallMass()
   {
      return this.ballMass;
   }

   double getTableWidth()
   {
      return this.tableWidth;
   }

   double getTableHeight()
   {
      return this.tableHeight;
   }

   double getTableSlope()
   {
      return this.tableSlope;
   }
}

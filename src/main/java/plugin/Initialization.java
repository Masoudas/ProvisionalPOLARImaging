package plugin;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fiji.tool.SliceObserver;
import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.GenericDialog;
import ij.gui.Roi;
import ij.plugin.PlugIn;
import mpicbg.imglib.multithreading.SimpleMultiThreading;
import mpicbg.spim.segmentation.InteractiveDoG.ValueChange;



public class Initialization implements PlugIn 
{	
	boolean isComputing = false;
	//@Override
	public void run(String arg0) 
	{	
		// get list of image stacks
		final int[] idList = WindowManager.getIDList();		
		if ( idList == null || idList.length > 1 )
		{
			IJ.error( "You need to open the bead image, with all four channels present." );
			return;
		}

		String imgName = WindowManager.getImage(idList[0]).getTitle();
		
		ImagePlus imp = WindowManager.getImage( imgName );

		int[][] initCoord = {{0,0}, {0,imp.getHeight()/2}, {imp.getWidth()/2,0}, {imp.getWidth()/2, imp.getHeight()/2}};
		/**
		 * The first dialog for choosing the images
		 */
		for (int ROIcounter = 0; ROIcounter < 4; ROIcounter++){
			final Frame frame = new Frame("Adjust ROI For Polarization");
			
			frame.setSize( 200, 220 );

			final GridBagLayout layout = new GridBagLayout();
			final GridBagConstraints c = new GridBagConstraints();
	
			final Button button = new Button( "Done" );
			final Button cancel = new Button( "Cancel" );
	
			frame.setLayout( layout );
			frame.setVisible( true );

			++c.gridy;
			c.insets = new Insets(0,75,0,75);
			frame.add( cancel, c );
	
			++c.gridy;
			c.insets = new Insets(10,150,0,150);
			frame.add( button, c );

			button.addActionListener( new FinishedButtonListener( frame, false ) );
			cancel.addActionListener( new FinishedButtonListener( frame, true ) );
				
			imp.setRoi( initCoord[ROIcounter][0], initCoord[ROIcounter][1], imp.getWidth()/3, imp.getHeight()/3 );		

			

			Rectangle rect = imp.getRoi().getBounds();
			IJ.log(Integer.toString(rect.height) + Integer.toString(rect.width) + Integer.toString(rect.x) + Integer.toString(rect.y));
			imp.killRoi();						
		}	
		
		// Here, write the coordinates in the database.
	}

	protected class RoiListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased( final MouseEvent e )
		{
			// here the ROI might have been modified, let's test for that
			final Roi roi = imp.getRoi();
			
			if ( roi == null || roi.getType() != Roi.RECTANGLE )
				return;
			
			while ( isComputing )
				SimpleMultiThreading.threadWait( 10 );
			
		//	updatePreview( ValueChange.ROI );				
		}
		
	}

	protected final void close( final Frame parent, final SliceObserver sliceObserver, final ImagePlus imp, final RoiListener roiListener )
	{
		if ( parent != null )
			parent.dispose();
		
		if ( sliceObserver != null )
			sliceObserver.unregister();
		
		if ( imp != null )
		{
			if ( roiListener != null )
				imp.getCanvas().removeMouseListener( roiListener );
			
			imp.getOverlay().clear();
			imp.updateAndDraw();
		}
		
		//isFinished = true;
	}


	protected class FinishedButtonListener implements ActionListener
	{
		final Frame parent;
		final boolean cancel;
		
		public FinishedButtonListener( Frame parent, final boolean cancel )
		{
			this.parent = parent;
			this.cancel = cancel;
		}
		
		@Override
		public void actionPerformed( final ActionEvent arg0 ) 
		{
			//wasCanceled = cancel;
			close( parent, sliceObserver, imp, roiListener );
		}
	}
		
	
}

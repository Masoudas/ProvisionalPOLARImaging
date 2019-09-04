package plugin;

import java.awt.*;
import java.awt.event.*;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;


public class ImageExcerpterGUI extends Frame implements PlugIn{
    private static final long serialVersionUID = 1L;
    
    // Buttons
    final private Button okBtn = new Button("ok");
    final private Button applyBtn = new Button("apply");
    final private Button cencelBtn = new Button("Cancel");

    // Text showing the polarization.
    final private Label message = new Label("Select the region corresponding to each polarization and click apply");
    final private Label pole0Text = new Label("0-degree polarization");
    final private Label pole45Text = new Label("45-degree polarization");
    final private Label pole90Text = new Label("90-degree polarization");
    final private Label pole135Text = new Label("135-degree polarization");
    
    // Text containing the polarization pixels.
    final private TextField pol0Label = new TextField("[ ]");
    final private TextField pol45Label = new TextField("[ ]");
    final private TextField pol90Label = new TextField("[ ]");
    final private TextField pol135Label = new TextField("[ ]");

    // Holder of the open image.
    ImagePlus imp;

    private boolean checkImageOpen() {
        // get list of image stacks
		final int[] idList = WindowManager.getIDList();		

		if ( idList == null || idList.length > 1 )
		{
			IJ.error( "You need to open the bead image with all four channels." );
			return false;
        }
        
        return true;
    }

    private void readOpenImage() {
        final int[] idList = WindowManager.getIDList();
        //final String imgList = WindowManager.getImage(idList[0]).getTitle();
        imp =  WindowManager.getImage( idList[ 0 ] );
        
        // Set an initial ROI on the open image.
        imp.setRoi(5, 5, imp.getWidth()/2, imp.getHeight()/2);
    }

    private void defineGrid(GridBagLayout layout, GridBagConstraints cst, Component comp, int[] wp, Insets insets){
        //  cst.gridwidth = gridWidth; // Sets the length of this grid. When we say relative, means continue on the same grid
        cst.anchor = GridBagConstraints.WEST;         
        cst.ipadx = wp[2]; // This is the size of the x direction of the component in pixels
        cst.ipady = wp[3]; // This is the size of the y direction of the component in pixels
  
        cst.gridwidth = wp[0];    // The x spacing of the components
        cst.gridheight = wp[1];    // The y spacing of the components

        cst.gridx = wp[4];
        cst.gridy = wp[5];

        if (insets != null) cst.insets = insets;

        layout.setConstraints(comp, cst);
        add( comp );  
    }
    
    public void addKeyListener(KeyListener kl){
        addKeyListener(kl);
    }

    public void addCancelBtnListener(ActionListener l){
        cencelBtn.addActionListener(l);
    }

    public void addOkBtnListener(ActionListener l){
        okBtn.addActionListener(l);
    }

    public TextField get_pole0Label(){
        return pol0Label;
    }
   
    public TextField get_pole45Label(){
        return pol45Label;
    }

    public TextField get_pole90Label(){
        return pol90Label;
    }

    public TextField get_pole135Label(){
        return pol135Label;
    }

    public ImagePlus get_imp(){
        return imp;
    }

    @Override
    public void run(String arg) {
        if (!checkImageOpen())
            return;

        readOpenImage();

        // Adding the window listener.
        addWindowListener(new WindowClosingListener());
        cencelBtn.addActionListener(new CancelBtnListener());
        okBtn.addActionListener(new OkBtnListener());
        applyBtn.addActionListener(new ApplyBtnListener());


        setSize(500, 250);
        setTitle("Handling key events");
        setResizable(false);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraint = new GridBagConstraints();
        int[] wp = {0, 0, 0, 0, 0, 0};    // Denotes the weights and padding associated with each component.

        wp[0] = 2; wp[1] = 1; wp[2] = 10; wp[3] = 2; wp[4] = 0; wp[5] = 0;
        defineGrid(layout, constraint, message, wp, null);
        
        wp[0] = 1; wp[1] = 1; wp[2] = 10; wp[3] = 2; wp[4] = 0; wp[5] = 2;
        defineGrid(layout, constraint, pole0Text, wp, null);
    
        wp[0] = 1; wp[1] = 1; wp[2] = 100; wp[3] = 2; wp[4] = 1; wp[5] = 2;
        defineGrid(layout, constraint, pol0Label, wp, null);
    
        wp[0] = 1; wp[1] = 1; wp[2] = 10; wp[3] = 2; wp[4] = 0; wp[5] = 3;
        defineGrid(layout, constraint, pole45Text, wp, null);
    
        wp[0] = 1; wp[1] = 1; wp[2] = 100; wp[3] = 2; wp[4] = 1; wp[5] = 3;
        defineGrid(layout, constraint, pol45Label, wp, null);
    
        wp[0] = 1; wp[1] = 1; wp[2] = 10; wp[3] = 2; wp[4] = 0; wp[5] = 4;
        defineGrid(layout, constraint, pole90Text, wp, null);
    
        wp[0] = 1; wp[1] = 1; wp[2] = 100; wp[3] = 2; wp[4] = 1; wp[5] = 4;
        defineGrid(layout, constraint, pol90Label, wp, null);
    
        wp[0] = 1; wp[1] = 1; wp[2] = 10; wp[3] = 2; wp[4] = 0; wp[5] = 5;
        defineGrid(layout, constraint, pole135Text, wp, null);
    
        wp[0] = 1; wp[1] = 1; wp[2] = 100; wp[3] = 2; wp[4] = 1; wp[5] = 5;
        defineGrid(layout, constraint, pol135Label, wp, null);
        
        wp[0] = 2; wp[1] = 1; wp[2] = 0; wp[3] = 0; wp[4] = 0; wp[5] = 6;
        Insets insets = new Insets(10, 20, 0, 0);
        defineGrid(layout, constraint, okBtn, wp, insets);
    
        wp[0] = 2; wp[1] = 1; wp[2] = 0; wp[3] = 0; wp[4] = 0; wp[5] = 6;
        insets.left = 70;
        defineGrid(layout, constraint, applyBtn, wp, insets);
        
        wp[0] = 2; wp[1] = 1; wp[2] = 10; wp[3] = 0; wp[4] = 0; wp[5] = 6;
        insets.left = 150;
        defineGrid( layout, constraint, cencelBtn, wp, insets );
    
        setLayout( layout );
        setVisible(true);
        repaint();
    }

    public static void main(String[] args) {
        ImageExcerpterGUI e3 = new ImageExcerpterGUI();
    }
}

class WindowClosingListener extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        Frame frame = (Frame)e.getSource();

        frame.setVisible(false);
        frame.dispose();
    }
}

class OkBtnListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        Button btn = (Button)e.getSource();
        Frame frame = (Frame)btn.getParent();
        System.out.println("Yo");
        Graphics g = frame.getGraphics();
        g.drawString("Locate the region for 0\u00B0 Polarization", 10, 10);
        frame.setForeground(Color.BLACK);
        frame.setSize(500, 500);
        frame.print(g); 
        frame.repaint();
    }
    
}

class CancelBtnListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        Button btn = (Button)e.getSource();
        Frame frame = (Frame)btn.getParent();

        frame.setVisible(false);
        frame.dispose();
    }
    
}

class ApplyBtnListener implements ActionListener{
    static byte counter = 0;    // keeps track of how many times the apply button has been pushed.
    @Override
    public void actionPerformed(ActionEvent e) {
        Button applyButton = (Button)e.getSource();
        ImageExcerpterGUI frame = (ImageExcerpterGUI)applyButton.getParent();
        TextField textField;
        
        
        //imp1.setRoi( sourcePoints );
        switch (counter) {
            case 0:
                textField = frame.get_pole0Label();
                printROI(frame, textField);
            break;

            case 1:
                textField = frame.get_pole45Label();
                printROI(frame, textField);
            break;

            case 2:
                textField = frame.get_pole90Label();
                printROI(frame, textField);
            break;

            case 3:
                textField = frame.get_pole135Label();
                printROI(frame, textField);
            break;

        }

        counter++;
    }

    private void printROI(ImageExcerpterGUI frame, TextField textField) {
        Rectangle roiRegion = frame.get_imp().getRoi().getBounds();
        int[] bottomCorner = {roiRegion.width + roiRegion.x, roiRegion.height + roiRegion.y};
        textField.setText("[[ " + roiRegion.x + ", " + roiRegion.y + 
            " ], [ " + bottomCorner[0] + ", " + bottomCorner[1] + " ]]");
    }

}
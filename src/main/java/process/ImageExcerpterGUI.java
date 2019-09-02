package EventHandling;

import java.awt.*;
import java.awt.event.*;

class ImageExcerpterGUI extends Frame{
    private static final long serialVersionUID = 1L;
    
    // Buttons
    final private Button okBtn = new Button("ok");
    final private Button applyBtn = new Button("apply");
    final private Button cencelBtn = new Button("Cancel");

    // Text showing the polarization.
    final private Label message = new Label("Excerpt the polarization region from the image and click apply");
    final private Label pole0Text = new Label("0-degree polarization");
    final private Label pole45Text = new Label("45-degree polarization");
    final private Label pole90Text = new Label("90-degree polarization");
    final private Label pole135Text = new Label("135-degree polarization");
    
    // Text containing the polarization pixels.
    final private TextField pol0Label = new TextField("[ ]");
    final private TextField pol45Label = new TextField("[ ]");
    final private TextField pol90Label = new TextField("[ ]");
    final private TextField pol135Label = new TextField("[ ]");

    // Window closing listener
    final private WindowClosingListener wcl = new WindowClosingListener();

    public ImageExcerpterGUI(){
        // Adding the window listener.
        addWindowListener(this.wcl);
        cencelBtn.addActionListener(new CancelBtnListener());
        okBtn.addActionListener(new OkBtnListener());
        applyBtn.addActionListener(new ApplyBtnListener());


        setSize(400, 400);
        setTitle("Handling key events");
        setResizable(false);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraint = new GridBagConstraints();
        int[] wp = {0, 0, 0, 0, 0, 0};    // Denotes the weights and padding associated with each component.

        wp[0] = 1; wp[1] = 1; wp[2] = 2; wp[3] = 1; wp[4] = 0; wp[5] = 0;
        defineGrid(layout, constraint, pole0Text, wp);

        wp[0] = 1; wp[1] = 1; wp[2] = 2; wp[3] = 0; wp[4] = 1; wp[5] = 0;
        defineGrid(layout, constraint, pol0Label, wp);

        wp[0] = 1; wp[1] = 1; wp[2] = 2; wp[3] = 0; wp[4] = 0; wp[5] = 1;
        defineGrid(layout, constraint, pole45Text, wp);

        wp[0] = 1; wp[1] = 1; wp[2] = 2; wp[3] = 1; wp[4] = 1; wp[5] = 1;
        defineGrid(layout, constraint, pol45Label, wp);

        wp[0] = 1; wp[1] = 1; wp[2] = 2; wp[3] = 1; wp[4] = 0; wp[5] = 2;
        defineGrid(layout, constraint, pole90Text, wp);

        wp[0] = 1; wp[1] = 1; wp[2] = 2; wp[3] = 1; wp[4] = 1; wp[5] = 2;
        defineGrid(layout, constraint, pol90Label, wp);

        wp[0] = 1; wp[1] = 1; wp[2] = 2; wp[3] = 1; wp[4] = 0; wp[5] = 3;
        defineGrid(layout, constraint, pole135Text, wp);

        wp[0] = 1; wp[1] = 1; wp[2] = 2; wp[3] = 1; wp[4] = 1; wp[5] = 3;
        defineGrid(layout, constraint, pol135Label, wp);
        
        wp[0] = 1; wp[1] = 1; wp[2] = 0; wp[3] = 0; wp[4] = 0; wp[5] = 4;
        defineGrid(layout, constraint, okBtn, wp);

        wp[0] = 1; wp[1] = 1; wp[2] = 0; wp[3] = 1; wp[4] = 1; wp[5] = 4;
        defineGrid(layout, constraint, applyBtn, wp);
        
        wp[0] = 1; wp[1] = 1; wp[2] = 10; wp[3] = 1; wp[4] = 2; wp[5] = 4;
        defineGrid(layout, constraint, cencelBtn, wp);

        setLayout( layout );
        setVisible(true);
        repaint();
    }

    private void defineGrid(GridBagLayout layout, GridBagConstraints cst, Component comp, int[] wp) {
      //  cst.gridwidth = gridWidth; // Sets the length of this grid. When we say relative, means continue on the same grid
        
        cst.ipadx = wp[0]; // This is the size of the x direction of the component relative to
        cst.ipady = wp[1]; // This is the size of the y direction of the component relative to
        
        cst.weightx = wp[2];    // The x spacing of the components
        cst.weighty = wp[3];    // The y spacing of the components

        cst.gridx = wp[4];
        cst.gridy = wp[5];

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

    public static void main(String[] args) {
        ImageExcerpterGUI e3 = new ImageExcerpterGUI();
        
        OkBtnListener okBtnListener = new OkBtnListener();
        CancelBtnListener cancelBtnListener = new CancelBtnListener();

     //   e3.addOkBtnListener(okBtnListener);
    //    e3.addCancelBtnListener(cancelBtnListener);
        //e3.setVisible(true);
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
        
        switch (counter) {
            case 0:
                textField = frame.get_pole0Label();
                textField.setText("Polarization 0");
            break;

            case 1:
                textField = frame.get_pole45Label();
                textField.setText("Polarization 45");
            break;

            case 2:
                textField = frame.get_pole90Label();
                textField.setText("Polarization 90");
            break;

            case 3:
                textField = frame.get_pole135Label();
                textField.setText("Polarization 135");                
            break;

        }

        counter++;
    }

}
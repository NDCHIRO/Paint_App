//package lec9_4_1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

enum DrawingStyle {FREE, LINE, RECT, CIRC};

class PaintPanel extends JPanel {
    private ArrayList list = new ArrayList(); //without generics

    public void addShape(Shape s){
        if(s!=null)
            list.add(s);
    }

    public void removeShape(Shape s){
        if(s!=null)
            list.remove(s);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        for(int i=0;i<list.size();i++)
            ((Shape)list.get(i)).draw(g);
    }
}

abstract class Shape {
    protected int x;
    protected int y;
    protected Color c;
    protected boolean f;

    public Shape (int x, int y, Color c, boolean f){
        this.x=x;
        this.y=y;
        this.c=c;
        this.f=f;
    }

    public abstract void draw(Graphics g);
}

class Oval extends Shape {
    private int w;
    private int h;

    public Oval (int x, int y, int w, int h, Color c, boolean f){
        super(x,y,c,f);
        this.w=w;
        this.h=h;
    }

    public void draw(Graphics g){
        g.setColor(c);
        if(f)
            g.fillOval(x,y,w,h);
        else
            g.drawOval(x,y,w,h);
    }
}

class Rect extends Shape {
    private int w;
    private int h;

    public Rect (int x, int y, int w, int h, Color c, boolean f){
        super(x,y,c,f);
        this.w=w;
        this.h=h;
    }

    public void draw(Graphics g){
        g.setColor(c);
        if(f)
            g.fillRect(x,y,w,h);
        else
            g.drawRect(x,y,w,h);
    }
}

class Line extends Shape {
    private int x2;
    private int y2;

    public Line (int x, int y, int x2, int y2, Color c, boolean f){
        super(x,y,c,f);
        this.x2=x2;
        this.y2=y2;
    }

    public void draw(Graphics g){
        g.setColor(c);
        g.drawLine(x, y, x2, y2);
    }
}


class PaintFrame extends JFrame implements ActionListener,
        AdjustmentListener {
    private Color col = Color.BLACK;
    private DrawingStyle sty = DrawingStyle.FREE;
    private int x1;
    private int y1;
    private Shape oldShape = null;

    private PaintPanel pnlDraw = new PaintPanel();
    private JPanel pnlRight = new JPanel();
    private JPanel pnlBottom = new JPanel();

    private JButton btnFree = new JButton("Free");
    private JButton btnLine = new JButton("Line");
    private JButton btnRect = new JButton("Rectangle");
    private JButton btnCirc = new JButton("Circle");
    private JCheckBox chkFilled = new JCheckBox("Filled");

    private JPanel pnlShape = new JPanel();

    private JButton btnBlack = new JButton("Black");
    private JButton btnRed = new JButton("Red");
    private JButton btnGreen = new JButton("Green");
    private JButton btnBlue = new JButton("Blue");
    private JButton btnErase = new JButton("Erase");
    private JButton btnOther = new JButton("Other...");
    private JPanel pnlSample = new JPanel();

    private JPanel pnlColor = new JPanel();

    private JPanel pnlLabels = new JPanel();
    private JPanel pnlScroll = new JPanel();
    private JPanel pnlText = new JPanel();

    private JLabel lblRed = new JLabel("Red");
    private JLabel lblGreen = new JLabel("Green");
    private JLabel lblBlue = new JLabel("Blue");

    private JTextField txtRed = new JTextField("0", 3);
    private JTextField txtGreen = new JTextField("0", 3);
    private JTextField txtBlue = new JTextField("0", 3);

    private JScrollBar scrRed = new JScrollBar(JScrollBar.HORIZONTAL, 0, 10, 0, 265);
    private JScrollBar scrGreen = new JScrollBar(JScrollBar.HORIZONTAL, 0, 10, 0, 265);
    private JScrollBar scrBlue = new JScrollBar(JScrollBar.HORIZONTAL, 0, 10, 0, 265);

    private JMenuBar bar = new JMenuBar();
    private JMenu mnuFile = new JMenu("File");
    private JMenu mnuColor = new JMenu("Color");
    private JMenu mnuShape = new JMenu("Shape");
    private JMenu mnuHelp = new JMenu("Help");

    private JMenuItem mniNew = new JMenuItem("New");
    private JMenuItem mniOpen = new JMenuItem("Open...");
    private JMenuItem mniSave = new JMenuItem("Save...");
    private JMenuItem mniExit = new JMenuItem("Exit");

    private JMenuItem mniFree = new JMenuItem("Free");
    private JMenuItem mniLine = new JMenuItem("Line");
    private JMenuItem mniRect = new JMenuItem("Rectangle");
    private JMenuItem mniCirc = new JMenuItem("Circle");
    private JCheckBoxMenuItem mniFilled = new JCheckBoxMenuItem("Filled");

    private JMenuItem mniBlack = new JMenuItem("Black");
    private JMenuItem mniRed = new JMenuItem("Red");
    private JMenuItem mniGreen = new JMenuItem("Green");
    private JMenuItem mniBlue = new JMenuItem("Blue");
    private JMenuItem mniErase = new JMenuItem("Erase");
    private JMenuItem mniOther = new JMenuItem("Other...");

    private JMenuItem mniAbout = new JMenuItem("About...");

    public PaintFrame(){
        init();
    }

    private void init() {
        this.setTitle("First Paint App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 800, 600);
        this.setMinimumSize(new Dimension(400,300));
        Container c = this.getContentPane();
        //c.setLayout(new BorderLayout());

        mnuFile.add(mniNew);
        mnuFile.add(mniOpen);
        mnuFile.add(mniSave);
        mnuFile.add(mniExit);

        mnuColor.add(mniBlack);
        mnuColor.add(mniRed);
        mnuColor.add(mniGreen);
        mnuColor.add(mniBlue);
        mnuColor.addSeparator();
        mnuColor.add(mniErase);
        mnuColor.addSeparator();
        mnuColor.add(mniOther);

        mnuShape.add(mniFree);
        mnuShape.add(mniLine);
        mnuShape.add(mniRect);
        mnuShape.add(mniCirc);
        mnuShape.addSeparator();
        mnuShape.add(mniFilled);

        mnuHelp.add(mniAbout);

        bar.add(mnuFile);
        bar.add(mnuShape);
        bar.add(mnuColor);
        bar.add(mnuHelp);

        this.setJMenuBar(bar);

        pnlShape.setLayout(new GridLayout(5,1));
        pnlShape.add(btnFree);
        pnlShape.add(btnLine);
        pnlShape.add(btnRect);
        pnlShape.add(btnCirc);
        pnlShape.add(chkFilled);
        pnlShape.setBorder(BorderFactory.createTitledBorder("Shape"));

        pnlSample.setBackground(Color.black);

        pnlColor.setLayout(new GridLayout(7,1));
        pnlColor.add(btnBlack);
        pnlColor.add(btnRed);
        pnlColor.add(btnGreen);
        pnlColor.add(btnBlue);
        pnlColor.add(btnErase);
        pnlColor.add(btnOther);
        pnlColor.add(pnlSample);
        pnlColor.setBorder(BorderFactory.createTitledBorder("Color"));

        pnlRight.setLayout(new GridLayout(2,1));
        pnlRight.add(pnlShape);
        pnlRight.add(pnlColor);

        pnlLabels.setLayout(new GridLayout(3,1));
        pnlScroll.setLayout(new GridLayout(3,1));
        pnlText.setLayout(new GridLayout(3,1));

        pnlLabels.add(lblRed);
        pnlLabels.add(lblGreen);
        pnlLabels.add(lblBlue);

        pnlScroll.add(scrRed);
        pnlScroll.add(scrGreen);
        pnlScroll.add(scrBlue);

        pnlText.add(txtRed);
        pnlText.add(txtGreen);
        pnlText.add(txtBlue);

        pnlBottom.setLayout(new BorderLayout());
        pnlBottom.add(pnlLabels,BorderLayout.WEST);
        pnlBottom.add(pnlText,BorderLayout.EAST);
        pnlBottom.add(pnlScroll);

        //pnlRight.setBackground(Color.red);
        //pnlBottom.setBackground(Color.blue);
        pnlDraw.setBackground(Color.white);

        c.add(pnlRight,BorderLayout.EAST);
        c.add(pnlBottom,BorderLayout.SOUTH);
        //c.add(pnlDraw,BorderLayout.CENTER);
        c.add(pnlDraw); //CENTER will be used by default

        pnlDraw.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                x1= e.getX();
                y1= e.getY();
            }

            public void mouseReleased(MouseEvent e){
                oldShape = null;
            }
        });

        pnlDraw.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                int x2 = e.getX();
                int y2 = e.getY();

                Shape s = null;
                if(sty!=DrawingStyle.FREE){
                    pnlDraw.removeShape(oldShape);
                    if(sty==DrawingStyle.CIRC)
                        s= new Oval(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x2-x1),Math.abs(y2-y1),col,chkFilled.isSelected());
                    else if(sty==DrawingStyle.RECT)
                        s= new Rect(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x2-x1),Math.abs(y2-y1),col,chkFilled.isSelected());
                    else if(sty==DrawingStyle.LINE)
                        s= new Line(x1,y1,x2,y2,col,chkFilled.isSelected());
                    oldShape=s;
                } else {
                    s = new Line(x1,y1,x2,y2,col,chkFilled.isSelected());
                    x1=x2;
                    y1=y2;
                }
                pnlDraw.addShape(s);
                pnlDraw.repaint();
            }
        });


        btnBlack.addActionListener(this);
        btnRed.addActionListener(this);
        btnGreen.addActionListener(this);
        btnBlue.addActionListener(this);
        btnErase.addActionListener(this);
        btnOther.addActionListener(this);
        mniBlack.addActionListener(this);
        mniRed.addActionListener(this);
        mniGreen.addActionListener(this);
        mniBlue.addActionListener(this);
        mniErase.addActionListener(this);
        mniOther.addActionListener(this);


        scrRed.addAdjustmentListener(this);
        scrGreen.addAdjustmentListener(this);
        scrBlue.addAdjustmentListener(this);

        btnFree.addActionListener(this);
        btnLine.addActionListener(this);
        btnRect.addActionListener(this);
        btnCirc.addActionListener(this);
        mniFree.addActionListener(this);
        mniLine.addActionListener(this);
        mniCirc.addActionListener(this);
        mniRect.addActionListener(this);

    }

    private void setColor(Color c){
        col=c;
        pnlSample.setBackground(c);
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        txtRed.setText(r+"");
        txtGreen.setText(g+"");
        txtBlue.setText(b+"");
        scrRed.setValue(r);
        scrGreen.setValue(g);
        scrBlue.setValue(b);
    }

    public void adjustmentValueChanged(AdjustmentEvent e){
        int r = scrRed.getValue();
        int g = scrGreen.getValue();
        int b = scrBlue.getValue();
        setColor(new Color(r,g,b));
    }

    public void actionPerformed(ActionEvent e){
        Object o = e.getSource();
        if((o==btnBlack)||(o==mniBlack))
            setColor(Color.BLACK);
        else if((o==btnRed)||(o==mniRed))
            setColor(Color.RED);
        else if((o==btnGreen)||(o==mniGreen))
            setColor(Color.GREEN);
        else if((o==btnBlue)||(o==mniBlue))
            setColor(Color.BLUE);
        else if((o==btnErase)||(o==mniErase))
            setColor(pnlDraw.getBackground());
        else if((o==btnOther)||(o==mniOther)){
            Color c = JColorChooser.showDialog(this, "Select a Color", col);
            if(c!=null)
                setColor(c);
        }
        else if((o==btnFree)||(o==mniFree))
            sty = DrawingStyle.FREE;
        else if((o==btnLine)||(o==mniLine))
            sty = DrawingStyle.LINE;
        else if((o==btnRect)||(o==mniRect))
            sty = DrawingStyle.RECT;
        else if((o==btnCirc)||(o==mniCirc))
            sty = DrawingStyle.CIRC;

    }
}

public class Main {
    public static void main(String[] args) {
        PaintFrame f = new PaintFrame();
        f.setVisible(true);
    }
}

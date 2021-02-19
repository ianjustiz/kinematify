import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KinematicUI implements ActionListener {
    Double[] kinematics = new Double[5];
    String[] kinematicsVars = new String[] {"a", "x", "vi", "vf", "t"};

    JTextField inputA = new JTextField("");
    JTextField inputX = new JTextField("");
    JTextField inputVF = new JTextField("");
    JTextField inputVI = new JTextField("");
    JTextField inputT = new JTextField("");

    public KinematicUI() {
        JFrame jfrm = new JFrame("Kinematics");
        jfrm.setSize(600, 200);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridLayout gl = new GridLayout(6, 2);
        jfrm.setLayout(gl);

        JButton select = new JButton("Input");
        select.addActionListener(this);

        JLabel acceleration = new JLabel("Acceleration:");
        JLabel finalVelocity = new JLabel("Final Velocity:");
        JLabel initialVelocity = new JLabel("Initial Velocity:");
        JLabel displacement = new JLabel("Displacement:");
        JLabel time = new JLabel("Time:");
        JLabel blank = new JLabel("");

        jfrm.add(acceleration);
        jfrm.add(time);
        jfrm.add(inputA);
        jfrm.add(inputT);
        jfrm.add(finalVelocity);
        jfrm.add(initialVelocity);
        jfrm.add(inputVF);
        jfrm.add(inputVI);
        jfrm.add(displacement);
        jfrm.add(blank);
        jfrm.add(inputX);
        jfrm.add(select);

        jfrm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KinematicUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
            kinematics[0] = Double.parseDouble(inputA.getText());
        }
        catch(Exception e) {
            kinematics[0] = null;
        }

        try {
            kinematics[1] = Double.parseDouble(inputX.getText());
        }
        catch(Exception e) {
            kinematics[1] = null;
        }

        try {
            kinematics[2] = Double.parseDouble(inputVI.getText());
        }
        catch(Exception e) {
            kinematics[2] = null;
        }

        try {
            kinematics[3] = Double.parseDouble(inputVF.getText());
        }
        catch(Exception e) {
            kinematics[3] = null;
        }

        try {
            kinematics[4] = Double.parseDouble(inputT.getText());
        }
        catch(Exception e) {
            kinematics[4] = null;
        }

        try {
            for (int i = 0; i < kinematics.length; i++) {
                System.out.println(kinematicsVars[0] + " is " +  kinematics[0]);
                System.out.println(kinematicsVars[1] + " is " +  kinematics[1]);
                System.out.println(kinematicsVars[2] + " is " +  kinematics[2]);
                System.out.println(kinematicsVars[3] + " is " +  kinematics[3]);
                System.out.println(kinematicsVars[4] + " is " +  kinematics[4]);
                if(kinematics[i] == null) {
                    switch(kinematicsVars[i]) {
                        case "a":
                            kinematics[i] = LinearKinematicsEquations.findAcceleration(kinematics[1], kinematics[3], kinematics[2], kinematics[4]);
                            break;
                        case "x":
                            kinematics[i] = LinearKinematicsEquations.findDisplacement(kinematics[0], kinematics[3], kinematics[2], kinematics[4]);
                            break;
                        case "vi":
                            kinematics[i] = LinearKinematicsEquations.findInitialVelocity(kinematics[1], kinematics[0], kinematics[3], kinematics[4]);
                            break;
                        case "vf":
                            kinematics[i] = LinearKinematicsEquations.findFinalVelocity(kinematics[1], kinematics[0], kinematics[2], kinematics[4]);
                            break;
                        case "t":
                            kinematics[i] = LinearKinematicsEquations.findTime(kinematics[1], kinematics[0], kinematics[3], kinematics[2]);
                            break;
                    }
                    JOptionPane.showMessageDialog(null, kinematicsVars[i] + " is " + (double)Math.round(kinematics[i] * 100) / 100);
                }
            }
        }
        catch(LinearKinematicsEquations.CannotSolveException cse) {
            JOptionPane.showMessageDialog(null, "Invalid Input");
        }
    }
}

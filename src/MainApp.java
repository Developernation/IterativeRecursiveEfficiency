import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Thomas Hodges on 9/23/2015.
 * Project: HodgesCMIS242Project3
 * Filename: MainApp.java
 *
 * Course: CMIS 242
 * Professor: Ioan Salomie
 * Assignment: Project 3
 *
 * Platform: Windows 10, IntelliJ IDEA 14.1.4
 * Compiler: jdk1.8.0_45
 */
public class MainApp extends JFrame {

    // Data fields that are for the look of the program
    static final int WINDOWWIDTH = 350, WINDOWHEIGHT = 200;

    // Data fields for the construction of the JFrame elements
    private JLabel enterNLabel = new JLabel("Enter n:");
    private JLabel resultLabel = new JLabel("Result:");
    private JLabel efficiencyLabel = new JLabel("Efficiency:");
    private JTextField efficiencyOutput = new JTextField("");
    private JTextField resultOutput = new JTextField("");
    private JRadioButton iterativeRadio = new JRadioButton("Iterative");
    private JRadioButton recursiveRadio = new JRadioButton("Recursive");
    private JButton computeButton = new JButton("Compute");
    private JTextField entry = new JTextField("");
    private ButtonGroup radios = new ButtonGroup();
    private JOptionPane frame = new JOptionPane();
    private JLabel blankLabel = new JLabel("");

    private void setFrame(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
    }

    public void display() {
        setVisible(true);
    }

    public MainApp() {
        super("Project 3");
        setFrame(WINDOWWIDTH, WINDOWHEIGHT);
        setResizable(false);
        JPanel mainPanel = new JPanel();
        add(mainPanel);
        mainPanel.setLayout(new GridLayout(5, 2, 0, 10));
        radios.add(iterativeRadio);
        radios.add(recursiveRadio);
        mainPanel.add(iterativeRadio);
        mainPanel.add(recursiveRadio);
        iterativeRadio.setSelected(true);
        mainPanel.add(enterNLabel);
        mainPanel.add(entry);
        mainPanel.add(blankLabel);
        mainPanel.add(computeButton);
        mainPanel.add(resultLabel);
        mainPanel.add(resultOutput);
        mainPanel.add(efficiencyLabel);
        mainPanel.add(efficiencyOutput);

        computeButton.addActionListener(new ComputeButtonListener());
        CloseApplicationWriteFileListener close = new CloseApplicationWriteFileListener();
        addWindowListener(close);
    }

    class ComputeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (iterativeRadio.isSelected()) {
                resultOutput.setText(String.valueOf(Sequence.computeIterative(getEntryValue())));
                efficiencyOutput.setText(String.valueOf(Sequence.getEfficiency()));
            } else if (recursiveRadio.isSelected()) {
                resultOutput.setText(String.valueOf(Sequence.computeRecursive(getEntryValue())));
                efficiencyOutput.setText(String.valueOf(Sequence.getEfficiency()));
            }
        }

    }

    class CloseApplicationWriteFileListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            // TODO - complete file writer
            System.out.println("Closing window.");
            System.exit(0);
        }
    }

    // This method returns the text in the text entry field as an int
    public int getEntryValue() {
        try {
            return Integer.parseInt(entry.getText());
        } catch (NumberFormatException e) {
            System.out.println("Caught in getEntryValue\n");
            errorPopUp();
            clearEntryValue();
            return 0;
        }
    }

    // Clears the text entry field
    public void clearEntryValue() {
        entry.setText("");
    }


    public void errorPopUp() {
        JOptionPane.showMessageDialog(frame, "Invalid input.");
    }

    public static void main(String[] args) {
        MainApp app = new MainApp();
        app.display();
    }
}

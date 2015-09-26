import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

    // File-specific variables, fileWriter writes to log
    private static FileWriter fileWriter;
    private File log = new File("log.csv");
    private File outIter = new File("outIter.txt");
    private File outRec = new File("outRec.txt");

    // Entry and efficiency values are stored as a string in this list
    // before being written to the log.csv file
    private ArrayList<String> logList = new ArrayList<>();
    private ArrayList<String> listIter = new ArrayList<>();
    private ArrayList<String> listRec = new ArrayList<>();

    // Variable to hold the user's input value
    private int entryValue;

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
        CloseApplicationWriteFileAdapter close = new CloseApplicationWriteFileAdapter();
        addWindowListener(close);
    }

    /**
     * ComputeButtonListener performs its action when the Compute button is pressed.
     * It determines that valid entry was entered first, then the radio selection to
     * set the output fields' values and run methods that add those values to each
     * list as defined at the beginning of the MainApp class.
     */
    class ComputeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setEntryValue();
            if (entryValue > 10 || entryValue < 0) {
                errorPopUp();
            } else if (iterativeRadio.isSelected()) {
                resultOutput.setText(String.valueOf(Sequence.computeIterative(entryValue)));
                efficiencyOutput.setText(String.valueOf(Sequence.getEfficiency()));
                addToCSV("Iterative");
                addToListIter();
            } else if (recursiveRadio.isSelected()) {
                resultOutput.setText(String.valueOf(Sequence.computeRecursive(entryValue)));
                efficiencyOutput.setText(String.valueOf(Sequence.getEfficiency()));
                addToCSV("Recursive");
                addToListRec();
            }
            clearEntryValue();
        }
    }

    /**
     * CloseApplicationWriteFileAdapter replaces the normal EXIT_ON_CLOSE method
     * that would usually be used as a default close operation. This is because
     * the files defined at the beginning of the MainApp class need to be written
     * to the computer before the program exits. This class will attempt the
     * writeFiles method before stopping the program.
     */
    class CloseApplicationWriteFileAdapter extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            try {
                if (!logList.isEmpty()) {
                    writeFiles();
                    fileWriter.close();
                }
                System.out.println("Try statement close");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Caught IOException");
                System.exit(0);
            } catch (NullPointerException e1) {
                e1.printStackTrace();
                System.out.println("Caught null pointer");
                System.exit(0);
            }
            System.exit(0);
        }
    }

    // Adds a label for the radio selection, efficiency, and entry to the logList array.
    public void addToCSV(String choice) {
        logList.add(choice + ", " + Sequence.getEfficiency() + ", " + entryValue);
    }

    // Adds the efficiency and entry value to the listIter array.
    public void addToListIter() {
        listIter.add(Sequence.getEfficiency() + ", " + entryValue);
    }

    // Adds the efficiency and entry value to the listRec array.
    public void addToListRec() {
        listRec.add(Sequence.getEfficiency() + ", " + entryValue);
    }

    // This method writes 3 files to the hard drive for a CSV and txt for each array
    public void writeFiles() {

        try {

            // First writes the CSV file
            fileWriter = new FileWriter(log);
            for (String l : logList) {
                fileWriter.write(l + System.getProperty("line.separator"));
            }

            fileWriter.close();

            // Then writes the outIter.txt file
            fileWriter = new FileWriter(outIter);

            for (String l : listIter) {
                fileWriter.write(l + System.getProperty("line.separator"));
            }

            fileWriter.close();

            // Last writes the outRec.txt file
            fileWriter = new FileWriter(outRec);

            for (String l : listRec) {
                fileWriter.write(l + System.getProperty("line.separator"));
            }

            fileWriter.close();

        } catch (IOException e) {
            e.getMessage();
        }
    }

    // This method returns the text in the text entry field as an int
    public int getEntryValue() {
        try {
            return Integer.parseInt(entry.getText());
        } catch (NumberFormatException e) {
            clearEntryValue();
            return 11; // Return 11 to prevent log file write
        }
    }

    public void setEntryValue() {
        this.entryValue = getEntryValue();
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

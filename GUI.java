import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI extends JFrame{

	private static final long serialVersionUID = 1L;
	JFrame frame;
	Container content;
	JPanel buttons;
	static GraphPicturePanel picture;
	static JButton addAllEButton, connectedCompButton, showCutVButton, helpButton;
	static JRadioButton addVRadio, addERadio, removeVRadio, removeERadio, moveVRadio;
	static CustomActionListener cal = new CustomActionListener();
	static MouseActionListener mos = new MouseActionListener();
	static ButtonGroup radioGroup = new ButtonGroup();

	
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
	    }
	    new GUI(); 
	}
	

	//GraphGui constructor
	public GUI() {
		frame = new JFrame("CS313 Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 450);
		content = frame.getContentPane();
		buttons = new JPanel();
		picture = new GraphPicturePanel();
		picture.addMouseListener(mos);
        
		// add radios and buttons to panel
		addVRadio = addRadio(buttons, "Add Vertex", cal);
		addERadio = addRadio(buttons, "Add Edge", cal);
		removeVRadio = addRadio(buttons, "Remove Vertex", cal);
		removeERadio = addRadio(buttons, "Remove Edge", cal);
		moveVRadio = addRadio(buttons, "Move Vertex", cal);
		addAllEButton = addButton(buttons, "Add All Edges", cal);
		connectedCompButton = addButton(buttons, "Connected Components", cal);
		showCutVButton = addButton(buttons, "Show Cut Vertices", cal);
		helpButton = addButton(buttons, "Help", cal);

		// adding components to frame
		buttons.setLayout(new GridLayout(10, 1));
		content.setLayout(new BorderLayout());
        content.add(buttons, BorderLayout.WEST);
        content.add(picture, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	//makes radio button and adds to panel
	public static JRadioButton addRadio(JPanel p, String name, ActionListener act) {
		JRadioButton  radio = new JRadioButton(name);
		p.add(radio);
		radio.addActionListener(act);
		radioGroup.add(radio);
		return radio;
	}
	
	//makes button and adds to panel
	public static JButton addButton(JPanel p, String name, ActionListener act) {
		JButton  button = new JButton(name);
		p.add(button);
		button.addActionListener(act);  
		return button;
	}
	
	// when function is called it releases all radios
	public static void releaseRadios(ButtonGroup radioGroup) {
		radioGroup.clearSelection();
	}
}
	
	





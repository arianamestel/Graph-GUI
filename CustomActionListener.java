import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

class CustomActionListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof JButton) {
			GUI.releaseRadios(GUI.radioGroup);
		}
		if (e.getActionCommand().contains("Help")) {
			String help = "This is the Help Page. Use the radio buttons on the left to chose an action and use your mouse to paint or select vertecies.";
			JOptionPane.showMessageDialog(null, help);
		}
		if (e.getActionCommand().contains("Add All Edges")) {
			GUI.picture.addAllEdges();
		}
		if (e.getActionCommand().contains("Connected Components")) {
			GUI.picture.connect();
		}
		if (e.getActionCommand().contains("Show Cut Vertices")) {
			GUI.picture.cut();
		}

	}
}
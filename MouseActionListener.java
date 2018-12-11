import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

class MouseActionListener extends MouseAdapter {
	static int click = 0;

	public void mousePressed(MouseEvent e) {
		JPanel panel = (JPanel) e.getSource();
		
		if (GUI.addVRadio.isSelected()) {
			addVertex(e, panel);
		}
		if (GUI.addERadio.isSelected()) {
			selectVertices(e, panel);
		}
		if (GUI.removeVRadio.isSelected()) {
			selectVertexToRemove(e, panel);
		}
		if (GUI.removeERadio.isSelected()) {
			selectEdgeToRemove(e, panel);
		}
		if (GUI.moveVRadio.isSelected()) {
			selectedVertexToMoveAndNewVertex(e, panel);
		}
		
	}
	
	public void addVertex(MouseEvent e, JPanel panel) {
	    GUI.picture.graph.addVert(new Vertex(e.getX(), e.getY()));
        System.out.println("(" + e.getX() + "," + e.getY() + ")");
        panel.repaint();
	}
	
	public boolean closeEnoughSelection(Vertex i, Vertex v) {
		// if selected point is close enough to the intended vertex it will be considered close enough
		 if ((Math.abs(i.getX() - v.getX()) < 10) && (Math.abs(i.getY() - v.getY()) < 10)) {
			System.out.println("close enough");
			return true;
		 }
		 return false;
	}
	
	public void selectVertices(MouseEvent e, JPanel panel) {
		Vertex init = new Vertex(e.getX(), e.getY());
		System.out.println( init.getX() + " "+ init.getY());
		for (Vertex vertex: GUI.picture.graph.getGraphVerts()) {
			if (closeEnoughSelection(init, vertex)) {
				GUI.picture.setSelectedVertex(vertex);
			}
			panel.repaint();
		}
	}
	
	public void selectVertexToRemove(MouseEvent e, JPanel panel) {
		Vertex selected = new Vertex(e.getX(), e.getY());
		for (Vertex vertex: GUI.picture.graph.getGraphVerts()) {
			if (closeEnoughSelection(selected, vertex)) {
				GUI.picture.setSelectedRemoveVertex(vertex);
				break;
			}
			if (GUI.picture.graph.getGraphVerts().isEmpty()) break;
		}
	}
	
	public void selectEdgeToRemove(MouseEvent e, JPanel panel) {
		Vertex select = new Vertex(e.getX(), e.getY());
		for (Edge edge: GUI.picture.graph.getGraphEdges()) {
			if (edge.isOnLine(select)) {
				GUI.picture.graph.getGraphEdges().remove(edge);
				panel.repaint();
				break;
			}
		}
	}
	
	public void selectedVertexToMoveAndNewVertex(MouseEvent e, JPanel panel) {
		Vertex selected = new Vertex(e.getX(), e.getY());
		boolean tracker = false;
		if (click == 0) {
			for (Vertex vertex: GUI.picture.graph.getGraphVerts()) {
				if (closeEnoughSelection(selected, vertex)) {
					GUI.picture.setSelectedVertexToMove(vertex);
					System.out.println("Old (" + vertex.getX() + "," + vertex.getY() + ")");
					tracker = true;
					GUI.picture.repaint();
					break;
				}
			}
		}
		if (click == 1) {
			GUI.picture.setSelectedNewVertexToMoveTo(selected);
			System.out.println("New: (" + e.getX() + "," + e.getY() + ")");
			tracker = true;
			click++;
		}
		if (click == 2) {
			click = 0;
			tracker = false;
		}
		if (!GUI.picture.graph.getGraphVerts().isEmpty() && tracker) click++;
		
	}
}
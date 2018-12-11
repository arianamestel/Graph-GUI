import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

class GraphPicturePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Vertex selectedRemoveVertex, selectedMoveVertex, selectedNewMoveVertex;
	ArrayList<Vertex> selectedVertices = new ArrayList<>();
	boolean moveTracker = false;
	Graph graph;
	Boolean connectTracker = false;
	Boolean cutTracker = false;
	

	public GraphPicturePanel() {
		graph = new Graph();
		setBackground(Color.WHITE);
	}
	
	 public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		//Draws points
		drawPoints(g2);	
		//if there is a vertex to move, move it to new vertex
		moveVertex(g2);
		//if 2 vertexes are selected it makes an edge
		createEdge();
		//Draws edges
		drawEdges(g2);
	 }
	 

	 
	public void setSelectedVertex(Vertex v) {
		 selectedVertices.add(v);
	 }
	 
	public void setSelectedRemoveVertex(Vertex v) {
		selectedRemoveVertex = v;
		//removes any edges that are connected to the vertex being removed
		ArrayList<Edge> connectedEdges = new ArrayList<>();
		for (Edge edge: graph.getGraphEdges()) {
			if (edge.init.equals(v) || edge.term.equals(v)) {
				connectedEdges.add(edge);
			}
		}
		for (Edge conEd: connectedEdges) {
			graph.getGraphEdges().remove(conEd);
		}
		graph.getGraphVerts().remove(v);
		repaint(); 
	}
	
	public void setSelectedVertexToMove(Vertex v) {
		selectedMoveVertex = v;
	}
	
	public void setSelectedNewVertexToMoveTo(Vertex v) {
		selectedNewMoveVertex = v;
		moveTracker = true;
		repaint();
	}
	
	public void moveVertex(Graphics2D g) {
		if (moveTracker) {
			int index = 0;
			for (Vertex vertex: graph.getGraphVerts()) {
				if (selectedMoveVertex.equals(vertex)) {
					graph.getGraphVerts().set(index, selectedNewMoveVertex);
					moveTracker = false;
					// reset the edges to use the new selected vertex
					for (Edge edge: graph.getGraphEdges()) {
						if (edge.getInit().equals(vertex)) 
							edge.setInit(selectedNewMoveVertex);
						if (edge.getTerm().equals(vertex)) 
							edge.setTerm(selectedNewMoveVertex);
					}
					repaint();
					break;
				}
				index++;
			}
		}
	}
	
	public void drawPoints(Graphics2D g) {
		for (Vertex vertex : graph.getGraphVerts()) {
			if ((selectedVertices != null && selectedVertices.contains(vertex)) || (selectedMoveVertex != null && selectedMoveVertex.equals(vertex))) {
				g.setColor(Color.green);
			}
			else {
				g.setColor(Color.red);
			}
			g.fillOval(vertex.x, vertex.y, 10, 10);
		}	
		if (cutTracker) cutVertices(g);
	}
	
	public void createEdge() {
		// checks if 2 vertices were selected and if they were, it will make an edge
		if (selectedVertices.size() == 2) {
			Vertex i = selectedVertices.get(0);
			Vertex t = selectedVertices.get(1);
			Edge e = new Edge(i, t);
			if (!e.contains(e)) {
				graph.addEdge(e);
			}
			selectedVertices.clear();
		}
	}
	
	public void drawEdges(Graphics2D g) {
		for (Edge edge: graph.getGraphEdges()) {
			g.setColor(Color.blue);
			g.setStroke(new BasicStroke(3));
			g.drawLine(edge.init.getX(), edge.init.getY(), edge.term.getX(), edge.term.getY());
		}
		if (connectTracker) connectedComponents(g);
	}
	
	public void addAllEdges() {
		graph.getGraphEdges().clear();
		for (int i = 0; i < graph.getNumVerts(); i++) {
			for (int h = 0; h < graph.getNumVerts(); h++) {
				Edge e = new Edge(graph.getGraphVerts().get(i), graph.getGraphVerts().get(h));
				if (i == h || e.contains(e)) continue;
				graph.addEdge(e);
				repaint();
			}
		}
	}
	
	public void connect() {
		connectTracker = true;
		repaint();
	}
	
	public void cut() {
		cutTracker = true;
		repaint();
	}
	
	public void connectedComponents(Graphics2D g) {
		boolean[] visited = new boolean[graph.getNumVerts()];
		for (int v = 0; v < graph.getNumVerts(); v++)
			visited[v] = false;
		for (int v = 0; v < graph.getNumVerts(); v++) {
			Color c = randomColor();
			if (!visited[v]) {
				DepthFirstSearch(v, visited, g, c);
			}
		}
		connectTracker = false;
	}
	
	
	public void DepthFirstSearch(int v, boolean[] visited, Graphics2D g, Color color) {
		visited[v] = true;
		for (int c: graph.getNeighbors(v)) {
			g.setColor(color);
			g.setStroke(new BasicStroke(3));
			g.fillOval(graph.vertices.get(v).getX(), graph.vertices.get(v).getY(), 10, 10);
			g.drawLine(graph.vertices.get(v).getX(), graph.vertices.get(v).getY(), graph.vertices.get(c).getX(), graph.vertices.get(c).getY());
			if (!visited[c]) {
				DepthFirstSearch(c, visited, g, color);
			}
		}
	}
	
	public Color randomColor() {
		Random r = new Random();
		Color randomColor = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
		return randomColor;
	}
	
	public void cutVertices(Graphics2D g) {
		int count = 0;
		boolean[] visited = new boolean[graph.getNumVerts()], cuts = new boolean[graph.getNumVerts()];
		int[] counter = new int[graph.getNumVerts()], min = new int[graph.getNumVerts()], parent = new int[graph.getNumVerts()];
		// initialize the arrays to default values
		for (int v = 0; v < graph.getNumVerts(); v++) {
			visited[v] = false;
			cuts[v] = false;
			parent[v] = -1;
		}
		for (int i = 0; i < graph.getNumVerts();i++) {
			if (!visited[i])
				//recursive helper function call to find cut vertices
				cutVertives(i, visited, cuts, counter, min, parent, count);
		}
		// goes through cut vertices and changes color so its viewable to the user
		for (int i = 0; i < graph.getNumVerts();i++) {
			if (cuts[i] == true) {
				g.setColor(Color.green);
				g.fillOval(graph.getGraphVerts().get(i).x, graph.getGraphVerts().get(i).y, 15, 15);
			}
		}
		cutTracker = false;
	}
	
	void cutVertives(int num, boolean[] visited, boolean[] cuts, int[] counter, int[] min, int[] parent,  int count) { 
    int neighbors = 0; 
    visited[num] = true; 
    counter[num] = ++count; 
    min[num] = ++count;
    for (int i: graph.getNeighbors(num)) { 
    	int v = i;
        if (!visited[v]) { 
            parent[v] = num; 
            cutVertives(v, visited, cuts, counter, min, parent, count); 
            min[num]  = Math.min(min[num], min[v]); 
            if (neighbors > 1 && parent[num] == -1) 
                cuts[num] = true; 
            if (min[v] >= counter[num] && parent[num] != -1 )
                cuts[num] = true; 
            neighbors++; 
        } 
        else if (v != parent[num]) 
            min[num]  = Math.min(min[num], counter[v]); 
    } 
} 

	
}
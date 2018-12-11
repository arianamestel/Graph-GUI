import java.awt.geom.Line2D;

class Edge {
	Vertex init;
	Vertex term;
	
	public Edge(Vertex i, Vertex t) {
		init = i;
		term = t;
	}
	
	public Vertex getInit() {
		return init;
	}

	public void setInit(Vertex init) {
		this.init = init;
	}

	public Vertex getTerm() {
		return term;
	}

	public void setTerm(Vertex term) {
		this.term = term;
	}
	
	public boolean contains(Edge e) {
		for (Edge edge: GUI.picture.graph.getGraphEdges()) {
			if ((edge.getInit() == e.getInit() && edge.getTerm() == e.getTerm()) || (edge.getInit() == e.getTerm() && edge.getTerm() == e.getInit())) return true;
		}
		return false;
	}
	public boolean isOnLine(Vertex a) {
		Line2D line = new Line2D.Float(init.getX(), init.getY(), term.getX(), term.getY());
		if (line.ptLineDist((double) a.getX(), (double) a.getY()) <= 5) return true;
		return false;
	}
	
	public Vertex getAdjVertex(Vertex v) {
		if (v.equals(this.getInit())) return this.getTerm();
		return this.getInit();
	}
	
	public boolean isVertexOfEdge(Vertex v) {
		if (getInit().equals(v) || getTerm().equals(v)) return true;
		return false;
	}
	
	
}
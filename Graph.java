import java.util.ArrayList;

class Graph {
	int numEdges;
	int numVerts;
	ArrayList<Vertex> vertices;
	ArrayList<Edge> edges;
	
	public Graph() {
		vertices = new ArrayList<>();
		edges= new ArrayList<>();
	}
	
	public int getNumEdges() {
		return edges.size();
	}

	public int getNumVerts() {
		return vertices.size();
	}
	
	public ArrayList<Vertex> getGraphVerts() {
		return vertices;
	}

	public ArrayList<Edge> getGraphEdges() {
		return edges;
	}
	
	public void addVert(Vertex v) {
		vertices.add(v);
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}
	
	// return index of all neighbors of vertex
	public ArrayList<Integer> getNeighbors(int index){
		ArrayList<Integer> neighbors = new ArrayList<>();
		for (Edge edge: edges) {
			if (edge.isVertexOfEdge(vertices.get(index))) {
				Vertex n = edge.getAdjVertex(vertices.get(index));
				neighbors.add(vertices.indexOf(n));
			}
		}
		return neighbors;
	}
	
	public boolean verticesOfAnEdge(Vertex i, Vertex t) {
		Edge test1 = new Edge(i, t);
		Edge test2 = new Edge(t, i);
		for (Edge edge: edges) 
			if (edge.equals(test1) || edge.equals(test2)) return true;
		return false;
	}
	
	
}
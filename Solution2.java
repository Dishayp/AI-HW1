import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

class Node implements Comparator<Node> {
	public int node;
	public int cost;
	public int timestamp;

	public Node() {
	}

	public Node(int node, int cost) {
		this.node = node;
		this.cost = cost;
		this.timestamp = 0;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int compare(Node node1, Node node2) {
		if (node1.cost < node2.cost)
			return -1;
		else if (node1.cost > node2.cost)
			return 1;
		else {
			return node1.timestamp - node2.timestamp;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			Node node = (Node) obj;
			if (this.node == node.node) {
				return true;
			}
		}
		return false;
	}
}

class Edge {
	int source, destination, weight;

	Edge(int s, int d, int w) {
		source = s;
		destination = d;
		weight = w;
	}
}

class Nod {
	ArrayList<Edge> AdjacencyList = new ArrayList<Edge>();
}

class Graph {
	int[] h;
	int[] distance;
	PriorityQueue<Node> p;
	int[] visited;
	int d, s, numberOfNodes;
	int[] parent;
	int counter;
	List<String> ar;
	Nod list[];
	int vertices;
	String file;
	PrintWriter fw = new PrintWriter("C:/Users/Disha Parekh/Desktop/output.txt", "UTF-8");

	Graph(int v) throws IOException {
		vertices = v;
		list = new Nod[vertices];
		for (int i = 0; i < vertices; i++) {
			list[i] = new Nod();
		}
	}

	public void addEdge(int start, int dest, int weight) {
		Edge e1 = new Edge(start, dest, weight);
		list[start].AdjacencyList.add(e1);
	}

	public void printthis(String line) throws IOException {
		line = line.trim();
		if (!line.equals("")) {
			fw.println(line);
		}
	}

	public void bfs(List<String> ar, String startnode, String goalnode) throws IOException {
		int numberOfNodes = ar.size();
		int s = ar.indexOf(startnode);
		int d = ar.indexOf(goalnode);
		int[] visited = new int[numberOfNodes];
		int[] parent = new int[numberOfNodes];
		ArrayList<String> path = new ArrayList<String>();
		LinkedList<Integer> queue = new LinkedList<Integer>();
		visited[s] = 1;
		queue.add(s);
		int i, s1;
		while (!queue.isEmpty()) {

			s1 = queue.remove();
			if (s1 == d) {
				break;
			}
			for (i = 0; i < list[s1].AdjacencyList.size(); i++) {
				Edge e = list[s1].AdjacencyList.get(i);
				if (visited[e.destination] != 1) {
					visited[e.destination] = 1;
					queue.add(e.destination);
					parent[e.destination] = s1;
				}
			}
		}
		path.add(ar.get(d));
		int a = parent[d];
		while (a != 0) {
			path.add(ar.get(a));
			a = parent[a];
		}
		if (!path.contains(ar.get(s)))
			path.add(ar.get(s));
		Collections.reverse(path);
		for (int k = 0; k < path.size(); k++) {
			String line = path.get(k) + " " + k;
			System.out.println(line);
			printthis(line);
		}
		fw.close();
	}

	public void dfs(List<String> ar, String startnode, String goalnode) throws IOException {
		int numberOfNodes = ar.size();
		ArrayList<String> path = new ArrayList<String>();
		int s = ar.indexOf(startnode);
		int d = ar.indexOf(goalnode);
		int[] visited = new int[numberOfNodes];
		int[] parent = new int[numberOfNodes];
		Stack<Integer> st = new Stack<Integer>();
		st.push(s);
		visited[s] = 1;
		int s1, i = 0;
		while (!st.isEmpty() || s == d) {
			s1 = (int) st.pop();
			if (s1 == d) {
				break;
			}
			for (i = list[s1].AdjacencyList.size(); i > 0; i--) {
				Edge e = list[s1].AdjacencyList.get(i - 1);
				if (visited[e.destination] != 1) {
					st.push(e.destination);
					visited[e.destination] = 1;
					parent[e.destination] = s1;
				}
			}
		}
		path.add(ar.get(d));
		int a = parent[d];
		while (a != 0) {
			path.add(ar.get(a));
			a = parent[a];
		}
		if (!path.contains(ar.get(s)))
			path.add(ar.get(s));
		Collections.reverse(path);
		for (int k = 0; k < path.size(); k++) {
			String line = path.get(k) + " " + k;
			System.out.println(line);
			printthis(line);
		}
		fw.close();
	}

	public void ucs(List<String> nodes, String startnode, String goalnode) {
		numberOfNodes = nodes.size();
		ar = nodes;
		s = nodes.indexOf(startnode);
		d = nodes.indexOf(goalnode);
		parent = new int[numberOfNodes];
		visited = new int[numberOfNodes];
		distance = new int[numberOfNodes];
		for (int k = 0; k < numberOfNodes; k++) {
			distance[k] = Integer.MAX_VALUE;
		}
		p = new PriorityQueue<Node>(numberOfNodes, new Node());
		p.add(new Node(s, 0));
		distance[s] = 0;
		while (!p.isEmpty()) {
			int current = p.remove().node;
			if (current == d)
				break;
			visited[current] = 1;
			Children(current);
		}
	}

	private void Children(int current) {
		for (int i = 0; i < list[current].AdjacencyList.size(); i++) {
			Edge e1 = list[current].AdjacencyList.get(i);
			if (visited[e1.destination] != 1) {
				if (distance[e1.destination] > e1.weight + distance[current]) {
					distance[e1.destination] = e1.weight + distance[current];
					parent[e1.destination] = current;
					Node n = new Node(e1.destination, distance[e1.destination]);
					if (p.contains(n))
						p.remove(n);
					n.setTimestamp(counter);
					counter++;
					p.add(n);
				}
			} else if (visited[i] == 1) {
				if (distance[e1.destination] > e1.weight + distance[current]) {
					distance[e1.destination] = e1.weight + distance[current];
					parent[i] = current;
					Node n = new Node(i, distance[e1.destination]);
					if (p.contains(n))
						p.remove(n);
					p.add(n);
				}
			}
		}
	}

	public void astar(List<String> nodes, String startnode, String goalnode, int[] traffic) {
		numberOfNodes = nodes.size();
		ar = nodes;
		s = nodes.indexOf(startnode);
		d = nodes.indexOf(goalnode);
		parent = new int[numberOfNodes];
		visited = new int[numberOfNodes];
		distance = new int[numberOfNodes];
		for (int k = 0; k < numberOfNodes; k++) {
			distance[k] = Integer.MAX_VALUE;
		}
		p = new PriorityQueue<Node>(numberOfNodes, new Node());
		h = new int[traffic.length];
		p.add(new Node(s, 0));
		distance[s] = 0;
		while (!p.isEmpty()) {
			int current = p.remove().node;
			if (current == d)
				break;
			visited[current] = 1;
			Child(current, traffic);
		}
	}

	private void Child(int current, int[] traffic) {

		for (int i = 0; i < list[current].AdjacencyList.size(); i++) {
			Edge e1 = list[current].AdjacencyList.get(i);
			if (distance[e1.destination] > e1.weight + distance[current]) {
				distance[e1.destination] = e1.weight + distance[current];
				h[e1.destination] = distance[e1.destination] + traffic[e1.destination];
				parent[e1.destination] = current;
				Node n = new Node(e1.destination, h[e1.destination]);
				if (p.contains(n))
					p.remove(n);
				n.setTimestamp(counter);
				counter++;
				p.add(n);
			}

		}
	}

	public void printPath() throws IOException {
		ArrayList<Integer> path = new ArrayList<Integer>();
		path.add(d);
		boolean found = false;
		int vertex = d;
		while (!found) {
			if (vertex == s) {
				found = true;
				continue;
			}
			path.add(parent[vertex]);
			vertex = parent[vertex];
		}
		Collections.reverse(path);
		for (int k = 0; k < path.size(); k++) {
			String line = ar.get(path.get(k)) + " " + distance[path.get(k)];
			System.out.println(line);
			printthis(line);
		}
		fw.close();
	}
}

public class Solution2 {
	BufferedReader br;
	PrintWriter out;
	StringTokenizer st;
	boolean eof;
	String algoName, startnode, goalnode;
	List<String> nodes;
	int numberOfNodes;
	HashMap indexStore;
	int[][] adjacency;
	int traffic[];
	Graph g;

	Solution2() throws IOException {
		long startTime = System.currentTimeMillis();
		String file = "C:/Users/Disha Parekh/Desktop/input100.txt";
		br = new BufferedReader(new FileReader(file));
		out = new PrintWriter(System.out);
		getInput();
		int start = nodes.indexOf(startnode);
		int end = nodes.indexOf(goalnode);
		switch (algoName) {
		case "BFS":
			g.bfs(nodes, startnode, goalnode);
			break;
		case "DFS":
			g.dfs(nodes, startnode, goalnode);
			break;
		case "UCS":
			g.ucs(nodes, startnode, goalnode);
			g.printPath();
			break;
		case "A*":
			g.astar(nodes, startnode, goalnode, traffic);
			g.printPath();
			break;
		default:
			out.println("No Algorithm Mentioned");
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(" ");
		System.out.println(totalTime);
		out.close();
	}

	public static void main(String[] args) throws IOException {
		new Solution2();
	}

	void getInput() throws IOException {
		int index = -1;
		indexStore = new HashMap();
		algoName = getEntireLine();
		startnode = getEntireLine();
		if (indexStore.get(startnode) == null) {
			index++;
			indexStore.put(startnode, index);
		}
		goalnode = getEntireLine();
		if (indexStore.get(goalnode) == null) {
			index++;
			indexStore.put(goalnode, index);
		}
		int len = getInt();
		String column1[] = new String[len];
		String column2[] = new String[len];
		int column3[] = new int[len];
		for (int i = 0; i < len; i++) {
			column1[i] = getNextWORD();
			column2[i] = getNextWORD();
			column3[i] = getInt();
		}
		for (int i = 0; i < len; i++) {
			if (indexStore.get(column1[i]) == null) {
				index++;
				indexStore.put(column1[i], index);
			}
			if (indexStore.get(column2[i]) == null) {
				index++;
				indexStore.put(column2[i], index);
			}
		}
		numberOfNodes = index + 1;
		g = new Graph(numberOfNodes);
		for (int i = 0; i < len; i++) {
			g.addEdge((Integer) indexStore.get(column1[i]), (Integer) indexStore.get(column2[i]), column3[i]);
		}
		int trafficLines = getInt();
		traffic = new int[numberOfNodes];
		for (int i = 0; i < trafficLines; i++) {
			traffic[(Integer) indexStore.get(getNextWORD())] = getInt();
		}
		String[] array = new String[numberOfNodes];
		Set set = indexStore.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry me = (Map.Entry) it.next();
			array[(Integer) me.getValue()] = (String) me.getKey();
		}
		nodes = Arrays.asList(array);
	}

	String nextToken() {
		while (st == null || !st.hasMoreTokens()) {
			try {
				st = new StringTokenizer(br.readLine());
			} catch (Exception e) {
				eof = true;
				return null;
			}
		}
		return st.nextToken();
	}

	String getEntireLine() {
		try {
			return br.readLine();
		} catch (IOException e) {
			eof = true;
			return null;
		}
	}

	String getNextWORD() {
		return nextToken();
	}

	int getInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	long getLong() throws IOException {
		return Long.parseLong(nextToken());
	}

	double getDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}
}
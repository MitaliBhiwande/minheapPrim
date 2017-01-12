import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MST_Mitalivijay_Bhiwande
{	
	static int numberofVertices;
	static int sum;

	
	class PrimsEdges
	{
		public Integer u;
		public Integer v;
		public  int w;
	}
	
	public static void main(String args[]) throws Exception
	{
		Scanner scan = new Scanner(new File("input.txt")); // scanner to read file
		PrintWriter pw= new PrintWriter("output.txt");
		int numberofEdges;
		while(scan.hasNext())
		{
		//String line = scan.nextLine(); // read first line
		//String[] parts = line.split(" ");
		numberofVertices = scan.nextInt();
		numberofEdges = scan.nextInt();
		//System.out.println(Integer.parseInt(parts[1]));
		MST_Mitalivijay_Bhiwande prim=new MST_Mitalivijay_Bhiwande();
		ArrayList<PrimsEdges> list = new ArrayList<>();
		
		for (int i = 0; i < numberofEdges; i++) 
		{				
			MST_Mitalivijay_Bhiwande.PrimsEdges p = prim.new PrimsEdges();
			//line = scan.nextLine();
			//String[] inputs = line.split(" ");
			p.u = scan.nextInt();
			p.v = scan.nextInt();
			p.w = scan.nextInt();
			list.add(p);
			
			}
		prim = new MST_Mitalivijay_Bhiwande();
		MST_Mitalivijay_Bhiwande.PrimsEdges result=prim.new PrimsEdges();
		List<PrimsEdges> listEdge = prim.SpanningTree(list);
		
		for (Object edge : listEdge) {
			result = prim.new PrimsEdges();
			result = (PrimsEdges) edge;
			sum = sum + result.w;
		}
		pw.println(sum);
		for (Object edges : listEdge) {
			result = prim.new PrimsEdges();
			result = (PrimsEdges) edges;
			pw.println(result.u + " " + result.v + " " + result.w);
		}
		}pw.close();
		scan.close();
	}
	public class MinHeap {

		private List<Node> allNodes = new ArrayList<>();
		private Map<Integer, Integer> nodePosition = new HashMap<>();

		public class Node {
			int weight;
			int key;
		}

		
		public boolean containsData(int key) {
			return nodePosition.containsKey(key);
		}

		
		public void add(int weight, int key) {
			Node node = new Node();
			node.weight = weight;
			node.key = key;
			allNodes.add(node);
			int size = allNodes.size();
			int current = size - 1;
			int parentIndex = (current - 1) / 2;
			nodePosition.put(node.key, current);

			while (parentIndex >= 0) {
				Node parentNode = allNodes.get(parentIndex);
				Node currentNode = allNodes.get(current);
				if (parentNode.weight > currentNode.weight) {
					swap(parentNode, currentNode);
					updatePositionMap(parentNode.key, currentNode.key, parentIndex, current);
					current = parentIndex;
					parentIndex = (parentIndex - 1) / 2;
				} else {
					break;
				}
			}
		}

		
		public boolean empty() {
			return allNodes.size() == 0;
		}
	
		public void decrease(int data, int reducedWeight) {
			Integer position = nodePosition.get(data);
			allNodes.get(position).weight = reducedWeight;
			int parent = (position - 1) / 2;
			while (parent >= 0) {
				if (allNodes.get(parent).weight > allNodes.get(position).weight) {
					swap(allNodes.get(parent), allNodes.get(position));
					updatePositionMap(allNodes.get(parent).key, allNodes.get(position).key, parent, position);
					position = parent;
					parent = (parent - 1) / 2;
				} else {
					break;
				}
			}
		}

		public Integer getWeight(int key) {
			Integer position = nodePosition.get(key);
			if (position == null) {
				return null;
			} else {
				return allNodes.get(position).weight;
			}
		}
		
		public int extractMinNode() {
			int size = allNodes.size() - 1;
			Node minNode = new Node();
			minNode.key = allNodes.get(0).key;
			minNode.weight = allNodes.get(0).weight;

			int lastNodeWeight = allNodes.get(size).weight;
			allNodes.get(0).weight = lastNodeWeight;
			allNodes.get(0).key = allNodes.get(size).key;
			nodePosition.remove(minNode.key);
			nodePosition.remove(allNodes.get(0));
			nodePosition.put(allNodes.get(0).key, 0);
			allNodes.remove(size);

			int currentIndex = 0;
			size--;
			while (true) {
				int left = 2 * currentIndex + 1;
				int right = 2 * currentIndex + 2;
				if (left > size) {
					break;
				}
				if (right > size) {
					right = left;
				}
				int smallerIndex = allNodes.get(left).weight <= allNodes.get(right).weight ? left : right;
				if (allNodes.get(currentIndex).weight > allNodes.get(smallerIndex).weight) {
					swap(allNodes.get(currentIndex), allNodes.get(smallerIndex));
					updatePositionMap(allNodes.get(currentIndex).key, allNodes.get(smallerIndex).key, currentIndex,
							smallerIndex);
					currentIndex = smallerIndex;
				} else {
					break;
				}
			}
			return minNode.key;
		}

	

		private void swap(Node node1, Node node2) {
			int weight = node1.weight;
			int data = node1.key;

			node1.key = node2.key;
			node1.weight = node2.weight;

			node2.key = data;
			node2.weight = weight;
		}

		private void updatePositionMap(int data1, int data2, int pos1, int pos2) {
			nodePosition.remove(data1);
			nodePosition.remove(data2);
			nodePosition.put(data1, pos1);
			nodePosition.put(data2, pos2);
		}
	}
	public List<PrimsEdges> SpanningTree(ArrayList<PrimsEdges> list) 
	{
		Map<Integer, PrimsEdges> vertexToEdge = new HashMap<Integer, PrimsEdges>();
		MinHeap heap = new MinHeap();
		List<PrimsEdges> result = new ArrayList<PrimsEdges>();
		for (int i = 1; i <= numberofVertices; i++) 
		{
			heap.add(Integer.MAX_VALUE, i);
		}

		heap.decrease(1, 0);
		while (!heap.empty()) {
			int current = heap.extractMinNode();
			//System.out.println(current);
			MST_Mitalivijay_Bhiwande prim1;
			if (current != 1) {
				Object spanningTreeEdge = vertexToEdge.get(current);
				if (spanningTreeEdge != null) {
					result.add((PrimsEdges) spanningTreeEdge);
				}
			}

			for (MST_Mitalivijay_Bhiwande.PrimsEdges edge : list) {
				prim1 = new MST_Mitalivijay_Bhiwande();
				PrimsEdges neighbour =prim1.new PrimsEdges();
				neighbour = (PrimsEdges) edge;
				//System.out.println(neighbour);

				if (neighbour.u == current) {
					if (heap.containsData(neighbour.v)
							&& heap.getWeight(neighbour.v) > neighbour.w) {
						heap.decrease(neighbour.v, neighbour.w);
						vertexToEdge.put(neighbour.v, edge);
					}
				}
				if (neighbour.v == current) {
					if (heap.containsData(neighbour.u)
							&& heap.getWeight(neighbour.u) > neighbour.w) {
						heap.decrease(neighbour.u, neighbour.w);
						vertexToEdge.put(neighbour.u, edge);

					}
				}
			}
		}
		return result;
	}
}

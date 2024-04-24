import java.io.*;
import java.sql.Array;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class GraphLists {
    // Main Method
    public static void main(String[] args) throws IOException {
        String file_name;
        int vert_to_do;
        // Input file name from the command line
        Scanner input = new Scanner(System.in);
        System.out.print("Enter file name: ");
        file_name = input.nextLine();

        System.out.println("geting: " + file_name);

        System.out.print("Enter Vertex to do: ");
        vert_to_do = input.nextInt();
        System.out.println("Testing : " + vert_to_do);
        input.close();

        Graph The_Graph = new Graph(file_name, vert_to_do);

        System.out.println();
        The_Graph.show_adj();
        // Calling our functions and passing graph object
        System.out.println("\n\n-----------------------------\nDepth First");
        The_Graph.depth_first(vert_to_do);

        System.out.println("\n\n-----------------------------\nBreath First");
        The_Graph.breadth_first(vert_to_do);

        System.out.println("\n\n-----------------------------\nMST Prim");
        The_Graph.prim(vert_to_do);

        System.out.println("\n\n-----------------------------\nDijikstra");
        The_Graph.dijkstra(vert_to_do);
    }
}

// Heap class
class Heap {
    public int[] values, positions, weights; // arrays to store the values, positions, and weights of the nodes
    public int size; // how many nodes in the heap?

    // Constructor for our heap class
    public Heap(int max_size, int[] weights, int[] positions) {
        size = 0;
        values = new int[max_size + 1];
        this.weights = weights;
        this.positions = positions;
    }

    // Checks for empty heap
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Sifts down a node from X in the heap
    public void siftDown(int position) {
        int value = values[position];

        // Mve the node down the heap until its at the right spot
        while (size >= (position * 2)) {
            int child = position * 2;

            // get the smaller child node
            if ((weights[values[child]] > weights[values[child + 1]]) && (child < size)) {
                child++;
            }

            // If the node is already in the right spot, stop sifting down
            if (weights[values[child]] >= weights[value]) {
                break;
            }

            values[position] = values[child];
            positions[values[position]] = position;
            position = child;
        }

        values[position] = value;
        positions[value] = position;
    }

    // Sifts up a node from X in the heap
    public void siftUp(int position) {
        int value = values[position];

        // Move the node up the heap until it's in the right spot
        while ((weights[values[position / 2]] > weights[value]) && (position > 1)) {
            values[position] = values[position / 2];
            positions[values[position]] = position;
            position = position / 2;
        }

        values[position] = value;
        positions[value] = position;
    }

    // add node with value X into the heap
    public void add(int value) {
        values[++size] = value;
        siftUp(size);
    }

    // Remove node with the smallest weight from the heap
    public int remove() {
        int minValue = values[1];

        // get rid of it and sift down the root node
        positions[minValue] = 0;
        values[1] = values[size--];
        siftDown(1);
        positions[minValue] = 0;

        return minValue;
    }
}

// Node class
class Node {
    public int vertex, weight; // vertex value and weight
    public Node next; // reference to the next node list

    public Node() {
    }

    // Constructor
    public Node(int vert, int wght, Node next_node) {
        this.vertex = vert;
        this.weight = wght;
        this.next = next_node;
    }
}

/*
 * 
 */
class Graph {

    public Node[] the_lists; // array of linked lists to store the graph
    public Node tail; // tail node used to mark the end of a linked list
    public int vertex_size; // stores the number of vertices in the graph
    public int edge_size; // stores the number of edges in the graph

    // Constructor

    public Graph(String file_name, int vert_to_do) throws IOException {

        System.out.println("Testing vertex: " + to_char(vert_to_do));

        FileReader file_read; // used to read from the input file
        BufferedReader buffer; // used to buffer the input file
        String[] cells; // stores the cells (values separated by spaces) in a row
        int vertex_1, vertex_2, weight; // first, second, and weight vertex of an edge
        Node node_1, node_2;
        String row;

        file_read = new FileReader(file_name);
        buffer = new BufferedReader(file_read);

        row = buffer.readLine(); // stores a row of input from the input file
        cells = row.split(" +");

        vertex_size = Integer.parseInt(cells[0]); // first value in input file is the number of vertices
        edge_size = Integer.parseInt(cells[1]); // second value in input file is the number of edges in the graph
        tail = new Node();
        tail.next = tail;
        the_lists = new Node[vertex_size + 1]; // array of linked lists to store the graph

        for (int i = 1; i <= vertex_size; ++i) {
            the_lists[i] = tail; // initially, all vertices are not connected to any other vertex
        }

        System.out.println("Total vertices: " + cells[0] + "\tTotal Edges: " + cells[1]);

        for (int i = 1; i <= edge_size; ++i) {
            row = buffer.readLine();
            cells = row.split(" +");

            vertex_1 = Integer.parseInt(cells[0]); // first value in row is the first vertex
            vertex_2 = Integer.parseInt(cells[1]); // second value in row is the second vertex
            weight = Integer.parseInt(cells[2]); // third value in row is the weight of the edge

            node_1 = new Node(vertex_1, weight, the_lists[vertex_2]);
            the_lists[vertex_2] = node_1;

            node_2 = new Node(vertex_2, weight, the_lists[vertex_1]);
            the_lists[vertex_1] = node_2;
        }

        // Print adjacency list
        System.out.println("Adjacency List:");
        for (int i = 1; i <= vertex_size; i++) {
            System.out.print(to_char(i) + ": ");
            Node curr = the_lists[i];
            while (curr != tail) {
                System.out.print(to_char(curr.vertex) + "(" + curr.weight + ")" + " ");
                curr = curr.next;
            }
            System.out.println();
        }

        // Close files
        buffer.close();
        file_read.close();
    }

    public void show_adj() {
        System.out.println("Adjacency List Diagram");

        for (int i = 1; i <= vertex_size; ++i) {
            System.out.println();
            System.out.print(to_char(i) + " -> ");

            Node node = the_lists[i];

            if (node == tail) {
                System.out.println("null");
            } else {
                while (node.next != tail) {
                    System.out.print(to_char(node.vertex) + " = " + node.weight + " || ");
                    node = node.next;
                }

                System.out.println(to_char(node.vertex) + " = " + node.weight);
            }
        }
    }

    // depth-first traversal starting from a vertex X
    public void depth_first(int vert_to_do) {
        System.out.print("\n\nDepth-First: ");

        // Array to keep track of visited vertices
        boolean[] visits = new boolean[vertex_size + 1];

        // recursive method for depth-first traversal
        depth_reccursion(visits, vert_to_do);
    }

    // Recursive depth-first traversal
    public void depth_reccursion(boolean[] check, int vert) {
        // Print the current vertex and mark it as visited
        System.out.print(to_char(vert) + " > ");
        check[vert] = true;

        // Loop through all adjacent vertices
        for (Node i = the_lists[vert]; i != tail; i = i.next) {

            int vertex = i.vertex;

            // If the adjacent vertex has not been visited, recursively call the method for
            // that vertex
            if (!check[vertex]) {
                depth_reccursion(check, vertex);
            }

        }
    }

    // do breadth-first traversal starting from a given vertex
    public void breadth_first(int vert_to_do) {
        System.out.print("\nBreadth-First: ");

        // Queue to keep track of vertices to visit
        Queue<Integer> list = new LinkedList<>();

        // Array to keep track of visited vertices
        boolean[] visits = new boolean[vertex_size + 1];

        // Mark the starting vertex as visited and add it to the queue
        visits[vert_to_do] = true;
        list.offer(vert_to_do);

        // Loop until all vertices have been visited
        while (list.isEmpty() == false) {
            // Get the next vertex from the queue and print it
            int i = list.poll();
            System.out.print(to_char(i) + " > ");

            // Loop through all adjacent vertices
            for (Node node = the_lists[i]; node != tail; node = node.next) {
                // If the adjacent vertex has not been visited, mark it as visited and add it
                if (visits[node.vertex] == false) {
                    visits[node.vertex] = true;
                    list.offer(node.vertex);
                }
            }
        }
    }

    // Prims algorithm method
    public void prim(int vert_to_do) {
        System.out.println("\nMST starting at L using Prims:");

        // Initialization
        Heap the_heap;
        int[] visited = new int[vertex_size + 1];
        int[] min_span = new int[vertex_size + 1];
        int[] parent = new int[vertex_size + 1];
        int[] positions = new int[vertex_size + 1]; // Vertex positions in heap
        int weight = 0; // total MST weight
        int where;

        the_heap = new Heap(vertex_size, min_span, positions);

        for (int i = 1; i <= vertex_size; ++i) {
            positions[i] = 0; // Initialize positions array
            visited[i] = 0;
            parent[i] = -1;
            min_span[i] = Integer.MAX_VALUE; // Janky but works
        }

        min_span[vert_to_do] = 0;
        parent[vert_to_do] = -1;

        // Pre-initialization output
        System.out.println("\n\nPre-initialization");
        printArrays("Visited", visited);
        printArrays("Minimum Span", min_span);
        printArrays("Parent", parent);
        printArrays("Positions", positions);

        // Initialize the heap
        the_heap.add(vert_to_do);

        // Heap output
        System.out.print("\nHeap positions\t[");
        for (int i : the_heap.positions) {
            System.out.print(i + ", ");
        }
        System.out.println("]");
        // Heap Values
        System.out.print("Heap values\t[");
        for (int i : the_heap.values) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        // Heap Weights
        System.out.print("Heap weights\t[");
        for (int i : the_heap.weights) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        System.out.println("\n");
        // Main loop
        while (!the_heap.isEmpty()) {
            where = the_heap.remove();
            visited[where] = 1;

            for (Node node = the_lists[where]; node != tail; node = node.next) {
                if ((node.weight < min_span[node.vertex]) && (visited[node.vertex] == 0)) {
                    System.out.println("Minimum Span (" + min_span[node.vertex] + ") => " + node.weight);
                    min_span[node.vertex] = node.weight;
                    System.out.println("Parent (" + parent[node.vertex] + ") => " + where);
                    parent[node.vertex] = where;

                    if (positions[node.vertex] != 0) {
                        System.out.println("Sifting position (" + positions[node.vertex] + ") up");
                        the_heap.siftUp(positions[node.vertex]);
                    } else {
                        System.out.println("Adding vertex (" + node.vertex + ") to the heap");
                        the_heap.add(node.vertex);
                    }
                }
            }
        }
        System.out.println("\n");

        // Post-initialization output
        System.out.println("\n-----------------------\nPost-initialization");
        printArrays("Visited", visited);
        printArrays("Minimum Span", min_span);
        printArrays("Parent", parent);
        printArrays("Positions", positions);
        System.out.print("\nHeap positions\t[");
        for (int i : the_heap.positions) {
            System.out.print(i + ", ");
        }
        System.out.println("]");
        // Heap Values
        System.out.print("Heap values\t[");
        for (int i : the_heap.values) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        // Heap Weights
        System.out.print("Heap weights\t[");
        for (int i : the_heap.weights) {
            System.out.print(i + ", ");
        }
        System.out.println("]");
        // Print the MST edges and calculate total weight
        System.out.println("\nMST Edges:");
        for (int i = 1; i <= vertex_size; ++i) {
            if (parent[i] != -1) {
                System.out.printf("%c -> %c = %d\n", to_char(parent[i]), to_char(i), min_span[i]);
                weight += min_span[i];
                System.out.println("Total weight so far: " + weight);
            }
        }
    }

    // Helper method to print arrays

    private void printArrays(String name, int[] arr) {
        System.out.printf("%-13s: [%s]\n", name, Arrays.toString(arr).replaceAll("[\\[\\]]", ""));
    }

    public void dijkstra(int vert_to_do) {
        int small;
        int vertex;
        int[] visited = new int[vertex_size + 1];
        int[] dist = new int[vertex_size + 1];
        int[] parent = new int[vertex_size + 1];
        int[] positions = new int[vertex_size + 1];
        Heap the_heap;

        // Initialize distances to infinity and parent pointers to -1
        for (int i = 1; i <= vertex_size; i++) {
            dist[i] = Integer.MAX_VALUE; // again, still works
            parent[i] = -1;
        }

        // Set the distance of the start vertex to 0
        dist[vert_to_do] = 0;

        // Create a new Heap instance with weights and positions arrays
        the_heap = new Heap(vertex_size, dist, positions);

        // Insert the start vertex into the heap
        the_heap.add(vert_to_do);

        System.out.println("Dijikstra Pre-initalisation");
        System.out.print("distArray:  \t[");
        for (int i : dist) {
            System.out.print(i + ", ");
        }
        System.out.println("]");
        System.out.print("parentArray: \t[");
        for (int i : parent) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        // Heap Positions
        System.out.print("\nHeap positions\t[");
        for (int i : the_heap.positions) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        // Heap Values
        System.out.print("Heap values\t[");
        for (int i : the_heap.values) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        // Heap Weights
        System.out.print("Heap weights\t[");
        for (int i : the_heap.weights) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        System.out.println("\n\n");

        while (the_heap.isEmpty() == false) {
            // Extract the vertex with the smallest distance from the heap
            small = the_heap.remove();
            visited[small] = 1;

            // Traverse the adjacency list of the smallest vertex
            for (Node node = the_lists[small]; node != tail; node = node.next) {
                // If the new distance to the adjacent vertex is smaller than the previous
                // distance, update the distance and parent
                if ((dist[small] + node.weight) < dist[node.vertex]) {
                    // Print out the updated distance for the adjacent vertex
                    System.out.println("Vertex " + node.vertex + " (" + to_char(node.vertex) + ") Distance: ("
                            + dist[node.vertex] + ") => " + (dist[small] + node.weight));

                    // Update the distance and parent of the adjacent vertex
                    dist[node.vertex] = dist[small] + node.weight;
                    parent[node.vertex] = small;

                    // If the adjacent vertex has not been visited, add it to the heap
                    if (visited[node.vertex] == 0) {
                        System.out.println("Adding vertex (" + node.vertex + ") to heap");
                        the_heap.add(node.vertex);
                    }
                    // If the adjacent vertex has been visited, sift it up in the heap to maintain
                    // the heap property
                    else {
                        System.out.println("Sifting up vertex (" + node.vertex + ")");
                        the_heap.siftUp(positions[node.vertex]);
                    }

                    System.out.println();
                }
            }
        }

        System.out.println("Dijikstra Post-initalisation");
        System.out.print("dist[]  \t[");
        for (int i : dist) {
            System.out.print(i + ", ");
        }
        System.out.println("]");
        System.out.print("parent[]\t[");
        for (int i : parent) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        // Heap Positions
        System.out.print("\nHeap positions\t[");
        for (int i : the_heap.positions) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        // Heap Values
        System.out.print("Heap values\t[");
        for (int i : the_heap.values) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        // Heap Weights
        System.out.print("Heap weights\t[");
        for (int i : the_heap.weights) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        // shortest paths
        System.out.println("Dijikstras From L:");

        for (int i = 1; i <= vertex_size; i++) {
            if (i != vert_to_do) {
                System.out.print(to_char(i) + ": ");
                vertex = i;

                while (vertex != vert_to_do) {
                    System.out.print(to_char(vertex) + " <- ");
                    vertex = parent[vertex];
                }

                System.out.println(to_char(vert_to_do) + "\tcost = " + dist[i]);
            }
        }
    }

    public char to_char(int vertex) {
        return (char) (vertex + 64);
    }
}

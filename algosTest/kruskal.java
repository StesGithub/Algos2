import java.io.*;
import java.util.Scanner;



public class kruskal
{
    public static void main(String[] args) throws IOException
    {
        //Input file name from the command line
        String file_name;
        Scanner input= new Scanner(System.in);
        System.out.print("Enter file name: ");  
        file_name = input.nextLine();
        input.close();
        
        System.out.print("geting: " + file_name); 

        // read wgraph and then do kruskals on it later to get MST
        Graph graph = new Graph(file_name);

        //Show Adjcencies
        graph.show_adj();

        // find MST of the graph
        graph.Kruskals();
    }
}



//Class to create our Edge objects 
class Edge
{
    public int vertex_1, vertex_2, weight;

    //initialise edge variables
    public Edge(int from, int to, int the_weight)
    {
        vertex_1 = from;
        vertex_2 = to;
        weight = the_weight;
    }


    public char to_char(int vertex)
    {
        return (char)(vertex + 64);
    }
}


//Custom Heap class for Kruskals
class Heap
{
    Edge[] edges; //array for edges
    public int[] values; //indicies of edges
    public int current_heap_size, max_heap_size; 


    // Constructor
    public Heap(int size, Edge[] edge)
    {
        max_heap_size = size;
        current_heap_size = size;

        values = new int[size + 1];
        edges = edge;

        for (int i = 0; i <= current_heap_size; ++i)
        {
            values[i] = i; // initialsise our values
        }

        // Sift down each element starting from the middle of the heap
        for (int i = (current_heap_size / 2); i > 0; --i)
        {
            siftDown(i);
        }
    }


    // Continue the loop until the element has no children left
    public void siftDown(int where)
    {
        int value, child;
        value = values[where];

        while (current_heap_size >= (where * 2))
        {
            child = where * 2;

            // Compare the two if they exist and get the smaller one
            if ((child < current_heap_size) && (edges[values[child]].weight > edges[values[child + 1]].weight))
            {
                child++;
            }

            // Compare the value of the current one with the value the smaller one, if
            // smaller, swap the positions
            if (edges[values[child]].weight < edges[value].weight)
            {
                values[where] = values[child];
                where = child;
            }
            else
            {
                break;
            }
        }

        // pop the original in the final position
        values[where] = value;
    }


    public int remove()
    {
        // Replace the root with the last element in the heap, make it a leaf node
        values[0] = values[1];
        values[1] = values[current_heap_size--];
        siftDown(1);

        return values[0];
    }


    public void heap_sort()
    {
        int temp; // placeholder for later

        // Build a max heap from the array
        for (int i = (current_heap_size / 2); i > 0; i--)
        {
            siftDown(i);
        }

        // Swap the root with the last element, then reduce the heap size by 1
        // and call siftDown on the root to keep the max heap
        for (int i = current_heap_size; i > 1; i--)
        {
            temp = values[1];
            values[1] = values[i];
            values[i] = temp;
            current_heap_size--;
            siftDown(1);
        }

        // reverse the sorted array
        for (int i = 1; i <= (max_heap_size / 2); i++)
        {
            temp = values[i];
            values[i] = values[max_heap_size - i + 1];
            values[max_heap_size - i + 1] = temp;
        }
    }
}


//Set class
class Set
{
    public int[] parent; // each ones parent
    public int[] rank;   // rank of each one


    // union (rank)
    public void union(int x, int y)
    {
        int root_1 = get(x);
        int root_2 = get(y);

        if (root_1 == root_2)
        {
            return; // Already in the same set
        }

        // Merge the two sets based on rank
        if (rank[root_1] < rank[root_2])
        {
            parent[root_1] = root_2;
        }
        else if (rank[root_1] > rank[root_2])
        {
            parent[root_2] = root_1;
        }
        else
        {
            parent[root_2] = root_1;
            rank[root_1]++;
        }
    }


    // Constructor to initialize disjoint set with given size
    public Set(int size)
    {
        size++; // makes counting from 1 easier
        parent = new int[size];
        rank = new int[size];

        // each one is now seprate and 0
        for (int i = 0; i < size; i++)
        {
            parent[i] = i;
            rank[i] = 0;
        }
        
    }


    // get parnt (path compressision)
    public int get(int x)
    {
        if (parent[x] != x)
        {
            parent[x] = get(parent[x]);
        }

        return parent[x];
    }
}


//Graph class
class Graph
{
    public Edge[] edges;
    public int vertex_size, edge_size;

    // Constructor
    public Graph(String file_name) throws IOException
    {
        // copy pasted from Graphliss.java
        FileReader file_read;
        BufferedReader buffer;
        String[] cells;
        int vertex_1, vertex_2, weight;
        String row;

        //Open file using buffered reader
        file_read = new FileReader(file_name);
        buffer = new BufferedReader(file_read);

        row = buffer.readLine();
        cells = row.split(" +");

        vertex_size = Integer.parseInt(cells[0]);
        edge_size = Integer.parseInt(cells[1]);

        edges = new Edge[edge_size + 1];

        System.out.println("Total vertices: " + cells[0] + "\tTotal Edges: " + cells[1]);

        //Read edge info and create edges
        for (int i = 1; i <= edge_size; ++i)
        {
            row = buffer.readLine();
            cells = row.split(" +");

            vertex_1 = Integer.parseInt(cells[0]);
            vertex_2 = Integer.parseInt(cells[1]);
            weight = Integer.parseInt(cells[2]);

            System.out.println("Edge " + to_char(vertex_1) + "--(" + weight + ")--" + to_char(vertex_2));

            edges[i] = new Edge(vertex_1, vertex_2, weight);
        }

        file_read.close();
        buffer.close();
    }

    //Method for list adjacent values
    public void show_adj()
    {
        System.out.println("Adjacency List Diagram (kruskal)");

        for (int x = 1; x <= vertex_size; ++x)
        {
            System.out.print(to_char(x) + " -> ");

            for (int y = 1; y <= edge_size; ++y)
            {
                if (edges[y].vertex_1 == x)
                {
                    System.out.print(to_char(edges[y].vertex_2) + " = " + edges[y].weight + " || -> ");
                }
                else if (edges[y].vertex_2 == x)
                {
                    System.out.print(to_char(edges[y].vertex_1) + " = " + edges[y].weight + " || -> ");
                }
            }
            System.out.println();
        }
    }   

    //Kruskals implementation
    public void Kruskals()
    {
        int num_edges, weight_MST, vert_1, vert_2;
        Edge[] edges_mst;
        Edge edge;
        Heap heap;

        System.out.println("\nKruskals mst:");

        // sort edges on there weights using heap sort
        heap = new Heap(edge_size, edges); // init heap
        heap.heap_sort();

        Set dj_set = new Set(vertex_size);

        num_edges = 0;  // how many edges
        weight_MST = 0; // weight of MST

        edges_mst = new Edge[(vertex_size - 1)]; // mst wont have all vert edges, decrease a bit to avoid error
        
        //iterate through each edge un the sorted order of their weights
        for (int i = 1; (i <= edge_size) && (num_edges < vertex_size); i++)
        {
            edge = edges[heap.values[i]]; // Get the edge with the current value from heap

            // Check if the vertices of the edge are in different sets
            vert_1 = dj_set.get(edge.vertex_1);
            vert_2 = dj_set.get(edge.vertex_2);

            if (vert_1 != vert_2) // Vertices are in different sets, adding the edge to MST
            {
                dj_set.union(vert_1, vert_2);  // Merge
                edges_mst[num_edges++] = edge; // Add to MST
                weight_MST += edge.weight;     // change value of weight
            }
        }

        //Output edges 
        for (Edge the_edge : edges_mst)
        {
            System.out.println(to_char(the_edge.vertex_1) + "--(" + the_edge.weight + ")--" + to_char(the_edge.vertex_2));
        }
        //Output total weight 
        System.out.println("Total weight = " + weight_MST);
    }

    //takes a vertex and gives it a corresponding character representation
    public char to_char(int vertex)
    {
        return (char)(vertex + 64);
    }
}

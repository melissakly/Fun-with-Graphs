package graph;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.List;
/* See restrictions in Graph.java. */

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Melissa Ly with pseudocode from Ching Fang and Wikipedia
 */

public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE  to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        tree = new TreeSet<>(compare);
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        tree.add(_source);
        for (int i = 0; i < _G.maxVertex() + 1; i++) {
            setWeight(i, Double.POSITIVE_INFINITY);
        }
        setWeight(_source, 0);
        while (!tree.isEmpty()) {
            int m = tree.pollFirst();
            if (m == _dest) {
                return;
            }
            for (int succ : _G.successors(m)) {
                double weight1 = getWeight(m) + getWeight(m, succ);
                double weight2 = getWeight(succ);
                if (weight1 < weight2) {
                    tree.remove(succ);
                    setWeight(succ, weight1);
                    tree.add(succ);
                    setPredecessor(succ, m);
                }
            }
        }
    }

    /** Comparator and heuristic for my TreeSet. */
    private final Comparator<Integer> compare = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            double edge1 = getWeight(o1) + estimatedDistance(o1);
            double edge2 = getWeight(o2) + estimatedDistance(o2);
            if (edge1 > edge2) {
                return 1;
            }
            if (edge1 == edge2) {
                return o1 - o2;
            }
            return -1;
        }

    };

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        LinkedList<Integer> shortest = new LinkedList<>();
        while ((getPredecessor(v) != 0)) {
            shortest.addFirst(v);
            v = getPredecessor(v);
        }
        shortest.addFirst(_source);
        return shortest;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }


    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** The set of nodes already evaluated. */
    private TreeSet<Integer> visitedNodes;
    /** The set of currently discovered nodes still to be evaluated.
     * Initially, only the start node is known. */
    private TreeSet<Integer> notVisited;
    /** For each node, which node it can most efficiently be reached from.
     * If a node can be reached from many nodes, prevNode
     * will eventually contain the most efficient previous step. */
    private int prevNode;
    /** My fringe used for traversing through the path. */
    private TreeSet<Integer> tree;

}

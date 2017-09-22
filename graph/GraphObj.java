package graph;

import java.util.ArrayList;
import java.util.Collections;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Melissa Ly with edgeID from
 *  the Wikipedia Cantor Pairing function and add+remove
 *  implementation from slack
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        ordEdges = new ArrayList<>();
        ordVertices = new ArrayList<>();
        maxVertex = 0;
        remoVertices = new ArrayList<>();
        adjEdges = new ArrayList<ArrayList<Integer>>();
        totalEdge = 0;
    }

    @Override
    public int vertexSize() {
        int size = 0;
        for (int i = 0; i < ordVertices.size(); i += 1) {
            if (ordVertices.get(i) != null) {
                size += 1;
            }
        }
        return size;
    }

    @Override
    public int maxVertex() {
        if (ordVertices == null || ordVertices.size() < 1) {
            return 0;
        }
        int max = 1;
        for (int i = 0; i < ordVertices.size(); i += 1) {
            if (ordVertices.get(i) != null && ordVertices.get(i) > max) {
                max = ordVertices.get(i);
            }
        }
        maxVertex = max;
        return maxVertex;
    }

    @Override
    public int edgeSize() {
        return totalEdge;
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (adjEdges.get(v - 1) == null) {
            return 0;
        }
        return adjEdges.get(v - 1).size();
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return ordVertices.contains(u);
    }

    @Override
    public boolean contains(int u, int v) {
        if (contains(u) && contains(v)) {
            for (int i : adjEdges.get(u - 1)) {
                if (i == v) {
                    return true;
                }
            }
            if (!isDirected()) {
                for (int i : adjEdges.get(v - 1)) {
                    if (i == u) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int add() {
        int firstV = 0;
        if (remoVertices.isEmpty()) {
            adjEdges.add(new ArrayList<>());
            ordVertices.add(adjEdges.size());
            return adjEdges.size();
        } else {
            firstV = Collections.min(remoVertices);
            ordVertices.add(firstV + 1);
            adjEdges.set(firstV, new ArrayList<>());
            remoVertices.remove((Object) firstV);
        }
        return firstV + 1;
    }

    /** Add an edge incident on U and V. If I am directed, the edge is
     *  directed (leaves U and enters V).  Assumes U and V are my
     *  vertices.  Has no effect if there is already an edge from U to
     *  V.  Returns a unique positive number identifying the edge,
     *  different from those returned for any other existing edge. */
    @Override
    public int add(int u, int v) {
        int[] input = new int[2];
        input[0] = u;
        input[1] = v;
        if (ordVertices.contains(u) && ordVertices.contains(v)) {
            if (isDirected()) {
                adjEdges.get(u - 1).add(v);
                ordEdges.add(input);
                totalEdge += 1;
            } else {
                if (u == v) {
                    adjEdges.get(u - 1).add(v);
                    totalEdge += 1;
                    ordEdges.add(input);
                } else {
                    adjEdges.get(u - 1).add(v);
                    adjEdges.get(v - 1).add(u);
                    totalEdge += 1;
                    ordEdges.add(input);
                }
            }
        }
        return edgeId(u, v);
    }

    @Override
    public void remove(int v) {
        remoVertices.add(v - 1);
        if (ordVertices.contains(v)) {
            ordVertices.set(v - 1, null);
            if (isDirected()) {
                for (ArrayList<Integer> i : adjEdges) {
                    if (i != null && i.contains(v)) {
                        i.remove((Object) v);
                    }
                }
                for (int i = 0; i < ordEdges.size(); i++) {
                    int edgeV = ordEdges.get(i)[0];
                    int edgeV2 = ordEdges.get(i)[1];
                    if (edgeV == v || edgeV2 == v) {
                        totalEdge -= 1;
                        ordEdges.remove(i);
                        i -= 1;
                    }
                }
            } else {
                for (int i = 0; i < ordEdges.size(); i++) {
                    int[] edge = ordEdges.get(i);
                    int edgeV = edge[0];
                    int edgeV2 = edge[1];
                    if (edgeV == v || edgeV2 == v) {
                        totalEdge -= 1;
                        ordEdges.remove(i);
                        i -= 1;
                    }
                }
                for (ArrayList<Integer> i : adjEdges) {
                    if (i != null && i.contains(v)) {
                        i.remove((Object) v);
                    }
                }
            }
        }
        adjEdges.set(v - 1, null);
    }


    @Override
    public void remove(int u, int v) {
        int[] input = new int[2];
        input[0] = u;
        input[1] = v;
        if (contains(u, v)) {
            if (isDirected()) {
                adjEdges.get(u - 1).remove((Object) v);
                totalEdge -= 1;
                for (int[] i : ordEdges) {
                    if (i == input) {
                        ordEdges.set(ordEdges.indexOf(i) - 1, null);
                    }
                }
            } else {
                adjEdges.get(u - 1).remove((Integer) (v));
                adjEdges.get(v - 1).remove((Integer) (u));
                totalEdge -= 1;
                int[] edg = {Math.min(u, v), Math.max(u, v)};
                for (int[] i : ordEdges) {
                    if (i == edg) {
                        ordEdges.set(ordEdges.indexOf(i) - 1, null);
                    }
                }
            }
        }
    }


    @Override
    public Iteration<Integer> vertices() {
        ArrayList<Integer> success = new ArrayList<>();
        for (int i = 0; i < ordVertices.size(); i++) {
            if (ordVertices.get(i) != null) {
                success.add(ordVertices.get(i));
            }
        }
        return Iteration.iteration(success);
    }


    @Override
    public int successor(int v, int k) {
        if (!contains(v)) {
            return 0;
        } else {
            return adjEdges.get(v - 1).get(k);
        }
    }

    /** Getter method of Directed Graph.
     * @param v is a vertex
     * @return my edges list */
    public ArrayList<int[]> getSucc(int v) {
        return ordEdges;
    }

    @Override
    public abstract int predecessor(int v, int k);

    @Override
    public Iteration<Integer> successors(int v) {
        if (!contains(v)) {
            return Iteration.iteration(new ArrayList<Integer>());
        }
        ArrayList<Integer> success = new ArrayList<>();
        for (int i : adjEdges.get(v - 1)) {
            success.add(i);
        }
        return Iteration.iteration(success.iterator());
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> edgeList = new ArrayList<>();
        for (int[] edge : ordEdges) {
            if (edge != null) {
                edgeList.add(edge);
            }

        }
        return Iteration.iteration(edgeList);
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!contains(v)) {
            throw new Error("Vertex is not in graph");
        }
    }

    /** Cantor pairing function. */
    @Override
    protected int edgeId(int u, int v) {
        if (isDirected()) {
            u -= 1;
            v -= 1;
            return (u + v) * (u + v + 1) / 2 + v;
        } else {
            int max = Math.max(u, v);
            int min = Math.min(u, v);
            return (max + min) * (max + min + 1) / 2 + min;
        }
    }

    /** Stores edges as they are being added. */
    private ArrayList<int[]> ordEdges;
    /** Stores vertices as they are being added. */
    private ArrayList<Integer> ordVertices;
    /** The max vertex. */
    private int maxVertex;
    /** My graph representation. */
    private ArrayList<ArrayList<Integer>> adjEdges;
    /** Vertices as they are being removed. */
    private ArrayList<Integer> remoVertices;
    /** Edge counter. */
    private int totalEdge;

}

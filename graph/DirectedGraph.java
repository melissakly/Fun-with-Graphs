package graph;
import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Melissa Ly
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        Iteration<Integer> i = predecessors(v);
        int size = 0;
        for (int c : i) {
            size += 1;
        }
        return size;
    }

    @Override
    public int predecessor(int v, int k) {
        ArrayList<int[]> succ = getSucc(v);
        if (contains(v)) {
            ArrayList<int[]> pred = new ArrayList<>();
            for (int[] x: succ) {
                if (x[1] == v) {
                    pred.add(x);
                }
            }
            if (pred.size() > k) {
                return pred.get(k)[0];
            }
        }
        return 0;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        int k = 0;
        ArrayList<Integer> list = new ArrayList<>();
        while (predecessor(v, k) != 0) {
            list.add(predecessor(v, k));
            k += 1;
        }
        return Iteration.iteration(list);
    }

}

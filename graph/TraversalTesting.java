package graph;
import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Traversal class.
 * @author Melissa Ly
 */
public class TraversalTesting {
    @Test
    public void testBFT() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(5, 4);
        g.add(5, 3);
        g.add(4, 1);
        g.add(3, 2);
        g.add(1, 5);
        BreadthFirstTraversal bft = new BreadthFirstTraversal(g);
        bft.traverse(1);
        assertEquals(true, bft.marked(5));
        assertEquals(false, bft.marked(7));
    }

    @Test
    public void testDFT() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(0, 1);
        g.add(1, 2);
        g.add(3, 4);
        g.add(4, 5);
        g.add(5, 6);
        DepthFirstTraversal dft = new DepthFirstTraversal(g);
        dft.traverse(5);
        assertEquals(true, dft.marked(5));
        assertEquals(false, dft.marked(9));
    }

    private Graph _G;

}

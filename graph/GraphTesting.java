package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Melissa Ly
 */
public class GraphTesting {

    @Test
    public void testContains() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Nothing in initial graph", false, g.contains(0));
        g.add();
        g.add();
        g.add();
        g.add(1, 2);
        g.add(2, 3);
        g.add(3, 4);
        assertEquals(true, g.contains(1));
        assertEquals(false, g.contains(5));
        g.remove(1);
        assertEquals(false, g.contains(1));
    }

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }


    @Test
    public void testUndirected() {
        UndirectedGraph un = new UndirectedGraph();
        un.add();
        un.add();
        un.add();
        un.add();
        un.add(1, 2);
        assertEquals(true, un.contains(1));
        un.add(2, 3);
        assertEquals(false, un.contains(6));
        un.remove(1);
        assertEquals(false, un.contains(1));
        assertEquals(1, un.inDegree(2));
        assertEquals(1, un.outDegree(2));
        assertEquals(3, un.vertexSize());
    }

    @Test
    public void testVertex() {
        UndirectedGraph un = new UndirectedGraph();
        un.add();
        un.add();
        assertEquals(2, un.vertexSize());
        assertEquals(0, un.inDegree(1));
        assertFalse(un.contains(6));
    }

    @Test
    public void testUnGRemove() {
        UndirectedGraph un = new UndirectedGraph();
        un.add();
        un.add();
        un.add();
        un.add();
        un.add(0, 1);
        un.add(1, 2);
        un.add(2, 3);
        un.add(3, 0);
        un.remove(1, 2);
        assertEquals(0, un.inDegree(1));
        assertTrue(un.contains(1));
    }

}

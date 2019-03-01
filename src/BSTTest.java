import student.TestCase;

/**
 * Test class for Rectangle1: Tests BST, BSTRectangle, and Rectangle1
 * 
 * @author Brady Siegel <bmsiegel@vt.edu>
 * @version 2019-02-19
 */
public class BSTTest extends TestCase {
    private BSTRectangle b = new BSTRectangle();
    private Rectangle1 r = new Rectangle1();


    /** Tests insert function */
    public void testInsert() {
        // valid
        assertEquals(b.getNodeCount(), 0);
        int[] dims = { 1, 2, 3, 4 };
        int[] dims1 = { 0, 0, 1024, 1024 };
        assertTrue(b.insertRectangle("Root", dims));
        assertTrue(b.insertRectangle("Root1", dims1));
        assertEquals(b.getNodeCount(), 2);

        // invalid
        int[] negXY = { -1, -1, 10, 10 };
        int[] negWH = { 10, 10, -1, -1 };
        int[] zeroHeight = { 10, 10, 10, 0 };
        int[] zeroWidth = { 10, 10, 0, 10 };
        int[] outOfRange = { 1023, 1023, 2, 2 };
        int[] outOfRange1 = { 1015, 1020, 5, 10 };
        int[] outOfRange2 = { -1, -2, 1025, 1026 };
        int[] outOfRange3 = { -1, -2, 1025, 1026 };
        assertFalse(b.insertRectangle("A", negXY));
        assertFalse(b.insertRectangle("A", negWH));
        assertFalse(b.insertRectangle("A", zeroHeight));
        assertFalse(b.insertRectangle("A", zeroWidth));
        assertFalse(b.insertRectangle("A", outOfRange));
        assertFalse(b.insertRectangle("A", outOfRange1));
        assertFalse(b.insertRectangle("A", outOfRange2));
        assertFalse(b.insertRectangle("9", outOfRange3));
        assertFalse(b.insertRectangle("_", outOfRange3));
    }


    /** Tests the clear function */
    public void testClear() {
        b.clear();
        assertEquals(b.root, null);
        assertEquals(b.getNodeCount(), 0);
    }


    /** Tests removeRectangleName */
    public void testRemoveName() {
        b.clear();

        assertFalse(b.removeRectangleName("a"));

        int[] dim = { 1, 0, 2, 4 };
        b.insertRectangle("M", dim);
        b.insertRectangle("J", dim);
        b.insertRectangle("A", dim);
        b.insertRectangle("K", dim);
        b.insertRectangle("R", dim);
        b.insertRectangle("Q", dim);
        b.insertRectangle("Z", dim);
        b.insertRectangle("P", dim);

        // Remove non-existent name
        assertFalse(b.removeRectangleName("ABVDASFADS"));

        // Remove leaf
        assertTrue(b.removeRectangleName("Z"));
        assertEquals(b.root.getKey(), "M");
        assertEquals(b.getNodeCount(), 7);

        // Remove node with one child
        assertTrue(b.removeRectangleName("Q"));
        assertEquals(b.root.getKey(), "M");
        assertEquals(b.getNodeCount(), 6);

        // Remove node with two children
        assertTrue(b.removeRectangleName("M"));
        assertEquals(b.root.getKey(), "P");
        assertEquals(b.getNodeCount(), 5);
    }


    /** Tests removeRectangleDim */
    public void testRemoveDimensions() {
        b.clear();

        int[] dim10 = { 1, 0, 2, 4 };
        assertFalse(b.removeRectangleDim(dim10));
        assertFalse(b.removeRectangleName("abc"));

        int[] dim = { 1, 0, 2, 4 };
        int[] dim1 = { 6, 0, 2, 4 };
        int[] dim2 = { 5, 0, 2, 4 };
        int[] dim3 = { 4, 0, 2, 4 };
        int[] dim4 = { 3, 0, 2, 4 };
        int[] dim5 = { 2, 0, 2, 4 };
        int[] dim6 = { 9, 0, 2, 4 };
        int[] dim7 = { 100, 100, 100, 100 };
        int[] dim8 = { 101, 101, 101, 101 };
        int[] dim9 = { 102, 102, 102, 102 };
        b.insertRectangle("M", dim8);
        b.insertRectangle("J", dim);
        b.insertRectangle("A", dim1);
        b.insertRectangle("M", dim2);
        b.insertRectangle("R", dim3);
        b.insertRectangle("Q", dim4);
        b.insertRectangle("Z", dim5);
        b.insertRectangle("P", dim6);
        b.insertRectangle("M", dim9);

        // Remove non-existent dimension
        assertFalse(b.removeRectangleDim(dim7));

        // Remove leaf
        assertTrue(b.removeRectangleDim(dim5));
        assertEquals(b.root.getKey(), "M");
        assertFalse(b.search("Z"));
        assertEquals(b.getNodeCount(), 8);

        // Remove node with one child
        assertTrue(b.removeRectangleDim(dim4));
        assertEquals(b.root.getKey(), "M");
        assertEquals(b.getNodeCount(), 7);

        // Remove node with two children
        assertTrue(b.removeRectangleDim(dim8));
        assertEquals(b.root.getKey(), "M");
        assertEquals(b.getNodeCount(), 6);

        // Remove node with the same name but different dimension
        assertTrue(b.removeRectangleDim(dim9));
        assertEquals(b.root.getKey(), "M");
        assertEquals(b.getNodeCount(), 5);
        assertTrue(b.removeRectangleDim(dim2));
    }


    /** Tests the dump function */
    public void testDump() {
        b.clear();
        int[] dim = { 1, 0, 2, 4 };
        int[] dim1 = { 4, 6, 2, 2 };
        int[] dim2 = { 1, 2, 1023, 4 };
        int[] dim3 = { 0, 0, 12, 4 };
        int[] dim4 = { 3, 0, 2, 4 };
        int[] dim5 = { 2, 0, 2, 4 };
        int[] dim6 = { 9, 0, 2, 4 };
        int[] dim7 = { 100, 100, 100, 100 };
        int[] dim8 = { 101, 101, 101, 101 };
        int[] dim9 = { 102, 102, 102, 102 };
        b.insertRectangle("M", dim8);
        b.insertRectangle("J", dim);
        b.insertRectangle("R", dim1);
        b.dump();
        String output = systemOut().getHistory();
        assertEquals("BST dump:\nNode has depth 1, Value (J, 1, 0, 2, 4)\n"
            + "Node has depth 0, Value (M, 101, 101, 101, 101)\n"
            + "Node has depth 1, Value (R, 4, 6, 2, 2)\n" + "BST size is: 3\n",
            output);

    }


    /** Tests the inorder output function */
    public void testOutput() {
        b.clear();
        int[] dim = { 1, 0, 2, 4 };
        int[] dim1 = { 6, 0, 2, 4 };
        int[] dim2 = { 5, 0, 2, 4 };
        int[] dim3 = { 4, 0, 2, 4 };
        int[] dim4 = { 3, 0, 2, 4 };
        int[] dim5 = { 2, 0, 2, 4 };
        int[] dim6 = { 9, 0, 2, 4 };
        int[] dim7 = { 100, 100, 100, 100 };
        int[] dim8 = { 101, 101, 101, 101 };
        int[] dim9 = { 102, 102, 102, 102 };
        b.insertRectangle("M", dim8);
        b.insertRectangle("J", dim);
        b.insertRectangle("R", dim1);
        b.output();
        String output = systemOut().getHistory();
        assertEquals(output, "J\tM\tR\t\n");
    }


    /** Tests the regionSearch function */
    public void testRegionSearch() {
        b.clear();
        int[] query = { 0, 0, 10, 10 };
        int[] badQuery = { -1, -1, -1, -1 };
        b.regionSearch(query);
        b.regionSearch(badQuery);
        int[] dim = { 1, 0, 2, 4 };
        int[] dim1 = { 6, 0, 2, 4 };
        int[] dim2 = { 5, 0, 2, 4 };
        int[] dim3 = { 4, 0, 2, 4 };
        int[] dim4 = { 3, 0, 2, 4 };
        int[] dim5 = { 2, 0, 2, 4 };
        int[] dim6 = { 9, 0, 2, 4 };
        int[] dim7 = { 100, 100, 100, 100 };
        int[] dim8 = { 101, 101, 101, 101 };
        int[] dim9 = { 102, 102, 102, 102 };
        b.insertRectangle("M", dim8);
        b.insertRectangle("J", dim);
        b.insertRectangle("R", dim1);
        b.regionSearch(query);
        String output = systemOut().getHistory();
        assertEquals(output, "Rectangles intersecting region (0, 0, 10, 10):\n"
            + "Rectangles intersecting region (0, 0, 10, 10):\n"
            + "(J, 1, 0, 2, 4)\n" + "(R, 6, 0, 2, 4)\n");
    }


    /** Tests intersection function */
    public void testIntersections() {
        int[] dim = { 1, 0, 2, 4 };
        int[] dim1 = { 6, 0, 2, 4 };
        int[] dim2 = { 5, 0, 2, 4 };
        int[] dim3 = { 4, 0, 2, 4 };
        int[] dim4 = { 3, 0, 2, 4 };
        int[] dim5 = { 2, 0, 2, 4 };
        int[] dim6 = { 9, 0, 2, 4 };
        int[] dim7 = { 100, 100, 100, 100 };
        int[] dim8 = { 101, 101, 101, 101 };
        int[] dim9 = { 102, 102, 102, 102 };
        b.insertRectangle("M", dim8);
        b.insertRectangle("J", dim);
        b.insertRectangle("R", dim1);
        b.intersections();
        String output = systemOut().getHistory();
        assertEquals(output, "Intersection pairs:\n");
    }


    /** Tests the search function */
    public void testSearch() {
        int[] dim = { 1, 0, 2, 4 };
        int[] dim1 = { 6, 0, 2, 4 };
        int[] dim2 = { 5, 0, 2, 4 };
        int[] dim3 = { 4, 0, 2, 4 };
        int[] dim4 = { 3, 0, 2, 4 };
        int[] dim5 = { 2, 0, 2, 4 };
        int[] dim6 = { 9, 0, 2, 4 };
        int[] dim7 = { 100, 100, 100, 100 };
        int[] dim8 = { 101, 101, 101, 101 };
        int[] dim9 = { 102, 102, 102, 102 };
        b.insertRectangle("M", dim8);
        b.insertRectangle("J", dim);
        b.insertRectangle("R", dim1);
        b.insertRectangle("R", dim3);
        assertTrue(b.search("R"));
        assertFalse(b.search("ABC"));
        String output = systemOut().getHistory();
        assertEquals(output, "Rectangle found: (R, 6, 0, 2, 4)\n"
            + "Rectangle found: (R, 4, 0, 2, 4)\n");
    }


    /**
     * Checks that the driver class reacts correctly when the input file is not
     * found
     */
    public void testMainBadFile() {
        String[] fileIn = { "fail" };
        r.main(fileIn);
        String output = systemOut().getHistory();
        assertEquals("File Not Found!\n", output);
    }


    /**
     * Checks that the driver class works correctly with outputs
     */
    public void testMain() {
        String[] fileIn = { "SyntaxTest.txt" };
        r.main(fileIn);
        String output = systemOut().getHistory();
        assertEquals(output, "Rectangle rejected: (r_r, -1, -20, 3, 4)\n"
            + "Rectangle rejected: (rec, 7, -8, 1, 3)\n"
            + "Rectangle rejected: (virtual_rec0, 1, 1, 0, 0)\n"
            + "Rectangle rejected: (virtual_REC0, 0, 0, 11, 0)\n"
            + "Rectangle rejected: (inExistRec_0, 1, 1, -1, -2)\n"
            + "Intersection pairs:\n" + "BST dump:\n"
            + "Node has depth 0, Value (null)\n" + "BST size is: 0\n"
            + "Rectangle not found: r_r\n" + "Rectangle rejected r_r\n"
            + "Rectangle rejected (1, 1, 0, 0)\n"
            + "Rectangles intersecting region (-5, -5, 20, 20):\n");
    }


    /**
     * Checks that the driver class reacts correctly when there is no command
     * line file input
     */
    public void testMainNoFile() {
        String[] fileIn = { "", "abc", " ahd;kjfaskd" };
        r.main(fileIn);
        String output = systemOut().getHistory();
        assertEquals("Usage: <Program Name> <filename>\n", output);
    }
}

import java.util.Iterator;
import java.util.Stack;

/**
 * BST class with functions for use with Rectangles specifically. Extends
 * abstract BST class. The array is for the x,y,w,h value associated with each
 * rectangle
 * 
 * @author Brady Siegel <bmsiegel@vt.edu>
 * @version 2019-02-19
 */
public class BSTRectangle extends BST<String, int[]> {
    /** Uses superclass constructor to initialize an empty tree */
    public BSTRectangle() {
        super();
    }


    /**
     * Inserts a rectangle. Contains checks for correct format as well as bounds
     * 
     * @param name
     *            Name of rectangle to be inserted. This serves as the key value
     * @param arr
     *            x,y,w,h dimensions of the rectangle to be inserted. This
     *            serves as the element value
     * @return boolean of whether the rectangle was accepted or not
     */
    public boolean insertRectangle(String name, int[] arr) {
        if (arr[0] < 0 || arr[1] < 0 || arr[0] >= 1024 || arr[1] >= 1024) {
            return false;
        }
        else if (arr[3] <= 0 || arr[2] <= 0) {
            return false;
        }
        else if (arr[0] + arr[2] > 1024 || arr[1] + arr[3] > 1024) {
            return false;
        }
        try {
            Integer.parseInt(name.substring(0, 1));
            return false;
        }
        catch (NumberFormatException e) {
            char check = name.charAt(0);
            if ((check >= 65 && check <= 90) || (check >= 97 && check <= 122)) {
                insert(name, arr);
                return true;
            }
            return false;
        }
    }


    /**
     * Removes rectangle by name, makes sure the rectangle exists before
     * removing
     * 
     * @param name
     *            Name of rectangle to be removed. Serves as search key
     * @return boolean of whether the rectangle was removed or not
     */
    public boolean removeRectangleName(String name) {
        if (find(name) == null) {
            return false;
        }
        remove(name);
        return true;
    }


    /**
     * Removes rectangle by dimension
     * 
     * @param arr
     *            x,y,w,h value to remove. Serves as element to search for
     * @return boolean of whether the value was removed or not
     */
    public boolean removeRectangleDim(int[] arr) {
        BSTNode<String, int[]> removeThis = removeDimHelper(root, arr, false);
        if (removeThis == null) {
            return false;
        }
        remove(removeThis.getKey());
        return true;
    }


    /**
     * Recursive helper function for removeRectangleDim edits the name of the
     * rectangle so that duplicate names are not inadvertently removed
     * 
     * @param subroot
     *            Node to be searched and changed
     * @param arr
     *            remove query
     * @param changed
     *            flag to show when a node has had its name changed
     * @return the edited node with its name + "remove " to signify which node
     *         to be removed
     */
    private BSTNode<String, int[]> removeDimHelper(
        BSTNode<String, int[]> subroot,
        int[] arr,
        boolean changed) {
        BSTNode<String, int[]> right = subroot;
        BSTNode<String, int[]> left = subroot;
        if (subroot != null && !changed) {
            boolean arrEquals = true;
            for (int c = 0; c < arr.length; c++) {
                if (subroot.getElement()[c] != arr[c]) {
                    arrEquals = false;
                }
            }
            if (arrEquals) {
                subroot.setKey(subroot.getKey() + "remove ");
                changed = true;
                return subroot;
            }
            else {
                left = removeDimHelper(subroot.getLeft(), arr, changed);
                right = removeDimHelper(subroot.getRight(), arr, changed);
            }
        }
        if (left != null) {
            return left;
        }
        return right;
    }


    /**
     * Searches a given region for rectangles that intersect. Uses a private
     * iterator class and the function intersect for checking the intersection,
     * and increase readability
     * 
     * @param query
     *            x,y,w,h dimensions to be searched for intersecting rectangles
     */
    public void regionSearch(int[] query) {
        if (!(query[3] <= 0 || query[2] <= 0)) {
            System.out.println("Rectangles intersecting region (" + query[0]
                + ", " + query[1] + ", " + query[2] + ", " + query[3] + "):");
        }
        BSTIterator b = new BSTIterator(root);
        while (b.hasNext()) {
            BSTNode<String, int[]> next = b.next();
            Point currentTL = new Point(next.getElement()[0], next
                .getElement()[1]);

            if (intersect(query, next.getElement())) {
                System.out.println("(" + next.getKey() + ", " + currentTL.getX()
                    + ", " + currentTL.getY() + ", " + next.getElement()[2]
                    + ", " + next.getElement()[3] + ")");
            }

        }
    }


    /**
     * Checks the entire tree for pairs of intersections. Uses private iterator
     * class and the function intersect for checking the intersection of
     * rectangles, and increasing readability
     */
    public void intersections() {
        System.out.println("Intersection pairs:");
        BSTIterator b = new BSTIterator(root);
        int offSet = 1;
        while (b.hasNext()) {
            BSTNode<String, int[]> next = b.next();
            int[] dim = next.getElement();
            BSTIterator inner = new BSTIterator(root);
            int c = 0;
            BSTNode<String, int[]> check;
            while (c != offSet && inner.hasNext()) {
                check = inner.next();
                c++;
            }
            Point topLeft = new Point(dim[0], dim[1]);
            while (inner.hasNext()) {
                check = inner.next();
                int[] checkDim = check.getElement();
                Point currentTL = new Point(checkDim[0], checkDim[1]);
                if (intersect(next.getElement(), checkDim)) {
                    System.out.print("(" + next.getKey() + ", " + topLeft.getX()
                        + ", " + topLeft.getY() + ", " + next.getElement()[2]
                        + ", " + next.getElement()[3] + ") : ");
                    System.out.print("(" + check.getKey() + ", " + currentTL
                        .getX() + ", " + currentTL.getY() + ", " + check
                            .getElement()[2] + ", " + check.getElement()[3]
                        + ")\n");
                }
            }
            offSet++;
        }
    }


    /**
     * Checks if rect2 is above, below, to the right, or to the left of a given
     * rect1
     * 
     * @param rect1
     *            rectangle to be checked for intersection
     * @param rect2
     *            rectangle to be checked for intersection
     * @return boolean of whether the rectangles intersect
     */
    public boolean intersect(int[] rect1, int[] rect2) {
        if (rect2[0] >= rect1[0] + rect1[2]) {
            return false;
        }
        else if (rect2[0] + rect2[2] <= rect1[0]) {
            return false;
        }
        else if (rect2[1] + rect2[3] <= rect1[1]) {
            return false;
        }
        return (rect2[1] < rect1[1] + rect1[3]);
    }


    /** Clears the tree, while printing the tree size (nodeCount) */
    public void dump() {
        int size = getNodeCount();
        System.out.println("BST dump:");
        if (root == null) {
            System.out.println("Node has depth 0, Value (null)");
        }
        dumpHelper(root, 0);
        System.out.println("BST size is: " + size);
        clear();
    }


    /**
     * Uses an in-order traversal to print the depth and value of each node in
     * the tree
     * 
     * @param subroot
     *            Node to be printed
     * @param depth
     *            depth of node (number of links to get from root to node)
     */
    private void dumpHelper(BSTNode<String, int[]> subroot, int depth) {
        if (subroot != null) {
            dumpHelper(subroot.getLeft(), depth + 1);
            System.out.println("Node has depth " + depth + ", Value (" + subroot
                .getKey() + ", " + subroot.getElement()[0] + ", " + subroot
                    .getElement()[1] + ", " + subroot.getElement()[2] + ", "
                + subroot.getElement()[3] + ")");
            dumpHelper(subroot.getRight(), depth + 1);
        }
    }


    /**
     * Searches for every rectangle matching a name search term, and prints out
     * its information
     * 
     * @param name
     *            Search name for the RectangleBST
     * @return boolean of whether there were any rectangles matching the given
     *         name
     */
    public boolean search(String name) {
        searchHelper(root, name);
        return find(name) != null;
    }


    /**
     * Recursively searches the tree using a pre-order traversal and returns any
     * matching rectangles
     * 
     * @param subroot
     *            node to be searched and printed if its key matches name
     * @param name
     *            search term for rectangles
     */
    private void searchHelper(BSTNode<String, int[]> subroot, String name) {
        if (subroot != null) {
            if (subroot.getKey().equals(name)) {
                System.out.println("Rectangle found: (" + name + ", " + subroot
                    .getElement()[0] + ", " + subroot.getElement()[1] + ", "
                    + subroot.getElement()[2] + ", " + subroot.getElement()[3]
                    + ")");
            }
            searchHelper(subroot.getLeft(), name);
            searchHelper(subroot.getRight(), name);
        }
    }


    /**
     * Private iterator class that implements a preorder traversal of the BST,
     * implements the Java iterator class and uses the Java stack class
     */
    private class BSTIterator implements Iterator<BSTNode<String, int[]>> {
        private Stack<BSTNode<String, int[]>> nodeStack =
            new Stack<BSTNode<String, int[]>>();


        /**
         * Iterator constructor, pushes root onto the stack if it is not null
         */
        public BSTIterator(BSTNode<String, int[]> root) {
            if (root != null) {
                nodeStack.push(root);
            }
        }


        /** Checks if iterator has another node in the stack */
        public boolean hasNext() {
            return !(nodeStack.isEmpty());
        }


        /** Pushes a node onto the stack if it's not null */
        public BSTNode<String, int[]> next() {
            BSTNode<String, int[]> current = null;
            if (hasNext()) {
                current = nodeStack.peek();
                nodeStack.pop();
                if (current.getRight() != null) {
                    nodeStack.push(current.getRight());
                }
                if (current.getLeft() != null) {
                    nodeStack.push(current.getLeft());
                }
            }
            return current;
        }
    }


    /**
     * Class to contain point on the coordinate plane for use with
     * intersections() and regionSearch()
     */
    private class Point {
        private int x;
        private int y;


        /** Constructor */
        public Point(int newX, int newY) {
            x = newX;
            y = newY;
        }


        /** Returns x value */
        public int getX() {
            return x;
        }


        /** Returns y value */
        public int getY() {
            return y;
        }
    }
}

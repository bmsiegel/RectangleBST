/**
 * Generic BST class: A Binary Search Tree that has a count of nodes and
 * implements functions to edit
 * the tree. Functions that work with Rectangles specifically are in
 * BSTRectangle.java
 * 
 * @author Brady Siegel <bmsiegel@vt.edu>
 * @version 2019-02-19
 * @param <K>
 *            Comparable key value for use with tree
 * @param <E>
 *            element value for use with tree
 */
public abstract class BST<K extends Comparable<? super K>, E> {

    /** Top of the tree, defined as protected for use with BSTRectangle */
    protected BSTNode<K, E> root;
    private int nodeCount;


    /** Initializes empty tree */
    public BST() {
        root = null;
        nodeCount = 0;
    }


    /** Empties the tree */
    public void clear() {
        root = null;
        nodeCount = 0;
    }


    /**
     * @return a count of the nodes in the current tree
     */
    public int getNodeCount() {
        return nodeCount;
    }


    /**
     * Inserts a node into the tree
     * 
     * @param k
     *            key value to be inserted into the tree in a new node
     * @param e
     *            element value to be inserted into the tree in a new node
     */
    public void insert(K k, E e) {
        root = insertHelper(root, k, e);
        nodeCount++;
    }


    /**
     * Recursive function to help insert nodes into the tree. Starts at the root
     * and recursively
     * searches the tree for the appropriate position
     * 
     * @param subroot
     *            BSTNode to be compared with the new data
     * @param k
     *            key value to be inserted into the tree in a new node
     * @param e
     *            element value to be inserted into the tree in a new node
     * @return the edited tree or subtree to hook the tree up correctly
     */
    private BSTNode<K, E> insertHelper(BSTNode<K, E> subroot, K k, E e) {
        if (subroot == null) {
            return new BSTNode<K, E>(k, e);
        }
        else if (k.compareTo(subroot.getKey()) < 0) {
            subroot.setLeft(insertHelper(subroot.getLeft(), k, e));
        }
        else {
            subroot.setRight(insertHelper(subroot.getRight(), k, e));
        }
        return subroot;
    }


    /**
     * Finds a key in the tree and returns its element
     * 
     * @param k
     *            Key search query
     * @return element of the node found by the search key
     */
    public E find(K k) {
        return findHelper(root, k);
    }


    /**
     * Recursive helper function. Recursively searches the tree for the
     * appropriate element
     * 
     * @param subroot
     *            The node to compare the key to
     * @param k
     *            The search term
     * @return The element that the search key refers to
     */
    private E findHelper(BSTNode<K, E> subroot, K k) {
        if (subroot != null) {
            if (k.compareTo(subroot.getKey()) < 0) {
                return findHelper(subroot.getLeft(), k);
            }
            else if (k.compareTo(subroot.getKey()) > 0) {
                return findHelper(subroot.getRight(), k);
            }
            else {
                return subroot.getElement();
            }
        }
        return null;
    }


    /**
     * Removes an element from the tree, searched by key, and returns the
     * element
     * 
     * @param k
     *            the key that refers to the node to be removed
     * @return the element of the node that was removed, if any
     */
    public E remove(K k) {
        E temp = find(k);
        root = removeHelper(root, k);
        nodeCount--;
        return temp;
    }


    /**
     * Recursive helper function for remove. Finds the node that needs to be
     * removed and
     * decides how to remove (remove node, find in-order successor, or return
     * node below)
     * 
     * @param subroot
     *            the node to compare the key with, and the node to be removed
     *            if it's a match
     * @param k
     *            the key value to search for
     * @return The edited node to hook up the tree correctly
     */
    private BSTNode<K, E> removeHelper(BSTNode<K, E> subroot, K k) {
        if (subroot != null) {
            if (k.compareTo(subroot.getKey()) < 0) {
                subroot.setLeft(removeHelper(subroot.getLeft(), k));
            }
            else if (k.compareTo(subroot.getKey()) > 0) {
                subroot.setRight(removeHelper(subroot.getRight(), k));
            }
            else {
                if (subroot.getLeft() == null) {
                    return subroot.getRight();
                }
                else if (subroot.getRight() == null) {
                    return subroot.getLeft();
                }
                BSTNode<K, E> target = findReplacement(subroot.getRight());
                subroot.setKey(target.getKey());
                subroot.setElement(target.getElement());
                subroot.setRight(removeHelper(subroot.getRight(), subroot
                    .getKey()));
            }
        }
        return subroot;
    }


    /**
     * Finds the in-order successor of a node with two children
     * 
     * @param subroot
     *            The node to the right of the one to be removed
     * @return the in order successor of the node to be removed
     */
    private BSTNode<K, E> findReplacement(BSTNode<K, E> subroot) {
        while (subroot.getLeft() != null) {
            subroot = subroot.getLeft();
        }
        return subroot;
    }


    /** Prints the tree */
    public void output() {
        outputHelper(root);
        System.out.print("\n");
    }


    /**
     * Helper function to print the tree with an in-order traversal
     * 
     * @param subroot
     *            Root to be printed
     */
    public void outputHelper(BSTNode<K, E> subroot) {
        if (subroot != null) {
            outputHelper(subroot.getLeft());
            System.out.print(subroot.getKey() + "\t");
            outputHelper(subroot.getRight());
        }
    }

}

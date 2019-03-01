/**
 * Binary node with a right and left child, and a data field. Class has getters
 * and setters for the node
 * 
 * @author Brady Siegel <bmsiegel@vt.edu>
 * @version 2019-02-19
 * @param <K>
 *            Key value of the data field
 * @param <E>
 *            Element value of the data field
 * 
 */
public class BSTNode<K, E> {
    private K key;
    private E element;
    private BSTNode<K, E> right;
    private BSTNode<K, E> left;


    /** Default constructor, initializes node as null */
    public BSTNode() {
        left = null;
        right = null;
    }


    /**
     * Constructor for a given key and element, initializes left and right
     * references as null
     * 
     * @param k
     *            The key value to be given to the new node
     * @param e
     *            The element value to be given to the new node
     */
    public BSTNode(K k, E e) {
        left = null;
        right = null;
        key = k;
        element = e;
    }


    /**
     * Constructor for given left and right children as well as key and element
     * 
     * @param k
     *            The key value to be given to the new node
     * @param e
     *            The element value to be given to the new node
     * @param l
     *            The left child of the new node
     * @param r
     *            The right child of the new node
     */
    public BSTNode(K k, E e, BSTNode<K, E> l, BSTNode<K, E> r) {
        key = k;
        element = e;
        left = l;
        right = r;
    }


    /** @return The node's key */
    public K getKey() {
        return key;
    }


    /** @return The element of the node */
    public E getElement() {
        return element;
    }


    /** @return the right child */
    public BSTNode<K, E> getRight() {
        return right;
    }


    /** @return the left child */
    public BSTNode<K, E> getLeft() {
        return left;
    }


    /**
     * Sets key value
     * 
     * @param k
     *            New key value for the node
     * @return the new key value
     */
    public K setKey(K k) {
        key = k;
        return key;
    }


    /**
     * Sets element value
     * 
     * @param e
     *            New element value for the node
     * @return the new element value
     */
    public E setElement(E e) {
        element = e;
        return element;
    }


    /**
     * Sets right child
     * 
     * @param r
     *            New right child
     * @return new right child
     */
    public BSTNode<K, E> setRight(BSTNode<K, E> r) {
        right = r;
        return right;
    }


    /**
     * Sets left child
     * 
     * @param l
     *            New left child
     * @return new left child
     */
    public BSTNode<K, E> setLeft(BSTNode<K, E> l) {
        left = l;
        return left;
    }

}

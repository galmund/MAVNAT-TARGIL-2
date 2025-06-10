import java.util.Map;

/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 */
public class FibonacciHeap
{
    public HeapNode min;
    public int c;
    public HeapNode firstRoot;
    public int size = 0;
    public int numTrees = 0;
    public int totalCuts = 0;
    public int totalLinks = 0;

    public int getC() {
        return this.c;
    }

    /**
     *
     * Constructor to initialize an empty heap.
     * pre: c >= 2.
     *
     */
    public FibonacciHeap(int c)
    {
        this.c = c;
    }

    /**
     * pre: key > 0
     * Insert (key,info) into the heap and return the newly generated HeapNode.
     */
    public HeapNode insert(int key, String info) {
        this.size += 1;
        this.numTrees += 1;

        HeapNode newHeapNode = new HeapNode(key, info);

        if (this.size == 1) {
            this.firstRoot = newHeapNode;
            this.min = newHeapNode;
            return newHeapNode;
        }

        newHeapNode.next = this.firstRoot;
        newHeapNode.prev = this.firstRoot.prev;
        this.firstRoot.prev.next = newHeapNode;
        this.firstRoot.prev = newHeapNode;

        this.firstRoot = newHeapNode;

        if (this.min.key > key)
            this.min = newHeapNode;

        return newHeapNode;
    }

    /**
     * Return the minimal HeapNode, null if empty.
     */
    public HeapNode findMin() { return this.min; }

    /**
     *
     * Delete the minimal item.
     * Return the number of links.
     *
     */
    public int deleteMin()
    {
        return 46; // should be replaced by student code

    }

    /**
     *
     * pre: 0<diff<x.key
     *
     * Decrease the key of x by diff and fix the heap.
     * Return the number of cuts.
     *
     */
    public int decreaseKey(HeapNode x, int diff)
    {
        return 46; // should be replaced by student code
    }

    /**
     * Delete the x from the heap.
     * Return the number of links.
     */
    public int delete(HeapNode x) {
        int dummy = this.decreaseKey(x, Integer.MIN_VALUE);
        return this.deleteMin();
    }


    /**
     * Return the total number of links.
     */
    public int totalLinks() { return this.totalLinks; }


    /**
     * Return the total number of cuts.
     */
    public int totalCuts() { return this.totalCuts; }


    /**
     * Meld the heap with heap2
     */
    public void meld(FibonacciHeap heap2) {
        if (this.min.key > heap2.min.key)
            this.min = heap2.min;

        HeapNode lastNode2 = heap2.firstRoot.prev;

        this.firstRoot.prev.next = heap2.firstRoot;
        heap2.firstRoot.prev =  this.firstRoot.prev;
        this.firstRoot.prev = lastNode2;
        heap2.firstRoot.prev.next = this.firstRoot;

        this.size += heap2.size;
        this.numTrees += heap2.numTrees;
    }

    /**
     * Return the number of elements in the heap
     */
    public int size() { return this.size; }

    /**
     * Return the number of trees in the heap.
     */
    public int numTrees() { return this.numTrees; }

    /**
     * Class implementing a node in a Fibonacci Heap.
     */
    public static class HeapNode{
        public int key;
        public String info;
        public HeapNode child;
        public HeapNode next;
        public HeapNode prev;
        public HeapNode parent;
        public int rank;

        public HeapNode(int key, String info) {
            this.key = key;
            this.info = info;
            this.child = null;
            this.prev = null;
            this.parent = null;
            this.next = null;
            this.rank = 0;
        }
    }

}

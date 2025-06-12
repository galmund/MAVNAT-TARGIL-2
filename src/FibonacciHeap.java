import java.util.ArrayList;
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


        HeapNode newHeapNode = new HeapNode(key, info);

        this.insertRoot(newHeapNode);

        return newHeapNode;
    }

    public void insertRoot(HeapNode heapNode) {
        this.size++;
        this.numTrees++;

        if (this.size == 1) {
            this.firstRoot = heapNode;
            this.min = heapNode;
            heapNode.next = heapNode.prev = heapNode;
            return;
        }

        heapNode.next = this.firstRoot;
        heapNode.prev = this.firstRoot.prev;
        this.firstRoot.prev.next = heapNode;
        this.firstRoot.prev = heapNode;

        this.firstRoot = heapNode;

        if (this.min.key > heapNode.key)
            this.min = heapNode;


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
        ArrayList<HeapNode> roots = new ArrayList<>();
        int cntLinks = 0;
        HeapNode minNode = this.min;


        if (this.size == 0) {
            return cntLinks;
        }

        if (minNode.child != null) {
            HeapNode curr = minNode.child;
            do {
                HeapNode nextNode = curr.next;
                curr.parent = null;
                this.insertRoot(curr);
                curr = nextNode;
            } while (curr != minNode.child);
        }

        if (minNode.next == minNode) {
            this.firstRoot = null;
            this.min = null;
            return cntLinks;
        }
        else {
            minNode.prev.next = minNode.next;
            minNode.next.next = minNode.prev;
            if (minNode == this.firstRoot) {
                this.firstRoot = minNode.next;
            }
        }
        numTrees--;
        size--;




        return 0;

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

        int cutsCnt = 0;

        x.key -= diff;

        if (x.key < this.min.key)
            this.min = x;

        if (x.parent == null) {
            return cutsCnt;
        }

        HeapNode curr_parent = x.parent;

        if (x.key < x.parent.key) {
            cutsCnt += this.cut(x);
        }

        while (curr_parent != null && curr_parent.numChildCuts >= this.c) {
            HeapNode next_parent = curr_parent.parent;
            cutsCnt += this.cut(curr_parent);
            curr_parent = next_parent;
        }

        return cutsCnt;
    }

    public int cut(HeapNode node) {

        HeapNode parent = node.parent;
        if (parent == null) {
            return 0;
        }

        parent.numChildCuts++;

        boolean singleChild = (node.next == node);

        if (!singleChild) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        if (parent.child == node) {
            parent.child = singleChild ? null : node.next;
        }

        parent.rank--;

        node.parent = null;
        node.next = this.firstRoot;
        node.prev = this.firstRoot.prev;
        this.firstRoot.prev.next = node;
        this.firstRoot.prev = node;
        this.firstRoot = node;

        this.numTrees++;
        this.totalCuts++;
        node.numChildCuts=0;

        return 1;
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
        if (heap2 == null) {
            return;
        }
        this.totalCuts += heap2.totalCuts;
        this.totalLinks += heap2.totalLinks;

        if(heap2.size == 0) {
            return;
        }

        if (this.size == 0) {
            this.min = heap2.min;
            this.firstRoot = heap2.firstRoot;
            this.size = heap2.size;
            this.numTrees = heap2.numTrees;
            return;
        }

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
        public int numChildCuts = 0;

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

import java.util.HashMap;
// MinHeap is used to store the rides based on their cost and trip duration if 2 rides have same cost value
public class MinHeap {
    // here the heap stores ride
    private Ride[] heap;
    //hashmap is used as a pointer that stores the index value of a ride,
    // so that it can be helpful in arbitrary remove of a ride
    private HashMap<Integer, Integer> rideToIndex;
    private int size;


    public MinHeap() {
        //as mentioned the data structure can store up to 100 active rides.
        this.heap = new Ride[100];
        //initalize size pointer as we are using array to implement it
        // ( It indicates where to insert a node(index)
        this.size = 0;
        rideToIndex = new HashMap<>();
    }
//function is used to insert a ride into the heap
    public void insert(Ride ride) {
        heap[size] = ride;
        size++;
// always insert them into the last used index +1. Then heapify it to store it to a new node.
        int index = size - 1;
        rideToIndex.put(ride.rideNumber, index);
        int parentIndex = (index - 1) / 2;
        while (index > 0 && heap[index].compareTo(heap[parentIndex]) < 0) {
            //it is used to swap the new node with it's parent node if the new node is smaller.
            // Loop it till it reaches the root.
            swap(index, parentIndex);
            index = parentIndex;
            //a node's child is stored in 2n+1 and 2n+2. so parent index is ciel((index -1)/2)
            parentIndex = (index - 1) / 2;
        }
    }

    public void delete(int rideNumber) {
//supports arbitrary remove using this function.
        int index = rideToIndex.get(rideNumber);
        int last = size - 1;
        //it is similar to removing the root node,
        // \except here we swap the last node with the desired node and heapify accordingly.
        if (index != last) {
            swap(index, last);
            rideToIndex.remove(rideNumber);
            size--;
            //rideToIndex.put(heap[index].rideNumber, index);
            minHeapify(index);
        } else {
            size--;
            rideToIndex.remove(rideNumber);
        }
    }
//to get the least element form the heap
    public Ride extractMin() {
        if (size == 0){
            return null;
        }
        Ride min = heap[0];
        delete(min.rideNumber);
        return min;
    }


//Heapifiction for min heap is done by checking if one of it's child is lesser than that of the parent
// and swapped accordingly. To get the appropriate heap
    private void minHeapify(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        if (leftChildIndex >= size) {
            return;
        }
        int minIndex = leftChildIndex;
        if (rightChildIndex < size && heap[rightChildIndex].compareTo(heap[leftChildIndex]) < 0) {
            minIndex = rightChildIndex;
        }
        if (heap[index].compareTo(heap[minIndex]) > 0) {
            swap(index, minIndex);
            minHeapify(minIndex);
        }
    }
//here swap function is used because we store the index of each and every ride.
// so during heapification,if a swap occurs the corresponding index must be swapped.
    //thus this code helps with re-odering the indexes
    private void swap(int i, int j) {
        Ride temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        rideToIndex.put(heap[i].rideNumber, i);
        rideToIndex.put(heap[j].rideNumber, j);
    }
}

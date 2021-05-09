import java.util.NoSuchElementException;

public class Heap {
	private int size;
	private int index;
	private int [] heap;
	private int capacity;
	
	//this heap data structure only works for integer types, can be modified to take E generic types by changing heap array to an object array and with casting
	public Heap() {
		size = 0;
		index = 1;	//using an array representation of a binary tree where root is stored at index, left child = 2 * rank(parent), right child = 2 * rank(parent) + 1, rank(parent) = floor(rank(child))
		capacity = 10;	//set starting size to 10
		heap = new int [capacity];	//size of heap array grows if heap becomes full
	}
	
	public boolean add(int element) {
		if(index > capacity) {
			return false;
		}
		if(index == capacity) { //double heap size once we reach capacity
			grow();
		}
		heap[index++] = element; //insert new element at the end
		int child = index - 1;
		int parent = (int) Math.floor(child / 2);
		
		upHeap(child, parent); //preform up heap to restore heap order property
		
		size++;
		return true;
	}
	
	private void upHeap(int child, int parent) {
		while(heap[parent] > heap[child] && parent > 0) {
			int temp = heap[parent];
			heap[parent] = heap[child];
			heap[child] = temp;
			child = parent;
			parent = (int) Math.floor(child / 2);
		}
	}
	
	public int removeMin() {
		if(size == 0) {
			throw new NoSuchElementException();
		}
		int result = heap[1];
		//preparing heap for down heap
		heap[1] = heap[size];	//swapping the root with the last element
		heap[size] = 0;
		size--;
		
		downHeap();	//down heap to restore heap order
		return result;
	}
	
	private void downHeap() {
		int parent = 1;
		while(2 * parent <= size) {
			int leftChild = 2 * parent;
			int minChild = leftChild;
			if(2 * parent + 1 <= size) {
				int rightChild = 2 * parent + 1;
				if(heap[leftChild] > heap[rightChild]) { //left child is greater than right child, right child is the min value between the two children
					minChild = rightChild;
				}
			}
			
			if(heap[minChild] >= heap[parent])
				break;
			int temp = heap[parent];
			heap[parent] = heap[minChild];
			heap[minChild] = temp;
			parent = minChild;
			
		}
	}
	
	private void grow() {
		int [] newHeap = new int [capacity * 2];
		capacity *= 2;
		int i = 0;
		while (i < heap.length) {
			newHeap[i] = heap[i];
			i++;
		}
		heap = newHeap;
	}
	@Override 
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		for(int i=0 ; i<size ; i++) {
			int value = heap[i];
			sb.append(value);
			sb.append(", ");
		}
		int value = heap[size];
		sb.append(value);
		sb.append(']');
		return sb.toString();
	}
	
	public static void main(String[] args) {
		//heap sort demonstration 
		Heap pq = new Heap();
		int [] a = {5,3,4,6,7,8,1,2,30,20};
		
		for(int e : a) {
			pq.add(e);
		}

		for(int i = 0 ; i < a.length ; i++) {
			a[i] = pq.removeMin();
		}
		
		for(int e: a)
			System.out.println(e);
	
	}
}

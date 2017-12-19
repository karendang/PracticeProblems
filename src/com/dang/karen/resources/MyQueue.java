package com.dang.karen.resources;

public class MyQueue<T> {
	private static class QueueNode<T> {
		private T data;
		private QueueNode<T> next;
		
		public QueueNode(T data) {
			this.data = data;
		}
	}
	
	private QueueNode<T> first;
	private QueueNode<T> last;
	
	public void add(T item) {
		QueueNode<T> t = new QueueNode<>(item);
		if (last != null) {
			last.next = t;
		}
		
		last = t;
		
		if (first == null) {
			first = last;
		}
	}
	
	public T remove() throws NoSuchElementException {
		if (first == null) {
			throw new NoSuchElementException();
		}
		
		T data = first.data;
		first = first.next;
		if (first == null) {
			last = null;
		}
		
		return data;
	}
	
	public T peek() throws EmptyStackException {
		if (first == null) {
			throw new EmptyStackException();	
		}
		
		return first.data;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
}

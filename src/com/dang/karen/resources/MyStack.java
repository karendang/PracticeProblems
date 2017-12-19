package com.dang.karen.resources;

public class MyStack<T> {
	private static class StackNode<T> {
		private T data;
		private StackNode<T> next;

		public StackNode(T data) {
			this.data = data;
		}
	}

	private StackNode<T> top;

	public T pop() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}

		// save the current top item
		T item = top.data;
		// change the top to the next node
		top = top.next;
		return item;
	}

	public void push(T item) {
		StackNode<T> newNode = new StackNode<>(item);
		// new node's next is the current top
		newNode.next = top;
		// the top is now the new node
		top = newNode;
	}

	public T peek() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}

		return top.data;
	}

	public boolean isEmpty() {
		return top == null;
	}
}

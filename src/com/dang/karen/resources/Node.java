package com.dang.karen.resources;

public class Node<T> {
	public Node<T> next = null; 
	public T data;

	public Node(T data) {
		this.data = data;
	}

	public Node<T> appendToTail(T data) {
		Node<T> end = new Node<>(data);
		Node<T> n = this;

		while (n.next != null) {
			n = n.next;
		}

		n.next = end;

		return n;
	}

	public String nodeToString() {
		StringBuilder sb = new StringBuilder();

		Node<T> current = this;

		while (current != null) {
			sb.append(current.data + " -> ");
			current = current.next;
		}

		sb.append("null");

		return sb.toString();
	}
}

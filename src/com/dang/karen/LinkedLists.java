package com.dang.karen;

import java.util.*;

import com.dang.karen.resources.Node;

public class LinkedLists {
	public Node deleteNode(Node<Integer> head, int d) {
		Node n = head;

		if (n.data.equals(d)) {
			return head.next; // moved head because it's the first one to be deleted
		}

		while (n.next != null) {
			if (n.next.data.equals(d)) {
				n.next = n.next.next;
				return head; // head didn't change
			}

			n = n.next;
		}

		return head;
	}

	// 2.1 Remove Dups: Write code to remove duplicates from an unsorted linked list
	// FOLLOWUP:
	// How would you solve this problem if a temporary buffer is not allowed?
	public static Node<Integer> removeDupes(Node<Integer> n) {
		Node<Integer> head = n;
		List<Integer> list = new ArrayList<>();

		list.add(n.data);

		while (n.next != null) {
			if (!list.contains(n.next.data)) {
				list.add(n.next.data);
				n = n.next;
			}
			else {
				n.next = n.next.next;
			}
		}

		System.out.println(list.toString());

		return head;
	}

	// 2.2 Return Kth to Last: implement an algorithm to find the kth to last element of a singly linked list
	// O(n) time and O(n) space (because all recursion is at least O(n) space)
	public static Node<Integer> returnKthToLastRecursive(Node<Integer> node, int k) {
		int kth = findKthNodeRecursive(node, k, 0);

		for (int i = 0 ; i <= kth ; i++) {
			node = node.next;
		}

		return node;
	}

	public static int findKthNodeRecursive(Node<Integer> node, int k, int counter) {
		if (node.next == null) {
			return counter - k;
		}

		return findKthNodeRecursive(node.next, k, counter + 1);
	}

	// 2.2 Return Kth to Last: implement an algorithm to find the kth to last element of a singly linked list
	// O(n) time and O(1) space
	public static Node<Integer> returnKthToLastIterative(Node<Integer> node, int k) {
		Node<Integer> p1 = node;
		Node<Integer> p2 = node;

		// iterate until the nodes are length k apart
		for (int i = 0 ; i < k ; i++) {
			if (p1 == null) {
				return null; // the length of k exceeds the size of the nodes
			}

			p1 = p1.next;
		}

		// iterate together until p1 is at the end
		while (p1 != null) {
			p1 = p1.next;
			p2 = p2.next;
		}

		return p2;
	}

	// 2.3 Delete Middle Node: Implement an algorithm to delete a node in the middle of a singly linked list, given only access to that node
	// EXAMPLE:
	// Input: the node c from the linked list a->b->c->d->e
	// Result: nothing is returned, but the new linked list looks like a->b->d->e
	public static boolean deleteMiddleNode(Node n) {
		if (n == null || n.next == null) {
			return false; // cannot be at the end or empty node
		}

		Node nextNode = n.next; // pointing to the next n node
		n.data = nextNode.data; // copying the data from the nextNode node to the n node
		n.next = nextNode.next; // pointing the next n node to the next nextNode node aka deleting the n node

		return true;
	}

	// 2.4 Partition: write code to partition a linked list around a value x, such that all nodes less than x come before all nodes greater than or equal to x.
	// if x is contained within the list, the values of x only need to be after the elements less than x.
	// EXAMPLE:
	// input: 	3->5->8->5->10->2->1 [partition = 5]
	// output: 	3->1->2->5->5->8->10
	public static Node<Integer> partition(Node<Integer> n, int partition) {
		Node<Integer> front = new Node<>(0);
		Node<Integer> frontHead = front;
		Node<Integer> back = new Node<>(0);
		Node<Integer> backHead = back;

		while (n != null) {
			if (n.data < partition) {
				front.appendToTail(n.data);
				front = front.next;
			}
			else {
				back.appendToTail(n.data);
				back = back.next;
			}

			n = n.next;
		}

		front.next = backHead.next;

		return frontHead.next;
	}

	// 2.5 Sum Lists: you have two numbers represented by a linked list, where each node contains a single digit. the digits are stored in reverse order, such as the 1's digit is at the head of the list.
	// write a function that adds the two numbers and returns the sum as a linked list.
	// EXAMPLE:
	// input: (7->1->6) + (5->9->2) = 617 + 295
	// output: (2->1->9) = 912
	public static Node<Integer> reverseSum(Node<Integer> n1, Node<Integer> n2) {
		Node<Integer> newN = new Node<>(0);
		Node<Integer> head = newN;

		boolean remainder = false;

		while (n1 != null || n2 != null) {
			int i = remainder ? 1 : 0;
			remainder = false;

			if (n1 != null) {
				i = i + (int)n1.data;
				n1 = n1.next;
			}

			if (n2 != null) {
				i = i + (int)n2.data;
				n2 = n2.next;
			}

			if (i > 9) {
				remainder = true;
				i = i % 10;
			}

			newN.appendToTail(i);
			newN = newN.next;
		}

		if (remainder) {
			newN.appendToTail(1);
		}

		return head.next;
	}

	// public static Node forwardSum(Node n1, Node n2) {

	// }

	// public static Node forwardSumRecursive(Node n1, Node n2, int sum) {
	// 	if (n1 != )
	// }

	// 2.6 Palindrome: implement a function to check if a linked list is a palindrome
	public static boolean isPalindrome(Node node) {
		Stack stack = new Stack();

		Node fast = node;
		Node slow = node;

		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}

		while(slow != null) {
			stack.push(slow.data);
			slow = slow.next;
		}

		while(!stack.isEmpty()) {
			String s = (String)stack.pop();

			if (!s.equals(node.data)) {
				return false;
			}

			node = node.next;
		}

		return true;
	}

	// 2.7 Intersection: Given two (singly) linked lists, determine if the two lists intersect. Return the intersecting node. Note that the intersection is defined based on reference not value.
	// That is if the kth node of the first linked list is the exact same node (by reference) as the jth node of the second linked list, then they are intersecting.
	public static Node intersection(Node n1, Node n2) {
		Result rN1 = getTailAndSize(n1);
		Result rN2 = getTailAndSize(n2);

		if (rN1.tail != rN2.tail) {
			return null;
		}

		Node longer = rN1.size > rN2.size ? n1 : n2;
		Node shorter = rN1.size < rN2.size ? n1 : n2;

		// iterate until longer is at the same length as shorter
		for (int i = 0 ; i < Math.abs(rN1.size - rN2.size) ; i++) {
			longer = longer.next;
		}

		while (longer != shorter) {
			longer = longer.next;
			shorter = shorter.next;
		}

		return longer;
	}

	static class Result {
		public Node tail;
		public int size;

		public Result(Node tail, int size) {
			this.tail = tail;
			this.size = size;
		}
	}

	public static Result getTailAndSize(Node node) {
		if (node == null) {
			return null;
		}

		int size = 1;

		while (node.next != null) {
			size++;

			node = node.next;
		}

		return new Result(node, size);
	}

	// 2.8 Loop Detection: Given a circular linked list, implement an algorithm that returns the node at the beginning of the loop.
	// Definitiono:
	// Circular linked list: a (corrupt) linked list in which a node's next pointer points to an earlier node, so as to make a loop in the linked list.
	// EXAMPLE:
	// input: A -> B -> C -> D -> E -> C (the same C as earlier)
	// output: C
	public static String loopDetection(Node<String> node) {
		Node<String> slow = node;
		Node<String> fast = node.next.next;

		while (slow != fast && fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;

			if (slow == node || fast == node) {
				return node.data;
			}
		}

		if (fast == null || fast.next == null) {
			return "";
		}

		slow = node;
		while (slow != fast) {
			slow = slow.next;
			fast = fast.next.next;
		}

		return fast.data;
	}

	public static void main(String[] args) {
		int[] arr = {3, 5, 8, 5, 10, 2, 1, 10};

		Node<Integer> head = new Node<>(arr[0]);

		for (int i = 1 ; i < arr.length ; i++) {
			head.appendToTail(arr[i]);
		}

		System.out.println(removeDupes(head).nodeToString());

		System.out.println(returnKthToLastRecursive(head, 2).nodeToString());
		System.out.println(returnKthToLastIterative(head, 2).nodeToString());

		deleteMiddleNode(head);
		System.out.println(head.nodeToString());

		System.out.println(partition(head,5).nodeToString());

		int[] arr1 = {7,1,6};
		int[] arr2 = {5,9,2};

		Node<Integer> num1 = new Node<>(arr1[0]);
		Node<Integer> num2 = new Node<>(arr2[0]);

		for (int i = 1 ; i < arr1.length ; i++) {
			num1.appendToTail(arr1[i]);
		}

		for (int i = 1 ; i < arr2.length ; i++) {
			num2.appendToTail(arr2[i]);
		}

		System.out.println(reverseSum(num1, num2).nodeToString());

		String[] str = "racecar".split("");
		Node<String> stringNode = new Node<>(str[0]);

		for(int i = 1 ; i < str.length ; i++) {
			stringNode.appendToTail(str[i]);
		}

		System.out.println(isPalindrome(stringNode));

		str = "abcdefghijklmnopqrstuv".split("");
		Node<String> strHead = new Node<>(str[0]);
		stringNode = strHead;

		for(int i = 1 ; i < 3 ; i++) {
			Node<String> next = new Node<>(str[i]);
			stringNode.next = next;
			stringNode = stringNode.next;
		}

		Node<String> last = stringNode;

		for(int i = 3 ; i < str.length ; i++) {
			Node<String> next = new Node<>(str[i]);
			stringNode.next = next;
			stringNode = stringNode.next;
		}

		stringNode.next = last;

		System.out.println(loopDetection(strHead));
	}
}

package com.dang.karen;

import java.util.*;

public class RecursionAndDynamicProgramming {
	public static int fibonacci(int i) {
		if (i < 2) {
			return 1;
		}

		int fib = (fibonacci(i - 1) + fibonacci(i - 2));

		return fib;
	}

	public static int fib(int n) {
		return fib(n, new int[n + 1]);
	}

	public static int fib(int i, int[] memo) {
		if (i < 2) {
			return 1;
		}

		if (memo[i] == 0) {
			memo[i] = fib(i - 1, memo) + fib(i - 2, memo);
		}

		return memo[i];
	}

	// 8.1 Trip step: A child is running up a staircase with n steps and can hop 1 step, 2 steps, or 3 steps at a time.
	// Implement a method to count how many possible ways the child can run up the stairs.
	public static int tripleStep(int numOfSteps) {
		int[] memo = new int[numOfSteps + 1];

		memo[1] = 1;
		memo[2] = 3;

		int sum = 4;
		for (int i = 3 ; i < memo.length ; i++) {
			memo[i] = memo[i - 1] + memo[i - 2] + memo[i - 3];
		}

		return memo[numOfSteps];
	}

	public static int tripleStepRecursive(int numOfStepsLeft, int[] memo) {
		if (numOfStepsLeft == 0) {
			return 0;
		}

		if (numOfStepsLeft == 1) {
			return 1;
		}

		if (numOfStepsLeft == 2) {
			return 3;
		}

		if (memo[numOfStepsLeft] == 0) {
			memo[numOfStepsLeft] = tripleStepRecursive(numOfStepsLeft - 1, memo) + tripleStepRecursive(numOfStepsLeft - 2, memo) + tripleStepRecursive(numOfStepsLeft - 3, memo);
		}

		return memo[numOfStepsLeft];
	}

	// 8.2 Robot in a grid: imagine a robot sitting on the upper left corner of grid with r rows and c columns. 
	// The robot can only move in two directions, right and down, but certain cells are "off limits" such that the robot cannot step on them.
	// Design an algorithm to find a path for the robot from the top left to the bottom right.
	static class Node {
		int x;
		int y;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	// class Stacks<T> {
	// 	private class StackNode<T> {
	// 		private T data;
	// 		private StackNode<T> next;

	// 		public StackNode(T data) {
	// 			this.data = data;
	// 		}
	// 	}

	// 	private StackNode<T> top;

	// 	public T pop() throws EmptyStackException {
	// 		if (isEmpty) {
	// 			throw new EmptyStackException();
	// 		}

	// 		// save the current top item
	// 		T item = top.data;
	// 		// change the top to the next node
	// 		top = top.next;
	// 		return item;
	// 	}

	// 	public void push(T item) {
	// 		StackNode<T> newNode = new StackNode<>(item);
	// 		// new node's next is the current top
	// 		newNode.next = top;
	// 		// the top is now the new node
	// 		top = newNode;
	// 	}

	// 	public T peek() throws EmptyStackException {
	// 		if (isEmpty) {
	// 			throw new EmptyStackException();
	// 		}

	// 		return top.data;
	// 	}

	// 	public boolean isEmpty() {
	// 		return top == null;
	// 	}
	// }

	// class EmptyStackException extends Exception {
	// 	public EmptyStackException() {
	// 		super("Stack is empty");
	// 	}
	// }

	// public static boolean robotPath(boolean[][] grid) {
	// 	return robotPathRecurse(grid, 0, 0);
	// }

	// public static boolean robotPathRecurse(boolean[][] grid, int x, int y) {
	// 	if (x >= grid.length || y >= grid[0].length) {
	// 		return false;
	// 	}

	// 	if (!grid[x][y]) {
	// 		return false;
	// 	}

	// 	return (x == grid.length - 1 && y == grid[0].length - 1) || robotPathRecurse(grid, x + 1, y) || robotPathRecurse(grid, x, y + 1);
	// }

	public static List<Node> robotPath(boolean[][] grid) {
		if (grid == null || grid.length == 0) return null;

		List<Node> path = new ArrayList<>();
		Map<Node, Boolean> map = new HashMap<>();

		int lastRow = grid.length - 1;
		int lastCol = grid[0].length -1;

		if (robotPathRecurse(grid, lastRow, lastCol, path, map)) {
			return path;
		}
		return null;
	}

	public static boolean robotPathRecurse(boolean[][] grid, int row, int col, List<Node> path, Map<Node, Boolean> cache) {
		if (col < 0 || row < 0 || !grid[row][col]) {
			return false;
		}

		Node n = new Node(row, col);

		if (cache.containsKey(n)) {
			return cache.get(n);
		}

		boolean isAtOrigin = (row == 0) && (col == 0);
		boolean success = false;

		if (isAtOrigin || robotPathRecurse(grid, row, col -1, path, cache) || robotPathRecurse(grid, row -1, col, path, cache)) {
			path.add(n);
			success = true;
		}

		cache.put(n, success);
		return success;
	}

	// 8.3 Magic Index: A magic index in an array A[0... n-1] is defined to be an index such that A[i] = i. Given a sorted array of distinct integers, write a method to find a magic index, if one exists, in array A.
	// FOLLOW UP:
	// what if the values are not distinct?
	public static int magicIndex(int[] a) {
		// int start = 0;
		// int end = a.length;
		// int i = end/2;

		// while (i > start && i < end) {
		// 	if (a[i] == i) {
		// 		return i;
		// 	}
		// 	else if (a[i] > i) {
		// 		end = i;
		// 		i--;
		// 	}
		// 	else {
		// 		start = i;
		// 		i++;
		// 	}
		// }

		// return -1;

		return magicIndexRecurse(a, 0, a.length - 1);
	}

	public static int magicIndexRecurse(int[] a, int start, int end) {
		if (end < start) {
			return -1;
		}

		int mid = (end + start)/2;
		
		if (a[mid] == mid) {
			return mid;
		}

		// search left
		int leftIndex = Math.min(mid -1, a[mid]);
		int left = magicIndexRecurse(a, start, leftIndex);
		if (left >= 0) {
			return left;
		}

		// search right
		int rightIndex = Math.max(mid + 1, a[mid]);
		int right = magicIndexRecurse(a, rightIndex, end);

		return right;
	}

	// // 8.4 Power set: Write a method to return all the subsets of a set
	// public static Set<Integer> powerSet() {

	// }

	// 8.5 Recursive Multiply: Write a recursive function to multiply numbers without using the * operator. You can use addition, subtraction and bit shifting, but you should minimuze the number of those operations.
	public static int recursiveMultiply(int num1, int num2) {
		int small = num1 < num2 ? num1 : num2;
		int big = num1 > num2 ? num1 : num2;

		System.out.println(big);
		System.out.println(small);
		return recursiveMultiply(big, small, 0);
	}

	public static int recursiveMultiply(int num1, int num2, int sum) {
		if (num2 == 0) {
			return sum;
		}

		sum = sum + num1;
		return recursiveMultiply(num1, num2 - 1, sum);
	}

	// ******************
	// 8.7 Permutations without Dups: write a method to compute all permutations of a string of unique characters
	public static List<String> permuatationWithoutDups(String s) {
		List<String> result = new ArrayList<>();
		permuatationWithoutDups(s, "", result);
		return result;
	}

	public static void permuatationWithoutDups(String remainder, String prefix, List result) {
		if (remainder.length() == 0) {
			result.add(prefix);
		}

		int len = remainder.length();

		for (int i = 0 ; i < len ; i++) {
			String before = remainder.substring(0, i);
			String after = remainder.substring(i + 1, len);
			char c = remainder.charAt(i);

			permuatationWithoutDups(before + after, prefix + c, result);
		}
	}

	// 8.7 Permutations with Dups: write a method to compute all permutations of a string whose characters are not necessarily unique. the list of permutations should not have duplicates.
	public static List<String> permuatationWithDups(String s) {
		Set<String> result = new HashSet<>();
		permuatationWithDups(s, "", result);
		return new ArrayList<String>(result);
	}

	public static void permuatationWithDups(String remainder, String prefix, Set<String> result) {
		if (remainder.length() == 0) {
			result.add(prefix);
		}

		int len = remainder.length();

		for (int i = 0 ; i < len ; i++) {
			String before = remainder.substring(0, i);
			String after = remainder.substring(i + 1, len);
			char c = remainder.charAt(i);

			permuatationWithDups(before + after, prefix + c, result);
		}
	}

	// 8.9 parens: implement an algorithm to print all valid (e.g. properly opened and closed) combinations of n pairs of parentheses.
	public static Set<String> paren(int num) {
		Set<String> list = new HashSet<>();

		if (num < 0) {
			return list;
		}

		paren(num, "()", list);

		return list;
	}

	public static void paren(int num, String build, Set list) {
		if (build.length() == (num * 2)) {
			list.add(build);
			return;
		}

		String pair = "()";
		int index = 0;
		int parenCount;

		for (int i = 0 ; i < build.length() ; i++) {
			if (i == build.length() - 1) {
				paren(num, pair + build, list);
			}

			if (build.charAt(i) == '(') {
				String start = build.substring(0, i + 1);
				String end = build.substring(i + 1, build.length());

				paren(num, start + pair + end, list);
			}
		}
	}

	// 8.10 Paint fill: implement the "paint fill" function that one might see on many image editing programs. 
	// that is given a screen (represented by two-dimensional array of colors), a point and a new color, fill in the surrounding area until the color changes from the original color.

	// 8.10 Coins: given an infinite number of quarters, dimes, nickels and pennies, write a code to calculate the number of ways to represent n cents.
	public static List<Map<String, Integer>> coins(int n) {
		List<Map<String, Integer>> list = new ArrayList<>();
//		Map<String, Integer> map = new HashMap<>();

		coins(n, list);
		
		return list;
	}

	public static void coins(int remaining, List<Map<String, Integer>> list) {
		Map<String, Integer> map = new HashMap<>();

		if (remaining < 5) {
			System.out.println(remaining);
			map.merge("pennies", remaining, Integer::sum);
			list.add(map);
			return;
		}

		if (remaining % 25 == 0) {
			map.merge("quarters", 1, Integer::sum);
			coins(remaining-25, list);
		}

		if (remaining % 10 == 0) {
			map.merge("dimes", 1, Integer::sum);
			coins(remaining-10, list);
		}

		if (remaining % 5 == 0) {
			map.merge("nickels", 1, Integer::sum);
			coins(remaining-5, list);
		}
	}

	public static void main(String[] args) {
		// System.out.println(fib(6) == fibonacci(6));

		// int numOfSteps = 20;
		// System.out.println(tripleStepRecursive(numOfSteps, new int[numOfSteps + 1]));
		// System.out.println(tripleStep(numOfSteps));

		// boolean[][] grid = new boolean[4][4];
		// for (int i = 0 ; i < grid.length ; i++) {
		// 	Arrays.fill(grid[i], true);
		// }

		// grid[2][1] = false;
		// grid[3][2] = false;
		// grid[0][3] = false;

		// System.out.println(Arrays.deepToString(grid));

		// System.out.println(robotPath(grid));

		// int[] arr = { -1, 0, 1, 2, 4, 6, 10 };
		// System.out.println(magicIndex(arr));

		// System.out.println(recursiveMultiply(6, 10));

		// System.out.println(String.join(", ", permuatationWithoutDups("cat")));
		// System.out.println(String.join(", ", permuatationWithoutDups("catc")));
		// System.out.println(paren(2));
		System.out.println(coins(10).toString());
		// paren(3);
	}
}

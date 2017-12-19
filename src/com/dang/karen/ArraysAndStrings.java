package com.dang.karen;

import java.util.*;

public class ArraysAndStrings {
	// 1.1 implement an algorithm to determine if a string has all unique characters. what if you cannot use any additional data structures?
	public static boolean isUnique(String string) {
		boolean[] checker = new boolean[256];

		for(char c : string.toCharArray()) {
			if (checker[(int)c]) {
				return false;
			}

			checker[(int)c] = true;
		}

		return true;
	}

	// 1.2 given two strings, write a method to determine if one is a permutation of the other
	/* public static boolean isPermutation(String one, String two) {
		if (one.length() != two.length()) {
			return false;
		}

		Map<String, Integer> map = new HashMap<>();

		for (String s : one.split("")) {
			if(map.containsKey(s)) {
				int value = map.get(s) + 1;
				map.put(s, value);
			}
			else {
				map.put(s, 1);
			}
		}

		for (String s : two.split("")) {
			if(map.containsKey(s)) {
				int value = map.get(s) - 1;
				if (value == 0) {
					map.remove(s);
				}
				else {
					map.put(s, value);
				}
			}
			else {
				return false;
			}
		}

		return map.isEmpty();
	} */

	public static boolean isPermutation(String one, String two) {
	 	if (one.length() != two.length()) {
			return false;
		}

		int[] letters = new int[128]; // assuming ascii not unicode

		char[] s_array = one.toCharArray();
		for (char c : s_array) {
			letters[c]++;
		}

		for (int i = 0 ; i < two.length() ; i ++) {
			int c = (int)  two.charAt(i);
			letters[c]--;
			if (letters[c] < 0) {
				return false;
			}
		}

		return true;
	}

	// 1.3 write a method to replace all spaces in a string with "%20". you may assume that the string has sufficient space at the end to hold the additional characters and that you are given the "true" length of the string. 
	// Note: if implementing in Java, please use character array so that you can perform this operation in place
	// EXAMPLE:
	// Input:	"Mr John Smith  ", 13
	// Output:	"Mr%20John%20Smith"
	public static String replaceSpaces(String input, int length) {
		char[] s_array = input.toCharArray();
		int lastEmpty = input.length() - 1;
		boolean newSpace = (s_array[input.length() -1] == ' ');

		for (int i = input.length() - 1 ; i > 0 ; i--) {
			if (s_array[i] == ' ') {
				if (!newSpace) {
					newSpace = true;

					s_array[lastEmpty] = '0';
					s_array[lastEmpty - 1] = '2';
					s_array[lastEmpty - 2] = '%';

					lastEmpty -= 3;
				}
				continue;
			}

			newSpace = false;
			s_array[lastEmpty] = s_array[i];
			lastEmpty--;
		}

		return String.valueOf(s_array);
	}

	// 1.4 Palindrome Permutation: given a string, write a function to check if it is a permutation of a palindrome. a palindrome is a word or phrase that is the same forwards and backwards. a permutation is a rearrangement of letters. 
	// the palindrome does not need to be limted to just dictionary words.
	public static boolean isPalindromePermutation(String string) {
		Map<String, Integer> map = new HashMap<>();

		for(String letter : string.split("")) {
			letter = letter.toLowerCase();

			if (map.containsKey(letter)) {
				map.remove(letter);
			}
			else {
				map.put(letter, 1);
			}
		}

		if (map.isEmpty() || map.size() == 1) {
			return true;
		}

		return false;
	}

	// 1.5 One Away: There are three types of edits that can be performed on strings: insert a character, remove a character, or replace a character. Given two strings, write a function to check if they are one edit (or zero edits) away
	// EXAMPLE: 
	// pale,	ple 	-> true
	// pales,	pale 	-> true
	// pale,	bale 	-> true
	// pale,	bake 	-> false
	public static boolean oneAway(String one, String two) {
		if(Math.abs(one.length() - two.length()) > 1) {
			return false;
		}

		String big = (one.length() > two.length()) ? one : two;
		String small = (one.equals(big)) ? two : one;

		boolean freebee = true;
		int j = 0;

		for (int i = 0 ; i < small.length() ; i++) {
			if (big.charAt(i) == small.charAt(j)) {
				j++;
				continue;
			}

			if (!freebee) {
				return false;
			}

			if (j < small.length()) {
				if (small.charAt(j + 1) == big.charAt(i) || small.charAt(j + 1) == big.charAt(i + 1)) {
					j++;
				}
			}

			freebee = false;
		}

		return true;
	}

	// 1.6 String Compression: Implement a method to perform basic string compression using the counts of repeated characters. For exmaple, the string aabcccccaaa would be come a2b1c5a3. 
	// if the "compressed" string would not become smaller than the original string, your method should return the original string. You can assume the string has only uppercase and lowercase letters (a-z).
	public static String stringCompression(String string) {
		StringBuilder sb = new StringBuilder();

		int counter = 0;
		String previousLetter = string.substring(0, 1);

		for (String letter : string.split("")) {
			if (previousLetter.equals(letter)) {
				counter++;
			}
			else {
				sb.append(previousLetter + counter);
				counter = 1;
				previousLetter = letter;
			}
		}

		sb.append(previousLetter + counter);

		return (string.length() < sb.toString().length()) ? string : sb.toString();
	}

	// TODO: UNDERSTAND THIS
	// 1.7 Rotate Matrix: given an image represented by an NxN matrix, where each pixel in the image is 4 bytes, write a method to rotate the image by 90 degrees. Can you do it in place?
	public static void rotateMatrix(int[][] matrix, int n) {
		for (int layer = 0 ; layer < n/2 ; ++layer) {
			int first = layer;
			int last = n - 1 - layer;
			for (int i = first; i < last ; ++i) {
				int offset = i - first;
				int top = matrix[first][i]; // save top

				// left -> top
				matrix[first][i] = matrix[last - offset][first];

				// bottom -> left
				matrix[last - offset][first] = matrix[last][last - offset];

				// right -> bottom
				matrix[last][last - offset] = matrix[i][last];

				// top -> right
				matrix[i][last] = top; // right <- saved top
			}
		}
	}

	// 1.8 Zero Matrix: write an algorith such that if an element in an MxN matrix is 0. its entire row and column are set to 0.
	public static void zeroMatrix(int[][] matrix) {
		List<Integer> x = new ArrayList<>();
		List<Integer> y = new ArrayList<>();

		for (int i = 0 ; i < matrix[0].length ; i++) {
			for (int j = 0 ; j < matrix[1].length ; j++) {
				if (matrix[i][j] == 0) {
					x.add(i);
					y.add(j);
				}
			}
		}

		for (int i = 0 ; i < matrix[0].length ; i ++) {
			for (int j = 0 ; j < matrix[1].length ; j++) {
				if (y.contains(j) || x.contains(i)) {
					matrix[i][j] = 0;
				}
			}
		}
	}

	// 1.9 String Rotation: Assume you have a method isSubstring which checks if one word is a substring of another. Given two strings, s1 and s2, write code to check if s2 is a rotation of s1 using only one call to isSubstring
	// EXAMPLE:
	// waterbottle is a rotation of erbottlewat
	public static boolean isStringRotation(String s1, String s2) {
		if (s1.length() != s2.length() || s1.length() == 0) {
			return false;
		}

		if (s1.equals(s2)) {
			return false;
		}

		return isSubstring(s1, s2 + s1);
	}

	public static boolean isSubstring(String s1, String s2) {
		return s2.contains(s1);
	}

	public static void main(String[] args) {
		String s = "abccvdvbA";
		String s2 = "abcdf";

		System.out.println("is " + s + " unique?: " + isUnique(s));
		System.out.println("is " + s2 + " unique?: " + isUnique(s2));
		
		System.out.println("are " + s + " " + s2 + " permutations of one another?: " + isPermutation(s, s2));

		System.out.println(replaceSpaces("Mr John Smith    ", 13));

		System.out.println(isPalindromePermutation(s));

		System.out.println(oneAway("pale", "bake"));

		System.out.println(stringCompression("aabcccccaaa"));

		int[][] image = new int[][] {{1,2,3,0},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
		rotateMatrix(image, 4);

		System.out.println(Arrays.deepToString(image));

		zeroMatrix(image);
		System.out.println(Arrays.deepToString(image));

		System.out.println(isStringRotation("waterbottle", "erbottlewat"));
	}
}

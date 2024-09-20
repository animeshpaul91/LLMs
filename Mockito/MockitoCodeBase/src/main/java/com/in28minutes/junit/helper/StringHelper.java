package com.in28minutes.junit.helper;

public class StringHelper {
	public String truncateAInFirst2Positions(String str) {
		if (str.length() <= 2)
			return str.replaceAll("A", "");

		String first2Chars = str.substring(0, 2);
		String stringMinusFirst2Chars = str.substring(2);
		return first2Chars.replaceAll("A", "") + stringMinusFirst2Chars;
	}

	public String truncateAInLast2Positions(String str) {
		if (str.length() <= 2)
			return str.replaceAll("A", "");

		int length = str.length();
		String last2Chars = str.substring(length - 2);
		String stringMinusLast2Chars = str.substring(0, length - 2);
		return stringMinusLast2Chars + last2Chars.replaceAll("A", "");
	}
	
	public boolean areFirstAndLastTwoCharactersTheSame(String str) {
		if (str.length() <= 1)
			return false;
		if (str.length() == 2)
			return true;

		String first2Chars = str.substring(0, 2);
		String last2Chars = str.substring(str.length() - 2);
		return first2Chars.equals(last2Chars);
	}
}

package task2;

import sun.util.resources.cldr.st.CurrencyNames_st;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by A.Kovalev on 28.09.2016.+
 */
public class Task2 {
	private static StringBuilder _str = new StringBuilder();
	private static long _cur = 1;

	public static void main(String[] args) {
		String[] result;
		String[] input = getInput();
		result = new String[input.length];
		for (int i = 0; i < input.length; i++){
			result[i] = find(input[i]);
		}
		for (String l : result) System.out.println(l);
	}

	private static String[] getInput() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		List<String> input = new ArrayList<>();
		try {
			while (true) {
				String str = in.readLine();
				if (str.isEmpty()) break;
				if (!str.matches("\\d+")) throw new IllegalArgumentException("Wrong input!");
				input.add(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input.toArray(new String[0]);
	}

	private static String find(String find)
	{
		int hashToFind = find.hashCode();
		// Start string init
		while (find.length() > _str.length()){
			_str.append(_cur++);
		}
		int subHash;
		int i = 0;
		while (true){
			String sub = _str.substring(i, find.length() + i);
			subHash = sub.hashCode();
			if (subHash == hashToFind){
				if (sub.equals(find)) return (i + 1) + "";
			}
			// extend
			i++;
			while (_str.length() < find.length() + i){
				_str.append(_cur++);
			}
		}
	}
}
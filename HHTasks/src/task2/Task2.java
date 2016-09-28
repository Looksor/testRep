package task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by A.Kovalev on 28.09.2016.+
 */
public class Task2 {
	public static void main(String[] args) {
		long[] input = getInput();
		for (long l : input) System.out.println(l);
	}

	private static long[] getInput() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		List<Integer> input = new LinkedList<>();
		try {
			while (true) {
				String str = in.readLine();
				if (str.isEmpty()) break;
				input.add(Integer.parseInt(str));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input.stream().mapToLong(l -> l).toArray();
	}
}
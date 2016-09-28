package task1;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by A.Kovalev on 27.09.2016.+
 */
public class Task1 {
	public static void main(String[] args) {
		//int[][][] input = getInputData();
		//print(input);
		int[][][] test1 = {{{5, 3, 4, 5},
				{6, 2, 1, 4},
				{3, 1, 1, 4},
				{8, 5, 4, 3}}};
		int[] result = calculate(test1);
	}

	private static int[] calculate(int[][][] input) {
		int[] result = new int[input.length];
		for (int n = 0; n < input.length; n++) {
			int[][] matrix = input[n];
//			int[][] tempColMatrix = calcCol(matrix);
			int[][] tempRowMatrix = calcRow(matrix);
			print(tempRowMatrix);
//			result[n] = getResult(tempColMatrix, tempRowMatrix);
		}
		return result;
	}

	private static int[][] calcCol(int[][] matrix) {
		return null;
	}

	private static int[][] calcRow(int[][] matrix) {
		int width = matrix.length;
		int heigh = matrix[0].length;
		int result = 0;
		for (int i = 1; i < width - 1; i++) {
			int prev = matrix[i][0];
			int cur = 0;
			int max_index = 0;
			int max_value = matrix[i][0];
			int water_count = 0;
			for (int j = 1; j < heigh; j++) {
				cur = matrix[i][j];
//				System.out.println("Cur = " + cur + " prev = " + prev);
				if (cur > max_value) {
					for (int k = j - 1; k > max_index; k--) {
						matrix[i][k] = max_value;
					}
					max_value = cur;
					max_index = j;
				} else {
					if (cur > prev){    // Fill
//						System.out.println("!! Cur = " + cur + " > prev = " + prev);
						for (int k = j - 1; k > max_index; k--) {
							matrix[i][k] = cur;
						}
					}
				}
				prev = cur;
			}
		}
		return matrix;
	}

	private static int getResult(int[][] tempColMatrix, int[][] tempRowMatrix) {
		return 0;
	}

	private static int[][][] getInputData() {
		int[][][] result;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			// Number of Matrix
			int numOfMatrix = Integer.parseInt(in.readLine());
			result = new int[numOfMatrix][][];

			// Matrix init
			for (int n = 0; n < numOfMatrix; n++) {
				String matrixProp = in.readLine();
				if (!matrixProp.trim().matches("^\\d*\\s\\d*$"))
					throw new IllegalArgumentException("wrong matrix properties");
				int splitter = matrixProp.indexOf(' ');
				// height
				int height = Integer.parseInt(matrixProp.substring(0, splitter));
				// width
				int width = Integer.parseInt(matrixProp.substring(splitter + 1, matrixProp.length()));
				result[n] = new int[height][width];

				// Values init
				for (int h = 0; h < height; h++) {
					String line = in.readLine();
					if (!line.matches("^(\\d*\\s)*\\d*$")) throw new IllegalArgumentException("wrong matrix values");
					String[] values = line.split(" ");
					if (values.length != width) throw new IllegalArgumentException("wrong matrix values");
					for (int w = 0; w < width; w++) result[n][h][w] = Integer.parseInt(values[w]);
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}

	private static void print(int[][][] input) {
		for (int[][] matrix : input) {
			for (int[] row : matrix) {
				for (int el : row) {
					System.out.print(el + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	private static void print(int[][] input) {
		for (int[] row : input) {
			for (int el : row) {
				System.out.print(el + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}

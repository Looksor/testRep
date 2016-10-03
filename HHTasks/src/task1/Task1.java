package task1;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by A.Kovalev on 27.09.2016.
 *
 * Реализовал первый алгоритм который пришёл в голову
 * Суть - "заполнить низины" отдельно по строкам и столбцам, а затем взять наименьшее из 2 значений
 * алгоритм не самый оптимальный, но на разработку другого не нашлось времени :(
 *
 * Известные баги - неточость в матрицах со сложными низинами, например:
 *
 * 4 4 4        4 4 4           4 4 4
 * 4 1 4        4 4 4           4 1 4
 * 1 1 4    =>  1 1 4   вместо  1 1 4
 * 4 1 4        4 4 4           4 1 4
 * 4 4 4        4 4 4           4 4 4
 *
 * PS возможно было бы проще считать матрицу от краёв к центру
 */
public class Task1 {
	public static void main(String[] args) {
		int[][][] input = getInputData();
		int[] result = calculate(input);
		for (int i : result) System.out.println(i);
	}

	private static int[] calculate(int[][][] input) {
		int[] result = new int[input.length];
		for (int n = 0; n < input.length; n++) {
			int[][] matrix = input[n];
			int width = matrix.length;
			int heigh = matrix[0].length;

			// 2 матрицы для расчета по колонкам и столбцам
			int[][] rowMatrix = new int[width][heigh];
			int[][] colMatrix = new int[width][heigh];
			for (int i = 0; i < width; i++) {
				rowMatrix[i] = matrix[i].clone();
				colMatrix[i] = matrix[i].clone();
			}
			// calculate row
			rowMatrix = calculateRow(rowMatrix);

			// calculate col
			colMatrix = calculateCol(colMatrix);

			// calculate water level
			result[n] = getResult(matrix, colMatrix, rowMatrix);
		}
		return result;
	}

	private static int[][] calculateRow(int [][] rowMatrix){
		int width = rowMatrix.length;
		int heigh = rowMatrix[0].length;
		for (int i = 1; i < width - 1; i++) {
			int prev = rowMatrix[i][0];
			int cur;
			int maxIndex = 0;
			int maxValue = rowMatrix[i][0];

			for (int j = 1; j < heigh; j++) {
				cur = rowMatrix[i][j];
				if (cur > maxValue) {
					// Заполнение, так как нашлось более высокое значение
					for (int k = j - 1; k > maxIndex; k--) {   // От текущего значения до прошлой вершины
						rowMatrix[i][k] = maxValue;
					}
					maxValue = cur;
					maxIndex = j;
				} else {
					// Если началось возрастание (образовалась низина)
					if (cur > prev) {
						// Заполнение, так как образовалась низина
						for (int k = j - 1; k > maxIndex; k--) {   // От текущего значения до прошлой вершины
							if (rowMatrix[i][k] > cur) break;      // Если левый склон уже выше правой точки
							rowMatrix[i][k] = cur;
						}
					}
				}
				prev = cur;
			}
		}
		return rowMatrix;
	}

	private static int[][] calculateCol(int [][] colMatrix){
		int width = colMatrix.length;
		int heigh = colMatrix[0].length;
		for (int i = 1; i < heigh - 1; i++) {
			int prev = colMatrix[0][i];
			int cur;
			int maxIndex = 0;
			int maxValue = colMatrix[0][i];

			for (int j = 1; j < width; j++) {
				cur = colMatrix[j][i];
				if (cur > maxValue) {
					// Заполнение, так как нашлось более высокое значение
					for (int k = j - 1; k > maxIndex; k--) {   // От текущего значения до прошлой вершины
						colMatrix[k][i] = maxValue;
					}
					maxValue = cur;
					maxIndex = j;
				} else {
					// Если началось возрастание (образовалась низина)
					if (cur > prev) {
						// Заполнение, так как образовалась низина
						for (int k = j - 1; k > maxIndex; k--) {   // От текущего значения до прошлой вершины
							if (colMatrix[k][i] > cur) break;      // Если верхний склон уже выше нижней точки
							colMatrix[k][i] = cur;
						}
					}
				}
				prev = cur;
			}
		}
		return colMatrix;
	}

	private static int getResult(int[][] matrix, int[][] colMatrix, int[][] rowMatrix) {
		int res = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != colMatrix[i][j] || matrix[i][j] != rowMatrix[i][j]) {   // Если есть изменения
					if (colMatrix[i][j] < rowMatrix[i][j]) {    // берем меньшее
						res += colMatrix[i][j] - matrix[i][j];
					} else {
						res += rowMatrix[i][j] - matrix[i][j];
					}
				}
			}
		}
		return res;
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

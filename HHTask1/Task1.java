import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by A.Kovalev on 27.09.2016.+
 */
public class Task1 {
	public static void main(String[] args) {
		int[][][] input = getInputData();
		print(input);
	}

	private static void print(int[][][] input) {
		for (int[][] matrix : input){
			for (int[] row: matrix){
				for (int el: row){
					System.out.print(el + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	private static int[][][] getInputData() {
		int[][][] result;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			// Number of Matrix
			System.out.print("1");
			int numOfMatrix = Integer.parseInt(in.readLine());
			result = new int[numOfMatrix][][];

			// Matrix init
			for (int n = 0; n < numOfMatrix; n++) {
				String matrixProp = in.readLine();
				if (!matrixProp.trim().matches("^\\d*\\s\\d*$")) throw new IllegalArgumentException("wrong matrix properties");
				int splitter = matrixProp.indexOf(' ');
				// height
				int height = Integer.parseInt(matrixProp.substring(0, splitter));
				// width
				int width = Integer.parseInt(matrixProp.substring(splitter + 1, matrixProp.length()));
				result[n] = new int[height][width];

				// Values init
				for (int h = 0; h < height; h++){
					String line = in.readLine();
					if (!line.matches("^(\\d*\\s)+\\d*$")) throw new IllegalArgumentException("wrong matrix values");
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


}

package image.compressor.matrix.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import Jama.Matrix;

public class MatrixReader {
	public static Matrix readMatrixFromPgmFile(String fileName) {
		Matrix result;
		FileInputStream fileInputStream = null;
		Scanner scan = null;
		try {
			fileInputStream = new FileInputStream(fileName);
			scan = new Scanner(fileInputStream);
			// Discard the magic number
			scan.nextLine();
			// Discard the comment line
			scan.nextLine();
			// Read pic width, height and max value
			int picWidth = scan.nextInt();
			int picHeight = scan.nextInt();
			// Discard the max value
			// TODO militsa - is this max value needed??!!
			scan.nextInt();
			result = new Matrix(picHeight, picWidth);
			for (int i = 0; i < picHeight; i++) {
				for (int j = 0; j < picWidth; j++) {
					result.set(i, j, scan.nextInt());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			result = new Matrix(null);
		} finally {
			try {
				fileInputStream.close();
				scan.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		//sample usage
		MatrixReader.readMatrixFromPgmFile("E:\\Pictures\\baboon.ascii.pgm").print(3, 0);
	}

}

package image.compressor.matrix.common.image.converter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Jama.Matrix;
import image.compressor.matrix.common.SVD;

public class ImageUtil {

	/**
	 * Convert RGB image to greyscale
	 * 
	 * @param imagePath
	 * @param greyScaleImagePath
	 */
	public static void turnToGreyScale(String imagePath,
			String greyScaleImagePath) {
		BufferedImage image;
		int width;
		int height;
		try {
			File input = new File(imagePath);
			image = ImageIO.read(input);
			width = image.getWidth();
			height = image.getHeight();

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					Color c = new Color(image.getRGB(j, i));
					int red = (int) (c.getRed() * 0.299);
					int green = (int) (c.getGreen() * 0.587);
					int blue = (int) (c.getBlue() * 0.114);
					Color newColor = new Color(red + green + blue, red + green
							+ blue, red + green + blue);
					image.setRGB(j, i, newColor.getRGB());
				}
			}
			File ouptut = new File(greyScaleImagePath);
			ImageIO.write(image, ImageFormat.JPEG.getFormat(), ouptut);
		} catch (Exception e) {
		}
	}

	/**
	 * Converts image to matrix
	 * 
	 * @param imagePath
	 * @return
	 */
	public static Matrix getImageAsMatrix(String imagePath) {
		File img = new File(imagePath);
		BufferedImage image = null;
		try {
			image = ImageIO.read(img);
		} catch (IOException e) {
			System.err.println("Unable to find file");
			e.printStackTrace();
		}
		int width = image.getWidth();
		int height = image.getHeight();
		double[][] result = new double[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				result[row][col] = image.getRGB(col, row);
			}
		}
		return new Matrix(result);
	}

	/**
	 * Converts matrix to image
	 * 
	 * @param matrix
	 * @param imagePath
	 */
	public static void createImageFromMatrix(Matrix matrix, String imagePath) {
		int width = matrix.getColumnDimension();
		int height = matrix.getRowDimension();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				image.setRGB(col, row, (int) matrix.get(row, col));
			}
		}
		File outputfile = new File(imagePath);
		try {
			ImageIO.write(image, ImageFormat.JPEG.getFormat(), outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static public void main(String args[]) throws Exception {
		Matrix imageAsMatrix = ImageUtil
				.getImageAsMatrix("E:\\Pictures\\download.jpg");
//
                Matrix a = SVD.SVD(imageAsMatrix,20);
//                double m [][]= {{2,0}, {1, 2}, {0, 1}};
//                Matrix b = new Matrix(m);
//                SVD.SVD(b, 20).print(5, 2);
//                Matrix randomMatrix = Matrix.random(100, 100);
//                SVD.SVD(randomMatrix, 0);
                ImageUtil.createImageFromMatrix( a ,
				"E:\\Pictures\\download1.jpg");
	}
}
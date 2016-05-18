//package image.compressor.matrix.common.image.converter;
//
//import java.awt.Color;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//
//import Jama.Matrix;
//import image.compressor.matrix.common.SVD;
//
//public class ImageUtil {
//
//	/**
//	 * Convert RGB image to greyscale
//	 * 
//	 * @param imagePath
//	 * @param greyScaleImagePath
//	 */
//	public static void turnToGreyScale(String imagePath,
//			String greyScaleImagePath) {
//		BufferedImage image;
//		int width;
//		int height;
//		try {
//			File input = new File(imagePath);
//			image = ImageIO.read(input);
//			width = image.getWidth();
//			height = image.getHeight();
//
//			for (int i = 0; i < height; i++) {
//				for (int j = 0; j < width; j++) {
//					Color c = new Color(image.getRGB(j, i));
//					int red = (int) (c.getRed() * 0.299);
//					int green = (int) (c.getGreen() * 0.587);
//					int blue = (int) (c.getBlue() * 0.114);
//					Color newColor = new Color(red + green + blue, red + green
//							+ blue, red + green + blue);
//					image.setRGB(j, i, newColor.getRGB());
//				}
//			}
//			File ouptut = new File(greyScaleImagePath);
//			ImageIO.write(image, ImageFormat.JPEG.getFormat(), ouptut);
//		} catch (Exception e) {
//		}
//	}
//
//	/**
//	 * Converts image to matrix
//	 * 
//	 * @param imagePath
//	 * @return
//	 */
//	public static Matrix getImageAsMatrix(String imagePath) {
//		File img = new File(imagePath);
//		BufferedImage image = null;
//		try {
//			image = ImageIO.read(img);
//		} catch (IOException e) {
//			System.err.println("Unable to find file");
//			e.printStackTrace();
//		}
//		int width = image.getWidth();
//		int height = image.getHeight();
//		double[][] result = new double[height][width];
//
//		for (int row = 0; row < height; row++) {
//			for (int col = 0; col < width; col++) {
//				result[row][col] = image.getRGB(col, row);
//			}
//		}
//		return new Matrix(result);
//	}
//
//	/**
//	 * Converts matrix to image
//	 * 
//	 * @param matrix
//	 * @param imagePath
//	 */
//	public static void createImageFromMatrix(Matrix matrix, String imagePath) {
//		int width = matrix.getColumnDimension();
//		int height = matrix.getRowDimension();
//		BufferedImage image = new BufferedImage(width, height,
//				BufferedImage.TYPE_INT_RGB);
//
//		for (int row = 0; row < height; row++) {
//			for (int col = 0; col < width; col++) {
//				image.setRGB(col, row, (int) matrix.get(row, col));
//			}
//		}
//		File outputfile = new File(imagePath);
//		try {
//			ImageIO.write(image, ImageFormat.JPEG.getFormat(), outputfile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	static public void main(String args[]) throws Exception {
//		Matrix imageAsMatrix = ImageUtil
//				.getImageAsMatrix("E:\\Pictures\\download.jpg");
//                
//		ImageUtil.createImageFromMatrix(SVD.SVD(imageAsMatrix, 90),
//				"E:\\Pictures\\download1.jpg");
//	}
//}
package image.compressor.matrix.common.image.converter;

import image.compressor.matrix.common.SVD;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Jama.Matrix;
import image.compressor.matrix.common.SVD;

public class ImageUtil {

    private Matrix redMatrix;
    private Matrix blueMatrix;
    private Matrix greenMatrix;
    private String imagePath;

    public ImageUtil(String imagePath) {
        this.imagePath = imagePath;
        createMatrices();
    }

    /**
     * Converts image to matrix
     *
     * @param imagePath
     */
    private void createMatrices() {
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
        double[][] red = new double[height][width];
        double[][] green = new double[height][width];
        double[][] blue = new double[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                try{
                int rgb = image.getRGB(col,row);
                Color color = new Color(rgb);
                blue[row][col] = color.getBlue();
                red[row][col] = color.getRed();
                green[row][col] = color.getGreen();}
                catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("ERROR");
                }
            }
        }
        redMatrix = new Matrix(red);
        greenMatrix = new Matrix(green);
        blueMatrix = new Matrix(blue);
    }

    private void createImageFromThreeMatrices(Matrix red, Matrix green,
            Matrix blue, String imagePathNew) {
        int width = red.getColumnDimension();
        int height = red.getRowDimension();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int r = (int) red.get(row, col);
                if (r < 0) {
                    r = 0;
                }
                if (r > 255) {
                    r = 255;
                }
                int g = (int) green.get(row, col);
                if (g < 0) {
                    g = 0;
                }
                if (g > 255) {
                    g = 255;
                }
                int b = (int) blue.get(row, col);
                if (b < 0) {
                    b = 0;
                }
                if (b > 255) {
                    b = 255;
                }
                Color color = new Color(r, g, b);
                image.setRGB( col,row, color.getRGB());
            }
        }
        File outputfile = new File(imagePathNew);
        try {
            ImageIO.write(image, ImageFormat.JPEG.getFormat(), outputfile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void convertImage(String newImagePath, int compression) {
        Matrix blueMatrixCompressed = SVD.SVD(blueMatrix, compression);
        Matrix redMatrixCompressed = SVD.SVD(redMatrix, compression);
        Matrix greenMatrixCompressed = SVD.SVD(greenMatrix, compression);
        createImageFromThreeMatrices(redMatrixCompressed,
                greenMatrixCompressed, blueMatrixCompressed, newImagePath);
//		createImageFromThreeMatrices(redMatrix,
//				greenMatrix, blueMatrix, newImagePath);
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
                image.setRGB(row, col, (int) matrix.get(row, col));
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
        ImageUtil util = new ImageUtil("E:\\\\Pictures\\\\12190927_891433274226774_1005484681681057446_n.jpg");
        util.convertImage("E:\\\\Pictures\\\\12190927_891433274226774_1005484681681057446_n1.jpg",95);

    }
}

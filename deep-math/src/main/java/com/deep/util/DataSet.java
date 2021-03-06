package com.deep.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DataSet {

	static String BASE_PATH = "E:/";
	static String INPUT_DIR = "E:/imgs/";
	static String OUTPUT_DIR = "E:/img/";
	static String[] TRIAN_SET_32 = new String[] { 
		"216_129", 
		"216_130", 
		"216_131", 
		"216_132", 
		"216_133", 
		"216_134", 
		"216_135", 
		"216_136", 
		"216_137", 
		"216_138", 
		"216_139", 
		"216_140",
		"270_190", 
		"270_191", 
		"270_192", 
		"270_193", 
		"270_194", 
		"270_195",
		"270_196", 
		"270_197", 
		"270_198", 
		"270_199", 
		"270_200", 
		"270_201"
	};

	public static void main(String[] args) throws IOException {
		img2slice("E:/chengdongliang.jpg", "E:/imgs", 32, 32);
	}

	public static double[][][][] loadImageData() {
		double[][][][] input = new double[TRIAN_SET_32.length][][][];
		for (int i = 0; i < TRIAN_SET_32.length; i++) {
			input[i] = img2rgb(INPUT_DIR + TRIAN_SET_32[i] + ".jpg");
		}
		return input;
	}

	public static double[] img2line(String src) {
		try {
			src = BASE_PATH + src;
			BufferedImage image = ImageIO.read(new File(src));
			int width = image.getWidth();
			int height = image.getHeight();
			double[] m = new double[width * height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = image.getRGB(x, y);
					double a = (pixel & 0xff000000) >> 24;// 屏蔽低位，并移位到最低位
					double r = (pixel & 0xff0000) >> 16;
					double g = (pixel & 0xff00) >> 8;
					double b = (pixel & 0xff);
					m[y * width + x] = (r + g + b) / (3 * 255);
				}
			}
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static double[][] img2lines(String src) {
		try {
			BufferedImage image = ImageIO.read(new File(src));
			int width = image.getWidth();
			int height = image.getHeight();
			double[][] M = new double[width * height][1];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = image.getRGB(x, y);
					double a = (pixel & 0xff000000) >> 24;// 屏蔽低位，并移位到最低位
					double r = (pixel & 0xff0000) >> 16;
					double g = (pixel & 0xff00) >> 8;
					double b = (pixel & 0xff);
					M[y * width + x][0] = (r + g + b) / (3 * 255);
				}
			}
			return M;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static double[][] img2linergb(String src) {
		try {
			BufferedImage image = ImageIO.read(new File(src));
			int width = image.getWidth();
			int height = image.getHeight();
			double[] R = new double[width * height];
			double[] G = new double[width * height];
			double[] B = new double[width * height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = image.getRGB(x, y);
					double a = (pixel & 0xff000000) >> 24;// 屏蔽低位，并移位到最低位
					double r = (pixel & 0xff0000) >> 16;
					double g = (pixel & 0xff00) >> 8;
					double b = (pixel & 0xff);
					R[y * width + x] = (r / 255);
					G[y * width + x] = (g / 255);
					B[y * width + x] = (b / 255);
				}
			}
			return new double[][] { R, G, B };
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void gray2img(double[][][] input) {
		for (int i = 0; i < input.length; i++) {
			gray2img(input[i]);
		}
	}

	public static void gray2img(double[][][][] input) {
		for (int i = 0; i < input.length; i++) {
			gray2img(input[i]);
		}
	}

	public static void gray2img(double[][] input) {
		try {
			int width = input[0].length;
			int height = input.length;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			File file = new File(OUTPUT_DIR);
			if (file.exists())
				file.delete();
			file.mkdirs();
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int r = ((int) (input[y][x] * 255)) << 16;
					int g = ((int) (input[y][x] * 255)) << 8;
					int b = ((int) (input[y][x] * 255));
					image.setRGB(x, y, r + g + b);
				}
			}
			ImageIO.write(image, "JPEG", new File(OUTPUT_DIR + System.currentTimeMillis() + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void rgb2img(double[][] input, String name) {
		try {
			int width = input[0].length;
			int height = input.length;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			File file = new File(OUTPUT_DIR);
			if (file.exists())
				file.delete();
			file.mkdirs();
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int r = ((int) (input[y][x] * 255)) << 16;
					int g = ((int) (input[y][x] * 255)) << 8;
					int b = ((int) (input[y][x] * 255));
					image.setRGB(x, y, r + g + b);
				}
			}
			ImageIO.write(image, "JPEG", new File(OUTPUT_DIR + name));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static double[][][] img2rgb(String src) {
		try {
			BufferedImage image = ImageIO.read(new File(src));
			int width = image.getWidth();
			int height = image.getHeight();
			double[][] R = new double[height][width];
			double[][] G = new double[height][width];
			double[][] B = new double[height][width];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = image.getRGB(x, y);
					double r = (pixel & 0xff0000) >> 16;
					double g = (pixel & 0xff00) >> 8;
					double b = (pixel & 0xff);
					R[y][x] = r / 255;
					G[y][x] = g / 255;
					B[y][x] = b / 255;
				}
			}
			return new double[][][] { R, G, B };
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static double[] img2line(BufferedImage image) {
		try {
			int width = image.getWidth();
			int height = image.getHeight();
			double[] array = new double[width * height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = image.getRGB(x, y);
					double a = (pixel & 0xff000000) >> 24;// 屏蔽低位，并移位到最低位
					double r = (pixel & 0xff0000) >> 16;
					double g = (pixel & 0xff00) >> 8;
					double b = (pixel & 0xff);
					array[y * width + x] = (r + g + b) / (3 * 255);
				}
			}
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static double[][][] img2line(String source, int width, int height) {
		try {
			String dir = BASE_PATH;
			BufferedImage buffer = ImageIO.read(new File(source));
			int cols = buffer.getWidth() - width + 1;
			int rows = buffer.getHeight() - height + 1;
			BufferedImage image = null;
			double[][][] images = new double[rows][cols][width * height];
			File file = new File(dir);
			if (file.exists())
				file.delete();
			file.mkdirs();
			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < cols; x++) {
					image = buffer.getSubimage(x, y, width, height);
					file = new File(dir + y + "_" + x + ".jpg");
					ImageIO.write(image, "JPEG", file);
					images[y][x] = img2line(ImageIO.read(file));
				}
			}
			return images;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void img2slice(String source, String target, int width, int height) {
		try {
			BufferedImage bi = ImageIO.read(new File(source));
			double cols = bi.getWidth() - width + 1;
			double rows = bi.getHeight() - height + 1;
			File file = new File(target);
			BufferedImage image = null;
			if (file.exists())
				file.delete();
			file.mkdirs();
			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < cols; x++) {
					image = bi.getSubimage(x, y, width, height);
					file = new File(target + "/" + y + "_" + x + ".jpg");
					ImageIO.write(image, "JPEG", file);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void copy(String fileName, String target) {
		try {
			File file = new File(fileName);
			BufferedImage image = ImageIO.read(file);
			File outFile = new File(target);
			if (!outFile.exists())
				outFile.mkdirs();
			ImageIO.write(image, "JPEG", new File(target + file.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
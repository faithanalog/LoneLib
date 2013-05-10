package com.unknownloner.lonelib.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	private static Graphics2D ctx = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
	
	public static BufferedImage scaledImg(BufferedImage img, int width, int height) {
		BufferedImage newImg = new BufferedImage(width, height, img.getType());
		Graphics2D g2d = newImg.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.drawImage(img, 0, 0, width, height, null);
		g2d.dispose();
		return newImg;
	}
	
	public static BufferedImage loadImg(String path) {
		try {
			return ImageIO.read(ImageUtil.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int ascent(Font font) {
		return ctx.getFontMetrics(font).getAscent();
	}
	
	public static int descent(Font font) {
		return ctx.getFontMetrics(font).getDescent();
	}
	
	public static int maxAscent(Font font) {
		return ctx.getFontMetrics(font).getMaxAscent();
	}
	
	public static int maxDescent(Font font) {
		return ctx.getFontMetrics(font).getMaxDescent();
	}
	
	public static int stringWidth(String str, Font font) {
		return ctx.getFontMetrics(font).stringWidth(str);
	}
	
	public static FontMetrics getMetrics(Font font) {
		return ctx.getFontMetrics(font);
	}

}

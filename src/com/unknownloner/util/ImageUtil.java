package com.unknownloner.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	
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

}

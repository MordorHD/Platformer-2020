package javaJG.component;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageView extends Component {

	private BufferedImage image;
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public ImageView() {
		
	}
	
	public ImageView(BufferedImage image) {
		this.image = image;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(image, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
	}
	
}

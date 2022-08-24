package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.swing.JOptionPane;

import object.OBJ_Key;

public class UI {

	GamePanel gp;
	Font arial_40;
	Font arial_80B;
	BufferedImage keyImage;
	public boolean messageOn = false;   // to the beginning, player have no key, so message "You got got the key" isn't showed 
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0");
	
	
	public UI(GamePanel gp) {
		
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		OBJ_Key key = new OBJ_Key(gp); 
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;   // when get the key, this variable changes to true
 	}
	
	public void draw(Graphics2D g2) {
		
		if(gameFinished == true) {
			
			g2.setFont(arial_40);
			g2.setColor(Color.white);
			
			String text;
			int textLength;
			int x;
			int y;
			
			text = "You found the treasure!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - (gp.tileSize*3);
			g2.drawString(text, x, y);
			
			text = "Your time is :" + dFormat.format(playTime);
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*4);
			g2.drawString(text, x, y);
			 
			g2.setFont(arial_80B);
			g2.setColor(Color.red);
			text = "Congratulations!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*2);
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
			
		}
		else {
			
			g2.setFont(arial_40);   // (font name, font style, font size)
			g2.setColor(Color.white);
			g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
			g2.drawString("x = " + gp.player.hasKey, 74, 65);
			
			//Time
			playTime += (double)1/60;
			g2.drawString("Time:" + dFormat.format(playTime), gp.tileSize*13, 55);
			
			// Message
			if(messageOn == true) {
				
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message, gp.tileSize/2, gp.tileSize * 5);
				
				messageCounter++;
				
				if(messageCounter > 120) {   // 60 frame is a 1 sec, so 120 is 2 sec
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
	}
}









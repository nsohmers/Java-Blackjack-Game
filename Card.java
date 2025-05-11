import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Card {
  private int type; // Type of card
  private int value; // Value of card in Black Jack
  private int suit; // Suit: 0 = Diamonds, 1 = Hearts, 2 = Spades, 3 = Clubs
  private static BufferedImage back_image; // Image of back of card.
  private BufferedImage suit_image; // Image showing value and suit
  private boolean hidden; // If false,the card is facing up (visible)

  public Card(int type, int suit, boolean hidden) {
    this.type = type;
    this.suit = suit;
    this.value = (this.type <= 10) ? this.value = this.type : 10;
    this.suit_image = generateSuitImage(this.type, this.suit);
    this.back_image = generateBackImage();
    this.hidden = hidden;
  }

  private static BufferedImage generateSuitImage(int type, int suit) {
    BufferedImage result = null;

    String filePath = "images/" + suit + "/" + type + ".png";
    try {
      result = ImageIO.read(new File(filePath));
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return result;
  }

  private static BufferedImage generateBackImage() {
    BufferedImage result = null;

    String filePath = "images/backimage.png";
    try {
      result = ImageIO.read(new File(filePath));
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return result;
  }

  public void draw(int x, int y, Graphics g) {
    g.drawImage(hidden ? back_image : suit_image, x, y, null);
  }

  public int getValue() {
    return this.value;
  }

  public boolean isAce() {
    return (this.type == 1) ? true : false;
  }

  public boolean isHidden() {
    return hidden;
  }

  public void show() {
    hidden = false;
  }

  public void hide() {
    hidden = true;
  }
}

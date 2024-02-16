import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
public class AnimatedText {
    private String text;
    private Color color;
    private int time;
    private boolean hidden;

    public AnimatedText(String text, Color color) {
        this.text = text;
        this.color = color;
        this.time = 0;
        this.hidden = true;
    }

    public void draw(Graphics g) {
        if (!hidden) {
            g.setFont(new Font("Arial", Font.PLAIN, 120));
            g.setColor(color);
            int x = 400 - g.getFontMetrics().stringWidth(text) / 2;
            for (int i = 0; i < text.length(); i++) {
                String currentChar = Character.toString(text.charAt(i));
                
                int y = (time * 2 - (i * 20)) - 50;
                g.drawString(currentChar, x, (y <= 335) ? y : 315); 
                x += g.getFontMetrics().stringWidth(currentChar);
            }
        }
    }

    public void update() { time++; }
    public void show() { hidden = false; time = 0; }
    public void hide() { hidden = true; }
}

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
public class AnimatedText {
    private String text;
    private Color color;
    private int time, animation;
    private boolean hidden;

    public AnimatedText(String text, Color color) {
        this.text = text;
        this.color = color;
        this.time = 0;
        this.hidden = true;
        this.animation = (int) (Math.random() * 3);
    }

    public void draw(Graphics g) {
        if (!hidden) {
            g.setFont(new Font("Arial", Font.PLAIN, 120));
            g.setColor(color);
            int x;
            switch (animation) {
                case 0:
                    x = 400 - g.getFontMetrics().stringWidth(text) / 2;
                    for (int i = 0; i < text.length(); i++) {
                        String currentChar = Character.toString(text.charAt(i));

                        int y = (time * 5 - (i * 20)) - 50;
                        g.drawString(currentChar, x, (y <= 335) ? y : 315);
                        x += g.getFontMetrics().stringWidth(currentChar);
                    }
                    break;
                case 1:
                    for (int i = 0; i < text.length(); i++) {
                        String currentChar = Character.toString(text.charAt(i));
                        int finalpos = (400 - g.getFontMetrics().stringWidth(text) / 2) + g.getFontMetrics().stringWidth(text.substring(0, i));
                        int initialpos = -g.getFontMetrics().stringWidth(text.substring(0, i));
                        x = initialpos + time * 10;
                        g.drawString(currentChar, (x <= finalpos) ? x : finalpos, 315);
                    }
                    break;
                case 2:
                    for (int i = 0; i < text.length(); i++) {
                        String currentChar = Character.toString(text.charAt(i));
                        int finalpos = (400 - g.getFontMetrics().stringWidth(text) / 2) + g.getFontMetrics().stringWidth(text.substring(0, i));
                        int initialpos = 800 + g.getFontMetrics().stringWidth(text.substring(i, text.length()));
                        x = initialpos - time * 10;
                        g.drawString(currentChar, (x >= finalpos) ? x : finalpos, 315);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void update() { time++; }
    public void show() { hidden = false; time = 0; animation = (int) (Math.random() * 3); }
    public void hide() { hidden = true; }
}


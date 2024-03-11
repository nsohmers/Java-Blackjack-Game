import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;

import javax.swing.JLabel;

import java.util.ArrayList;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Table extends JPanel implements MouseListener, MouseMotionListener  {
    private ArrayList<Card> deck;
    private ArrayList<Card> playerCards;
    private ArrayList<Card> dealerCards;

    private JLabel playerScoreLabel;
    private JLabel playerWinCounterLabel;
    private JLabel dealerScoreLabel;
    private JLabel dealerWinCounterLabel;

	private AudioPlayer audioPlayer;

    private AnimatedText lostText, winText, tieText;

	private int playerWins;
    private int dealerWins;

    private int newGameButtonX = 635;
    private int newGameButtonY = 468;

    private Font buttonFont = new Font("Times New Roman", Font.PLAIN, 35);

    private boolean showGameButtons, hitButtonHover, standButtonHover, newGameButtonHover;
    private int buttonSize, hitButtonX, hitButtonY, standButtonX, standButtonY;

	public Table(){
		setLayout(null);

        playerScoreLabel = new JLabel();
        playerScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerScoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        playerScoreLabel.setBounds(460, 5, 300, 30);
        playerScoreLabel.setText("Current Player Score:");
        playerScoreLabel.setForeground(Color.WHITE);
        this.add(playerScoreLabel);

        dealerScoreLabel = new JLabel();
        dealerScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dealerScoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dealerScoreLabel.setBounds(460, 55, 300, 30);
        dealerScoreLabel.setText("Current Dealer Score:");
        dealerScoreLabel.setForeground(Color.WHITE);
        this.add(dealerScoreLabel);

        playerWinCounterLabel = new JLabel();
        playerWinCounterLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerWinCounterLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        playerWinCounterLabel.setBounds(460, 105, 300, 30);
        playerWinCounterLabel.setText("Player Win Counter:");
        playerWinCounterLabel.setForeground(Color.WHITE);
        this.add(playerWinCounterLabel);

        dealerWinCounterLabel = new JLabel();
        dealerWinCounterLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dealerWinCounterLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dealerWinCounterLabel.setBounds(460, 155, 300, 30);
        dealerWinCounterLabel.setText("Dealer Win Counter:");
        dealerWinCounterLabel.setForeground(Color.WHITE);
        this.add(dealerWinCounterLabel);

		setFocusable(true);

        addMouseListener(this);
        addMouseMotionListener(this);

        lostText = new AnimatedText("You Lost!", Color.RED);
        winText = new AnimatedText("You Won!", Color.YELLOW);
        tieText = new AnimatedText("It's a Tie!", Color.GREEN);

		deck = new ArrayList<Card>();
        playerCards = new ArrayList<Card>();
        dealerCards = new ArrayList<Card>();

        resetDecks();
        shuffleDeck();

		audioPlayer = new AudioPlayer();
		
		playerWins = 0;
        dealerWins = 0;

        showGameButtons = false;
        hitButtonHover = false;
        standButtonHover = false;
        newGameButtonHover = false;
	}

    private void moveCardDecktoPlayer() {
        if (deck.size() == 0) {
            shuffleDeck();
        }
        playerCards.add(deck.get(0));
        playerCards.get(playerCards.size() - 1).show();
        deck.remove(0);
    }

    private void moveCardDecktoDealer() {
        if (deck.size() == 0) {
            shuffleDeck();
        }
        dealerCards.add(deck.get(0));
        dealerCards.get(dealerCards.size() - 1).show();
        deck.remove(0);
    }

    private void shuffleDeck() {
        if (deck.size() != 0) return;

        for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				deck.add(new Card(j + 1, i, true));
			}
		}

		for (int i = 0; i < deck.size(); i++) {
			int j = (int) (Math.random() * deck.size());
			Card temp = deck.get(i);
			deck.set(i, deck.get(j));
            deck.set(j, temp);
		}
    }

	private void resetDecks() {
        playerCards.clear();
        dealerCards.clear();
	}

	private int getPlayerCardValue() {
		int total = 0;
		boolean hasAce = false;
		for (int i = 0; i < playerCards.size(); i++) {
            total += playerCards.get(i).isHidden() ? 0 : playerCards.get(i).getValue();
			if (playerCards.get(i).isAce() && !(playerCards.get(i).isHidden())) hasAce = true;
        }
		if (total + 10 <= 21 && hasAce) {
			total += 10;
		}
		return total;
	}

    private int getDealerCardValue() {
		int total = 0;
		boolean hasAce = false;
		for (int i = 0; i < dealerCards.size(); i++) {
            total += dealerCards.get(i).isHidden() ? 0 : dealerCards.get(i).getValue();
			if (dealerCards.get(i).isAce() && !(dealerCards.get(i).isHidden())) hasAce = true;
        }
		if (total + 10 <= 21 && hasAce) {
			total += 10;
		}
		return total;
	}

	private void updateTexts() {
		playerScoreLabel.setText("Current Player Score: " + this.getPlayerCardValue());
        dealerScoreLabel.setText("Current Dealer Score: " + this.getDealerCardValue());
		playerWinCounterLabel.setText("Player Win Counter: " + playerWins);
        dealerWinCounterLabel.setText("Dealer Win Counter: " + dealerWins);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(800,600);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        g.setColor(new Color(48, 95, 40)); // 47, 90, 40
        g.fillRect(0, 0, 800, 600);

        g.setColor(new Color(37, 72, 30));// 22, 43, 18
        g.fillOval(0, 525, 800, 150);

        g.setColor(new Color(22, 43, 18));
        int deckX = ((int) (40 - (deck.size() * 0.5)));
        int deckY = ((int) (20 - ((deck.size() * 0.1)))) ;
        int deckW = 138 + ((int) (deck.size() * 0.5));
        int deckH = ((int) (205 - (deck.size() * 0.1)));

        int[] shadowX = {deckX, (int) (deckX - deckW * 0.05), deckX + deckW};
        int[] shadowY = {deckY + deckH, deckY - (int) (deckH * 0.01), 20};

        if (deck.size() != 0) {
            g.fillPolygon(shadowX, shadowY, 3);
        }
        

        for (int i = 0; i < playerCards.size(); i++) {
            playerCards.get(i).draw(20 + i * 70, 325, g);
        }

        for (int i = 0; i < dealerCards.size(); i++) {
            dealerCards.get(i).draw(215 + i * 70, 20, g);
        }

        for (int i = 0; i < deck.size(); i++) {
            deck.get(i).draw((int) (40 - (i * 0.5)), (int) (20 - (i * 0.1)), g);
        }

        g.setFont(buttonFont);

        buttonSize = g.getFontMetrics().stringWidth("iStandi");

        hitButtonX = newGameButtonX - (buttonSize / 2) - 5;
        hitButtonY = newGameButtonY - buttonSize - 5;

        standButtonX = newGameButtonX + (buttonSize / 2) + 5;
        standButtonY = newGameButtonY - buttonSize - 5;

        if (showGameButtons) {
            g.setColor((hitButtonHover) ? Color.YELLOW : (new Color(22, 43, 18)));
            g.fillOval(hitButtonX, hitButtonY, buttonSize, buttonSize);
    
            g.setColor(Color.WHITE);
            g.drawOval(hitButtonX, hitButtonY, buttonSize, buttonSize);
    
            g.drawString("Hit", (hitButtonX  + (buttonSize / 2)) - (g.getFontMetrics().stringWidth("Hit") / 2), ((hitButtonY  + (buttonSize / 2)) + (g.getFontMetrics().getHeight() / 2)) - g.getFontMetrics().getHeight() / 4);
    
            g.setColor((standButtonHover) ? Color.YELLOW : (new Color(22, 43, 18)));
            g.fillOval(standButtonX, standButtonY, buttonSize, buttonSize);
    
            g.setColor(Color.WHITE);
            g.drawOval(standButtonX, standButtonY, buttonSize, buttonSize);
    
            g.drawString("Stand", standButtonX + g.getFontMetrics().stringWidth("i"), ((standButtonY  + (buttonSize / 2)) + (g.getFontMetrics().getHeight() / 2)) - g.getFontMetrics().getHeight() / 4);
        }

        g.setColor((newGameButtonHover) ? Color.YELLOW : (new Color(22, 43, 18)));
        g.fillOval(newGameButtonX, newGameButtonY, buttonSize, buttonSize);

        g.setColor(Color.WHITE);
        g.drawOval(newGameButtonX, newGameButtonY, buttonSize, buttonSize);
        g.drawString("New", (newGameButtonX  + (buttonSize / 2)) - (g.getFontMetrics().stringWidth("New") / 2), ((newGameButtonY  + (buttonSize / 2)) + (g.getFontMetrics().getHeight() / 2)) - (g.getFontMetrics().getHeight() / 4) * 3);

        g.drawString("Game", (newGameButtonX  + (buttonSize / 2)) - (g.getFontMetrics().stringWidth("Game") / 2), ((newGameButtonY  + (buttonSize / 2)) + (g.getFontMetrics().getHeight() / 2)) +  (g.getFontMetrics().getHeight() / 8));

        lostText.draw(g);
        winText.draw(g);
        tieText.draw(g);
    }

    public void animate() {
        while (true) {
			try {
				Thread.sleep(10);
			}
			catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

            lostText.update();
            winText.update();
            tieText.update();

            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        boolean updateScreen = false;

        if (x >= newGameButtonX && x <= newGameButtonX + buttonSize && y >= newGameButtonY && y <= newGameButtonY + buttonSize) {
            audioPlayer.playShuffleAudio();

            lostText.hide();
            winText.hide();
            tieText.hide();

            this.resetDecks();

            this.moveCardDecktoPlayer();
            this.moveCardDecktoPlayer();

            this.moveCardDecktoDealer();
            this.moveCardDecktoDealer();

            dealerCards.get(1).hide();

            showGameButtons = true;
            updateScreen = true;
        } 

        if ((x >= hitButtonX && x <= hitButtonX + buttonSize && y >= hitButtonY && y <= hitButtonY + buttonSize) && showGameButtons) {
            audioPlayer.playHitAudio();
            this.moveCardDecktoPlayer();

			if (this.getPlayerCardValue() > 21) {
                audioPlayer.playLoseAudio();
                lostText.show();
				dealerWins++;

                showGameButtons = false;
			}
            updateScreen = true;
        }
        
        if ((x >= standButtonX && x <= standButtonX + buttonSize && y >= standButtonY && y <= standButtonY + buttonSize) || this.getPlayerCardValue() == 21 && showGameButtons) {
            audioPlayer.playStandAudio();

            dealerCards.get(1).show();
	
            while (this.getDealerCardValue() < 17 ) {
                this.moveCardDecktoDealer();
            }

            if (this.getDealerCardValue() > 21) {
                audioPlayer.playWinAudio();
                winText.show();
                playerWins++;
            } else {
                if (this.getPlayerCardValue() > this.getDealerCardValue()) {
                    audioPlayer.playWinAudio();
                    winText.show();
                    playerWins++;
                } else if (this.getDealerCardValue() > this.getPlayerCardValue()) {
                    audioPlayer.playLoseAudio();
                    lostText.show();
                    dealerWins++;
                } else {
                    tieText.show();
                }
            }

            showGameButtons = false;
            updateScreen = true;
        }

        if (updateScreen) {
            this.updateTexts();
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (x >= newGameButtonX && x <= newGameButtonX + buttonSize && y >= newGameButtonY && y <= newGameButtonY + buttonSize) {
            newGameButtonHover = true;
        } else {
            newGameButtonHover = false;
        }

        if ((x >= hitButtonX && x <= hitButtonX + buttonSize && y >= hitButtonY && y <= hitButtonY + buttonSize) && showGameButtons) {
            hitButtonHover = true;
        } else {
            hitButtonHover = false;
        }

        if ((x >= standButtonX && x <= standButtonX + buttonSize && y >= standButtonY && y <= standButtonY + buttonSize) || this.getPlayerCardValue() == 21 && showGameButtons) {
            standButtonHover = true;
        } else {
            standButtonHover = false;
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}
}

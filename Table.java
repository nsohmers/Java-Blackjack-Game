import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
public class Table extends JPanel implements ActionListener {
    private ArrayList<Card> deck;
    private ArrayList<Card> playerCards;
    private ArrayList<Card> dealerCards;
    
	private JButton newGameButton;
    private JButton standButton;
    private JButton hitButton;

    private JLabel playerScoreLabel;
    private JLabel playerWinCounterLabel;
    private JLabel dealerScoreLabel;
    private JLabel dealerWinCounterLabel;

	AudioPlayer audioPlayer;

	private int playerWins;
    private int dealerWins;

	public Table(){
		setLayout(null);

        newGameButton = new JButton();
        newGameButton.setFont(new Font("Arial", Font.BOLD, 20));
        newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        newGameButton.setBounds(560, 365, 200, 30);
        newGameButton.setText("New Game");
        this.add(newGameButton);
        newGameButton.addActionListener(this);

        standButton = new JButton();
        standButton.setFont(new Font("Arial", Font.BOLD, 20));
        standButton.setHorizontalAlignment(SwingConstants.CENTER);
        standButton.setBounds(560, 305, 200, 30);
        standButton.setText("Stand");
        this.add(standButton);
        standButton.addActionListener(this);

        hitButton = new JButton();
        hitButton.setFont(new Font("Arial", Font.BOLD, 20));
        hitButton.setHorizontalAlignment(SwingConstants.CENTER);
        hitButton.setBounds(560, 245, 200, 30);
        hitButton.setText("Hit");
        this.add(hitButton);
        hitButton.addActionListener(this);

        playerScoreLabel = new JLabel();
        playerScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerScoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        playerScoreLabel.setBounds(460, 415, 300, 30);
        playerScoreLabel.setText("Current Player Score:");
        playerScoreLabel.setForeground(Color.WHITE);
        this.add(playerScoreLabel);

        dealerScoreLabel = new JLabel();
        dealerScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dealerScoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dealerScoreLabel.setBounds(460, 465, 300, 30);
        dealerScoreLabel.setText("Current Dealer Score:");
        dealerScoreLabel.setForeground(Color.WHITE);
        this.add(dealerScoreLabel);

        playerWinCounterLabel = new JLabel();
        playerWinCounterLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerWinCounterLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        playerWinCounterLabel.setBounds(460, 515, 300, 30);
        playerWinCounterLabel.setText("Player Win Counter:");
        playerWinCounterLabel.setForeground(Color.WHITE);
        this.add(playerWinCounterLabel);

        dealerWinCounterLabel = new JLabel();
        dealerWinCounterLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dealerWinCounterLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dealerWinCounterLabel.setBounds(460, 565, 300, 30);
        dealerWinCounterLabel.setText("Dealer Win Counter:");
        dealerWinCounterLabel.setForeground(Color.WHITE);
        this.add(dealerWinCounterLabel);

		setFocusable(true);

        hitButton.setVisible(false);
		standButton.setVisible(false);

		deck = new ArrayList<Card>();
        playerCards = new ArrayList<Card>();
        dealerCards = new ArrayList<Card>();

        resetDecks();

		audioPlayer = new AudioPlayer();
		
		playerWins = 0;
        dealerWins = 0;
	}

    private void moveCardDecktoPlayer() {
        playerCards.add(deck.get(0));
        playerCards.get(playerCards.size() - 1).show();
        deck.remove(0);
    }

    private void moveCardDecktoDealer() {
        dealerCards.add(deck.get(0));
        dealerCards.get(dealerCards.size() - 1).show();
        deck.remove(0);
    }

	private void resetDecks() {
        playerCards.clear();
        dealerCards.clear();
        deck.clear();

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

        g.setColor(new Color(22, 43, 18));
        int deckX = ((int) (40 - (deck.size() * 0.5)));
        int deckY = ((int) (20 - ((deck.size() * 0.1)))) ;
        int deckW = 138 + ((int) (deck.size() * 0.5));
        int deckH = ((int) (205 - (deck.size() * 0.1)));

        int[] shadowX = {deckX, (int) (deckX - deckW * 0.15), deckX + deckW};
        int[] shadowY = {deckY + deckH, deckY - (int) (deckH * 0.05), 20};

        g.fillPolygon(shadowX, shadowY, 3);

        for (int i = 0; i < playerCards.size(); i++) {
            playerCards.get(i).draw(20 + i * 70, 325, g);
        }

        for (int i = 0; i < dealerCards.size(); i++) {
            dealerCards.get(i).draw(215 + i * 70, 20, g);
        }

        for (int i = 0; i < deck.size(); i++) {
            deck.get(i).draw((int) (40 - (i * 0.5)), (int) (20 - (i * 0.1)), g);
        }

        }

	@Override
	public void actionPerformed(ActionEvent e) {
		if  (e.getSource() == newGameButton) {
            audioPlayer.playShuffleAudio();
            hitButton.setVisible(true);
			standButton.setVisible(true);
            this.resetDecks();

            this.moveCardDecktoPlayer();
            this.moveCardDecktoPlayer();

            this.moveCardDecktoDealer();
            this.moveCardDecktoDealer();

            dealerCards.get(1).hide();
		}

        if (e.getSource() == hitButton) {
            audioPlayer.playHitAudio();
            this.moveCardDecktoPlayer();

			if (this.getPlayerCardValue() > 21) {
                audioPlayer.playLoseAudio();
				hitButton.setVisible(false);
				standButton.setVisible(false);
				dealerWins++;
			}
        }

        if (e.getSource() == standButton || this.getPlayerCardValue() == 21) {
            audioPlayer.playStandAudio();
            hitButton.setVisible(false);
			standButton.setVisible(false);

            dealerCards.get(1).show();
	
            while (this.getDealerCardValue() < 17 ) {
                this.moveCardDecktoDealer();
            }

            if (this.getDealerCardValue() > 21) {
                audioPlayer.playWinAudio();
                playerWins++;
            } else {
                if (this.getPlayerCardValue() > this.getDealerCardValue()) {
                    audioPlayer.playWinAudio();
                    playerWins++;
                } else if (this.getDealerCardValue() > this.getPlayerCardValue()) {
                    audioPlayer.playLoseAudio();
                    dealerWins++;
                }
            }
            
        }

		this.updateTexts();
        repaint();
	}
}

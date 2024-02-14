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
    
	private JButton hitButton;
	private JButton newGameButton;
	private JButton standButton;
	private JLabel pointTable;
	private JLabel pointTable6;
	private JLabel pointTable5;
	private JLabel pointTable4;
	private JLabel pointTable2;
	private JLabel pointTable3;
	private JLabel pointTable1;
	private JLabel scoreLabel;
	private JLabel currentValueLabel;

	private JLabel lostLabel;
	private JLabel wonLabel;
	private JLabel blackJackLabel;

	AudioPlayer audioPlayer;

	private int score;
	private int previousPointsWon;
	private boolean first;

	public Table(){
		setLayout(null);

        hitButton = new JButton();
        hitButton.setFont(new Font("Arial", Font.BOLD, 20));
        hitButton.setHorizontalAlignment(SwingConstants.CENTER);
        hitButton.setBounds(35, 25, 200, 30);
        hitButton.setText("Hit");
        this.add(hitButton);
        hitButton.addActionListener(this);
		hitButton.setVisible(false);

        standButton = new JButton();
        standButton.setFont(new Font("Arial", Font.BOLD, 20));
        standButton.setHorizontalAlignment(SwingConstants.CENTER);
        standButton.setBounds(35, 95, 200, 30);
        standButton.setText("Stand");
        this.add(standButton);
        standButton.addActionListener(this);
		standButton.setVisible(false);

        newGameButton = new JButton();
        newGameButton.setFont(new Font("Arial", Font.BOLD, 20));
        newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        newGameButton.setBounds(35, 525, 200, 30);
        newGameButton.setText("New Game");
        this.add(newGameButton);
        newGameButton.addActionListener(this);
		
        pointTable = new JLabel();
        pointTable.setFont(new Font("Arial", Font.BOLD, 20));
        pointTable.setHorizontalAlignment(SwingConstants.RIGHT);
        pointTable.setBounds(520, 25, 250, 30);
        pointTable.setText("Winning Points Table:");
        this.add(pointTable);
		pointTable.setVisible(false);

		pointTable6 = new JLabel();
		pointTable6.setFont(new Font("Arial", Font.BOLD, 20));
		pointTable6.setHorizontalAlignment(SwingConstants.RIGHT);
		pointTable6.setBounds(620, 270, 150, 30);
		pointTable6.setText("16 - 1 point");
		this.add(pointTable6);
		pointTable6.setVisible(false);

		pointTable5 = new JLabel();
		pointTable5.setFont(new Font("Arial", Font.BOLD, 20));
		pointTable5.setHorizontalAlignment(SwingConstants.RIGHT);
		pointTable5.setBounds(620, 230, 150, 30);
		pointTable5.setText("17 - 1 point");
		this.add(pointTable5);
		pointTable5.setVisible(false);

		pointTable4 = new JLabel();
		pointTable4.setFont(new Font("Arial", Font.BOLD, 20));
		pointTable4.setHorizontalAlignment(SwingConstants.RIGHT);
		pointTable4.setBounds(620, 190, 150, 30);
		pointTable4.setText("18 - 2 points");
		this.add(pointTable4);
		pointTable4.setVisible(false);

		pointTable3 = new JLabel();
		pointTable3.setFont(new Font("Arial", Font.BOLD, 20));
		pointTable3.setHorizontalAlignment(SwingConstants.RIGHT);
		pointTable3.setBounds(620, 150, 150, 30);
		pointTable3.setText("19 - 2 points");
		this.add(pointTable3);
		pointTable3.setVisible(false);

		pointTable2 = new JLabel();
		pointTable2.setFont(new Font("Arial", Font.BOLD, 20));
		pointTable2.setHorizontalAlignment(SwingConstants.RIGHT);
		pointTable2.setBounds(620, 110, 150, 30);
		pointTable2.setText("20 - 3 points");
		this.add(pointTable2);
		pointTable2.setVisible(false);

		pointTable1 = new JLabel();
		pointTable1.setFont(new Font("Arial", Font.BOLD, 20));
		pointTable1.setHorizontalAlignment(SwingConstants.RIGHT);
		pointTable1.setBounds(620, 70, 150, 30);
		pointTable1.setText("21 - 5 points");
		this.add(pointTable1);
		pointTable1.setVisible(false);

		scoreLabel = new JLabel();
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreLabel.setBounds(560, 530, 230, 30);
		scoreLabel.setText("Score: 20");
		this.add(scoreLabel);

		currentValueLabel = new JLabel();
		currentValueLabel.setFont(new Font("Arial", Font.BOLD, 20));
		currentValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		currentValueLabel.setBounds(525, 480, 265, 30);
		currentValueLabel.setText("Current Card Value: 0");
		this.add(currentValueLabel);

		lostLabel = new JLabel();
		lostLabel.setFont(new Font("Arial", Font.BOLD, 31));
		lostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lostLabel.setBounds(40, 65, 200, 30);
		lostLabel.setText("You Lost!");
		lostLabel.setForeground(Color.RED);
		this.add(lostLabel);
		lostLabel.setVisible(false);

		wonLabel = new JLabel();
		wonLabel.setFont(new Font("Arial", Font.BOLD, 30));
		wonLabel.setHorizontalAlignment(SwingConstants.LEFT);
		wonLabel.setBounds(40, 65, 400, 30);
		wonLabel.setText("You Just Won Points!");
		wonLabel.setForeground(Color.GREEN);
		this.add(wonLabel);
		wonLabel.setVisible(false);

		blackJackLabel = new JLabel();
		blackJackLabel.setFont(new Font("Arial", Font.BOLD, 30));
		blackJackLabel.setHorizontalAlignment(SwingConstants.LEFT);
		blackJackLabel.setBounds(40, 65, 200, 30);
		blackJackLabel.setText("Black Jack!");
		blackJackLabel.setForeground(new Color(164, 157, 19));
		this.add(blackJackLabel);
		blackJackLabel.setVisible(false);

		setFocusable(true);

		deck = new ArrayList<Card>();
        playerCards = new ArrayList<Card>();
        dealerCards = new ArrayList<Card>();

        resetDecks();

		audioPlayer = new AudioPlayer();
		
		previousPointsWon = 0;
		score = 20;
		first = true;
	}

	private void resetDecks() {
        playerCards.clear();
        dealerCards.clear();
        deck.clear();

        for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				deck.add(new Card(j + 1, i, false));
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
            total += playerCards.get(i).getValue();
			if (playerCards.get(i).isAce()) hasAce = true;
        }
		if (total + 10 <= 21 && hasAce) {
			total += 10;
		}
		return total;
	}

    private void moveCardDecktoPlayer() {
        playerCards.add(deck.get(0));
        deck.remove(0);
    }

	private void updateTexts() {
		currentValueLabel.setText("Current Card Value: " + this.getPlayerCardValue());
		scoreLabel.setText("Score: " + score);
		wonLabel.setText("You Just Won " + previousPointsWon + " Point" + ((previousPointsWon > 1) ? "s!" : "!"));
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(800,600);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for (int i = 0; i < playerCards.size(); i++) {
            playerCards.get(i).draw(35 + i * 50, 200, g);
        }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if  (e.getSource() == newGameButton) {
			audioPlayer.playShuffleAudio();
			if (first) {
				newGameButton.setText("Play Again");
				pointTable.setVisible(true);
				pointTable1.setVisible(true);
				pointTable2.setVisible(true);
				pointTable3.setVisible(true);
				pointTable4.setVisible(true);
				pointTable5.setVisible(true);
				pointTable6.setVisible(true);
                first = false;
			} else {
                this.resetDecks();
            }

			hitButton.setVisible(true);
			standButton.setVisible(true);
			lostLabel.setVisible(false);
			wonLabel.setVisible(false);
			blackJackLabel.setVisible(false);			

			score--;
			this.moveCardDecktoPlayer();
            this.moveCardDecktoPlayer();

			if  (this.getPlayerCardValue() == 21) {
				hitButton.setVisible(false);
				standButton.setVisible(false);
				blackJackLabel.setVisible(true);
				score += 5;
				audioPlayer.playWinAudio();
			}
		} else if (e.getSource() == hitButton) {
			audioPlayer.playHitAudio();
            this.moveCardDecktoPlayer();

			if (this.getPlayerCardValue() > 21) {
				hitButton.setVisible(false);
				standButton.setVisible(false);
				lostLabel.setVisible(true);
				audioPlayer.playLoseAudio();
			} else if  (this.getPlayerCardValue() == 21) {
				hitButton.setVisible(false);
				standButton.setVisible(false);
				blackJackLabel.setVisible(true);
				score += 5;
				audioPlayer.playWinAudio();
			}
        } else if (e.getSource() == standButton) {
			audioPlayer.playStandAudio();
			hitButton.setVisible(false);
			standButton.setVisible(false);
            
			if (this.getPlayerCardValue() > 15 && this.getPlayerCardValue() < 21) {
				wonLabel.setVisible(true);
				switch (this.getPlayerCardValue()) {
					case 16:
						previousPointsWon = 1;
						score += 1;
						break;
					case 17:
						previousPointsWon = 1;
						score += 1;
						break;
					case 18:
						previousPointsWon = 2;
						score += 2;
						break;
					case 19:
						previousPointsWon = 2;
						score += 2;
						break;
					case 20:
						previousPointsWon = 3;
						score += 3;
						break;
				}
				audioPlayer.playWinAudio();
			} else {
				lostLabel.setVisible(true);
				audioPlayer.playLoseAudio();
			}
		}

		this.updateTexts();
        repaint();
	}
}

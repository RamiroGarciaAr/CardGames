package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture waterTexture;
	private Texture fireTexture;
	private Texture earthTexture;
	private BitmapFont font;
	private GameManager gameManager;
	private ShapeRenderer shapeRenderer;
	private Player player;
	private Player machine;
	private static final int CARD_WIDTH = 100;
	private static final int CARD_HEIGHT = 150;
	private static final int NUM_CARDS_IN_HAND = 3;

	@Override
	public void create() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		waterTexture = new Texture("water.jpg");
		fireTexture = new Texture("fire.jpg");
		earthTexture = new Texture("earth.jpg");

		font = new BitmapFont();
		font.setColor(Color.WHITE);


		String[] typeNames = {"Water", "Fire", "Earth"};
		gameManager = new GameManager(typeNames, 10);
		gameManager.addTypeRelation("Water", "Fire");
		gameManager.addTypeRelation("Fire", "Earth");
		gameManager.addTypeRelation("Earth", "Water");

		player = new Player(0, NUM_CARDS_IN_HAND);
		machine = new Player(0, NUM_CARDS_IN_HAND);

		gameManager.dealInitialCards(player, NUM_CARDS_IN_HAND);
		gameManager.dealInitialCards(machine, NUM_CARDS_IN_HAND);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		drawPlayerCards(player, 50, Gdx.graphics.getHeight() - 200);
		drawScores();
		batch.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		drawPlayerCardCircles(player, 50, Gdx.graphics.getHeight() - 200);
		shapeRenderer.end();

		batch.begin();
		drawPlayerCardNumbers(player, 50, Gdx.graphics.getHeight() - 200);
		batch.end();



		if (Gdx.input.justTouched()) {
			playRound();
		}
	}

	private void drawPlayerCardNumbers(Player player, float startX, float startY) {
		float cardSpacing = 20;
		float currentX = startX;

		for (Card card : player.getCardsInHand()) {
			drawCardNumber(card, currentX, startY);
			currentX += CARD_WIDTH + cardSpacing;
		}
	}

	private void drawPlayerCardCircles(Player player, float startX, float startY) {
		float cardSpacing = 20;
		float currentX = startX;

		for (Card card : player.getCardsInHand()) {
			drawNumberCircle(card, currentX + CARD_WIDTH / 2, startY + CARD_HEIGHT / 2);
			currentX += CARD_WIDTH + cardSpacing;
		}
	}

	private void drawPlayerCards(Player player, float startX, float startY) {
		float cardSpacing = 20;
		float currentX = startX;

		for (Card card : player.getCardsInHand()) {
			Texture texture = getTextureForCard(card);
			if (texture != null) {
				batch.draw(texture, currentX, startY, CARD_WIDTH, CARD_HEIGHT);
				currentX += CARD_WIDTH + cardSpacing;
			}
		}
	}

	private void drawNumberCircle(Card card, float x, float y) {
		float circleRadius = 15;
		float circleX = x + CARD_WIDTH - 70;
		float circleY = y + CARD_HEIGHT - 100;
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.circle(circleX, circleY, circleRadius);
	}

	private void drawCardNumber(Card card, float x, float y) {
		String numberText = String.valueOf(card.getValue());
		float numberX = x + CARD_WIDTH - 26;
		float numberY = y + CARD_HEIGHT - 26 + font.getLineHeight() / 2;
		font.draw(batch, numberText, numberX, numberY);
	}

	private Texture getTextureForCard(Card card) {
		switch (card.getType().getTypeName()) {
			case "Water":
				return waterTexture;
			case "Fire":
				return fireTexture;
			case "Earth":
				return earthTexture;
			default:
				return null;
		}
	}

	private void playRound() {
		// Human player (player1) plays a card randomly
		if (!player.getCardsInHand().isEmpty()) {
			Random random = new Random();
			int index = random.nextInt(player.getCardsInHand().size());
			Card player1Card = player.playCard(index);

			// AI player (player2) plays a card randomly
			if (!machine.getCardsInHand().isEmpty()) {
				index = random.nextInt(machine.getCardsInHand().size());
				Card player2Card = machine.playCard(index);

				// Determine winner
				int comparisonResult = CardComparator.compare(player1Card, player2Card);
				if (comparisonResult > 0) {
					player.setScore(player.getScore() + 1);
					System.out.println("Player 1 wins with " + player1Card + " against " + player2Card);
				} else if (comparisonResult < 0) {
					machine.setScore(machine.getScore() + 1);
					System.out.println("Player 2 wins with " + player2Card + " against " + player1Card);
				} else {
					System.out.println("It's a tie with " + player1Card + " and " + player2Card);
				}
			}
		}
	}
	private void drawScores() {
		String playerScoreText = "Player Score: " + player.getScore();
		String machineScoreText = "Machine Score: " + machine.getScore();
		font.draw(batch, playerScoreText, 50, 50);
		font.draw(batch, machineScoreText, 50, 100);
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		waterTexture.dispose();
		fireTexture.dispose();
		earthTexture.dispose();
		font.dispose();
	}
}

class CardComparator {
	public static int compare(Card o1, Card o2) {
		if (o1.getType().canBeat(o2.getType())) {
			return 1;
		} else if (o2.getType().canBeat(o1.getType())) {
			return -1;
		} else {
			return Integer.compare(o1.getValue(), o2.getValue());
		}
	}
}

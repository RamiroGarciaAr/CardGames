package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;
	private GameManager gameManager;
	private Player player;
	private Player machine;
	private TextureManager textureManager;

	public static final int CARD_WIDTH = 100;
	public static final int CARD_HEIGHT = 150;

	private static final int NUM_CARDS_IN_HAND = 3;

	@Override
	public void create() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		font = new BitmapFont();
		font.setColor(Color.WHITE);

		String[] typeNames = {"Water", "Fire", "Earth"};
		textureManager = new TextureManager(typeNames);

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

		// Draw player cards using SpriteBatch
		batch.begin();
		drawPlayerCardsBatch(player, 50, Gdx.graphics.getHeight() - 450);
		batch.end();

		// Draw scores
		batch.begin();
		drawScores();
		batch.end();

		// Draw player cards using ShapeRenderer
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		drawPlayerCardsShape(player, 50, Gdx.graphics.getHeight() - 450);
		shapeRenderer.end();

		// Handle input for playing a round
		if (Gdx.input.justTouched()) {
			playRound();
		}
	}

	private void drawPlayerCardsBatch(Player player, float startX, float startY) {
		float cardSpacing = 20;
		float totalWidth = player.getCardsInHand().size() * (Card.CARD_WIDTH + cardSpacing) - cardSpacing;
		float currentX = startX + (Gdx.graphics.getWidth() - totalWidth) / 2;

		for (Card card : player.getCardsInHand()) {
			card.setCardPosition(currentX, startY);
			card.drawCardBatch(batch, font);
			currentX += Card.CARD_WIDTH + cardSpacing;
		}
	}
	private void drawPlayerCardsShape(Player player, float startX, float startY) {
		float cardSpacing = 20;
		float totalWidth = player.getCardsInHand().size() * (Card.CARD_WIDTH + cardSpacing) - cardSpacing;
		float currentX = startX + (Gdx.graphics.getWidth() - totalWidth) / 2;

		for (Card card : player.getCardsInHand()) {
			card.setCardPosition(currentX, startY);
			//card.drawCardShape(shapeRenderer);
			currentX += Card.CARD_WIDTH + cardSpacing;
		}
	}
	private void playRound() {
		if (!player.getCardsInHand().isEmpty()) {
			Random random = new Random();
			int index = random.nextInt(player.getCardsInHand().size());
			Card playerCard = player.playCard(index);

			if (!machine.getCardsInHand().isEmpty()) {
				index = random.nextInt(machine.getCardsInHand().size());
				Card machineCard = machine.playCard(index);

				int comparisonResult = CardComparator.compare(playerCard, machineCard);
				if (comparisonResult > 0) {
					player.setScore(player.getScore() + 1);
					System.out.println("Player wins with " + playerCard + " against " + machineCard);
				} else if (comparisonResult < 0) {
					machine.setScore(machine.getScore() + 1);
					System.out.println("Machine wins with " + machineCard + " against " + playerCard);
				} else {
					System.out.println("It's a tie with " + playerCard + " and " + machineCard);
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

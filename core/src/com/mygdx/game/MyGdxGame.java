package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Comparator;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture waterTexture;
	private Texture fireTexture;
	private Texture earthTexture;
	private BitmapFont font;
	private GameManager gameManager;
	private Card player1Card;
	private Card player2Card;
	private ShapeRenderer shapeRenderer;

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

		player1Card = gameManager.getDeck().drawCard();
		player2Card = gameManager.getDeck().drawCard();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Dibujar cartas y círculos
		batch.begin();
		drawCard(player1Card, 100, 100);
		drawCard(player2Card, 300, 100);
		batch.end();

		// Dibujar círculos detrás de los números
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		drawNumberCircle(player1Card, 100, 100);
		drawNumberCircle(player2Card, 300, 100);
		shapeRenderer.end();

		// Dibujar números
		batch.begin();
		drawCardNumber(player1Card, 100, 100);
		drawCardNumber(player2Card, 300, 100);
		batch.end();

		if (Gdx.input.justTouched()) {
			determineWinner();
			player1Card = gameManager.getDeck().drawCard();
			player2Card = gameManager.getDeck().drawCard();
		}
	}

	private void drawCard(Card card, float x, float y) {
		Texture texture = getTextureForCard(card);
		if (texture != null) {
			batch.draw(texture, x, y, CARD_WIDTH, CARD_HEIGHT);
		}
	}

	private void drawNumberCircle(Card card, float x, float y) {
		float circleX = x + CARD_WIDTH - 20;
		float circleY = y + CARD_HEIGHT - 20;
		float circleRadius = 15;

		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.circle(circleX, circleY, circleRadius);
	}


	private void drawCardNumber(Card card, float x, float y) {
		if (card == null) {
			return; // No se puede dibujar el número si la carta es null
		}

		String numberText = String.valueOf(card.getValue());
		GlyphLayout layout = new GlyphLayout(font, numberText);
		float numberX = x + CARD_WIDTH - 20 - layout.width / 2;
		float numberY = y + CARD_HEIGHT - 20 + layout.height / 2;
		font.draw(batch, numberText, numberX, numberY);
	}

	private Texture getTextureForCard(Card card) {
		if (card == null) {
			return null; // Handle null card case gracefully
		}

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

	private void determineWinner() {
		int comparisonResult = CardComparator.compare(player1Card, player2Card);
		if (comparisonResult > 0) {
			System.out.println("Player 1 wins with " + player1Card + " against " + player2Card);
		} else if (comparisonResult < 0) {
			System.out.println("Player 2 wins with " + player2Card + " against " + player1Card);
		} else {
			System.out.println("It's a tie with " + player1Card + " and " + player2Card);
		}
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
class CardComparator
{
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

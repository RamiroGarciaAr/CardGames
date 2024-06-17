package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;
	private GameManager gameManager;
	private Player player;
	private AI machine;
	private float mouseX,mouseY;
	private Sound placementSound;
	private Music gameMusic;
	private static final int NUM_CARDS_IN_HAND = 3;
	Texture backgroundTexture = null;
	@Override
	public void create()
	{
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		LoadRandomBackgroundImage();
		font = new BitmapFont();
		font.setColor(Color.WHITE);

		String[] typeNames = {"Water", "Fire", "Earth"};

		gameManager = new GameManager(typeNames, 10);
		gameManager.addTypeRelation("Water", "Fire");
		gameManager.addTypeRelation("Fire", "Earth");
		gameManager.addTypeRelation("Earth", "Water");

		player = new Player(0, NUM_CARDS_IN_HAND);
		machine = new AI(0, NUM_CARDS_IN_HAND);

		gameManager.dealInitialCards(player, NUM_CARDS_IN_HAND);
		gameManager.dealInitialCards(machine, NUM_CARDS_IN_HAND);
	}

	private void LoadRandomBackgroundImage() {
		FileHandle folder = Gdx.files.internal("assets/Backgrounds/");
		if (folder.exists() && folder.isDirectory()) {
			FileHandle[] backgrounds = folder.list();
			if (backgrounds.length > 1) {
				int index = new Random().nextInt(backgrounds.length);
				backgroundTexture = new Texture(backgrounds[index]);
			} else backgroundTexture = new Texture(backgrounds[0]);
		} else {
			Gdx.app.error("TextureManager", "Backgrounds directory is missing or not found.");
		}

	}
	private void LoadSounds()
	{
		FileHandle folder = Gdx.files.internal("assets/Sounds/");
		if (folder.exists() && folder.isDirectory())
		{
			placementSound = 
		}

	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw player cards and Background using SpriteBatch
		batch.begin();
		if (backgroundTexture != null) {
			Sprite backgroundSprite = new Sprite(backgroundTexture,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			backgroundSprite.draw(batch);
		}
		drawPlayerCardsBatch(player, 50, Gdx.graphics.getHeight() - 450);
		batch.end();

		// Draw scores
		batch.begin();
		drawScores();
		batch.end();

		// Resaltar la carta bajo el mouse
		highlightCardUnderMouse();

		// Draw player cards using ShapeRenderer
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		drawPlayerCardsShape(player, 50, Gdx.graphics.getHeight() - 450);
		shapeRenderer.end();
		mouseX = Gdx.input.getX();
		mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		// Handle input for playing a round
		if (Gdx.input.justTouched()) {

			Card selectedCard = getSelectedCard(mouseX,mouseY);
			if (selectedCard != null) {
				System.out.println("Pressed " + selectedCard.toString());
				gameManager.playRound(selectedCard,player,machine);
				if (winGameCondition(player))
				{
					System.out.println("PLAYER WON");
				}
				else if (winGameCondition(machine))
				{
					System.out.println("MACHINE WON");
				}
			}
		}
	}
	//Esto puede ser un metodo abstracto que se crea en al GM
	private boolean winGameCondition(Player player)
	{
		return player.getScore() == 2;
	}

	private void highlightCardUnderMouse() {
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Invertir el eje Y

		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.YELLOW); // Definir el color una vez fuera del bucle

		for (Card card : player.getCardsInHand()) {
			if (card.isTouched(mouseX, mouseY)) {
				card.highlightCard(shapeRenderer); // Resaltar la carta sin llamar a begin/end dentro del bucle
			}
		}

		shapeRenderer.end();
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

	private Card getSelectedCard(float mouseX,float mouseY) {
		for (Card card : player.getCardsInHand()) {
			if (card.isTouched(mouseX,mouseY)) return card;
		}
		return null;
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
			return o1.compareTo(o2);
		}
	}
}

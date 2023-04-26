package ie.tudublin;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;

/**
 * @edited Ledion Pashaj | C21317311
 * @date 26/04/2023
 */

public class DANI extends PApplet {

	/*
	 * Generated sonnet that displays
	 */
	String[] generatedSonnet;

	/*
	 * Array which will hold the current sonnet saved
	 */
    String[] sonnet;

	/*
	 * Words arraylist
	 */
	ArrayList<Word> words = new ArrayList<>();
	

	/*
	 * Screen settings for the application
	 */
	public void settings() {
		size(750, 600);
	}

	/*
	 * Renderring the application
	 */
	public void setup() {
		surface.setTitle("DANI | Ledion Pashaj C21317311");
		colorMode(HSB);

		//Load text from file
		processText("strings.txt");

		//Generate the sonnet on setup() so its not null when printing it on screen
		generatedSonnet = writeSonnet();
	}

	/*
	 * Drawing the actual text you will see
	 */
	public void draw() {
		background(0);
		fill(255);
		noStroke();
		textSize(20);
        textAlign(CENTER, CENTER);
        
		//Show sonnet on the screen with fixed line numbers on the left
		if (generatedSonnet != null) {
			int lineHeight = 24;
			int startY = height / 2 - (lineHeight * generatedSonnet.length) / 2;
			float maxLineNumberWidth = textWidth(String.valueOf(generatedSonnet.length)) + textWidth(". ");

			for (int i = 0; i < generatedSonnet.length; i++) {
				String line = generatedSonnet[i];
				String lineNumber = (i + 1) + ". ";
				text(lineNumber, maxLineNumberWidth / 2, startY + (i * lineHeight));//Line numbers
				text(line, width / 2, startY + (i * lineHeight));//Line of sonnet
			}
		}
  

		//Show hints too
		showHints();

	}

	/*
	 * Function which handles any key presses while running
	 */
	public void keyPressed() {
		if (key == ' ') {
			generatedSonnet = writeSonnet();
			printModel();
		}
		else if (key == ESC) {
			exit();
		}
	}

	/*
	 * Processing the text to store it
	 */
	public void processText(String filename) {
		String[] lines = loadStrings(filename);
	
		ArrayList<String> wordsList = new ArrayList<>();
		for (String line : lines) {
			String[] wordsInLine = split(line, ' ');
	
			for (int i = 0; i < wordsInLine.length; i++) {
				//Cleaning the text
				String w = wordsInLine[i];
				String cleanWord = w.replaceAll("[^\\w\\s]", "");
				cleanWord = cleanWord.toLowerCase();
				wordsList.add(cleanWord);
	
				//If the current word not in the model, add it
				Word currentWord = findWord(cleanWord);
				if (currentWord == null) {
					currentWord = new Word(cleanWord);
					words.add(currentWord);
				}
	
				//If next word, add it as follow to current word
				if (i < wordsInLine.length - 1) {
					String nextWord = wordsInLine[i + 1].replaceAll("[^\\w\\s]", "").toLowerCase();
					Follow follow = currentWord.findFollow(nextWord);
					if (follow == null) {
						follow = new Follow(nextWord, 1);
						currentWord.addFollow(follow);
					} else {
						follow.setCount(follow.getCount() + 1);
					}
				}
			}
		}
	
		sonnet = wordsList.toArray(new String[0]);
	}
	
	/*
	 * Printing the text on console
	 */
	public void printModel() {
        for (Word word : words) {
            System.out.print(word.getWord() + ": ");
            ArrayList<Follow> follows = word.getFollows();

            for (int i = 0; i < follows.size(); i++) {
                Follow follow = follows.get(i);
                System.out.print(follow.getWord() + "(" + follow.getCount() + ")");
                if (i < follows.size() - 1) {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }
	/*
	* Function that writes the sonnet based on looking to see what possible words will follow the chosen word
	* and it picks one at random from the list for the next word. It then repeats the process until it has 8 words,
	* or until it finds a word that has nothing following it - in which case it will terminate the sentence. 
	*/
	public String[] writeSonnet() {
		String[] sonnet = new String[14];
		Random random = new Random();
	
		for (int lineIndex = 0; lineIndex < 14; lineIndex++) {
			StringBuilder line = new StringBuilder();
			Word currentWord = words.get(random.nextInt(words.size()));
	
			for (int wordIndex = 0; wordIndex < 8; wordIndex++) {
				line.append(currentWord.getWord());
				ArrayList<Follow> follows = currentWord.getFollows();
				
				if (!follows.isEmpty()) {
					//If not empty, choose a random follow next
					Follow randomFollow = follows.get(random.nextInt(follows.size()));
					currentWord = findWord(randomFollow.getWord());
				} 
				else {
					//Else terminate
					break;
				}
	
				//Spacing for between words
				if (wordIndex < 7) {
					line.append(" ");
				}
			}

			sonnet[lineIndex] = line.toString();
		}
	
		return sonnet;
	}
	
	/*
	 * Method to find if the word exists and to return the word
	 */
    public Word findWord(String str) {
        for (Word word : words) {
            if (word.getWord().equals(str)) {
                return word;
            }
        }

        return null;
    }

	/*
	 * Method that prints out hints on the bottom left of screen for user
	 */
	private void showHints() {
		textSize(14);
		textAlign(LEFT, BOTTOM);
		stroke(255);

		//Space bar hint
		String hintText = "Press SPACE to change the sonnet";
		float hintTextWidth = textWidth(hintText);
		float hintTextX = 10;
		float hintTextY = height - 10;
		text(hintText, hintTextX, hintTextY);
		line(hintTextX, hintTextY + 2, hintTextX + hintTextWidth, hintTextY + 2);

		//ESC key hint
		String hintText2 = "Press ESC to exit app";
		float hintText2Width = textWidth(hintText2);
		float hintText2X = 10;
		float hintText2Y = hintTextY - 20;
		text(hintText2, hintText2X, hintText2Y);
		line(hintText2X, hintText2Y + 2, hintText2X + hintText2Width, hintText2Y + 2);
	} 

}
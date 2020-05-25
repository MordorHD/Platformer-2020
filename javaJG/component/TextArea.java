package javaJG.component;

import java.util.ArrayList;

/**
 * Implementation: 15.03
 * 
 * You might wonder why TextArea came before WritableTextComponent. That's
 * simply because the TextArea class got to messy and I wanted to re-structure
 * everything a little bit.
 * 
 * Last edit: 17.03
 */
public class TextArea extends WritableTextComponent {

	public TextArea() {
		this("", STANDARD_CHARACTERS);
	}

	public TextArea(String text) {
		this(text, STANDARD_CHARACTERS);
	}

	public TextArea(ArrayList<String> allowedCharacters) {
		this("", allowedCharacters);
	}

	public TextArea(String text, ArrayList<String> allowedCharacters) {
		super(text, allowedCharacters);
	}

}

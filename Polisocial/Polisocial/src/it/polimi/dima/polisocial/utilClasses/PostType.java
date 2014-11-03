package it.polimi.dima.polisocial.utilClasses;

public enum PostType {

	EVENT("event"), SPOTTED("spotted"),
	RENTAL("rental"),SECOND_HAND_BOOK("secondHandBook"),
	PRIVATE_LESSON("privateLesson"), HIT_ON("hit_on");

	private final String postType;

	PostType(final String category) {
		this.postType = category;
	}

	@Override
	public String toString() {
		return postType;
	}

}

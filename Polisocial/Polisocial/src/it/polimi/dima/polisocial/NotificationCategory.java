package it.polimi.dima.polisocial;

public enum NotificationCategory {

	HIT_ON("hit_on"), EVENT("event"), SIMPLE_SPOTTED("spotted"),
	NOT_FROM_NOTIFICATION("nfn"),RENTAL("rental"),SECOND_HAND_BOOK("secondHandBook"),
	PRIVATE_LESSON("privateLesson");

	private final String category;

	NotificationCategory(final String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return category;
	}

}

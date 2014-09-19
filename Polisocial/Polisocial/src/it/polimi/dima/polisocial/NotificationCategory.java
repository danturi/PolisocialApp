package it.polimi.dima.polisocial;

public enum NotificationCategory {

	HIT_ON("hit_on"), EVENT("event"), SIMPLE_SPOTTED("spotted"), ANNOUNCEMENT(
			"announcement"), NOT_FROM_NOTIFICATION("nfn");

	private final String category;

	NotificationCategory(final String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return category;
	}

}

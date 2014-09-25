package it.polimi.dima.polisocial.utilClasses;

public enum ProfileFieldType {
	SELF_SUMMARY("Self summary"), WHAT_IM_DOING("What i'm doing with my life"),IM_REALLY_GOOD_AT("I'm really good at..."), FAVORITE("Favorite books, movies, shows, music and food"), SIX_THINGS_WITHOUT("The six things I could never do without");

	private final String fieldType;

	ProfileFieldType(final String fieldType) {
		this.fieldType = fieldType;
	}

	@Override
	public String toString() {
		return fieldType;
	}
}

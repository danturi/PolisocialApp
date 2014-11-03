package it.polimi.dima.polisocial.utilClasses;

public enum WhatToShow {

	FROM_NOTIFICATION("fromNotification"),
	DETAILS("details"), ONLY_COMMENTS("onlyComments");

	private final String whatToShow;

	WhatToShow(final String category) {
		this.whatToShow = category;
	}

	@Override
	public String toString() {
		return whatToShow;
	}

}

package it.polimi.dima.polisocial.entity;

import java.util.Date;

import javax.persistence.Entity;


@Entity
public class Initiative extends Post {

private String category;

private Date beginningDate;

public String getCategory() {
	return category;
}

public void setCategory(String category) {
	this.category = category;
}

public Date getBeginningDate() {
	return beginningDate;
}

public void setBeginningDate(Date beginningDate) {
	this.beginningDate = beginningDate;
}


}

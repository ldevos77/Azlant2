package org.ldevos77.azlant.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Portfolio
 * 
 * @author Ludovic Devos
 */
@Entity
public class Portfolio {

	/**
	 * Portfolio ID
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	/**
	 * Portfolio name
	 */
	private String name;
	
	/**
	 * Creation date is technical field.
	 * It's update only one time : when the record is created.
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationDate;
	
	/**
	 * Modification date is technical field.
	 * It's updated each time the record is updated.
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modificationDate;
	
	/**
	 * Private constructor, not for direct instantiation.
	 * Needed for JPA.
	 */
	protected Portfolio() {}
	
	/**
	 * Portfolio constructor
	 * @param name : must not be empty string
	 * @throws IllegalArgumentException if name is empty
	 */
	public Portfolio(String name) {
		if (name != "") {
			this.name = name;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	@PreUpdate
    private void setModificationDate() {
		this.setModificationDate(LocalDateTime.now());
	}
	
    @PrePersist
    private void setCreationDate() {
    	LocalDateTime localDateTime = LocalDateTime.now();
    	this.setCreationDate(localDateTime);
    	this.setModificationDate(localDateTime);
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(LocalDateTime modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Override
    public String toString() {
        return String.format(
                "Portfolio[id=%d, name='%s']",
                id, name);
    }
}


package org.ldevos77.azlant.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Portfolio
 * 
 * -------------------------
 * Portfolio
 * -------------------------
 * String name
 * List<PortfolioLine> lines
 * -------------------------
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
	 * Portfolio lines
	 * 
	 * BEST PRACTICE
	 * Whenever a bidirectional association is formed, the application developer must make sure both sides are 
	 * in-sync at all times. 
	 * The addLine() and removeLine() are utilities methods that synchronize both ends whenever a child element 
	 * is added or removed.
	 */
	@OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<PortfolioLine> lines = new ArrayList<>();
	
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
	
	public Portfolio(Long id, String name) {
		if (id > 0) {
			this.id = id;
		}
		else {
			throw new IllegalArgumentException();
		}
		
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
	
	@Override
	public String toString() {
		return "Portfolio [id=" + id + ", name=" + name + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PortfolioLine> getLines() {
		return lines;
	}
	
	public void setLines(List<PortfolioLine> lines) {
		this.lines = lines;
	}
	
    public void addLine(PortfolioLine line) {
    	lines.add(line);
        line.setPortfolio( this );
    }

    public void removeLine(PortfolioLine line) {
    	lines.remove(line);
        line.setPortfolio( null );
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

}


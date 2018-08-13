package org.ldevos77.azlant.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Portfolio {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	/*
	 * BEST PRACTICE
	 * Whenever a bidirectional association is formed, the application developer must make sure both sides are 
	 * in-sync at all times. 
	 * The addLine() and removeLine() are utilities methods that synchronize both ends whenever a child element 
	 * is added or removed.
	 */
	@OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<PortfolioLine> lines = new ArrayList<>();
	
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

}

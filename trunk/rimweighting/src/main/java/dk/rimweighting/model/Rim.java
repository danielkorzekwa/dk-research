package dk.rimweighting.model;

import java.util.Collection;

/** Represents one Rim used for Rim weighting algorithm.
 * 
 * @author dankor
 *
 */
public class Rim implements Comparable<Rim>{

	/**Identifier of rim - must be unique.*/
	private int id;
	
	/**Name of rim.*/
	private String name;
	
	/**Index of rim. Starts from 0 and must be unique.*/
	private int index;
	
	private Collection<RimComponent> rimComponents;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(Rim arg0) {
	
		return new Integer(this.index).compareTo(new Integer(arg0.index));
	}

	public Collection<RimComponent> getRimComponents() {
		return rimComponents;
	}

	public void setRimComponents(Collection<RimComponent> rimComponents) {
		this.rimComponents = rimComponents;
	}
		
}

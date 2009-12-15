package dk.rimweighting.model;

import java.util.Collection;

 /**Represents one respondent for that the weight is calculated for.
 * 
 * @author dankor
 *
 */
public class Respondent {

	/**Identifier of respondent - must be unique.*/
	private int id;
		
	/**Weight for respondent calculated using rim weighting algorithm.*/
	private double weight;
	
	/**It allows to associate one respondent with particular component of the rim.*/
	private Collection<RimComponent> rimComponents;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Collection<RimComponent> getRimComponents() {
		return rimComponents;
	}

	public void setRimComponents(
			Collection<RimComponent> rimComponents) {
		this.rimComponents = rimComponents;
	}
	
}

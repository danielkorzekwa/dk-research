package dk.rimweighting.model;

/**Represents one component of Rim. It is used for Rim weighting alghoritm
 * 
 * @author dankor
 *
 */
public class RimComponent {

	/**Identifier of rim component - must be unique.*/
	private int id;
	
	/**Name of component, e.g. age or sex*/
	private String name;
	
	/** People amount in universe, e.g. 4 millions.*/
	private int universe;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getUniverse() {
		return universe;
	}

	public void setUniverse(int universe) {
		this.universe = universe;
	}
	
	
}

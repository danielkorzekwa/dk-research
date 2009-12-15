package dk.rimweighting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import dk.rimweighting.model.Respondent;
import dk.rimweighting.model.Rim;
import dk.rimweighting.model.RimComponent;

/**Rim Weighting Alghoritm
 * 
 * @author dankor
 *
 */
public class RimWeighting {

	
	/**Respondents that the weights are calculated for.*/
	private Collection<Respondent> respondents;
	/**It is used for optimization purpose during calculating sample for RimComponent
	 * key - RimComponent id
	 * value - respondents associated with RimComponent
	 * */
	private HashMap<Integer,Collection<Respondent>> respondentsMap = new HashMap<Integer, Collection<Respondent>>();
	
	/**Rims (RimWeighitng) used for calculating weights.*/
	private List<Rim> rims;
	
	/**Convergence parameter used to know when calculation of weights should stop.*/
	private double rms;
	
	/**Max number of iteration used for calculating weights.*/
	private int iterLimit;
	
	
	/**
	 * 
	 * @param respondents respondents that the weights are calculated for
	 * @param rims rims (RimWeighitng) used for calculating weights
	 * @param rms - convergence parameter used to know when calculation of weights should stop
	 * @param iterLimit - max number of iteration used for calculating weights
	 */
	public RimWeighting(Collection<Respondent> respondents, List<Rim> rims, double rms,int iterLimit) {
		this.respondents=respondents;
		this.rims=rims;
		this.rms=rms;
		this.iterLimit=iterLimit;
		
		/**sort rims by the rim index*/
		Collections.sort(this.rims);
		
		/**Init respondentsMap and set initial weight to 1 for all respondents*/
		for(Respondent resp: this.respondents) {
			
			/**Prepare respondents map.*/
			for(RimComponent rimComponent: resp.getRimComponents()) {
				Collection<Respondent> componentRespondents = this.respondentsMap.get(rimComponent.getId());
				if(componentRespondents!=null) {
				componentRespondents.add(resp);
				}
				else {
					componentRespondents = new ArrayList<Respondent>();
					componentRespondents.add(resp);
					this.respondentsMap.put(new Integer(rimComponent.getId()),componentRespondents);
				}
			}
			
			/**set initial weight*/
			resp.setWeight(1);
		}
	}
	
	/**This method calculates weights for respondents using RimWeighting algorithm.
	 * 
	 * @return True if rim weighting is finished successfully.
	 */
	public boolean calculateWeights() {
		long now = System.currentTimeMillis();
		
		/**do iterations, which calculate weights*/
		int iterNumber=1;
		while(true) {
			this.doIteration();
			
			/**break if guard is true*/
			boolean convergenceOk = isConvergenceOk();
			if(convergenceOk || iterNumber==iterLimit) {
				
				if(!convergenceOk) {
					System.out.println("Rim Weighting END. Convergence:" + convergenceOk + "(" + rms + " ), iterNumber:" + iterNumber + "/" + iterLimit + ",time:" + (System.currentTimeMillis()-now) + ",resps:" + respondents.size());
					return false;
				}
				else {
					return true;
				}
					
			}
				iterNumber++;
		}	
	}
	
	/**Returns convergences values - it is used by unit test.
	 * @return Convergence values sorted by rim and rim component*/ 
	public List<Double> getConvergences() {
		
		List<Double> convergences = new ArrayList<Double>();
		for(Rim rim : this.rims) {
			
			for(RimComponent rimComponent: rim.getRimComponents()) {
				
				/**calculate weight*/
				int universe = rimComponent.getUniverse();
				Collection<Respondent> componentRespondents = this.respondentsMap.get(rimComponent.getId());
				double sample=0;
				for(Respondent resp: componentRespondents) {
					sample = sample + resp.getWeight();
				}
				
				double convergence = Math.abs((((double)universe/sample)-1)*100);
				System.out.println(convergence);
				convergences.add(convergence);
			}
		}
		
		return convergences;
	}
	
	/**One iteration calculates weights for all respondent using all rims*/
	private void doIteration() {
		
		/**calculate weights using all rims*/
		for (Iterator iter = this.rims.iterator(); iter.hasNext();) {
			Rim rim = (Rim) iter.next();
			
			/**calculate weights for components*/
			for(RimComponent rimComponent: rim.getRimComponents()) {
				
				/**calculate weight*/
				int universe = rimComponent.getUniverse();
				Collection<Respondent> componentRespondents = this.respondentsMap.get(rimComponent.getId());
				if(componentRespondents==null) {
					componentRespondents=new ArrayList<Respondent>();
					this.respondentsMap.put(rimComponent.getId(), componentRespondents);
				}
				double sample=0;
				for(Respondent resp: componentRespondents) {
					sample = sample + resp.getWeight();
				}
				double componentWeight = (double)universe/(double)sample;
				
				/**set weight for all respondents*/
				for(Respondent resp: componentRespondents) {
					resp.setWeight(componentWeight*resp.getWeight());
				}
			} /**end of weighting for components*/
			
			//this.printRespondentWeights();
			
			
		}/**end of iteration*/
	}
	
	
	
	/**check is the convergence parameter is ok. 
	 *It's ok if convergences for all rims after processing iteration are less than cenvergence criteria
	 * @return true if convergence is ok, false if not ok 
	 */
	private boolean isConvergenceOk() {

			for(Rim rim : this.rims) {
				
				for(RimComponent rimComponent: rim.getRimComponents()) {
					
					/**calculate weight*/
					int universe = rimComponent.getUniverse();
					Collection<Respondent> componentRespondents = this.respondentsMap.get(rimComponent.getId());
					double sample=0;
					for(Respondent resp: componentRespondents) {
						sample = sample + resp.getWeight();
					}
					
					double convergence = Math.abs((((double)universe/sample)-1)*100);
								
					/**convergence is not ok*/
					if(convergence>this.rms) {
						return false;
					}
					
				}
			}
		
			/**convergence is ok*/
			return true;
	}
	
	/**It's a debug method used for testing purpose.*/
	private void printRespondentWeights() {
		System.out.println("**********************************************************************************");
		for(Respondent respondent: this.respondents) {
			
			for(RimComponent rimComponent : respondent.getRimComponents()) {
				System.out.print(rimComponent.getId() + ":");
			}
			
			System.out.println(":" + respondent.getWeight());
		}
	}

}

package dk.rimweighting;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Test;

import dk.rimweighting.model.Respondent;
import dk.rimweighting.model.Rim;
import dk.rimweighting.model.RimComponent;
import static org.junit.Assert.*;

public class RimWeightingTest {

	/** It is used to calculate unique identifiers for the model objects. */
	private int uniqieKey = 0;

	/** Rim convergence parameter. */
	private double rms = 0.1d;

	/** Maximum iteration in weighting. */
	private int iterMax = 30;

	private RimWeighting panel;

	@Test
	public void testCalculateWeights() {

		List<Rim> rims = initRims();
		Set<Respondent> respondents = initRespondents();

		panel = new RimWeighting(respondents, rims, rms, iterMax);

		panel.calculateWeights();

		this.checkWeightedRespondents(respondents);

	}

	private void checkWeightedRespondents(Collection<Respondent> respondents) {
		NumberFormat format = NumberFormat.getNumberInstance(Locale.ENGLISH);
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);

		List<Double> convergences = this.panel.getConvergences();
		/** check convergences values - component A */
		double conv = convergences.get(0);
		assertEquals(format.format(conv), "0.00");
		/** check convergences values - component B */
		conv = convergences.get(1);
		assertEquals(format.format(conv), "0.00");
		/** check convergences values - component C */
		conv = convergences.get(2);
		assertEquals(format.format(conv), "0.02");
		/** check convergences values - component D */
		conv = convergences.get(3);
		assertEquals(format.format(conv), "0.01");
		/** check convergences values - component E */
		conv = convergences.get(4);
		assertEquals(format.format(conv), "0.03");
		/** check convergences values - component F */
		conv = convergences.get(5);
		assertEquals(format.format(conv), "0.00");
		/** check convergences values - component G */
		conv = convergences.get(6);
		assertEquals(format.format(conv), "0.00");
		/** check convergences values - component H */
		conv = convergences.get(7);
		assertEquals(format.format(conv), "0.00");
		/** check convergences values - component I */
		conv = convergences.get(8);
		assertEquals(format.format(conv), "0.00");
		/** check convergences values - component J */
		conv = convergences.get(9);
		assertEquals(format.format(conv), "0.00");

	}

	private List<Rim> initRims() {
		List<Rim> rims = new ArrayList<Rim>();
		/** rim 1 */
		Rim rim = new Rim();
		rim.setId(1);
		rim.setName("RIM 1");
		rim.setIndex(1);
		Collection<RimComponent> rimComponents = new ArrayList<RimComponent>();
		rim.setRimComponents(rimComponents);
		RimComponent rimComponent = new RimComponent();
		rimComponent.setId(1);
		rimComponent.setName("A");
		rimComponent.setUniverse(2580);
		rimComponents.add(rimComponent);
		rimComponent = new RimComponent();
		rimComponent.setId(2);
		rimComponent.setName("B");
		rimComponent.setUniverse(1720);
		rimComponents.add(rimComponent);
		rims.add(rim);

		/** rim 2 */
		rim = new Rim();
		rim.setId(2);
		rim.setName("RIM 2");
		rim.setIndex(2);
		rimComponents = new ArrayList<RimComponent>();
		rim.setRimComponents(rimComponents);
		rimComponent = new RimComponent();
		rimComponent.setId(3);
		rimComponent.setName("C");
		rimComponent.setUniverse(634);
		rimComponents.add(rimComponent);
		rimComponent = new RimComponent();
		rimComponent.setId(4);
		rimComponent.setName("D");
		rimComponent.setUniverse(2433);
		rimComponents.add(rimComponent);
		rimComponent = new RimComponent();
		rimComponent.setId(5);
		rimComponent.setName("E");
		rimComponent.setUniverse(1233);
		rimComponents.add(rimComponent);
		rims.add(rim);

		/** rim 3 */
		rim = new Rim();
		rim.setId(3);
		rim.setName("RIM 3");
		rim.setIndex(3);
		rimComponents = new ArrayList<RimComponent>();
		rim.setRimComponents(rimComponents);
		rimComponent = new RimComponent();
		rimComponent.setId(6);
		rimComponent.setName("F");
		rimComponent.setUniverse(2200);
		rimComponents.add(rimComponent);
		rimComponent = new RimComponent();
		rimComponent.setId(7);
		rimComponent.setName("G");
		rimComponent.setUniverse(2100);
		rimComponents.add(rimComponent);
		rims.add(rim);

		/** rim 4 */
		rim = new Rim();
		rim.setId(4);
		rim.setName("RIM 4");
		rim.setIndex(4);
		rimComponents = new ArrayList<RimComponent>();
		rim.setRimComponents(rimComponents);
		rimComponent = new RimComponent();
		rimComponent.setId(8);
		rimComponent.setName("H");
		rimComponent.setUniverse(1804);
		rimComponents.add(rimComponent);
		rimComponent = new RimComponent();
		rimComponent.setId(9);
		rimComponent.setName("I");
		rimComponent.setUniverse(1393);
		rimComponents.add(rimComponent);
		rimComponent = new RimComponent();
		rimComponent.setId(10);
		rimComponent.setName("J");
		rimComponent.setUniverse(1103);
		rimComponents.add(rimComponent);
		rims.add(rim);

		return rims;

	}

	private Set<Respondent> initRespondents() {

		Set<Respondent> respondents = new HashSet<Respondent>();

		/** [0][0] */
		for (int i = 1; i <= 5; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponent = new RimComponent();

			rimComponent.setId(1);
			rimComponentsValues.add(rimComponent);
			rimComponent = new RimComponent();

			rimComponent.setId(3);
			rimComponentsValues.add(rimComponent);
			rimComponent = new RimComponent();

			rimComponent.setId(6);
			rimComponentsValues.add(rimComponent);
			rimComponent = new RimComponent();

			rimComponent.setId(8);
			rimComponentsValues.add(rimComponent);

			respondents.add(respondent);
		}
		/** [0][1] */
		for (int i = 1; i <= 7; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(3);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(9);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [0][2] */
		for (int i = 1; i <= 7; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(3);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(10);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}

		/** [0][3] */
		for (int i = 1; i <= 4; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(3);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(8);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [0][4] */
		for (int i = 1; i <= 7; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(3);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(9);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [0][5] */
		for (int i = 1; i <= 4; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(3);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(10);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}

		/** [1][0] */
		for (int i = 1; i <= 25; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(4);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(8);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [1][1] */
		for (int i = 1; i <= 28; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(4);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(9);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [1][2] */
		for (int i = 1; i <= 23; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(4);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(10);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}

		/** [1][3] */
		for (int i = 1; i <= 31; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(4);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(8);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [1][4] */
		for (int i = 1; i <= 22; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(4);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(9);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [1][5] */
		for (int i = 1; i <= 21; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(4);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(10);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}

		/** [2][0] */
		for (int i = 1; i <= 26; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(5);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(8);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [2][1] */
		for (int i = 1; i <= 11; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(5);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(9);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [2][2] */
		for (int i = 1; i <= 12; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(5);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(10);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}

		/** [2][3] */
		for (int i = 1; i <= 12; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(5);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(8);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [2][4] */
		for (int i = 1; i <= 12; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(5);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(9);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [2][5] */
		for (int i = 1; i <= 7; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(1);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(5);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(10);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}

		/** [3][0] */
		for (int i = 1; i <= 9; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(3);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(8);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [3][1] */
		for (int i = 1; i <= 8; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(3);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(9);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [3][2] */
		for (int i = 1; i <= 3; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(3);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(10);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}

		/** [3][3] */
		for (int i = 1; i <= 4; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(3);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(8);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [3][4] */
		for (int i = 1; i <= 2; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(3);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(9);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [3][5] */
		for (int i = 1; i <= 3; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(3);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(10);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}

		/** [4][0] */
		for (int i = 1; i <= 19; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(4);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(8);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [4][1] */
		for (int i = 1; i <= 11; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(4);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(9);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [4][2] */
		for (int i = 1; i <= 8; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(4);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(10);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}

		/** [4][3] */
		for (int i = 1; i <= 13; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(4);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(8);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [4][4] */
		for (int i = 1; i <= 9; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(4);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(9);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [4][5] */
		for (int i = 1; i <= 8; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(4);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(10);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}

		/** [5][0] */
		for (int i = 1; i <= 14; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(5);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(8);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [5][1] */
		for (int i = 1; i <= 8; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(5);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(9);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [5][2] */
		for (int i = 1; i <= 3; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(5);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(6);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(10);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}

		/** [5][3] */
		for (int i = 1; i <= 18; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(5);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(8);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [5][4] */
		for (int i = 1; i <= 11; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(5);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(9);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}
		/** [5][5] */
		for (int i = 1; i <= 5; i++) {
			Respondent respondent = new Respondent();
			respondent.setId(getUniqueKey());
			respondent.setWeight(1d);
			Collection<RimComponent> rimComponentsValues = new ArrayList<RimComponent>();
			respondent.setRimComponents(rimComponentsValues);
			RimComponent rimComponentValue = new RimComponent();

			rimComponentValue.setId(2);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(5);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(7);
			rimComponentsValues.add(rimComponentValue);
			rimComponentValue = new RimComponent();

			rimComponentValue.setId(10);
			rimComponentsValues.add(rimComponentValue);

			respondents.add(respondent);
		}

		return respondents;

	}

	/** It is used for calculate unique identifiers for the model objects. */
	private int getUniqueKey() {
		uniqieKey++;
		return uniqieKey;
	}

}

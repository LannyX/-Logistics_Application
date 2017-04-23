package logistic_application;

import java.util.Map;

public class FacilityImplFactory {
	  public static Facility createFacility(String fcltName, int fcltRateint, int fcltCostint, Map<String, Integer> fcltneighbors, Inventory fcltinventory ){
		  return new FacilityImpl(fcltName, fcltRateint, fcltCostint, fcltneighbors, fcltinventory);
	  }
}

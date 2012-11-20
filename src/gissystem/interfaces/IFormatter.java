package gissystem.interfaces;

import gissystem.models.GeographicFeature;

public interface IFormatter {
	public String formatFeatureOutput( Long offset, GeographicFeature feature );
}

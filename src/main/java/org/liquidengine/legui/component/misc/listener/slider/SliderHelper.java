package org.liquidengine.legui.component.misc.listener.slider;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.optional.Orientation;

final class SliderHelper {

	/**
	 * Private constructor for final class.
	 */
	private SliderHelper(){}

	/**
	 * Calculates the sliders new value for the given mouse position.
	 * @param slider the slider to calculate the value for
	 * @param mousePosition the position of the mouse cursor
	 * @return the sliders new value
	 */
	static float determineSliderValue(Slider slider, Vector2f mousePosition) {
		Vector2f pos = slider.getAbsolutePosition();
		float sliderSize = slider.getSliderSize();
		float minValue = slider.getMinValue();
		float maxValue = slider.getMaxValue();
		float difference = maxValue - minValue;
		float percentage;
		if (Orientation.VERTICAL.equals(slider.getOrientation())) {
			percentage = (mousePosition.y - pos.y - sliderSize / 2f) / (slider.getSize().y - sliderSize);
		} else {
			percentage = (mousePosition.x - pos.x - sliderSize / 2f) / (slider.getSize().x - sliderSize);
		}
        float value = difference * percentage + minValue;
		// check for min/max values
		if (value > slider.getMaxValue()) {
			value = slider.getMaxValue();
		}
		if (value < slider.getMinValue()) {
			value = slider.getMinValue();
		}
		return value;
	}

}
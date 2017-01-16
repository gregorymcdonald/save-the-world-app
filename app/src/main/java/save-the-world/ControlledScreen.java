package com.savetheworld;

public interface ControlledScreen {

	/* Allows for the injection of the Parent ScreenPane */
	public void setScreenParent(ScreensController screenPage);

	/* Called when the view appears */
	public void viewDidLoad();

}
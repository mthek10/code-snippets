README - Extra Credit
Mark Thekkethala, Jonathon Volfson
--------------------------------------------------------
Part 1 - Slider:
    Visually:
    Put animation panel in a JPanel layout
    added a JSlider to that layout
    Fixed the minimum dimensions of EditorView
    Added a black border to the actual animation to align with slider

    Controller:
    IListener can be the listener which extends all the other ActionListeners needed then just pass in an IActionListener
    Leaving IActionListener for older code to run and IListener is an IActionListener allowing non breakage

    EditorView:
    addingChangeListener to scrubber when setListener is called
    set final tick when setting the listener to ensure views final tick is set
    created new IViewExtended interface to accept new IListener controller and add get tick without breaking any of the current impl
    Only had Visual and Editor view to use the IViewExtended because the others don't handle ticks
    Changes : setListeners(IListener) implemented as cleaner solution for setting listeners uses setButtonListeners
        Can do this because IListener is an ActionListener which is used by the buttons

    Slider Edge Cases:
        Sliding Scale Updates now when Shapes are added and removed or Key Frames are added and removed
            Resets the Final Tick which is used by the Slider to setMaximum() for the scale of the slider
        Sliding Scale Updates also when new files are loaded
            New model set and final tick run again
        *EXTRA FUNCTIONALITY*:
            Slider moves as ticks increment in animation along the track to show progress of animation
                Works by calling setScrubber which uses setValue() of slider whenever tick is incremented
	TESTED: TestScrubber test class test the stateChanged method in controller which affects the IView
	TESTED: also tests the fact that setting tick on scrubber in the program will not change the isAdjusting value of Slider
			- this allows the Editor to show the extra functionality of having slider progress with ticks of video


    Changes in AnimationModelImpl:
        Updating getFinalTick in AnimationModelImpl to return a dynamic time calculation for each shape when called
            Was using static variable in each of the IShapes which was incrementing when a something was added
            vs
            Now running through all IShapes and sorting their CommandTimes, then comparing last Command Times to find last one of Animation


-------------------------------------------------------
Part 2 - Rotation:
	IShapes:
		new IShapeExtended has setter and getters for theta AND linear interpolation calculates angles
			each IShapeExtended can draw itself rotated correctly
			- New Interface allows for backwards compatibility and no breaking of implementations used by anyone
		RectangleShape and EllipseShape updated to implement IShapeExtended with their draw methods
		Updated linear interpolation in Shape.java to interpolate angles and 
			TESTED in TestShapeLinearInterpolation
		Added AffineTransformation in Abstract Shape class which use currState to get theta and draw this shape
	
	ShapeState:
		Editted ShapeState to have double theta field with a DEFAULT value of 0.0 to maintain backwards compatiblity
			Overloaded constructor to create shapestate with theta
			made getter for theta in shapestate
			Added theta to equalOtherShapeState method 
			Created a toStringEnhanced which displays Theta values
			TESTED:
				tested ShapeState enhanced
	AnimationPanel:
		Cleaned/Changed AnimationPanel to run through each shape and have them draw themselves at the tick
		Check-Casted IShapes to IShapeExtended in drawing in AnimationPanel
	
	Editting Rotations From EditorView:
		EditorView:
			Added to Editor View Theta label and text field
			integrated with the reset functions 
		Controller 
			added to add/edit keyframe to be able to manipulte from GUI
		added to the shapestate built in the controller

	Reading and Building Model w/Theta
		AnimationBuilder
			Added theta as a parameter to a new function which is called by addKeyFrame and addMotion when dispatched to the methods with no theta defined (0 Default is used)
		ModelBuilder
			Implemented changes simple with calling older functions with the new functions 0 default theta or the read-in theta
		AnimationReader	
			extended the vals array to store theta1,theta2 also 
			*FILE FORMAT* for theta is a "*THETA*" in the file which is used to set a boolean in AnimationReader which reads theta values instead of using default 0
	Exporting to SVG Rotation Animation:
		SVGView:
			Cleaned up SVG header to run with IntelliJ viewer
			Fixed sizes of ellipses in animation because radius was being set as width so was twice the size of normal
			added getSVGRotationCommand private method to SVGView
				had to adjust the center for rotation or rectangles because xy is left corner for rect and center for ellipse
			TESTED each individual shape rotation and a combined rotation going different directions for SVG in SVGExtendedTestClass
			TESTED new ModelBuilder and AnimationReader features in TestReaderBuilder test class


--------------------------------------------------------
Part 3 - Layers:

	Model:
		Added int shapeLayer to abstract shape class
		added setters and getters to IShape
		implemented in abstract shape class

		overloaded constructor to take in shapeLayer and set other constructors to use 0 as shapeLayer default if not defined

		compareTo in abstract shape class checks if IShapeExtended and compares layering first then drawOrder or just drawOrder depending on instanceof IShapeExtended 
			if not an IShapeExtended but an IShape then uses draw order 
			if only a provider Shape type then use ID

		overloaded constructors for RectangleShape and EllipseShape to accept shapeLayer 
		AnimationModelImpl takes in map of shapeLayers and runs through each of the mapped shapes and sets their shape layers in overloaded cosntructor
		AnimationModel interface:
			- made addShape which takes in a layer for ease of adding layer and redirected all calls to addShape through this with default 0 for layer
			- made setLayer to set the layer of an IShape given name

		TESTED: setting getting constructing and comparing for layering in TestLayeringIShape

	Sorting Layers For Drawing:
		currentImplementation uses Collections.sort
			- this will work because we updated the compareTo to factor in layering in terms of what to draw
	AnimationBuilder:
		- added declareShape ovverloade to take in layer

	ModelBuilder 
		- implemented by redirecting all previous declareShape calls through this method with default layer 0 if not defined.
		- ModelBuilder holds map of name,layer

	AnimationReader
		- added boolean for if layer is enabled
		- added to switch statement checking for *LAYER* tag in file

	FILE FORMAT:
		Uses *LAYER* tag at top of file similar to *THETA*
		Layer when defining shape
			- Shape r rectangle 0 (EXAMPLE)
		TESTED: TestReaderBuilderLayer test class

	EditorView
		added Layer Label and TextField to View
		- moved around fields to pair shape and layer fields visually
		- added label updated for layer in clear and set methods of IView
		- added getter method for layer field in IViewExtended and implemented
		Added two new buttons
			editLayer
				- mapped in controller to edit layer of a shape by replacing with new IShape and resort the list of shapes to be drawn
			removeLayer
				- mapped in controller to remove the entire layer by looping through shapes and removing them if they are on the layer
	IViewExtended
		- added setters for shape textfield and layer textfield for extensive simulation testing


	Controller
		- in add shape function, set shape layer after added to model
		- Created editLayer function to use model setLayer function
		- Created removeLayer function which creates a list of all shapes on specified layer, removes them from model, and resets the view.
		TESTED editing and removing layers through the controller with view buttons and text fields affecting the model done in TestEditRemoveLayersController (Full Simulation)
			- Test use setting fields in the view, calling Events for controller, and checking model for changes

	
	TESTING NOTE:
		- Extra tests are added in testing suite which uses reading in file. If the user does not have the files for the extra test the suite will notify them but not fail the enire testing suite.

--------------------------------------------------------
Extra Functionality:
	Part 1:
            Slider moves as ticks increment in animation along the track to show progress of animation
                Works by calling setScrubber which uses setValue() of slider whenever tick is incremented

	BACKWARDS COMPATIBILITY:
		- All files were used from Assignment8 in order to be fully compatible with the provider view and the extra credit functionality.

---------------------------------------------------------
DEMO FILES:
	- In the DemoDay directory there are the files used during demo to show EC functionality
	FILES:
		- ReadRotations.txt : DEMONSTRATES A SHAPE THAT ONLY ROTATES (C) and A SHAPE THAT ROTATES AND CHANGES ALL PROPERTIES (R)
		- SingleRotation.txt: USED TO READ IN AND EXPORT SINGLE ROTATION TO SVG VIEWING
		- SVGRoatation.svg: DEMONSTRATES THE PRODUCT OF THE SVG VIEW AND SingleRotation.txt (CAN BE OPENED IN CHROME)
		- buildingsLayer.txt : USED TO SHOW THE READING OF A FILE WITH LAYERS DEFINED FOR SHAPES
		- buildingsLayerBackground1.txt USED TO SHOW 2-LAYERING EFFECTIVE (BACKGROUND READ-IN FIRST BUT STILL ON TOP DUE TO LAYER 1)
		- buildingsLayerBackground2.txt USED TO SHOW 3-LAYERING EFFECTIVE (BACKGROUND READ-IN FIRST BUT B0 and B3 ON TOP DUE TO LAYER2)
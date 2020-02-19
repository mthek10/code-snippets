import org.junit.Test;

import cs3500.animator.controller.Controller;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.ShapeState;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;

import static junit.framework.TestCase.assertEquals;

/**
 * SVG Test class.
 */
public class SVGViewExtendedTest {


  @Test
  public void testNoShapeSVGView() {
    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    // Set up View
    Appendable out = new StringBuilder("");
    IView view = new SVGView(out);

    // Set up Controller
    Controller controller = new Controller(model, view);

    assertEquals("<svg width=\"1000\" height=\"1000\" version=\"1.1\" xmlns" +
            "=\"http://www.w3.org/2000/svg\">\n" +
            "</svg>", out.toString());
  }

  @Test(expected = Exception.class)
  public void testExceptionTextView() {
    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    // Set up View
    Appendable out = null;
    IView view = new SVGView(out);

    // Set up Controller
    Controller controller = new Controller(model, view);

  }


  @Test
  public void testOneShapeTextView() {
    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    ShapeState state1 = new ShapeState(300, 200,
            300, 400, 0, 0, 0);
    ShapeState state2 = new ShapeState(300, 200,
            300, 10, 0, 0, 0);

    model.addShape("r", "rectangle");
    model.addCommand("r", 10, state1, 20, state2);

    // Set up View
    Appendable out = new StringBuilder("");
    IView view = new SVGView(out);


    // Set up Controller
    Controller controller = new Controller(model, view);

    assertEquals("<svg width=\"1000\" height=\"1000\" version=\"1.1\" x" +
            "mlns=\"http://www.w3.org/2000/svg\">\n" +
            "  <rect id=\"r\" x=\"300.0\" y=\"200.0\" width=\"300.0\" height=\"400" +
            ".0\" visibility=\"hidden\" fill=\"rgb(0,0,0)\"  >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visi" +
            "ble\" begin=\"10s\" dur=\"1\" fill=\"freeze\" />\n" +
            "    <animate attributeName=\"height\" attributeType=\"XML\" begin=\"1" +
            "0s\" dur=\"10s\" fill=\"freeze\" from=\"400.0\" to=\"10.0\" />\n" +
            "  </rect>\n" +
            "\n" +
            "</svg>", out.toString());
  }

  @Test
  public void testMultipleShapeTextView() {
    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    ShapeState state1 = new ShapeState(300, 200, 300,
            400, 0, 0, 0);
    ShapeState state2 = new ShapeState(300, 200, 300,
            10, 0, 0, 0);
    ShapeState state3 = new ShapeState(100, 200, 300,
            400, 0, 0, 0);
    ShapeState state4 = new ShapeState(300, 200, 300,
            400, 0, 0, 0);

    model.addShape("r", "rectangle");
    model.addCommand("r", 10, state1, 20, state2);

    model.addShape("r2", "rectangle");
    model.addCommand("r2", 5, state3, 15, state4);

    // Set up View
    Appendable out = new StringBuilder("");
    IView view = new SVGView(out);


    // Set up Controller
    Controller controller = new Controller(model, view);

    assertEquals("<svg width=\"1000\" height=\"1000\" version=\"1.1\" xml" +
            "ns=\"http://www.w3.org/2000/svg\">\n" +
            "  <rect id=\"r\" x=\"300.0\" y=\"200.0\" width=\"300.0\" height=\"400" +
            ".0\" visibility=\"hidden\" fill=\"rgb(0,0,0)\"  >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visib" +
            "le\" begin=\"10s\" dur=\"1\" fill=\"freeze\" />\n" +
            "    <animate attributeName=\"height\" attributeType=\"XML\" begin=\"10" +
            "s\" dur=\"10s\" fill=\"freeze\" from=\"400.0\" to=\"10.0\" />\n" +
            "  </rect>\n" +
            "\n" +
            "  <rect id=\"r2\" x=\"100.0\" y=\"200.0\" width=\"300.0\" height=\"40" +
            "0.0\" visibility=\"hidden\" fill=\"rgb(0,0,0)\"  >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visi" +
            "ble\" begin=\"5s\" dur=\"1\" fill=\"freeze\" />\n" +
            "    <animate attributeName=\"x\" attributeType=\"XML\" begin=\"5s\" du" +
            "r=\"10s\" fill=\"freeze\" from=\"100.0\" to=\"300.0\" />\n" +
            "  </rect>\n" +
            "\n" +
            "</svg>", out.toString());
  }


  @Test
  public void testFullSVGMultipleShapesMultipleStates() {
    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    ShapeState state1 = new ShapeState(300, 200, 300,
            400, 0, 0, 0);
    ShapeState state2 = new ShapeState(300, 200, 300,
            10, 0, 0, 0);
    ShapeState state3 = new ShapeState(100, 200, 300,
            400, 0, 0, 0);
    ShapeState state4 = new ShapeState(300, 200, 300,
            400, 0, 0, 0);

    model.addShape("r", "rectangle");
    model.addCommand("r", 10, state1, 20, state2);

    model.addShape("r2", "rectangle");
    model.addCommand("r2", 5, state3, 15, state4);

    // Set up View
    Appendable out = new StringBuilder("");
    SVGView view = new SVGView(out);

    // Set up Controller
    Controller controller = new Controller(model, view);

    assertEquals("<svg width=\"1000\" height=\"1000\" versi" +
            "on=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "  <rect id=\"r\" x=\"300.0\" y=\"200.0\" width=\"300.0\" he" +
            "ight=\"400.0\" visibility=\"hidden\" fill=\"rgb(0,0,0)\"  >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"CSS\" t" +
            "o=\"visible\" begin=\"10s\" dur=\"1\" fill=\"freeze\" />\n" +
            "    <animate attributeName=\"height\" attributeType=\"XML\" beg" +
            "in=\"10s\" dur=\"10s\" fill=\"freeze\" from=\"400.0\" to=\"10.0\" />\n" +
            "  </rect>\n" +
            "\n" +
            "  <rect id=\"r2\" x=\"100.0\" y=\"200.0\" width=\"300.0\" height=\"40" +
            "0.0\" visibility=\"hidden\" fill=\"rgb(0,0,0)\"  >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"vis" +
            "ible\" begin=\"5s\" dur=\"1\" fill=\"freeze\" />\n" +
            "    <animate attributeName=\"x\" attributeType=\"XML\" begin=\"5s\" du" +
            "r=\"10s\" fill=\"freeze\" from=\"100.0\" to=\"300.0\" />\n" +
            "  </rect>\n" +
            "\n" +
            "</svg>", out.toString());
  }

  @Test
  public void testEllipseSVG() {
    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    ShapeState state1 = new ShapeState(300, 200,
            300, 400, 0, 0, 0);
    ShapeState state2 = new ShapeState(300, 200,
            300, 10, 0, 0, 0);

    model.addShape("e", "ellipse");
    model.addCommand("e", 10, state1, 20, state2);

    // Set up View
    Appendable out = new StringBuilder("");
    IView view = new SVGView(out);


    // Set up Controller
    Controller controller = new Controller(model, view);

    assertEquals("<svg width=\"1000\" height=\"1000\" ve" +
            "rsion=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "  <ellipse id=\"e\" cx=\"300.0\" cy=\"200.0\" rx=\"15" +
            "0.0\" ry=\"200.0\" visibility=\"hidden\" fill=\"rgb(0,0,0)\"  >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"C" +
            "SS\" to=\"visible\" begin=\"10s\" dur=\"1\" fill=\"freeze\" />\n" +
            "    <animate attributeName=\"ry\" attributeType=\"XML\" beg" +
            "in=\"10s\" dur=\"10s\" fill=\"freeze\" from=\"400.0\" to=\"10.0\" />\n" +
            "  </ellipse>\n" +
            "\n" +
            "</svg>", out.toString());
  }

  @Test
  public void testEllipseSVGRotation() {
    // Testing Rotation of a Single Ellipse No Changes 0-10

    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    ShapeState state1 = new ShapeState(300, 200,
            300, 400, 0, 0, 0, 0);
    ShapeState state2 = new ShapeState(300, 200,
            300, 400, 0, 0, 0, 360);

    model.addShape("e", "ellipse");
    model.addCommand("e", 0, state1, 10, state2);

    // Set up View
    Appendable out = new StringBuilder("");
    IView view = new SVGView(out);


    // Set up Controller
    Controller controller = new Controller(model, view);

    assertEquals("<svg width=\"1000\" height=\"1000\" versio" +
            "n=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "  <ellipse id=\"e\" cx=\"300.0\" cy=\"200.0\" rx=\"150.0\" ry=\"200.0\" " +
            "visibility=\"hidden\" fill=\"rgb(0,0,0)\"  >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" begin=\"" +
            "0s\" dur=\"1\" fill=\"freeze\" />\n" +
            "\n" +
            " <animateTransform attributeName=\"transform\" attributeType=\"XML\" type=\"" +
            "rotate\" from=\"0.0 300.0 200.0\" to=\"360.0 300.0 200.0\" " +
            "dur=\"10s\" begin=\"0s\"/>\n" +
            "  </ellipse>\n" +
            "\n" +
            "</svg>", out.toString());
  }


  @Test
  public void testRectangleSVGRotation() {
    // Testing Rotation of a Single Rectangle No Changes 0-10

    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    ShapeState state1 = new ShapeState(300, 200,
            300, 400, 255, 0, 0, 0);
    ShapeState state2 = new ShapeState(300, 200,
            300, 400, 255, 0, 0, 360);

    model.addShape("r", "rectangle");
    model.addCommand("r", 0, state1, 10, state2);


    ShapeState state3 = new ShapeState(600, 600,
            200, 300, 0, 0, 255, 360);
    ShapeState state4 = new ShapeState(600, 600,
            200, 300, 0, 0, 255, 0);

    model.addShape("e", "ellipse");
    model.addCommand("e", 0, state3, 10, state4);


    // Set up View
    Appendable out = new StringBuilder("");
    IView view = new SVGView(out);


    // Set up Controller
    Controller controller = new Controller(model, view);

    assertEquals("<svg width=\"1000\" height=\"100" +
            "0\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "  <rect id=\"r\" x=\"300.0\" y=\"200.0\" width=\"3" +
            "00.0\" height=\"400.0\" visibility=\"hidden\" fill=\"rgb(255,0,0)\"  >\n" +
            "    <set attributeName=\"visibility\" attributeTy" +
            "pe=\"CSS\" to=\"visible\" begin=\"0s\" dur=\"1\" fill=\"freeze\" />\n" +
            "\n" +
            " <animateTransform attributeName=\"transform\" attribu" +
            "teType=\"XML\" type=\"rotate\" from=\"0.0 450.0 400.0\" to=\"360.0 " +
            "450.0 400.0\" dur=\"10s\" begin=\"0s\"/>\n" +
            "  </rect>\n" +
            "\n" +
            "  <ellipse id=\"e\" cx=\"600.0\" cy=\"600.0\" rx=\"100.0\" ry=\"15" +
            "0.0\" visibility=\"hidden\" fill=\"rgb(0,0,255)\"  >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"vi" +
            "sible\" begin=\"0s\" dur=\"1\" fill=\"freeze\" />\n" +
            "\n" +
            " <animateTransform attributeName=\"transform\" attributeType=\"XML\" ty" +
            "pe=\"rotate\" from=\"360.0 600.0 600.0\" to=\"0.0 600.0 600.0\" " +
            "dur=\"10s\" begin=\"0s\"/>\n" +
            "  </ellipse>\n" +
            "\n" +
            "</svg>", out.toString());
  }

  @Test
  public void testRectangleEllipseCombineSVGRotation() {
    // Testing Rotation of a Single Rectangle No Changes 0-10

    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    ShapeState state1 = new ShapeState(300, 200,
            300, 400, 0, 0, 0, 0);
    ShapeState state2 = new ShapeState(300, 200,
            300, 400, 0, 0, 0, 360);

    model.addShape("r", "rectangle");
    model.addCommand("r", 0, state1, 10, state2);

    // Set up View
    Appendable out = new StringBuilder("");
    IView view = new SVGView(out);


    // Set up Controller
    Controller controller = new Controller(model, view);

    assertEquals("<svg width=\"1000\" height=\"1000\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "  <rect id=\"r\" x=\"300.0\" y=\"200.0\" width=\"300.0\" height=\"40" +
            "0.0\" visibility=\"hidden\" fill=\"rgb(0,0,0)\"  >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visib" +
            "le\" begin=\"0s\" dur=\"1\" fill=\"freeze\" />\n" +
            "\n" +
            " <animateTransform attributeName=\"transform\" attributeType=\"XML\" typ" +
            "e=\"rotate\" from=\"0.0 450.0 400.0\" to=\"360.0 450.0 400.0\" dur=\"10s\" beg" +
            "in=\"0s\"/>\n" +
            "  </rect>\n" +
            "\n" +
            "</svg>", out.toString());
  }


}

package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TS implements Initializable {
    @FXML
    public Pane body;
    @FXML
    public Pane baseHCoord;
    @FXML
    public Box base;
    @FXML
    public Box table;
    @FXML
    public Box columnL;
    @FXML
    public Box columnR;
    @FXML
    public Pane baseVCoord;
    @FXML
    public Box turner;
    @FXML
    public Cylinder eyepiece;
    @FXML
    public Cylinder telescope;
    @FXML
    public Cylinder lenz;

    private final Rotate rotateBaseH = new Rotate(0, 100., 0., 0., new Point3D(0., -1., 0.));
    private final Rotate rotateBaseV = new Rotate(0., 0., 100., 0., new Point3D(1., 0., 0.));
    private final PhongMaterial peruPhongMaterial = new PhongMaterial(Color.PERU);
    private final PhongMaterial goldPhongMaterial = new PhongMaterial(Color.GOLD);
    private final PhongMaterial greyPhongMaterial = new PhongMaterial(Color.GREY);
    private final PhongMaterial blackPhongMaterial = new PhongMaterial(Color.BLACK);

    public Node node;
    public TS() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/TS.fxml"));
            loader.setController(this);
            node = loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public TS(double x, double y, double z) {
        this();
        body.setTranslateX(x);
        body.setTranslateY(y);
        body.setTranslateZ(z);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        base.setMaterial(greyPhongMaterial);
        table.setMaterial(goldPhongMaterial);
        columnL.setMaterial(greyPhongMaterial);
        columnR.setMaterial(greyPhongMaterial);
        turner.setMaterial(goldPhongMaterial);
        eyepiece.setMaterial(greyPhongMaterial);
        telescope.setMaterial(greyPhongMaterial);
        lenz.setMaterial(blackPhongMaterial);
        baseHCoord.getTransforms().addAll(rotateBaseH);
        baseVCoord.getTransforms().addAll(rotateBaseV);
    }

    public void setAzimuth(double azimuth_DEG) {
        rotateBaseH.setAngle(azimuth_DEG);
    }

    public void setElevation(double elevation_DEG) {
        rotateBaseV.setAngle(elevation_DEG);
    }
}

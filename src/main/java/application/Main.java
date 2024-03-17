package application;
	
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class Main extends Application {
	
	private final Rotate horizontalAxisRotate = new Rotate(0, Rotate.X_AXIS);
	private final Rotate verticalAxisRotate = new Rotate(0, Rotate.Y_AXIS);
    private double azimuthRotateAngle = 0.0;
    private double elevationRotateAngle = 0.0;
    private double right = 0.0;
    private double down = 0.0;
    private double forward = -4000.0;
	private final Rotate azimuthRotate = new Rotate(-azimuthRotateAngle, Rotate.Y_AXIS);
    private final Rotate elevationRotate = new Rotate(-elevationRotateAngle, Rotate.X_AXIS);
    private final Translate translate = new Translate(right, down, forward);
	private TS ts1, ts2;

	@Override
	public void start(Stage primaryStage) {
		try {
			PerspectiveCamera camera = new PerspectiveCamera(true);
			camera.setNearClip(100); // The default value is 0.1
			camera.setFarClip(10000); // The default value is 100.0

			camera.getTransforms().addAll(
					horizontalAxisRotate,
					verticalAxisRotate,
					azimuthRotate,
					elevationRotate,
					translate
					);
			
			Group group = new Group();
			SubScene subScene = new SubScene(group, 600, 600, true, SceneAntialiasing.BALANCED);
			subScene.setFill(Color.LIGHTGREY);
			subScene.setCamera(camera);

			ts1 = new TS();
			ts2 = new TS(1000., 0., 0.);
			group.getChildren().add(ts1.node);
			group.getChildren().add(ts2.node);


			VBox controlVBox = new VBox();
			makeControlVBox(controlVBox);
			
			HBox hBox = new HBox();
			hBox.setAlignment(Pos.CENTER);
			hBox.getChildren().addAll(subScene, controlVBox);

			Scene scene = new Scene(hBox);
			
			handleSliders();
			handleMouse();
			
			scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
			primaryStage.setTitle("Camera Rotation and Translation");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setTranslates() {
		translate.setX(right);
		translate.setY(down);
		translate.setZ(forward);
		textTranslateToRight.setText(String.format("%06.1f", right));
		textTranslateToDown.setText(String.format("%06.1f", down));
		textTranslateToForward.setText(String.format("%06.1f", forward));
	}
	
	private void setAzimuthRotate( ) {
		azimuthRotate.setAngle(-azimuthRotateAngle);
		textAzimuth.setText(String.format("%04.1f", azimuthRotateAngle));
	}
	
	private void setElevationRotate() {
		elevationRotate.setAngle(-elevationRotateAngle);
		textElevation.setText(String.format("%04.1f", elevationRotateAngle));
	}

	private void handleSliders() {
		sliderTranslateToRight.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
			right = (double)new_val;
			setTranslates();
		});

		sliderTranslateToDown.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
			down = (double)new_val;
			setTranslates();
		});

		sliderTranslateToForward.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
			forward = (double)new_val;
			setTranslates();
		});

		sliderAzimuth.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
			if (new_val != null) {
				azimuthRotateAngle = (double)new_val;
				setAzimuthRotate();
			}
		});

		sliderElevation.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
			if (new_val != null) {
				elevationRotateAngle = (double)new_val;
				setElevationRotate();
			}
		});

		sliderTSAzimuth.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
			if (new_val != null) {
				ts1.setAzimuth((double)new_val);
				textTSAzimuth.setText(String.format("%04.1f", (double)new_val));
			}
		});

		sliderTSElevation.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
			if (new_val != null) {
				ts1.setElevation((double)new_val);
				textTSElevation.setText(String.format("%04.1f", (double)new_val));
			}
		});
	}

	private void handleMouse() {
	}

	private final Text textTranslateToRight = new Text("0.0");
	private final Text textTranslateToDown = new Text("0.0");
	private final Text textTranslateToForward = new Text("0.0");
	private final Text textAzimuth = new Text("0.0");
	private final Text textElevation = new Text("0.0");
	private final Slider sliderTranslateToRight = new Slider();
	private final Slider sliderTranslateToDown = new Slider();
	private final Slider sliderTranslateToForward = new Slider();
	private final Text textTSAzimuth = new Text("0.0");
	private final Text textTSElevation = new Text("0.0");
	private final Slider sliderAzimuth = new Slider();
	private final Slider sliderElevation = new Slider();
	private final Slider sliderTSAzimuth = new Slider();
	private final Slider sliderTSElevation = new Slider();
	
	private void makeControlVBox(VBox vBox) {
		vBox.setPadding(new Insets(10, 10, 10, 10));

		HBox hBox = new HBox();
		hBox.setSpacing(5);
		Label label1 = new Label("軸の区別：");
		label1.setPadding(new Insets(0, 10, 0, 0));
		label1.getStyleClass().add("bold");
		Label labelx = new Label("X軸");
		labelx.setTextFill(Color.RED);
		Label labely = new Label("Y軸");
		labely.setTextFill(Color.GREEN);
		Label labelz = new Label("Z軸");
		labelz.setTextFill(Color.BLUE);
		hBox.getChildren().addAll(label1, labelx, labely, labelz);
		
		Label labelCameraInitialSetting = new Label("カメラの初期設定：");
		labelCameraInitialSetting.setPadding(new Insets(5, 0, 0, 0));
		labelCameraInitialSetting.getStyleClass().add("bold");
		VBox vBoxRotateAxis = new VBox();
		vBoxRotateAxis.setPadding(new Insets(0, 10, 10, 10));
		Label label0 = new Label("camera.getTransforms().addAll(");
		CheckBox checkBoxHorizontalAxis = new CheckBox("Rotate(-90, X_AXIS);");
		checkBoxHorizontalAxis.setSelected(false);
		checkBoxHorizontalAxis.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
				if (new_val) {
					horizontalAxisRotate.setAngle(-90);
				} else {
					horizontalAxisRotate.setAngle(0);
				}
		});
		CheckBox checkBoxVerticalAxis = new CheckBox("Rotate(-90, Y_AXIS);");
		checkBoxVerticalAxis.setSelected(false);
		checkBoxVerticalAxis.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			if (new_val) {
				verticalAxisRotate.setAngle(-90);
			} else {
				verticalAxisRotate.setAngle(0);
			}
		});
		
		vBoxRotateAxis.getChildren().addAll(label0, checkBoxHorizontalAxis, checkBoxVerticalAxis);
		
		VBox vBoxTranslate = new VBox();
		
		Label labelCameraPosition = new Label("カメラの位置");
		labelCameraPosition.getStyleClass().add("bold");
		Label labelCameraToRight = new Label("右へ：　");
		labelCameraToRight.setPadding(new Insets(0, 0, 0, 5));
		Label labelCameraToDown = new Label("下へ：　");
		labelCameraToDown.setPadding(new Insets(0, 0, 0, 5));
		Label labelCameraToForward = new Label("向こうへ：　");
		labelCameraToForward.setPadding(new Insets(0, 0, 0, 5));
		sliderTranslateToRight.setPadding(new Insets(0, 0, 0, 5));
		sliderTranslateToRight.setMin(-2000);
		sliderTranslateToRight.setMax(2000);
		sliderTranslateToRight.setValue(right);
		sliderTranslateToRight.setBlockIncrement(100);
		sliderTranslateToRight.setShowTickLabels(true);
		sliderTranslateToRight.setShowTickMarks(true);
		sliderTranslateToRight.setMajorTickUnit(1000);
		sliderTranslateToRight.setMinorTickCount(100);
		
		sliderTranslateToDown.setPadding(new Insets(0, 0, 0, 5));
		sliderTranslateToDown.setMin(-2000);
		sliderTranslateToDown.setMax(2000);
		sliderTranslateToDown.setValue(down);
		sliderTranslateToDown.setBlockIncrement(100);
		sliderTranslateToDown.setShowTickLabels(true);
		sliderTranslateToDown.setShowTickMarks(true);
		sliderTranslateToDown.setMajorTickUnit(1000);
		sliderTranslateToDown.setMinorTickCount(100);
		
		sliderTranslateToForward.setPadding(new Insets(0, 0, 10, 5));
		sliderTranslateToForward.setMin(-8000);
		sliderTranslateToForward.setMax(0);
		sliderTranslateToForward.setValue(forward);
		sliderTranslateToForward.setBlockIncrement(100);
		sliderTranslateToForward.setShowTickLabels(true);
		sliderTranslateToForward.setShowTickMarks(true);
		sliderTranslateToForward.setMajorTickUnit(1000);
		sliderTranslateToForward.setMinorTickCount(100);
		
		HBox hBoxTranslateX = new HBox();
		hBoxTranslateX.getChildren().addAll(labelCameraToRight, textTranslateToRight);
		
		HBox hBoxTranslateY = new HBox();
		hBoxTranslateY.getChildren().addAll(labelCameraToDown, textTranslateToDown);
		
		HBox hBoxTranslateZ = new HBox();
		hBoxTranslateZ.getChildren().addAll(labelCameraToForward, textTranslateToForward);
		
		vBoxTranslate.getChildren().addAll(labelCameraPosition, hBoxTranslateX, sliderTranslateToRight, hBoxTranslateY, sliderTranslateToDown, hBoxTranslateZ, sliderTranslateToForward);
		
		VBox vBoxRotate = new VBox();
		vBoxRotate.setPrefWidth(200);
		Label label2 = new Label("カメラの回転");
		label2.getStyleClass().add("bold");
		
		HBox hBox1 = new HBox();
		Label label3 = new Label("カメラの方位角　：　");
		label3.setPadding(new Insets(0, 0, 0, 5));
		hBox1.getChildren().addAll(label3, textAzimuth);

		sliderAzimuth.setPadding(new Insets(0, 10, 0, 10));
		sliderAzimuth.setMin(0);
		sliderAzimuth.setMax(360);
		sliderAzimuth.setValue(0);
		sliderAzimuth.setBlockIncrement(10);
		sliderAzimuth.setShowTickLabels(true);
		sliderAzimuth.setShowTickMarks(true);
		sliderAzimuth.setMajorTickUnit(60);
		sliderAzimuth.setMinorTickCount(5);
		
		HBox hBox2 = new HBox();
		Label label4 = new Label("カメラの仰角　：　");
		label4.setPadding(new Insets(0, 0, 0, 5));
		hBox2.getChildren().addAll(label4, textElevation);

		sliderElevation.setPadding(new Insets(0, 10, 0, 10));
		sliderElevation.setMin(-180);
		sliderElevation.setMax(180);
		sliderElevation.setValue(0);
		sliderElevation.setBlockIncrement(10);
		sliderElevation.setShowTickLabels(true);
		sliderElevation.setShowTickMarks(true);
		sliderElevation.setMajorTickUnit(60);
		sliderElevation.setMinorTickCount(5);

		HBox hBox3 = new HBox();
		Label label5 = new Label("TSの方位角　：　");
		label5.setPadding(new Insets(0, 0, 0, 5));
		hBox3.getChildren().addAll(label5, textTSAzimuth);

		sliderTSAzimuth.setPadding(new Insets(0, 10, 0, 10));
		sliderTSAzimuth.setMin(0);
		sliderTSAzimuth.setMax(360);
		sliderTSAzimuth.setValue(0);
		sliderTSAzimuth.setBlockIncrement(10);
		sliderTSAzimuth.setShowTickLabels(true);
		sliderTSAzimuth.setShowTickMarks(true);
		sliderTSAzimuth.setMajorTickUnit(60);
		sliderTSAzimuth.setMinorTickCount(5);

		HBox hBox4 = new HBox();
		Label label6 = new Label("カメラの仰角　：　");
		label6.setPadding(new Insets(0, 0, 0, 5));
		hBox4.getChildren().addAll(label6, textTSElevation);

		sliderTSElevation.setPadding(new Insets(0, 10, 0, 10));
		sliderTSElevation.setMin(-180);
		sliderTSElevation.setMax(180);
		sliderTSElevation.setValue(0);
		sliderTSElevation.setBlockIncrement(10);
		sliderTSElevation.setShowTickLabels(true);
		sliderTSElevation.setShowTickMarks(true);
		sliderTSElevation.setMajorTickUnit(60);
		sliderTSElevation.setMinorTickCount(5);

		vBoxRotate.getChildren().addAll(label2, hBox1, sliderAzimuth, hBox2, sliderElevation, hBox3, sliderTSAzimuth, hBox4, sliderTSElevation);
		
		vBox.getChildren().addAll(hBox, labelCameraInitialSetting, vBoxRotateAxis, vBoxTranslate, vBoxRotate/*, imageView*/);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

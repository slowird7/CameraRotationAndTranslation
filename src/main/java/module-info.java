module cameraAngleTest {
	requires transitive javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.base;
	
	exports application to javafx.graphics,javafx.fxml;
//	opens application to javafx.fxml;
}
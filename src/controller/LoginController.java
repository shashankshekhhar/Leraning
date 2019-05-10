package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.LoginModel;

public class LoginController implements Initializable {

	@FXML
	private Label status;
	@FXML
	private Label isConnected;

	@FXML
	private TextField username;

	@FXML
	private Label lberror;

	@FXML
	private TextField password;

	static String temp;
	LoginModel loginModel = new LoginModel();

	public Connection getConnection1() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shashank", "root", "root");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void Login(ActionEvent event) throws Exception {

		temp = username.getText();
		PreparedStatement preparedStatement;
		ResultSet resultSet = null;
		Connection con = getConnection1();
		String username1 = username.getText().toString();
		String password1 = password.getText().toString();
		String sql = "select * from login where username=? and password=?";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, username1);
		preparedStatement.setString(2, password1);
		resultSet = preparedStatement.executeQuery();
		System.out.println(resultSet);

		if (resultSet.next()) {

			if (username1.equalsIgnoreCase("admin")) {
				((Node) event.getSource()).getScene().getWindow().hide();
				Stage stage = new Stage();
				FXMLLoader fxmlLoader = new FXMLLoader();
				Pane parent = fxmlLoader.load(getClass().getResource("/view/MainStudent.fxml").openStream());
				//MainController mainCont = (MainController) fxmlLoader.getController();
				AdminController mainCont = (AdminController) fxmlLoader.getController();
				mainCont.getUser("Welcome " + username.getText() + "!");
				Scene scene = new Scene(parent, Color.RED);
				scene.setFill(Color.RED);
				stage.setScene(scene);

				stage.setTitle("Student Management System");
				stage.show();
			} else {
				System.out.println("askdbjaksdkashdksa");
				((Node) event.getSource()).getScene().getWindow().hide();
				Stage stage = new Stage();
				FXMLLoader fxmlLoader = new FXMLLoader();
				Pane parent = fxmlLoader.load(getClass().getResource("/view/Student.fxml").openStream());
				StudentController mainCont = (StudentController) fxmlLoader.getController();
				mainCont.getUser("Welcome " + username.getText() + "!");
				Scene scene = new Scene(parent, Color.RED);
				scene.setFill(Color.RED);
				stage.setScene(scene);

				stage.setTitle("Student Management System");
				stage.show();
			}

		} else {
			System.out.println("Invalid Password");
			lberror.setText("Enter Correct UserName/Password");
			username.clear();
			password.clear();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		if (loginModel.isDbConnected()) {
			// status.setText("Connected");
		}
	}

}
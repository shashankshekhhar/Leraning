package controller;


import library.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ResourceBundle;

public class StudentController implements Initializable {

	@FXML
	private Label userField;
	@FXML
	private TextField idField;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private TextField major;

	@FXML
	private TextField dob;
	@FXML
	private TextField fee;

	@FXML
	private Button insertButton;


	@FXML
	private TableView<Student> TableView;

	@FXML
	private TableColumn<Student, Integer> idColumn;

	@FXML
	private TableColumn<Student, String> firstNameColumn;

	@FXML
	private TableColumn<Student, String> lastNameColumn;

	@FXML
	private TableColumn<Student, String> majorColumn;

	@FXML
	private TableColumn<Student, String> dobColumn;
	
	@FXML
	private TableColumn<Student, Integer> feeColumn;



	public void executeQuery(String query) {
		Connection conn = getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		viewDetail();
	}

	public void getUser(String user) {
		userField.setText(user);
	}

	public void signOut(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader();
		Pane parent = fxmlLoader.load(getClass().getResource("/view/Login.fxml").openStream());

		Scene scene = new Scene(parent, Color.RED);
		scene.setFill(Color.RED);
		stage.setScene(scene);

		stage.setTitle("Student Management System");
		stage.show();
	}

	public Connection getConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shashank", "root", "root");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ObservableList<Student> getstudentlist() {
		ObservableList<Student> studentList = FXCollections.observableArrayList();
		Connection connection = getConnection();
		String query = "SELECT * FROM students where firstName = '"+ LoginController.temp +"'";
		Statement st;
		ResultSet rs;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Student students;
			while (rs.next()) {
				students = new Student(rs.getInt("StudentId"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("dob"),
						rs.getString("major"),rs.getFloat("fee"));
				studentList.add(students);
				
				System.out.println(rs.getInt("StudentId"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}


	public void viewDetail() {
		ObservableList<Student> list = getstudentlist();

		idColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("studentId"));
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
		
		dobColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("dob"));
		majorColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("major"));
		feeColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("fee"));
		
		
		TableView.setItems(list);
	}

}

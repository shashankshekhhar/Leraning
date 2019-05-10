package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.sun.istack.internal.NotNull;

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

import library.Student;

public class AdminController implements Initializable  {

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
	private Button addStudent;

	@FXML
	private Button updateStudent;

	@FXML
	private Button deleteStudent;

	@FXML
	private Label maerror;
	
	
	@FXML
	private TableView<Student> TableView;
	
	@FXML
	@NotNull
	private TableColumn<Student, Integer> idColumn;

	@FXML
	@NotNull
	private TableColumn<Student, String> firstNameColumn;

	@FXML
	private TableColumn<Student, String> lastNameColumn;
	
	@FXML
	private TableColumn<Student, String> dobColumn;
	

	@FXML
	private TableColumn<Student, String> majorColumn;

	
	@FXML
	private TableColumn<Student, Integer> feeColumn;
	
	
	ValidationSupport validationSupport = new ValidationSupport();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		viewStudents();
		validationSupport.registerValidator(idField, Validator.createEmptyValidator("Student Id is required"));
		validationSupport.registerValidator(firstName, Validator.createEmptyValidator("Student Id is required"));
		validationSupport.registerValidator(lastName, Validator.createEmptyValidator("Student Id is required"));
		
		validationSupport.registerValidator(dob, Validator.createEmptyValidator("Student Id is required"));
		validationSupport.registerValidator(major, Validator.createEmptyValidator("Student Id is required"));
		validationSupport.registerValidator(fee, Validator.createEmptyValidator("Student Id is required"));

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
	
	
	@FXML
	private void addStudent() {

		if(idField.getText().isEmpty() || firstName.getText().isEmpty() || lastName.getText().isEmpty()|| dob.getText().isEmpty() || major.getText().isEmpty()||fee.getText().isEmpty() ) {
			System.out.println("Pass Valid Values ");
			maerror.setText("EnterValidFields");
			idField.clear();
			firstName.clear();
			lastName.clear();
			
			dob.clear();
			major.clear();
			fee.clear();
		}
		else {
			System.out.println("Nothinsasasasg");
			System.out.println(idField.getText()+firstName.getText()+lastName.getText()+dob.getText()+major.getText()+Float.parseFloat(fee.getText() ));
			
		String query = "insert into Students values(" + idField.getText() + ",'" + firstName.getText() + "','"
				+ lastName.getText() + "','" + dob.getText() + "','" + major.getText() +"'," +Float.parseFloat(fee.getText() ) +")";
		executeQuery(query);
		viewStudents();
		
		idField.clear();
		firstName.clear();
		lastName.clear();
		
		dob.clear();
		major.clear();
		fee.clear();
		}
	}
	@FXML
	private void updateStudent() {
		String query = "UPDATE Students SET firstName='" + firstName.getText() + "',lastName='" + lastName.getText()
				 + "',dob='" + dob.getText() + "',major='" + major.getText() + "',fee=" + fee.getText() +" WHERE StudentId=" + idField.getText()
				+ "";
		executeQuery(query);
		viewStudents();
	}
	@FXML
	private void deleteStudent() {
		String query = "DELETE FROM Students WHERE StudentId=" + idField.getText() + "";
		executeQuery(query);
		viewStudents();
	}

	public ObservableList<Student> getStudentList() {
		ObservableList<Student> studentList = FXCollections.observableArrayList();
		Connection connection = getConnection();
		String query = "SELECT * FROM Students  ";
		Statement st;
		ResultSet rs;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Student students;
			while (rs.next()) {
				students = new Student(rs.getInt("StudentId"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getString("dob"), rs.getString("major"),rs.getFloat("fee"));
				studentList.add(students);
				System.out.println(rs.getString("major"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}
	public void viewStudents() {
		System.out.println("Value :: " + userField.getText().toUpperCase() + " " + LoginController.temp);
		ObservableList<Student> list = getStudentList();

		idColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("studentId"));
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
		
		dobColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("dob"));
		majorColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("major"));
		feeColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("fee"));

		TableView.setItems(list);
	}
}

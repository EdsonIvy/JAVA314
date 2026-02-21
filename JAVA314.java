// JavaFX imports (for the window and controls)
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;

// SQL imports (for database connection)
import java.sql.*;

// Main class for the program
public class JAVA314 extends Application {

    // Text fields for user input
    private TextField tfId = new TextField();
    private TextField tfLastName = new TextField();
    private TextField tfFirstName = new TextField();
    private TextField tfMI = new TextField();
    private TextField tfAddress = new TextField();
    private TextField tfCity = new TextField();
    private TextField tfState = new TextField();
    private TextField tfTelephone = new TextField();
    private TextField tfEmail = new TextField();

    // Database connection info
    private final String URL =
        "jdbc:mysql://localhost:3306/companyDB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private final String USER = "root";
    private final String PASSWORD = "INSERT YOUR PASSWORD HERE!"; //implement your MySQL password to make it run

    // Builds the window
    @Override
    public void start(Stage stage) {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(8);
        grid.setVgap(8);

        // Add labels and text fields
        grid.add(new Label("ID:"), 0, 0); grid.add(tfId, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1); grid.add(tfLastName, 1, 1);
        grid.add(new Label("First Name:"), 0, 2); grid.add(tfFirstName, 1, 2);
        grid.add(new Label("MI:"), 0, 3); grid.add(tfMI, 1, 3);
        grid.add(new Label("Address:"), 0, 4); grid.add(tfAddress, 1, 4);
        grid.add(new Label("City:"), 0, 5); grid.add(tfCity, 1, 5);
        grid.add(new Label("State:"), 0, 6); grid.add(tfState, 1, 6);
        grid.add(new Label("Telephone:"), 0, 7); grid.add(tfTelephone, 1, 7);
        grid.add(new Label("Email:"), 0, 8); grid.add(tfEmail, 1, 8);

        // Buttons
        Button btView = new Button("View");
        Button btInsert = new Button("Insert");
        Button btUpdate = new Button("Update");

        HBox buttons = new HBox(15, btView, btInsert, btUpdate);
        VBox root = new VBox(10, grid, buttons);
        root.setPadding(new Insets(15));

        // Button actions
        btView.setOnAction(e -> viewStaff());
        btInsert.setOnAction(e -> insertStaff());
        btUpdate.setOnAction(e -> updateStaff());

        stage.setScene(new Scene(root, 450, 500));
        stage.setTitle("Staff Manager");
        stage.show();
    }

    // Connect to MySQL database
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // View record by ID
    private void viewStaff() {
        String id = tfId.getText().trim();

        if (id.isEmpty()) {
            showAlert("Please enter an ID.");
            return;
        }

        String sql = "SELECT * FROM Staff WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tfLastName.setText(rs.getString("lastName"));
                tfFirstName.setText(rs.getString("firstName"));
                tfMI.setText(rs.getString("mi"));
                tfAddress.setText(rs.getString("address"));
                tfCity.setText(rs.getString("city"));
                tfState.setText(rs.getString("state"));
                tfTelephone.setText(rs.getString("telephone"));
                tfEmail.setText(rs.getString("email"));
            } else {
                showAlert("No record found.");
            }

        } catch (SQLException ex) {
            showAlert("SQL Error: " + ex.getMessage());
        }
    }

    // Insert new record
    private void insertStaff() {
        String id = tfId.getText().trim();

        if (id.isEmpty()) {
            showAlert("ID cannot be empty.");
            return;
        }

        String sql = "INSERT INTO Staff VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, tfLastName.getText().trim());
            ps.setString(3, tfFirstName.getText().trim());
            ps.setString(4, tfMI.getText().trim());
            ps.setString(5, tfAddress.getText().trim());
            ps.setString(6, tfCity.getText().trim());
            ps.setString(7, tfState.getText().trim());
            ps.setString(8, tfTelephone.getText().trim());
            ps.setString(9, tfEmail.getText().trim());

            ps.executeUpdate();
            showAlert("Record inserted.");

        } catch (SQLException ex) {
            showAlert("SQL Error: " + ex.getMessage());
        }
    }

    // Update existing record
    private void updateStaff() {
        String id = tfId.getText().trim();

        if (id.isEmpty()) {
            showAlert("Please enter an ID.");
            return;
        }

        String sql = """
                UPDATE Staff
                SET lastName=?, firstName=?, mi=?, address=?,
                    city=?, state=?, telephone=?, email=?
                WHERE id=?
                """;

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tfLastName.getText().trim());
            ps.setString(2, tfFirstName.getText().trim());
            ps.setString(3, tfMI.getText().trim());
            ps.setString(4, tfAddress.getText().trim());
            ps.setString(5, tfCity.getText().trim());
            ps.setString(6, tfState.getText().trim());
            ps.setString(7, tfTelephone.getText().trim());
            ps.setString(8, tfEmail.getText().trim());
            ps.setString(9, id);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                showAlert("Record updated.");
            } else {
                showAlert("No record found.");
            }

        } catch (SQLException ex) {
            showAlert("SQL Error: " + ex.getMessage());
        }
    }

    // Show message box
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Start the program
    public static void main(String[] args) {
        launch();
    }
}
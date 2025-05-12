package house_rental_system;
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
// User class to store user details
class User {
String username; 
String password;
String role; // Landlord or Tenant
User(String username, String password, String role) {
this.username = username; 
this.password = password; 
this.role = role;
}
}
// Property class to store rental details
class Property { 
String address; 
String location; 
double rent;
String landlord; // Landlord username
boolean isAvailable;
Property(String address, String location, double rent, String landlord) {
this.address = address; 
this.location = location; 
this.rent = rent; 
this.landlord = landlord; 
this.isAvailable = true;
}
@Override
public String toString() {
return "Address: " + address + ", Location: " + location + ", Rent: $" + rent + ", Available: " + 
isAvailable;
}
}
12
// Booking request class 
class BookingRequest { 
Property property;
String tenant; // Tenant username
String status; // Pending, Approved, Rejected
BookingRequest(Property property, String tenant) {
this.property = property; 
this.tenant = tenant; 
this.status = "Pending";
}
@Override
public String toString() {
return "Property: " + property.address + ", Tenant: " + tenant + ", Status: " + status;
}
}
public class HouseRentalSystem {
private static List<User> users = new ArrayList<>();
private static List<Property> properties = new ArrayList<>();
private static List<BookingRequest> bookingRequests = new ArrayList<>();
private static JFrame mainFrame;
public static void main(String[] args) { 
SwingUtilities.invokeLater(HouseRentalSystem::createAndShowGUI);
}
private static void createAndShowGUI() {
mainFrame = new JFrame("House Rental System"); 
mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
mainFrame.setSize(600, 400);
mainFrame.setLayout(new BorderLayout());
JLabel welcomeLabel = new JLabel("Welcome to House Rental System", JLabel.CENTER); 
welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
mainFrame.add(welcomeLabel, BorderLayout.NORTH);
JPanel buttonPanel = new JPanel();
JButton loginButton = new JButton("Login");
JButton registerButton = new JButton("Register"); 
JButton adminButton = new JButton("Admin Panel");
loginButton.addActionListener(e -> showLoginPage()); 
registerButton.addActionListener(e -> showRegisterPage()); 
adminButton.addActionListener(e -> showAdminPanel());
13
buttonPanel.add(loginButton); 
buttonPanel.add(registerButton); 
buttonPanel.add(adminButton);
mainFrame.add(buttonPanel, BorderLayout.CENTER);
mainFrame.setVisible(true);
}
private static void showRegisterPage() {
JFrame registerFrame = new JFrame("Register"); 
registerFrame.setSize(300, 200);
registerFrame.setLayout(new GridLayout(5, 2));
JLabel userLabel = new JLabel("Username:"); 
JTextField userField = new JTextField(); 
JLabel passLabel = new JLabel("Password:");
JPasswordField passField = new JPasswordField(); 
JLabel roleLabel = new JLabel("Role:");
JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Landlord", 
"Tenant"});
JButton registerButton = new JButton("Register");
registerButton.addActionListener(e -> { 
String username = userField.getText();
String password = new String(passField.getPassword()); 
String role = (String) roleComboBox.getSelectedItem();
if (username.isEmpty() || password.isEmpty()) { 
JOptionPane.showMessageDialog(registerFrame, "All fields are required!");
} else {
users.add(new User(username, password, role)); 
JOptionPane.showMessageDialog(registerFrame, "Registration successful!"); 
registerFrame.dispose();
}
});
registerFrame.add(userLabel); 
registerFrame.add(userField); 
registerFrame.add(passLabel); 
registerFrame.add(passField); 
registerFrame.add(roleLabel); 
registerFrame.add(roleComboBox); 
registerFrame.add(new JLabel("")); 
registerFrame.add(registerButton);
registerFrame.setVisible(true);
}
14
private static void showLoginPage() {
JFrame loginFrame = new JFrame("Login"); 
loginFrame.setSize(300, 200);
loginFrame.setLayout(new GridLayout(4, 2));
JLabel userLabel = new JLabel("Username:"); 
JTextField userField = new JTextField(); 
JLabel passLabel = new JLabel("Password:");
JPasswordField passField = new JPasswordField(); 
JButton loginButton = new JButton("Login");
loginButton.addActionListener(e -> { 
String username = userField.getText();
String password = new String(passField.getPassword());
boolean isValid = false;
for (User user : users) {
if (user.username.equals(username) && user.password.equals(password)) { 
isValid = true;
JOptionPane.showMessageDialog(loginFrame, "Welcome, " + user.role + "!");
if (user.role.equals("Landlord")) {
showLandlordPanel(username);
} else if (user.role.equals("Tenant")) {
showTenantPanel(username);
}
loginFrame.dispose();
break;
}
}
if (!isValid) {
JOptionPane.showMessageDialog(loginFrame, "Invalid username or password!");
}
});
loginFrame.add(userLabel); 
loginFrame.add(userField); 
loginFrame.add(passLabel); 
loginFrame.add(passField); 
loginFrame.add(new JLabel("")); 
loginFrame.add(loginButton);
loginFrame.setVisible(true);
}
private static void showLandlordPanel(String username) { 
JFrame landlordFrame = new JFrame("Landlord Panel"); 
landlordFrame.setSize(600, 400); 
landlordFrame.setLayout(new BorderLayout());
15
// Property Table
String[] columnNames = {"Address", "Location", "Rent", "Available"}; 
DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0); 
JTable propertyTable = new JTable(tableModel);
JScrollPane scrollPane = new JScrollPane(propertyTable);
// Refresh Button
JButton refreshButton = new JButton("Refresh");
refreshButton.addActionListener(e -> refreshPropertyTable(tableModel, username));
// Buttons
JButton addPropertyButton = new JButton("Add Property"); 
JButton handleRequestsButton = new JButton("Handle Requests");
addPropertyButton.addActionListener(e -> showAddPropertyDialog(username)); 
handleRequestsButton.addActionListener(e -> showBookingRequests(username));
JPanel buttonPanel = new JPanel(); 
buttonPanel.add(addPropertyButton); 
buttonPanel.add(handleRequestsButton); 
buttonPanel.add(refreshButton);
landlordFrame.add(scrollPane, BorderLayout.CENTER); 
landlordFrame.add(buttonPanel, BorderLayout.SOUTH); 
landlordFrame.setVisible(true); 
refreshPropertyTable(tableModel, username); // Initial load
}
private static void refreshPropertyTable(DefaultTableModel tableModel, String username) { 
tableModel.setRowCount(0); // Clear existing rows
for (Property property : properties) {
if (property.landlord.equals(username)) {
tableModel.addRow(new Object[]{property.address, property.location, property.rent, 
property.isAvailable});
}
}
}
private static void showAddPropertyDialog(String username) { 
JFrame addPropertyFrame = new JFrame("Add Property"); 
addPropertyFrame.setSize(300, 200);
addPropertyFrame.setLayout(new GridLayout(4, 2));
JLabel addressLabel = new JLabel("Address:"); 
JTextField addressField = new JTextField(); 
JLabel locationLabel = new JLabel("Location:"); 
JTextField locationField = new JTextField(); 
JLabel rentLabel = new JLabel("Rent:"); 
JTextField rentField = new JTextField();
16
JButton addButton = new JButton("Add");
addButton.addActionListener(e -> {
String address = addressField.getText();
String location = locationField.getText();
try {
double rent = Double.parseDouble(rentField.getText());
properties.add(new Property(address, location, rent, username)); 
JOptionPane.showMessageDialog(addPropertyFrame, "Property added successfully!"); 
addPropertyFrame.dispose();
} catch (NumberFormatException ex) { 
JOptionPane.showMessageDialog(addPropertyFrame, "Invalid rent amount!");
}
});
addPropertyFrame.add(addressLabel); 
addPropertyFrame.add(addressField); 
addPropertyFrame.add(locationLabel); 
addPropertyFrame.add(locationField); 
addPropertyFrame.add(rentLabel); 
addPropertyFrame.add(rentField); 
addPropertyFrame.add(new JLabel("")); 
addPropertyFrame.add(addButton);
addPropertyFrame.setVisible(true);
}
private static void showBookingRequests(String landlord) { 
JFrame requestsFrame = new JFrame("Booking Requests"); 
requestsFrame.setSize(300, 200);
DefaultTableModel tableModel
= new DefaultTableModel(new String[]{"Address", "Tenant", "Status"}, 0); 
JTable requestsTable = new JTable(tableModel);
for (BookingRequest request : bookingRequests) {
if (request.property.landlord.equals(landlord)) {
tableModel.addRow(new Object[]{request.property.address, request.tenant, 
request.status});
}
}
JButton acceptButton = new JButton("Accept"); 
JButton rejectButton = new JButton("Reject");
acceptButton.addActionListener(e -> {
int selectedRow = requestsTable.getSelectedRow();
if (selectedRow >= 0) {
BookingRequest selectedRequest = bookingRequests.get(selectedRow);
17
}
});
selectedRequest.status = "Approved"; 
selectedRequest.property.isAvailable = false; 
tableModel.setValueAt("Approved", selectedRow, 2); 
JOptionPane.showMessageDialog(requestsFrame, "Request approved!");
rejectButton.addActionListener(e -> {
int selectedRow = requestsTable.getSelectedRow();
if (selectedRow >= 0) {
BookingRequest selectedRequest = bookingRequests.get(selectedRow); 
selectedRequest.status = "Rejected"; 
tableModel.setValueAt("Rejected", selectedRow, 2); 
JOptionPane.showMessageDialog(requestsFrame, "Request rejected!");
}
});
JPanel buttonPanel = new JPanel(); 
buttonPanel.add(acceptButton); 
buttonPanel.add(rejectButton);
requestsFrame.add(new JScrollPane(requestsTable), BorderLayout.CENTER); 
requestsFrame.add(buttonPanel, BorderLayout.SOUTH); 
requestsFrame.setVisible(true);
}
private static void showTenantPanel(String username) { 
JFrame tenantFrame = new JFrame("Tenant Panel"); 
tenantFrame.setSize(600, 400);
DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Address", 
"Location", "Rent", "Available"}, 0);
JTable propertyTable = new JTable(tableModel);
for (Property property : properties) {
if (property.isAvailable) {
tableModel.addRow(new Object[]{property.address, property.location, property.rent, 
property.isAvailable});
}
}
JButton bookButton = new JButton("Request to Rent");
JButton viewRequestsButton = new JButton("View Booking Requests");
bookButton.addActionListener(e -> {
int selectedRow = propertyTable.getSelectedRow();
if (selectedRow >= 0) {
Property selectedProperty = properties.get(selectedRow); 
bookingRequests.add(new BookingRequest(selectedProperty, username));
18
JOptionPane.showMessageDialog(tenantFrame, "Booking request sent!");
}
});
viewRequestsButton.addActionListener(e -> showTenantRequests(username));
tenantFrame.add(new JScrollPane(propertyTable), BorderLayout.CENTER); 
JPanel buttonPanel = new JPanel();
buttonPanel.add(bookButton); 
buttonPanel.add(viewRequestsButton); 
tenantFrame.add(buttonPanel, BorderLayout.SOUTH); 
tenantFrame.setVisible(true);
}
private static void showTenantRequests(String tenant) {
JFrame requestsFrame = new JFrame("My Booking Requests"); 
requestsFrame.setSize(300, 200);
DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Property", 
"Status"}, 0);
JTable requestsTable = new JTable(tableModel);
for (BookingRequest request : bookingRequests) {
if (request.tenant.equals(tenant)) {
tableModel.addRow(new Object[]{request.property.address, request.status});
}
}
requestsFrame.add(new JScrollPane(requestsTable), BorderLayout.CENTER); 
requestsFrame.setVisible(true);
}
private static void showAdminPanel() {
JFrame adminFrame = new JFrame("Admin Panel"); 
adminFrame.setSize(600, 400);
DefaultTableModel propertyTableModel = new DefaultTableModel(new String[]{"Address", 
"Location", "Rent", "Landlord", "Available"}, 0);
JTable propertyTable = new JTable(propertyTableModel);
for (Property property : properties) {
propertyTableModel.addRow(new Object[]{property.address, property.location, 
property.rent, property.landlord, property.isAvailable});
}
JButton deleteButton = new JButton("Delete Property"); 
JButton updateButton = new JButton("Update Property");
deleteButton.addActionListener(e -> {
19
int selectedRow = propertyTable.getSelectedRow();
if (selectedRow >= 0) { 
properties.remove(selectedRow); 
propertyTableModel.removeRow(selectedRow);
JOptionPane.showMessageDialog(adminFrame, "Property deleted successfully!");
}
});
updateButton.addActionListener(e -> {
int selectedRow = propertyTable.getSelectedRow();
if (selectedRow >= 0) {
Property selectedProperty = properties.get(selectedRow); 
showUpdatePropertyDialog(selectedProperty, propertyTableModel, selectedRow);
}
});
JPanel buttonPanel = new JPanel(); 
buttonPanel.add(deleteButton); 
buttonPanel.add(updateButton);
adminFrame.add(new JScrollPane(propertyTable), BorderLayout.CENTER); 
adminFrame.add(buttonPanel, BorderLayout.SOUTH); 
adminFrame.setVisible(true);
}
private static void showUpdatePropertyDialog(Property property, DefaultTableModel 
tableModel, int rowIndex) {
JFrame updatePropertyFrame = new JFrame("Update Property"); 
updatePropertyFrame.setSize(300, 200);
updatePropertyFrame.setLayout(new GridLayout(4, 2));
JLabel addressLabel = new JLabel("Address:");
JTextField addressField = new JTextField(property.address); 
JLabel locationLabel = new JLabel("Location:");
JTextField locationField = new JTextField(property.location); 
JLabel rentLabel = new JLabel("Rent:");
JTextField rentField = new JTextField(String.valueOf(property.rent)); 
JButton updateButton = new JButton("Update");
updateButton.addActionListener(e -> { 
String address = addressField.getText(); 
String location = locationField.getText(); 
try {
double rent = Double.parseDouble(rentField.getText()); 
property.address = address;
property.location = location; 
property.rent = rent;
tableModel.setValueAt(address, rowIndex, 0);
tableModel.setValueAt(location, rowIndex, 1);
20
tableModel.setValueAt(rent, rowIndex, 2);
JOptionPane.showMessageDialog(updatePropertyFrame, "Property updated 
successfully!");
updatePropertyFrame.dispose();
} catch (NumberFormatException ex) { 
JOptionPane.showMessageDialog(updatePropertyFrame, "Invalid rent amount!");
}
});
updatePropertyFrame.add(addressLabel); 
updatePropertyFrame.add(addressField); 
updatePropertyFrame.add(locationLabel); 
updatePropertyFrame.add(locationField); 
updatePropertyFrame.add(rentLabel); 
updatePropertyFrame.add(rentField); 
updatePropertyFrame.add(new JLabel("")); 
updatePropertyFrame.add(updateButton);
updatePropertyFrame.setVisible(true);
}
}
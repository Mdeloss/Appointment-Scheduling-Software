# Appointment Scheduling Software
 
Purpose of the application is to provide a list of customers and appointments for a business operating in EST. The appointment list can be view depending on 
different criteria as well. This application actively reads and write to the company database.

•  Mariano De Los Santos

•  IntelliJ Community 2021.1.3, Java SE 17.0.1, JavaFX-SDK-17.0.1

•  Directions: To begin, run the application and you will be shown a login screen.

LoginScreen 
- You may enter username and password. Press Enter on keyboard or click the "Login" button on screen. "Exit" button will close the application.

MainScreen 
- You will see all customers listed on the top table view. You may add, update or delete these customers.
- You will see all appointments listed on the bottom table view. You may add, update or delete these appointments. You may also filter appointments by month or week
 using the radio buttons.  
- You may search for customer and appointments via name or ID using the textfield above each respectively.
- Select "Report" to go to Reports screen.
- Select "Log out" to go back to LoginScreen screen.
- Select "Exit" to close application.

AddCustomer and UpdateCustomer screens 
- You may enter customer attributes in textfields.
- Select a country from the top tableview and then an associated Division from bottom tableview. Then click the "Add" button to confirm.
- Select "Save" to add to database or "Cancel" to cancel this screen.

AddAppointment and UpdateAppointment screens 
- You may enter appointment attributes in textfields.
- Select a contact from the top tableview, then a customer from middle tableview, then
a user from the bottom tableview. Then click the "Add" button to confirm each respective selection.
- Select "Save" to add to database or "Cancel" to cancel this screen.

Reports screen
- This will show appointments by certain criteria.
- "Total Appointments by Month and Type" will allow you to enter a month number and a type name in the textfields.
- Select "Find Total" to display total appointments found.
- "Total Appointments by Type: Today Only" will allow you to enter a type name in the textfields, but not DayOfYear.
- Select "Find Total" to display total appointments found.

- Contacts allows you to select a contact from the table view.
- "Show Scheduled" will display a schedule for the selected contact. This will be displayed in the bottom tableview.
- Select "Exit" to go back to the MainScreen screen.

 
•  Part A3f: This function will provide the total number of appointments found that are scheduled for current day (Today) and appointment type.

•  MySQL Connector driver: mysql-connector-java-8.0.251
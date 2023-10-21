package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class holds the attributes for Customer .*/

public class Customer {


        //private ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();
        private int id;
        private String name;
        private String address;
        private String postal_code;
        private String phone;
        private int div_id;


        /*
        public void addAssociatedAppointments (Appointment appt) {
            associatedAppointments.add(appt);
        }


        public boolean deleteAssociatedAppointments (Appointment selectedAssociatedAppointments) {

            for (Appointment object : getAllAssociatedAppointments()) {
                if (object.getApptID() == selectedAssociatedAppointments.getApptID())
                    return associatedAppointments.remove(selectedAssociatedAppointments);
            }
            return false;
        }

        public ObservableList<Appointment> getAllAssociatedAppointments() {
            return associatedAppointments;
        }
*/

        /** This constructor is for the Customer object. */
        public Customer(int id, String name, String address, String postal_code, String phone, int div_id) {

            this.id = id;
            this.name = name;
            this.address = address;
            this.postal_code = postal_code;
            this.phone = phone;
            this.div_id = div_id;

        }
        /**
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the address
         */
        public String getAddress() {
            return address;
        }

        /**
         * @param address the address to set
         */
        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * @return the postal code
         */
        public String getPostal_code() {
            return postal_code;
        }

        /**
         * @param postal_code the postal code to set
         */
        public void setPostal_code(String postal_code) {
            this.postal_code = postal_code;
        }

        /**
         * @return the phone number
         */
        public String getPhone() {
            return phone;
        }

        /**
         * @param phone the phone number to set
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         * @return the Division ID
         */
        public int getDiv_id() {
            return div_id;
        }

        /**
         * @param div_id the DivisionID to set
         */
        public void setDiv_id(int div_id) {
            this.div_id = div_id;
        }

    }



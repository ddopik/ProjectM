package com.spade.mek.ui.cart.model;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/23/17.
 */

public class Order {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String typeOfDonation;
    private String donationRegion;
    private List<OrderItems> orderItems;

    public String getDonationRegion() {
        return donationRegion;
    }

    public void setDonationRegion(String donationRegion) {
        this.donationRegion = donationRegion;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTypeOfDonation() {
        return typeOfDonation;
    }

    public void setTypeOfDonation(String typeOfDonation) {
        this.typeOfDonation = typeOfDonation;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }
}

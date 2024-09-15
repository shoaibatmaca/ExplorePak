package com.example.shahzadasadfyp.Model;

public class Hotel {
    private String Hotelname;
    private String totalroom;
    private String address;
    private String contactNumber;
    private String email;
    private String website;
    private String description;
    private String cancellationPolicy;
    private String paymentPolicy;
    private String startingcharges;
    private String id;
    private String hotelimg; // URL of the hotel image

    public Hotel(){
        // Default constructor required for calls to DataSnapshot.getValue(Hotel.class)
    }

    public Hotel(String Hotelname, String totoalroom, String address, String contactNumber, String email, String website, String description, String cancellationPolicy, String paymentPolicy, String startingcharges, String id, String hotelimg) {
        this.Hotelname = Hotelname;
        this.totalroom=totalroom;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
        this.website = website;
        this.description = description;
        this.cancellationPolicy = cancellationPolicy;
        this.paymentPolicy = paymentPolicy;
        this.startingcharges = startingcharges;
        this.id = id;
        this.hotelimg = hotelimg;
    }

    // Getters and Setters for all fields
    public String getHotelname() { return Hotelname; }
    public void setHotelname(String Hotelname) {
        this.Hotelname = Hotelname;
    }

    public String getTotalroom() {
        return totalroom;
    }

    public void setTotalroom(String totalroom) {
        this.totalroom = totalroom;
    }


    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCancellationPolicy() { return cancellationPolicy; }
    public void setCancellationPolicy(String cancellationPolicy) { this.cancellationPolicy = cancellationPolicy; }

    public String getPaymentPolicy() { return paymentPolicy; }
    public void setPaymentPolicy(String paymentPolicy) { this.paymentPolicy = paymentPolicy; }

    public String getStartingcharges() { return startingcharges; }
    public void setStartingcharges(String additionalCharges) { this.startingcharges = startingcharges; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getHotelimg() { return hotelimg; }
    public void setHotelimg(String hotelimg) { this.hotelimg = hotelimg; }
}

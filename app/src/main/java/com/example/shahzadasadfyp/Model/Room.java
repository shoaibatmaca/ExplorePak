package com.example.shahzadasadfyp.Model;


public class Room {
    private String RoomType;
    private String facilities;
    private String foodOptions;
    private String relaxationServices;
    private String businessServices;
    private String price;
    private String roompic;
    private String UserId;


    public  Room(){}



//
//    public Room(String roomType, String facilities, String foodOptions, String relaxationServices,
//                String businessServices, String price, String roompic) {
//        RoomType = roomType;
//        this.facilities = facilities;
//        this.foodOptions = foodOptions;
//        this.relaxationServices = relaxationServices;
//        this.businessServices = businessServices;
//        this.price = price;
//        this.roompic = roompic;
//    }

    public Room(String roomType, String facilities, String foodOptions, String relaxationServices, String businessServices, String price, String roompic, String userId) {
        RoomType = roomType;
        this.facilities = facilities;
        this.foodOptions = foodOptions;
        this.relaxationServices = relaxationServices;
        this.businessServices = businessServices;
        this.price = price;
        this.roompic = roompic;
        UserId = userId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String roomType) {
        RoomType = roomType;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getFoodOptions() {
        return foodOptions;
    }

    public void setFoodOptions(String foodOptions) {
        this.foodOptions = foodOptions;
    }

    public String getRelaxationServices() {
        return relaxationServices;
    }



    public void setRelaxationServices(String relaxationServices) {
        this.relaxationServices = relaxationServices;
    }

    public String getBusinessServices() {
        return businessServices;
    }

    public void setBusinessServices(String businessServices) {
        this.businessServices = businessServices;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRoompic() {
        return roompic;
    }

    public void setRoompic(String roompic) {
        this.roompic = roompic;
    }
}

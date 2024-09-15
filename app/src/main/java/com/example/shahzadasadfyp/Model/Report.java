package com.example.shahzadasadfyp.Model;

public class Report {
    private String id;            // Unique identifier for the report
    private String userId;        // ID of the reported user
    private String userName;      // Name of the reported user
    private String complaintDetails;  // Details of the complaint
    private String timestamp;     // Time when the complaint was reported

    // Default constructor required for calls to DataSnapshot.getValue(Report.class)
    public Report(String reportId, String currentUserId, String otherUserId, String reason, long l) {
    }

    public Report(String id, String userId, String userName, String complaintDetails, String timestamp) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.complaintDetails = complaintDetails;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComplaintDetails() {
        return complaintDetails;
    }

    public void setComplaintDetails(String complaintDetails) {
        this.complaintDetails = complaintDetails;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

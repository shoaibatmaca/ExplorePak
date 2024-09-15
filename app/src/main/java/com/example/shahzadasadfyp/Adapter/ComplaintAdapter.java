package com.example.shahzadasadfyp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shahzadasadfyp.Model.Report;
import com.example.shahzadasadfyp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {

    private List<Report> complaintList;
    private Context context;
    private DatabaseReference reportsRef;

    public ComplaintAdapter(List<Report> complaintList, Context context) {
        this.complaintList = complaintList;
        this.context = context;
        reportsRef = FirebaseDatabase.getInstance().getReference("Complaints");
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        Report report = complaintList.get(position);

        holder.textViewUser.setText(report.getUserName());
        holder.textViewComplaint.setText(report.getComplaintDetails());

        holder.buttonBlock.setOnClickListener(v -> blockUser(report.getUserId(), position));
        holder.buttonDelete.setOnClickListener(v -> deleteComplaint(report.getId(), position));
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUser, textViewComplaint;
        Button buttonBlock, buttonDelete;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUser = itemView.findViewById(R.id.textViewUser);
            textViewComplaint = itemView.findViewById(R.id.textViewComplaint);
            buttonBlock = itemView.findViewById(R.id.buttonBlock);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    private void blockUser(String userId, int position) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("status");
        usersRef.setValue("blocked").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "User blocked successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to block user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteComplaint(String complaintId, int position) {
        reportsRef.child(complaintId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Complaint deleted successfully", Toast.LENGTH_SHORT).show();
                complaintList.remove(position);
                notifyItemRemoved(position);
            } else {
                Toast.makeText(context, "Failed to delete complaint", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

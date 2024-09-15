package com.example.shahzadasadfyp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shahzadasadfyp.Model.Room;
import com.example.shahzadasadfyp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private Context context;
    private List<Room> roomList;
    private OnRoomClickListener onRoomClickListener;

    public RoomAdapter(Context context, List<Room> roomList, OnRoomClickListener onRoomClickListener) {
        this.context = context;
        this.roomList = roomList;
        this.onRoomClickListener = onRoomClickListener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.roomType.setText(room.getRoomType());
        holder.amenities.setText(room.getFacilities());
        holder.foodOptions.setText(room.getFoodOptions());
        holder.relaxationServices.setText(room.getRelaxationServices());
        holder.businessServices.setText(room.getBusinessServices());
        holder.price.setText(room.getPrice());

        if (room.getRoompic() != null && !room.getRoompic().isEmpty()) {
            Picasso.get().load(room.getRoompic()).into(holder.roomPic);
        }

        holder.bookButton.setOnClickListener(v -> {
            if (onRoomClickListener != null) {
                onRoomClickListener.onRoomClick(room);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public interface OnRoomClickListener {
        void onRoomClick(Room room);
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView roomType, amenities, foodOptions, relaxationServices, businessServices, price;
        ImageView roomPic;
        Button bookButton;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);

            roomType = itemView.findViewById(R.id.txtRoomType);
            amenities = itemView.findViewById(R.id.txtAmenities);
            foodOptions = itemView.findViewById(R.id.txtFoodOptions);
            relaxationServices = itemView.findViewById(R.id.txtRelaxationServices);
            businessServices = itemView.findViewById(R.id.txtBusinessServices);
            price = itemView.findViewById(R.id.txtPrice);
            roomPic = itemView.findViewById(R.id.roomPic);
            bookButton = itemView.findViewById(R.id.btnBookRoom); // Add this button in your XML layout
        }
    }
}

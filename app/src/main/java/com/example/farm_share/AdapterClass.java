package com.example.farm_share;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {

    ArrayList<Equipments> list;
    public AdapterClass(ArrayList<Equipments> list)
    {
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(list.get(i).getTitle());
        myViewHolder.additionalInfo.setText(list.get(i).getAdditionalInfo());
        myViewHolder.contactNumber.setText(list.get(i).getContactNumber());
        myViewHolder.days.setText(list.get(i).getDays());
        myViewHolder.cost.setText(list.get(i).getCost());
        Picasso.get().load(list.get(i).getImage()).into(myViewHolder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,days,cost,additionalInfo,contactNumber;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.Equip_name);
            additionalInfo=itemView.findViewById(R.id.Equip_description);
            contactNumber=itemView.findViewById(R.id.Equip_CNumber);
            cost=itemView.findViewById(R.id.Equip_cost);
            days=itemView.findViewById(R.id.Equip_days);
            image=(ImageView)itemView.findViewById(R.id.Equip_image);
        }
    }
}

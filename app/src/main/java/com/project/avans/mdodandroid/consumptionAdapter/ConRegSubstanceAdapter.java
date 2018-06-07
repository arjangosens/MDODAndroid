package com.project.avans.mdodandroid.consumptionAdapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.object_classes.Substance;

import java.util.ArrayList;

public class ConRegSubstanceAdapter extends
        RecyclerView.Adapter<ConRegSubstanceAdapter.MyViewHolder> {

    private ArrayList<Substance> substances;

    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button substanceButton;

        public MyViewHolder(View view) {
            super(view);
            substanceButton = view.findViewById(R.id.row_consumptionType_button);
        }
    }

    public ConRegSubstanceAdapter(ArrayList<Substance> substances) {
        this.substances = substances;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Substance substance = substances.get(position);
        holder.substanceButton.setText(substance.getType());

        Drawable top = substance.getDrawable();
        holder.substanceButton.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
    }

    @Override
    public int getItemCount() {
        return substances.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_row_consumption_type, parent, false);
        return new MyViewHolder(v);
    }
}
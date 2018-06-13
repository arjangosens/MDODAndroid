package com.project.avans.mdodandroid.adapters.consumptionAdapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.domain.Substance;

import java.util.ArrayList;


public class ConRegSubstanceAdapter extends RecyclerView.Adapter<ConRegSubstanceAdapter.ViewHolder> {


    private final OnItemClickListener listener;
    private static ArrayList<Substance> substances;


    public ConRegSubstanceAdapter(ArrayList<Substance> substances, OnItemClickListener listener) {
        this.listener = listener;
        ConRegSubstanceAdapter.substances = substances;
    }

    public static ArrayList<Substance> getSubstances() {

        return substances;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_consumption_type, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(listener, position, holder);
    }


    @Override
    public int getItemCount() {
        return substances.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private Button substanceBtn;
        private ArrayList<Substance> substances;

        //private ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            this.substanceBtn = (Button) itemView.findViewById(R.id.row_consumptionType_button);
            substances = ConRegSubstanceAdapter.getSubstances();
        }


        public void bind(final OnItemClickListener listener, final int position, final ViewHolder holder) {

            holder.substanceBtn.setClickable(false);

            Substance substance = substances.get(position);

            holder.substanceBtn.setText(substance.getType());

            Drawable top = substance.getDrawable();
//            top.setTint(itemView.getResources().getColor(android.R.color.black));
            holder.substanceBtn.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }

//                        Drawable[] drawables = holder.substanceBtn.getCompoundDrawables();
//                        Drawable wrapDrawable = DrawableCompat.wrap(drawables[1]);
//                        DrawableCompat.setTint(wrapDrawable, itemView.getResources().getColor(R.color.coloraccent));

                }

            });

        }

    }
}



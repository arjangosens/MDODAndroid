package com.project.avans.mdodandroid.consumptionAdapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.object_classes.Substance;

import java.util.ArrayList;


public class ConRegSubstanceAdapter extends RecyclerView.Adapter<ConRegSubstanceAdapter.ViewHolder> {


    private final OnItemClickListener listener;
    private ArrayList<Substance> substances;


    public ConRegSubstanceAdapter(ArrayList<Substance> substances, OnItemClickListener listener) {
        this.listener = listener;
        this.substances = substances;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_consumption_type, parent, false);
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

        //private ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            this.substanceBtn = (Button) itemView.findViewById(R.id.row_consumptionType_button);
        }


        public void bind(final OnItemClickListener listener, final int position, final ViewHolder holder) {

//            if (!cursor.moveToPosition(position)) {
//                return;
//            }


//            String fullName = cursor.getString(cursor.getColumnIndex(MainActivity.EXTRA_FULLNAME));
//            String URL = cursor.getString(cursor.getColumnIndex(MainActivity.EXTRA_PIC_URL));
//            final String id = cursor.getString(cursor.getColumnIndex(MainActivity.EXTRA_ID));
//            final String _id = cursor.getString(cursor.getColumnIndex(MainActivity.EXTRA_IDENTIFIER));


//            holder.textViewList.setText("IMAGE ID: " + id);
//            Picasso.get().load(URL).into(holder.imageViewList);


            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
//                    Intent intent = new Intent(itemView.getContext(), ImageDetailActivity.class);
//                    intent.putExtra(MainActivity.EXTRA_IDENTIFIER, _id);
//                    itemView.getContext().startActivity(intent);
                }

            });

        }

    }
}



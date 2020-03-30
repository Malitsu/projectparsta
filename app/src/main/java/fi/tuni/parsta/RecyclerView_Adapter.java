package fi.tuni.parsta;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Achievement> mData;
    private String cardBackground;

    public RecyclerView_Adapter(Context mContext, List<Achievement> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);

        view = mInflater.inflate(R.layout.card_view_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        if(mData.get(position).isUnlocked()){
            String locked2 = " " + mData.get(position).isUnlocked();
            String imgString = mData.get(position).getUnlockedIcon();
            int resID = mContext.getResources().getIdentifier(imgString , "drawable", mContext.getPackageName());
            holder.item_thumbnail.setImageResource(resID);

            //on click listener
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent newIntent = new Intent(mContext, CardItemContentsActivity.class);
                    newIntent.putExtra("fi.tuni.parsta.achievementIcon", mData.get(position).getUnlockedIcon());
                    newIntent.putExtra("fi.tuni.parsta.achievementDesc", mData.get(position).getLongDesc());

                    mContext.startActivity(newIntent);
                }
            });
        }else{
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView item_thumbnail;
        CardView cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_thumbnail = (ImageView) itemView.findViewById(R.id.achievementIcon);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
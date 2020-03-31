package fi.tuni.parsta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class DotRecyclerView_Adapter extends RecyclerView.Adapter<DotRecyclerView_Adapter.MyDotViewHolder> {
    private Context mContext;
    private List<Dots> mData;
    private String cardBackground;

    public DotRecyclerView_Adapter(Context mContext, List<Dots> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyDotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);

        view = mInflater.inflate(R.layout.gameloop_progress_dot_item, parent, false);

        return new MyDotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDotViewHolder holder, int position) {

        holder.item_thumbnail.setImageResource(mData.get(position).getThumbnail());
//        if(mData.get(position).isUnlocked()){
//            String locked2 = " " + mData.get(position).isUnlocked();
//            String imgString = mData.get(position).getUnlockedIcon();
//            int resID = mContext.getResources().getIdentifier(imgString , "drawable", mContext.getPackageName());
//            holder.item_thumbnail.setImageResource(resID);
//
//            //on click listener
//            holder.cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Intent newIntent = new Intent(mContext, CardItemContentsActivity.class);
//                    newIntent.putExtra("fi.tuni.parsta.achievementIcon", mData.get(position).getUnlockedIcon());
//                    newIntent.putExtra("fi.tuni.parsta.achievementDesc", mData.get(position).getLongDesc());
//
//                    mContext.startActivity(newIntent);
//                }
//            });
//        }else{
//        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyDotViewHolder extends RecyclerView.ViewHolder {

        ImageView item_thumbnail;
        CardView cardView;


        public MyDotViewHolder(@NonNull View itemView) {
            super(itemView);

            item_thumbnail = (ImageView) itemView.findViewById(R.id.gameloopDotIcon);
            cardView = (CardView) itemView.findViewById(R.id.cardview_dot_id);
        }
    }
}

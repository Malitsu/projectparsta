package fi.tuni.parsta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LevelsActivity extends AppCompatActivity {

    List<LevelProgress> levelsList;
    ArrayList<Dots> starList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        levelsList = new ArrayList<LevelProgress>();

        levelsList.add(new LevelProgress(this, 1));
        levelsList.add(new LevelProgress(this, 2));
        levelsList.add(new LevelProgress(this, 3));
        levelsList.add(new LevelProgress(this, 4));

        ListView levelList = (ListView) findViewById(R.id.levelList);

        levelList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final RelativeLayout rl_listViewItem = (RelativeLayout)view.findViewById(R.id.rl_listViewItem);

                View child = getLayoutInflater().inflate(R.layout.listview_item_levels2, null);

                starList = new ArrayList<Dots>();
                starList.add(new Dots(R.drawable.unlocked));
                starList.add(new Dots(R.drawable.unlocked));
                starList.add(new Dots(R.drawable.unlocked));
                starList.add(new Dots(R.drawable.unlocked));
                starList.add(new Dots(R.drawable.unlocked));
                starList.add(new Dots(R.drawable.star_grey));
                starList.add(new Dots(R.drawable.star_grey));
                starList.add(new Dots(R.drawable.star_grey));
                starList.add(new Dots(R.drawable.star_grey));
                starList.add(new Dots(R.drawable.star_grey));

                RecyclerView myrv = (RecyclerView) child.findViewById(R.id.recyclerview_level);
                DotRecyclerView_Adapter myAdapter = new DotRecyclerView_Adapter(getApplicationContext(), starList);
                myrv.setLayoutManager(new GridLayoutManager(getApplicationContext(),10));
                myrv.setAdapter(myAdapter);

                TextView levelInfo = (TextView) child.findViewById(R.id.levelText2);
//                levelInfo.setText("Level " + levelsList.get(position).getCurrentLevel());

                TextView scoreText = (TextView) child.findViewById(R.id.scoreText);
                scoreText.setText(levelsList.get(position).getMaxScore() + "/10");

                Button closeItemBtn = (Button) child.findViewById(R.id.closeItemBtn);
                closeItemBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if(rl_listViewItem.getChildAt(0)!= null){
                            rl_listViewItem.removeAllViews();
                        }
                    }
                });

                Button startLevelBtn = (Button) child.findViewById(R.id.startLevelBtn);
                startLevelBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Log.d("INFLATERI", "JOOJEE");
                    }
                });

                rl_listViewItem.addView(child);
            }
        });

        ListViewAdapter adapter = new ListViewAdapter();

        levelList.setAdapter(adapter);

    }

    public void quitLevels(View v){
        Intent i= new Intent(this, MainActivity.class);
        startActivity(i);
    }

    class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return levelsList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.listview_item_levels, null);

            TextView levelText = (TextView) view.findViewById(R.id.levelText);
//            levelText.setText("Level " + levelsList.get(i).getCurrentLevel());

            return view;
        }
    }
}

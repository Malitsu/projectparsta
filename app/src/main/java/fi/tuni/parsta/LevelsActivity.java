package fi.tuni.parsta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LevelsActivity extends AppCompatActivity {

    List<LevelProgress> levelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        levelsList = new ArrayList<LevelProgress>();

        levelsList.add(new LevelProgress(1, 0));
        levelsList.add(new LevelProgress(2, 0));
        levelsList.add(new LevelProgress(3, 0));
        levelsList.add(new LevelProgress(4, 0));

        ListView levelList = (ListView) findViewById(R.id.levelList);

        levelList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                RelativeLayout rl_listViewItem = (RelativeLayout)view.findViewById(R.id.rl_listViewItem);

                View child = getLayoutInflater().inflate(R.layout.listview_item_levels2, null);
                rl_listViewItem.addView(child);
            }
        });

        ListViewAdapter adapter = new ListViewAdapter();

        levelList.setAdapter(adapter);
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
            levelText.setText("Level " + levelsList.get(i).getCurrentLevel());

            return view;
        }
    }
}

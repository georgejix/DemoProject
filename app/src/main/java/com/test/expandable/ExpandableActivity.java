package com.test.expandable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_expandable)
public class ExpandableActivity extends BaseActivity
{

    ExpandableListView elv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        elv = (ExpandableListView)findViewById(R.id.elv);
        elv.setOnGroupExpandListener(onGroupExpandListenser);
        MyExpandableAdapter adapter = new MyExpandableAdapter(this, getData());
        elv.setAdapter(adapter);
    }

    ExpandableListView.OnGroupExpandListener onGroupExpandListenser = new ExpandableListView.OnGroupExpandListener() {
        int previousGroup =-1;
        @Override
        public void onGroupExpand(int groupPosition) {
            if(groupPosition!= previousGroup)
                elv.collapseGroup(previousGroup);
            previousGroup = groupPosition;
        }
    };

    public List<ParentObject> getData()
    {
        List<ParentObject> parentObjects = new ArrayList<ParentObject>();
        for (int i = 0; i<20; i++)
        {
            parentObjects.add(new ParentObject("Mother " +i, "Father " +i, "Header " + i, "Footer " +i, getChildren(i)));

        }
        return parentObjects;
    }

    private  List<ChildObject> getChildren(int childCount)
    {
        List<ChildObject> childObjects = new ArrayList<ChildObject>();
        for (int i =0; i<childCount; i++)
        {
            childObjects.add(new ChildObject("Child " + (i+1), 10 +i ));
        }
        return childObjects;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

package ru.kozhushko.task_1;

import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

public class ListActivity extends ActionBarActivity{

    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Fragment fragment=new Fragment();
        Bundle args=new Bundle();
        args.putString("JSON",getIntent().getStringExtra("JSON"));
        fragment.setArguments(args);
        Log.d("d","ListActivity OnCreate");
        getFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
    }

}

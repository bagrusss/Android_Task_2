package ru.kozhushko.task_1;

import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


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
        getFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
    }

}

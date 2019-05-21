package com.oultoncollege.licenseplateocr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.oultoncollege.licenseplateocr.data.AppDatabase;

public class LogActivity extends Activity {

    private static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_layout);

        db = AppDatabase.getInstance(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logs, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.date_picker) {
//            Toast.makeText(this, "Debugging date picking", Toast.LENGTH_SHORT).show();
//        }
//
//        return true;
//    }
}

package com.juliodev.nytimessearch.activties;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.juliodev.nytimessearch.DatePickerFragment;
import com.juliodev.nytimessearch.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Setting extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText myDate;

    String sortOrder;
    String art;
    String fashion;
    String sport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Setting");
        //set the top title
        //String title = actionBar.getTitle().toString(); // get the title
        //Display a action bar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_setting_bnt);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        initialisation();
    }

    public void initialisation(){
        myDate = (EditText) findViewById(R.id.edtMydate);
    }

    //public void recupererDate (View view){}

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_older:
                if (checked)
                    sortOrder = "oldest";
                    break;
            case R.id.radio_newest:
                if (checked)
                    sortOrder = "newest";
                    break;
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.art:
                if (checked)
                    art = "Arts";// get art value
                else
                  art = null; // Remove value
                break;
            case R.id.fashion:
                if (checked)
                  fashion="Fashion & Style" ;  //get fashion value
                else
                 fashion= null; // remove fashion value
                break;
            case R.id.sport:
                if (checked)
                  sport = "Sports" ;//get sport value;
                else
                    sport = null;  // remove sport value
                break;
        }
    }

     public void submitFormulaire(View view){

         String olderOrnewest = sortOrder;

         Intent i  = new Intent();
         i.putExtra("beginDate", myDate.getText().toString());
         i.putExtra("art", art);
         i.putExtra("fashion", fashion);
         i.putExtra("sport", sport);
         i.putExtra("sortOrder",sortOrder );

         setResult(RESULT_OK,i);
         finish();
         overridePendingTransition(0,0);
     }
    //gestion de date picker

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        //dateformat


        EditText myDateformat = myDate;
        myDateformat.setText(dateformat.format(c.getTime()));
    }
}

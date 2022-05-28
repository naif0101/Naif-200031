package alfaisal.naif.naif_200031;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class MainActivity4 extends AppCompatActivity {
    EditText idInputSql,firstNameInputSql,fatherNameInputSql,surnameInputSql,nationalIdInputSql;
    RadioButton maleBtnSql, femaleBtnSql;
    RadioGroup radioGroupSql;
    Button insertBtnSql, updateBtnSql, deleteBtnSql, dateBtnSql, searchBtnFire, searchBtnSql, viewAllStudentsSql, goToAct1;

    DatabaseHelper myDB = new DatabaseHelper(this);

    //weather stuff
    Button changeCityAct;
    //weather stuff
    ImageView weatherIconn;
    TextView cityNameTxtt,cityTempTxtt,cityHumidTxtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        idInputSql = (EditText) findViewById(R.id.idInputSql);
        firstNameInputSql = (EditText) findViewById(R.id.firstNameInputSql);
        fatherNameInputSql = (EditText) findViewById(R.id.fatherNameInputSql);
        surnameInputSql = (EditText) findViewById(R.id.surnameInputSql);
        nationalIdInputSql = (EditText) findViewById(R.id.nationalIdInputSql);

        maleBtnSql = (RadioButton)findViewById(R.id.maleBtnSql);
        femaleBtnSql = (RadioButton)findViewById(R.id.femaleBtnSql);
        radioGroupSql = (RadioGroup)findViewById(R.id.rGroupSql);

        dateBtnSql = (Button)findViewById(R.id.dateBtnSql);
        insertBtnSql = (Button)findViewById(R.id.insertSqlBtn);
        updateBtnSql = (Button)findViewById(R.id.updateSqlBtn);
        deleteBtnSql = (Button)findViewById(R.id.deleteSqlBtn);
        searchBtnFire = (Button)findViewById(R.id.searchFireBtn);
        searchBtnSql = (Button)findViewById(R.id.searchSqlBtn);
        viewAllStudentsSql = (Button)findViewById(R.id.viewAllSqlStudents);
        goToAct1 = (Button)findViewById(R.id.goToAct1);

        dateBtnSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity4.this, d,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        insertBtnSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(idInputSql)||isEmpty(firstNameInputSql)||isEmpty(fatherNameInputSql)||isEmpty(surnameInputSql)||
                        isEmpty(nationalIdInputSql)){
                    Log.d("Naif insert","Please insert all fields");
                    Toasty.error(getBaseContext(), "Please insert all fields.", Toast.LENGTH_LONG, true).show();
                } else if(dateBtnSql.getText().equals("Pick Date")){
                    Log.d("Naif insert","Please pick the date of birth");
                    Toasty.error(getBaseContext(), "Please pick the date of birth.", Toast.LENGTH_LONG, true).show();
                } else {
                    if (maleBtnSql.isChecked()) {
                        if(!myDB.addData(idInputSql.getText().toString(), firstNameInputSql.getText().toString(), fatherNameInputSql.getText().toString(),
                                surnameInputSql.getText().toString(), nationalIdInputSql.getText().toString(), dateBtnSql.getText().toString(), "Male")){
                            Log.d("Naif insert",idInputSql.getText().toString()+" already exists");
                            Toasty.error(getBaseContext(), idInputSql.getText().toString()+" already exists.", Toast.LENGTH_LONG, true).show();
                        } else {
                            Log.d("Naif insert",firstNameInputSql.getText().toString()+" added.");
                            Toasty.success(getBaseContext(), firstNameInputSql.getText().toString()+" added successfully.", Toast.LENGTH_LONG, true).show();

                            idInputSql.getText().clear(); firstNameInputSql.getText().clear(); fatherNameInputSql.getText().clear(); surnameInputSql.getText().clear();
                            nationalIdInputSql.getText().clear(); dateBtnSql.setText("Pick Date"); radioGroupSql.clearCheck();
                        }
                    } else if (femaleBtnSql.isChecked()) {
                        if(!myDB.addData(idInputSql.getText().toString(), firstNameInputSql.getText().toString(), fatherNameInputSql.getText().toString(),
                                surnameInputSql.getText().toString(), nationalIdInputSql.getText().toString(), dateBtnSql.getText().toString(), "Female")){
                            Log.d("Naif insert",idInputSql.getText().toString()+" already exists");
                            Toasty.error(getBaseContext(), idInputSql.getText().toString()+" already exists.", Toast.LENGTH_LONG, true).show();
                        } else {
                            Log.d("Naif insert",firstNameInputSql.getText().toString()+" added.");
                            Toasty.success(getBaseContext(), firstNameInputSql.getText().toString()+" added successfully.", Toast.LENGTH_LONG, true).show();

                            idInputSql.getText().clear(); firstNameInputSql.getText().clear(); fatherNameInputSql.getText().clear(); surnameInputSql.getText().clear();
                            nationalIdInputSql.getText().clear(); dateBtnSql.setText("Pick Date"); radioGroupSql.clearCheck();
                        }
                    } else {
                        Log.d("Naif insert","Please specify the gender");
                        Toasty.error(getBaseContext(), "Please specify the gender.", Toast.LENGTH_LONG, true).show();
                    }
                }
            }
        });

        updateBtnSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(idInputSql) || isEmpty(firstNameInputSql) || isEmpty(fatherNameInputSql) || isEmpty(surnameInputSql) ||
                        isEmpty(nationalIdInputSql)) {
                    Log.d("Naif update", "Please insert all fields");
                    Toasty.error(getBaseContext(), "Please insert all fields.", Toast.LENGTH_LONG, true).show();
                } else if (dateBtnSql.getText().equals("Pick Date")) {
                    Log.d("Naif update", "Please pick the date of birth");
                    Toasty.error(getBaseContext(), "Please pick the date of birth.", Toast.LENGTH_LONG, true).show();
                } else {
                    if (maleBtnSql.isChecked()) {
                        if(!myDB.updateSpecific(idInputSql.getText().toString(), firstNameInputSql.getText().toString(), fatherNameInputSql.getText().toString(),
                                surnameInputSql.getText().toString(), nationalIdInputSql.getText().toString(), dateBtnSql.getText().toString(), "Male")){
                            Log.d("Naif update", idInputSql.getText().toString()+" does not exists.");
                            Toasty.error(getBaseContext(), idInputSql.getText().toString()+" does not exists.", Toast.LENGTH_LONG, true).show();
                        } else {
                            Log.d("Naif update", idInputSql.getText().toString()+" updated successfully.");
                            Toasty.success(getBaseContext(), idInputSql.getText().toString()+" updated successfully.", Toast.LENGTH_LONG, true).show();
                        }
                    } else if (femaleBtnSql.isChecked()) {
                        if(!myDB.updateSpecific(idInputSql.getText().toString(), firstNameInputSql.getText().toString(), fatherNameInputSql.getText().toString(),
                                surnameInputSql.getText().toString(), nationalIdInputSql.getText().toString(), dateBtnSql.getText().toString(), "Female")){
                            Log.d("Naif update", idInputSql.getText().toString()+" does not exists.");
                            Toasty.error(getBaseContext(), idInputSql.getText().toString()+" does not exists.", Toast.LENGTH_LONG, true).show();
                        } else {
                            Log.d("Naif update", idInputSql.getText().toString()+" updated successfully.");
                            Toasty.success(getBaseContext(), idInputSql.getText().toString()+" updated successfully.", Toast.LENGTH_LONG, true).show();
                        }
                    }
                }
            }
        });

        deleteBtnSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(idInputSql)){
                    Log.d("Naif delete", "Please insert ID to delete");
                    Toasty.error(getBaseContext(), "Please insert ID to delete.", Toast.LENGTH_LONG, true).show();
                } else {
                    if(myDB.deleteSpecific(idInputSql.getText().toString())){
                        Log.d("Naif delete",idInputSql.getText().toString()+" deleted successfully.");
                        Toasty.success(getBaseContext(), idInputSql.getText().toString()+" deleted successfully.", Toast.LENGTH_LONG, true).show();

                        idInputSql.getText().clear(); firstNameInputSql.getText().clear(); fatherNameInputSql.getText().clear(); surnameInputSql.getText().clear();
                        nationalIdInputSql.getText().clear(); dateBtnSql.setText("Pick Date"); radioGroupSql.clearCheck();
                    } else {
                        Log.d("Naif delete",idInputSql.getText().toString()+" does not exist.");
                        Toasty.error(getBaseContext(), idInputSql.getText().toString()+" does not exist.", Toast.LENGTH_LONG, true).show();
                    }
                }
            }
        });

        searchBtnFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(idInputSql)) {
                    Log.d("Naif search", "Please insert ID to search");
                    Toasty.error(getBaseContext(), "Please insert ID to search", Toast.LENGTH_LONG, true).show();
                } else {
                    MainActivity.searchStudentF(idInputSql.getText().toString(), getBaseContext(), firstNameInputSql, fatherNameInputSql, surnameInputSql, nationalIdInputSql, dateBtnSql, radioGroupSql, maleBtnSql, femaleBtnSql);
                }
            }
        });

        searchBtnSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(idInputSql)) {
                    Log.d("Naif search", "Please insert ID to search");
                    Toasty.error(getBaseContext(), "Please insert ID to search.", Toast.LENGTH_LONG, true).show();
                } else {
                    Cursor data = myDB.getSpecific(idInputSql.getText().toString());
                    if (data != null) {
                        data.moveToFirst();
                        idInputSql.setText(data.getString(0));
                        firstNameInputSql.setText(data.getString(1));
                        fatherNameInputSql.setText(data.getString(2));
                        surnameInputSql.setText(data.getString(3));
                        nationalIdInputSql.setText(data.getString(4));
                        dateBtnSql.setText(data.getString(5));
                        if (data.getString(6).equals("Male")){
                            radioGroupSql.clearCheck();
                            maleBtnSql.toggle();
                        } else if (data.getString(6).equals("Female")){
                            radioGroupSql.clearCheck();
                            femaleBtnSql.toggle();
                        }
                        Toasty.success(getBaseContext(), data.getString(1)+" info obtained successfully.", Toast.LENGTH_LONG, true).show();
                    }
                    else {
                        Log.d("Naif search",idInputSql.getText().toString()+" does not exist.");
                        Toasty.error(getBaseContext(), idInputSql.getText().toString()+" does not exist.", Toast.LENGTH_LONG, true).show();
                    }
                }
            }
        });

        viewAllStudentsSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this,MainActivity5.class));
            }
        });

        goToAct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this,MainActivity.class));
            }
        });

        changeCityAct = (Button)findViewById(R.id.changeCityAct);

        changeCityAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this,MainActivity3.class));
            }
        });

        // weather stuff
        weatherIconn = (ImageView)findViewById(R.id.weatherIcon);

        cityNameTxtt = (TextView)findViewById(R.id.cityNameTxt);
        cityTempTxtt = (TextView)findViewById(R.id.cityTempTxt);
        cityHumidTxtt = (TextView)findViewById(R.id.cityHumidTxt);

        MainActivity3.weather("https://api.openweathermap.org/data/2.5/weather?q="+MainActivity.city+"&appid=8de97d4e16d0d57d860cdb3a0b7a93d3&units=metric",cityNameTxtt,cityTempTxtt,cityHumidTxtt, weatherIconn, MainActivity4.this);


        changeCityAct = (Button)findViewById(R.id.changeCityAct);

        changeCityAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this,MainActivity3.class));
            }
        });


    }
    Calendar c = Calendar.getInstance();
    DateFormat fmtDate = DateFormat.getDateInstance();

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateBtnSql.setText(fmtDate.format(c.getTime()));
        }
    };

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().length() == 0;
    }
}
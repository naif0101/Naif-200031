package alfaisal.naif.naif_200031;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    EditText idInput,firstNameInput,fatherNameInput,surnameInput,nationalIdInput;
    RadioButton maleBtn, femaleBtn;
    RadioGroup radioGroup;
    Button insertBtn, updateBtn, deleteBtn, dateBtn, searchBtn, viewAllStudents, goToSql;

    // Connecting To database
    final static FirebaseDatabase database = FirebaseDatabase.getInstance("https://naif-200031-default-rtdb.asia-southeast1.firebasedatabase.app/");
    final static DatabaseReference myRef = database.getReference("students");

    //weather stuff
    Button changeCityAct;

    //weather stuff
    ImageView weatherIconn;
    TextView cityNameTxtt,cityTempTxtt,cityHumidTxtt;
    static String city = "berlin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idInput = (EditText) findViewById(R.id.idInput);
        firstNameInput = (EditText) findViewById(R.id.firstNameInput);
        fatherNameInput = (EditText) findViewById(R.id.fatherNameInput);
        surnameInput = (EditText) findViewById(R.id.surnameInput);
        nationalIdInput = (EditText) findViewById(R.id.nationalIdInput);

        maleBtn = (RadioButton)findViewById(R.id.maleBtn);
        femaleBtn = (RadioButton)findViewById(R.id.femaleBtn);
        radioGroup = (RadioGroup)findViewById(R.id.rGroup);

        dateBtn = (Button)findViewById(R.id.dateBtn);
        insertBtn = (Button)findViewById(R.id.insertBtn);
        updateBtn = (Button)findViewById(R.id.updateBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        searchBtn = (Button)findViewById(R.id.searchBtn);
        viewAllStudents = (Button)findViewById(R.id.viewAllStudents);
        goToSql = (Button)findViewById(R.id.goToSql);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, d,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(idInput)||isEmpty(firstNameInput)||isEmpty(fatherNameInput)||isEmpty(surnameInput)||
                        isEmpty(nationalIdInput)){
                    Log.d("Naif insert","Please insert all fields");
                    Toasty.error(getBaseContext(), "Please insert all fields.", Toast.LENGTH_LONG, true).show();
                } else if(dateBtn.getText().equals("Pick Date")){
                    Log.d("Naif insert","Please pick the date of birth");
                    Toasty.error(getBaseContext(), "Please pick the date of birth.", Toast.LENGTH_LONG, true).show();
                } else {
                    if (maleBtn.isChecked()) {
                        insertStudent(idInput.getText().toString(), firstNameInput.getText().toString(), fatherNameInput.getText().toString(),
                                surnameInput.getText().toString(), nationalIdInput.getText().toString(), dateBtn.getText().toString(), "Male", getBaseContext(), idInput, firstNameInput, fatherNameInput, surnameInput, nationalIdInput, dateBtn, radioGroup);
                    } else if (femaleBtn.isChecked()) {
                        insertStudent(idInput.getText().toString(), firstNameInput.getText().toString(), fatherNameInput.getText().toString(),
                                surnameInput.getText().toString(), nationalIdInput.getText().toString(), dateBtn.getText().toString(), "Female", getBaseContext(), idInput, firstNameInput, fatherNameInput, surnameInput, nationalIdInput, dateBtn, radioGroup);
                    } else {
                        Log.d("Naif insert","Please specify the gender");
                        Toasty.error(getBaseContext(), "Please specify the gender.", Toast.LENGTH_LONG, true).show();
                    }
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(idInput) || isEmpty(firstNameInput) || isEmpty(fatherNameInput) || isEmpty(surnameInput) ||
                        isEmpty(nationalIdInput)) {
                    Log.d("Naif update", "Please insert all fields");
                    Toasty.error(getBaseContext(), "Please insert all fields.", Toast.LENGTH_LONG, true).show();
                } else if (dateBtn.getText().equals("Pick Date")) {
                    Log.d("Naif update", "Please pick the date of birth");
                    Toasty.error(getBaseContext(), "Please pick the date of birth.", Toast.LENGTH_LONG, true).show();
                } else {
                    if (maleBtn.isChecked()) {
                        updateStudent(idInput.getText().toString(), firstNameInput.getText().toString(), fatherNameInput.getText().toString(),
                                surnameInput.getText().toString(), nationalIdInput.getText().toString(), dateBtn.getText().toString(), "Male", getBaseContext());
                    } else if (femaleBtn.isChecked()) {
                        updateStudent(idInput.getText().toString(), firstNameInput.getText().toString(), fatherNameInput.getText().toString(),
                                surnameInput.getText().toString(), nationalIdInput.getText().toString(), dateBtn.getText().toString(), "Female", getBaseContext());
                    }
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(idInput)){
                    Log.d("Naif delete", "Please insert ID to delete");
                    Toasty.error(getBaseContext(), "Please insert ID to delete.", Toast.LENGTH_LONG, true).show();
                } else {
                    deleteStudent(idInput.getText().toString(), getBaseContext(), idInput, firstNameInput, fatherNameInput, surnameInput, nationalIdInput, dateBtn, radioGroup);
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(idInput)) {
                    Log.d("Naif search", "Please insert ID to search");
                    Toasty.error(getBaseContext(), "Please insert ID to search.", Toast.LENGTH_LONG, true).show();
                } else {
                    searchStudentF(idInput.getText().toString(), getBaseContext(), firstNameInput, fatherNameInput, surnameInput, nationalIdInput, dateBtn, radioGroup, maleBtn, femaleBtn);
                }
            }
        });

        viewAllStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });



        // weather stuff
        weatherIconn = (ImageView)findViewById(R.id.weatherIcon);

        cityNameTxtt = (TextView)findViewById(R.id.cityNameTxt);
        cityTempTxtt = (TextView)findViewById(R.id.cityTempTxt);
        cityHumidTxtt = (TextView)findViewById(R.id.cityHumidTxt);

        MainActivity3.weather("https://api.openweathermap.org/data/2.5/weather?q="+MainActivity.city+"&appid=8de97d4e16d0d57d860cdb3a0b7a93d3&units=metric",cityNameTxtt,cityTempTxtt,cityHumidTxtt, weatherIconn, MainActivity.this);


        changeCityAct = (Button)findViewById(R.id.changeCityAct);

        changeCityAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity3.class));
            }
        });

        goToSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity4.class));
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
            dateBtn.setText(fmtDate.format(c.getTime()));
        }
    };

    private static void insertStudent(String student_ID, String first_Name, String father_Name, String surname, String national_ID, String date_of_birth, String gender, Context context, EditText idInput, EditText firstNameInput, EditText fatherNameInput, EditText surnameInput, EditText nationalIdInput, Button dateBtn,
                               RadioGroup radioGroup) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(student_ID)) {
                    Log.d("Naif insert",student_ID+" already exists.");
                    Toasty.error(context, student_ID+" already exists.", Toast.LENGTH_LONG, true).show();
                } else {
                    myRef.child(student_ID).setValue(new Student(student_ID, first_Name, father_Name, surname, national_ID, date_of_birth, gender));
                    Log.d("Naif insert",first_Name+" added.");
                    Toasty.success(context, first_Name+" added.", Toast.LENGTH_LONG, true).show();

                    idInput.getText().clear(); firstNameInput.getText().clear(); fatherNameInput.getText().clear(); surnameInput.getText().clear();
                    nationalIdInput.getText().clear(); dateBtn.setText("Pick Date"); radioGroup.clearCheck();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Naif","Failed to read value "+error.toException());
                Toasty.error(context, "Failed to read value "+error.toException(), Toast.LENGTH_LONG, true).show();
            }
        });
    }

    private static void updateStudent(String student_ID, String first_Name, String father_Name, String surname, String national_ID, String date_of_birth, String gender, Context context){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(student_ID)) {
                    myRef.child(student_ID).setValue(new Student(student_ID, first_Name, father_Name, surname, national_ID, date_of_birth, gender));
                    Log.d("Naif update",student_ID+" updated successfully.");
                    Toasty.success(context, student_ID+" updated successfully.", Toast.LENGTH_LONG, true).show();
                } else {
                    Log.d("Naif update",student_ID+" does not exist.");
                    Toasty.error(context, student_ID+" does not exist.", Toast.LENGTH_LONG, true).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Naif","Failed to read value "+error.toException());
                Toasty.error(context, "Failed to read value "+error.toException(), Toast.LENGTH_LONG, true).show();
            }
        });
    }

    private static void deleteStudent(String student_ID, Context context, EditText idInput, EditText firstNameInput, EditText fatherNameInput, EditText surnameInput, EditText nationalIdInput, Button dateBtn,
                               RadioGroup radioGroup){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(student_ID)) {
                    myRef.child(student_ID).removeValue();
                    Log.d("Naif delete",student_ID+" deleted successfully.");
                    Toasty.success(context, student_ID+" deleted successfully.", Toast.LENGTH_LONG, true).show();

                    idInput.getText().clear(); firstNameInput.getText().clear(); fatherNameInput.getText().clear(); surnameInput.getText().clear();
                    nationalIdInput.getText().clear(); dateBtn.setText("Pick Date"); radioGroup.clearCheck();
                } else {
                    Log.d("Naif delete",student_ID+" does not exist.");
                    Toasty.error(context, student_ID+" does not exist.", Toast.LENGTH_LONG, true).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Naif","Failed to read value "+error.toException());
                Toasty.error(context, "Failed to read value "+error.toException(), Toast.LENGTH_LONG, true).show();
            }
        });
    }

    protected static void searchStudentF(String student_ID, Context context, EditText firstNameInput, EditText fatherNameInput, EditText surnameInput, EditText nationalIdInput, Button dateBtn,
                                         RadioGroup radioGroup, RadioButton maleBtn, RadioButton femaleBtn){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(student_ID)) {
                    firstNameInput.setText(String.valueOf(snapshot.child(student_ID).child("first_Name").getValue()));
                    fatherNameInput.setText(String.valueOf(snapshot.child(student_ID).child("father_Name").getValue()));
                    surnameInput.setText(String.valueOf(snapshot.child(student_ID).child("surname").getValue()));
                    nationalIdInput.setText(String.valueOf(snapshot.child(student_ID).child("national_ID").getValue()));
                    dateBtn.setText(String.valueOf(snapshot.child(student_ID).child("date_of_birth").getValue()));
                    if (String.valueOf(snapshot.child(student_ID).child("gender").getValue()).equals("Male")){
                        radioGroup.clearCheck();
                        maleBtn.toggle();
                    } else if (String.valueOf(snapshot.child(student_ID).child("gender").getValue()).equals("Female")){
                        radioGroup.clearCheck();
                        femaleBtn.toggle();
                    }
                    Toasty.success(context, student_ID+" info obtained successfully.", Toast.LENGTH_LONG, true).show();
                } else {
                    Log.d("Naif search",student_ID+" does not exist.");
                    Toasty.error(context, student_ID+" does not exist.", Toast.LENGTH_LONG, true).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Naif","Failed to read value "+error.toException());
                Toasty.error(context, "Failed to read value "+error.toException(), Toast.LENGTH_LONG, true).show();
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().length() == 0;
    }
}
package alfaisal.naif.naif_200031;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class MainActivity2 extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    DatabaseReference myRef2 = MainActivity.database.getReference("students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = (ListView) findViewById(R.id.list);

        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };

        listView.setAdapter(arrayAdapter);

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Student student = postSnapshot.getValue(Student.class);
                    arrayList.add(
                            "\nID:                           "+student.getStudent_ID()+"\n"+
                                    "Name:                    "+student.getFirst_Name()+"\n"+
                                    "Father's Name:     "+student.getFather_Name()+"\n"+
                                    "Surname:               "+student.getSurname()+"\n"+
                                    "National ID:           "+student.getNational_ID()+"\n"+
                                    "Date of Birth:         "+student.getDate_of_birth()+"\n"+
                                    "Gender:                   "+student.getGender()+"\n"
                    );
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Naif","Failed to read value "+error.toException());
                Toasty.error(getBaseContext(), "Failed to read value "+error.toException(), Toast.LENGTH_LONG, true).show();
            }
        });
    }
}
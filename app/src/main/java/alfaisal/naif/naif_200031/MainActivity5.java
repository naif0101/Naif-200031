package alfaisal.naif.naif_200031;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class MainActivity5 extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    DatabaseHelper myDB = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

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

        ArrayList<Student> nameSurname = new ArrayList<Student>();

        Cursor cur = myDB.ViewList();
        while (cur.moveToNext()) {
            Student student = new Student(cur.getString(0),cur.getString(1),cur.getString(2),
                    cur.getString(3),cur.getString(4),cur.getString(5),cur.getString(6));

            arrayList.add(
                            "\nID:                           "+student.getStudent_ID()+"\n"+
                            "Name:                    "+student.getFirst_Name()+"\n"+
                            "Father's Name:     "+student.getFather_Name()+"\n"+
                            "Surname:               "+student.getSurname()+"\n"+
                            "National ID:           "+student.getNational_ID()+"\n"+
                            "Date of Birth:         "+student.getDate_of_birth()+"\n"+
                            "Gender:                   "+student.getGender()+"\n"
            );
            //arrayAdapter.notifyDataSetChanged();

            nameSurname.add(new Student(cur.getString(0),cur.getString(1),cur.getString(2),
                    cur.getString(3),cur.getString(4),cur.getString(5),cur.getString(6)));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i< listView.getChildCount(); i++){
                    if(position == i){
                        Toasty.info(getBaseContext(), nameSurname.get(i).getFirst_Name()+" "+nameSurname.get(i).getSurname(), Toast.LENGTH_LONG, true).show();
                    }
                }
            }
        });
    }
}
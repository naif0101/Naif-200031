package alfaisal.naif.naif_200031;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import es.dmoral.toasty.Toasty;

public class MainActivity3 extends AppCompatActivity {
    Button changeCityBtn, goToActOne;
    EditText cityInput;

    //weather stuff
    ImageView weatherIconn;
    TextView cityNameTxtt,cityTempTxtt,cityHumidTxtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        changeCityBtn = (Button)findViewById(R.id.changeCityBtn);
        cityInput = (EditText)findViewById(R.id.cityInput) ;
        goToActOne = (Button)findViewById(R.id.goToActOne);

        // weather stuff
        weatherIconn = (ImageView)findViewById(R.id.weatherIcon);

        cityNameTxtt = (TextView)findViewById(R.id.cityNameTxt);
        cityTempTxtt = (TextView)findViewById(R.id.cityTempTxt);
        cityHumidTxtt = (TextView)findViewById(R.id.cityHumidTxt);

        weather("https://api.openweathermap.org/data/2.5/weather?q="+MainActivity.city+"&appid=8de97d4e16d0d57d860cdb3a0b7a93d3&units=metric",cityNameTxtt,cityTempTxtt,cityHumidTxtt, weatherIconn, MainActivity3.this);

        changeCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.city = cityInput.getText().toString();
                weather("https://api.openweathermap.org/data/2.5/weather?q="+MainActivity.city+"&appid=8de97d4e16d0d57d860cdb3a0b7a93d3&units=metric",cityNameTxtt,cityTempTxtt,cityHumidTxtt, weatherIconn, MainActivity3.this);
            }
        });

        goToActOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity3.this,MainActivity.class));
            }
        });
    }
    public static void weather(String url, TextView cityNameTxt, TextView cityTempTxt, TextView cityHumidTxt, ImageView weatherIcon, Context context) {
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Naif ","Response received");
                Log.d("Naif ",response.toString());
                try {
                    String town = response.getString("name");
                    Log.d("Naif-town ",town);
                    cityNameTxt.setText(town);
                    // nested object
                    JSONObject jsonMain = response.getJSONObject("main");
                    //
                    double temp = jsonMain.getDouble("temp");
                    DecimalFormat df = new DecimalFormat("##");
                    Log.d("Naif-temp ",String.valueOf(df.format(temp)+" °C"));
                    cityTempTxt.setText(String.valueOf(df.format(temp)+" °C"));

                    String humid = jsonMain.getString("humidity");
                    Log.d("Naif-humidity ",String.valueOf(humid));
                    cityHumidTxt.setText(String.valueOf(humid)+"%");

                    /* sub categories as JSON arrays */
                    JSONArray jsonArray = response.getJSONArray("weather");
                    for (int i=0; i < jsonArray.length(); i++){
                        Log.d("Naif-array ",jsonArray.getString(i));
                        JSONObject oneObject = jsonArray.getJSONObject(i);
                        String weather = oneObject.getString("main");
                        Log.d("Naif-w ",weather);

                        if (weather.equals("Clouds")){
                            Glide.with(context)
                                    .load("https://help.apple.com/assets/6222428998C2CE34C75A5252/6222428B98C2CE34C75A5267/en_US/b83a352b1228b6e4c2f29e916941654e.png")
                                    .into(weatherIcon);
                        }
                        else if (weather.equals("Clear")){
                            Glide.with(context)
                                    .load("https://help.apple.com/assets/6222428998C2CE34C75A5252/6222428B98C2CE34C75A5267/en_US/35c778528f79f2aa038b07526447f606.png")
                                    .into(weatherIcon);
                        }
                        else if (weather.equals("Dust")){
                            Glide.with(context)
                                    .load("https://help.apple.com/assets/6222428998C2CE34C75A5252/6222428B98C2CE34C75A5267/en_US/6e9f3624353a4f884357af89f49d0b39.png")
                                    .into(weatherIcon);
                        }
                        else if (weather.equals("Rain")){
                            Glide.with(context)
                                    .load("https://help.apple.com/assets/6222428998C2CE34C75A5252/6222428B98C2CE34C75A5267/en_US/6816842292a58d78e7586a48b672e45e.png")
                                    .into(weatherIcon);
                        }
                        else {
                            Glide.with(context)
                                    .load("https://help.apple.com/assets/6222428998C2CE34C75A5252/6222428B98C2CE34C75A5267/en_US/267eb826f049db26cb9f083e8f8919bb.png")
                                    .into(weatherIcon);
                        }
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                    Log.d("Receive Error ",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Naif", "Error retrieving URL "+error.toString());
                Toast.makeText(context, "ERROR! City Info Not Found.",Toast.LENGTH_LONG).show();
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObj);
    }
}
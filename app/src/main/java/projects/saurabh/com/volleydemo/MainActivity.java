package projects.saurabh.com.volleydemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // URL of object to be parsed
    String JsonObjectURL = "https://raw.githubusercontent.com/ianbar20/JSON-Volley-Tutorial/master/Example-JSON-Files/Example-Object.JSON";
    // This string will hold the results
    String JsonURL = "https://raw.githubusercontent.com/ianbar20/JSON-Volley-Tutorial/master/Example-JSON-Files/Example-Array.JSON";
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching JSON!!!");
        progressDialog.show();
        progressDialog.setCancelable(true);


//        JSONObjectRequest();
        JSONArrayRequest();
    }

    public void JSONObjectRequest() {
        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonObjectURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Hello", String.valueOf(response));
                            JSONObject obj = response.getJSONObject("colorObject");
                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            String color = obj.getString("colorName");
                            String desc = obj.getString("description");

                            // Adds strings from object to the "data" string
                            data += "Color Name: " + color +
                                    "nDescription : " + desc;

                            // Adds the data string to the TextView "results"
                            Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG)
                                    .show();

                            progressDialog.dismiss();
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        progressDialog.dismiss();
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }

    public void JSONArrayRequest(){


        // Creating the JsonArrayRequest class called arrayreq, passing the required parameters
        //JsonURL is the URL to be fetched from
        JsonArrayRequest arrayreq = new JsonArrayRequest(JsonURL,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter
                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Retrieves first JSON object in outer array
                            JSONObject colorObj = response.getJSONObject(0);
                            // Retrieves "colorArray" from the JSON object
                            JSONArray colorArry = colorObj.getJSONArray("colorArray");
                            // Iterates through the JSON Array getting objects and adding them
                            //to the list view until there are no more objects in colorArray
                            for (int i = 0; i < colorArry.length(); i++) {
                                //gets each JSON object within the JSON array
                                JSONObject jsonObject = colorArry.getJSONObject(i);

                                // Retrieves the string labeled "colorName" and "hexValue",
                                // and converts them into javascript objects
                                String color = jsonObject.getString("colorName");
                                String hex = jsonObject.getString("hexValue");

                                // Adds strings from the current object to the data string
                                //spacing is included at the end to separate the results from
                                //one another
                                data += "Color Number " + (i + 1) + "nColor Name: " + color +
                                        "nHex Value : " + hex + "nnn";
                            }
                            // Adds the data string to the TextView "results"
//                            results.setText(data);
                            Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG)
                                    .show();
                            progressDialog.dismiss();
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        progressDialog.dismiss();
                    }
                }
        );
        // Adds the JSON array request "arrayreq" to the request queue
        requestQueue.add(arrayreq);
    }
}
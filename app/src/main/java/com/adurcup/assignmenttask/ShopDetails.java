package com.adurcup.assignmenttask;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by om on 5/9/2016.
 */
public class ShopDetails extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText editTextId;
    private Button buttonGet;
    private TextView textViewResult;
    Spinner spinnerOsversions;
    String selState;
    String new_id;
    private ProgressDialog loading;

    private String[] state = { "Select-Shop", "Shop-1", "Shop-2", "Shop-3", "Shop-4"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_details, container, false);
        getActivity().setTitle("Shops-Details");
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();


        buttonGet = (Button) rootView.findViewById(R.id.buttonGet);
        textViewResult = (TextView) rootView.findViewById(R.id.textViewResult);
        spinnerOsversions = (Spinner)rootView.findViewById(R.id.osversions);

        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, state);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOsversions.setAdapter(adapter_state);
        spinnerOsversions.setOnItemSelectedListener(this);




        buttonGet.setOnClickListener(this);

return rootView;
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        spinnerOsversions.setSelection(position);
        selState = (String) spinnerOsversions.getSelectedItem();

        if (selState == "Shop-1") {
            new_id = "1";

        } else if(selState == "Shop-2") {
            new_id = "2";

        }else if(selState == "Shop-3") {
            new_id = "3";

        }else if(selState == "Shop-4") {
            new_id = "4";

        }else if(selState == "Select-Shop"){
            new_id = "Select-Shop";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void getData() {
        String id = new_id;
        if (id.equals("Select-Shop")) {
            Toast.makeText(getActivity(), "Please Select a shop", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);

        String url = Config.DATA_URL+id;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String name="";
        String type="";
        String city = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(Config.KEY_NAME);
            type = collegeData.getString(Config.KEY_ADDRESS);
            city = collegeData.getString(Config.KEY_VC);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textViewResult.setText("Name:\t" + name + "\nType:\t" + type + "\nCity:\t"+ city);
    }

    @Override
    public void onClick(View v) {
        getData();
    }

}
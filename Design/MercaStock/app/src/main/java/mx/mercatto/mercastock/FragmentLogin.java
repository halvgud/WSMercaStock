package mx.mercatto.mercastock;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragmentLogin extends Fragment implements View.OnClickListener {
    public static final String ARG_ARTICLES_NUMBER = "articles_number";

    private static final String TAG_ID = "idSucursal";
    private static final String TAG_NAME = "nombre";
    private static final String TAG_DATA = "datos";
    private static final String MAP_API_URL = "http://192.168.1.56/wsMercaStock/sucursal";
    private static final String MAP_API_LOGIN = "http://192.168.1.56/wsMercaStock/usuario/login";
    private static final String TAG_USERNAME = "";
    private static final String TAG_PASSWORD="";
    public static String ClaveApi = "";
    private BackGroundTask bgt;

    EditText txtusuario;
    EditText txtpassword;
    ArrayList<ListaSucursal> countryList = new ArrayList<ListaSucursal>();
    public FragmentLogin() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);
//        int i = getArguments().getInt(ARG_ARTICLES_NUMBER);
       // String article = getResources().getStringArray(R.array.Tags)[i];

      //  getActivity().setTitle(article);
   //     TextView headline = (TextView) rootView.findViewById(R.id.headline);

     //   rootView = inflater.inflate(R.layout.fragment_article, container, false);
         Button upButton = (Button) rootView.findViewById(R.id.button2);
        upButton.setOnClickListener(this);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String auth_token_string = settings.getString("ClaveApi", ""/*default value*/);
        if (auth_token_string!=""){
            //Intent intent = new Intent(this, .class);
            // this.startActivity(intent);
            cargarListadoSucursal(rootView);
            txtusuario = (EditText) rootView.findViewById(R.id.editText);
            txtpassword = (EditText) rootView.findViewById(R.id.editText2);
        }
        else {

            //setContentView(R.layout.activity_login);
            //setTitle("MercaStock");
            cargarListadoSucursal(rootView);
            txtusuario = (EditText) rootView.findViewById(R.id.editText);
            txtpassword = (EditText) rootView.findViewById(R.id.editText2);
        }
        return rootView;
    }

    public void cargarListadoSucursal(View rootView) {
        // Building post parameters, key and value pair
        List<NameValuePair> apiParams = new ArrayList<NameValuePair>(1);
        apiParams.add(new BasicNameValuePair("call", "countrylist"));

        bgt = new BackGroundTask(MAP_API_URL, "GET", null,getActivity(),2);
        bgt.execute();


            // bind adapter to spinner


    }

    @Override
    public void onClick(View v) {
        String usuario = txtusuario.getText().toString();
        String password = txtpassword.getText().toString();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        //String auth_token_string = settings.getString("ClaveApi", ""/*default value*/);

        SharedPreferences.Editor editor = settings.edit();
        try {
            JSONObject jsonObj1 = new JSONObject();
            jsonObj1.put("usuario", usuario);
            jsonObj1.put("contrasena", password);
            // Create the POST object and add the parameters
            bgt = new BackGroundTask(MAP_API_LOGIN, "POST", jsonObj1,getActivity(),1);
            bgt.execute();
        } catch(JSONException e){
            showToast(e.toString());
        }catch (Exception e){
            showToast(e.toString());
        }
    }



    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
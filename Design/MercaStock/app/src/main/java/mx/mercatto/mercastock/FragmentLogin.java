package mx.mercatto.mercastock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

<<<<<<< HEAD


=======
import mx.mercatto.mercastock.BGT.BGTCargarSucursal;
import mx.mercatto.mercastock.BGT.BGTLogIn;
>>>>>>> origin/master
public class FragmentLogin extends Fragment implements View.OnClickListener {
    private BGTLogIn bgt;
    private BGTCargarSucursal bgtSucursal;
    TextView txSucursal;


    EditText txtUsuario;
    EditText txtPassword;
    String id_sucursal;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);

        getActivity().setTitle("MercaStock");
        Button upButton = (Button) rootView.findViewById(R.id.button2);
        upButton.setOnClickListener(this);

        Main.idSesion=0;
        Main.controlUsuario=-1;

        txSucursal=(TextView) rootView.findViewById(R.id.textView13);

       SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
      SharedPreferences.Editor editor = settings.edit();
        String auth_token_string = settings.getString("ClaveApi", ""/*default value*/);
        txtUsuario = (EditText) rootView.findViewById(R.id.editText);
        if (auth_token_string != "") {
            txtUsuario = (EditText) rootView.findViewById(R.id.editText);
            txtPassword = (EditText) rootView.findViewById(R.id.editText2);
        } else {
            txtUsuario = (EditText) rootView.findViewById(R.id.editText);
            txtPassword = (EditText) rootView.findViewById(R.id.editText2);
        }
        cargarListadoSucursal();

        txtUsuario.addTextChangedListener(new TextWatcher() {
            String value1 = "";
            String value2 = "";
            String gg = "";

            @Override
            public void afterTextChanged(Editable s) {
                value1 = txtUsuario.getText().toString();
                value2 = txtPassword.getText().toString();

                if ((!value1.equals(gg) && !value2.equals(gg)) && (value1.length() > 1 && value2.length() == 4)) {
                    getView().findViewById(R.id.button2).setEnabled(true);
                } else {
                    getView().findViewById(R.id.button2).setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        txtPassword.addTextChangedListener(new TextWatcher() {
            String value1 = "";
            String value2 = "";
            String value3 = "";
            String gg = "";

            @Override
            public void afterTextChanged(Editable s) {
                value1 = txtUsuario.getText().toString();
                value2 = txtPassword.getText().toString();

                if ((!value1.equals(gg) && !value2.equals(gg)) && (value1.length() > 1 && value2.length() == 4 )) {
                    getView().findViewById(R.id.button2).setEnabled(true);
                } else {
                    getView().findViewById(R.id.button2).setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        return rootView;
    }


<<<<<<< HEAD
    public void buttonClicked(View view) {
        //if (view.getId() == R.id.button) {
            // button1 action
       // } else if (view.getId() == R.id.button2) {
            //button2 action
       // } else if (view.getId() == R.id.button3) {
            //button3 action
        //}
    }

=======
>>>>>>> origin/master
    @Override
    public void onClick(View v) {


        String usuario = txtUsuario.getText().toString();
        String password = txtPassword.getText().toString();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        //String auth_token_string = settings.getString("ClaveApi", ""*//*default value*//*);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("idSucursal",id_sucursal);
        editor.putString("sucursal",txSucursal.getText().toString());
        editor.apply();
        try {
            JSONObject jsonObj1 = new JSONObject();
            jsonObj1.put("usuario", usuario);
            jsonObj1.put("contrasena", password);
            // Create the POST object and add the parameters
            bgt = new BGTLogIn(Configuracion.getApiUrlLogIn(),getActivity(),jsonObj1);
            bgt.execute();
        } catch(JSONException e){
            showToast(e.toString());
        }catch (Exception e){
            showToast(e.toString());
        }

    }

     public void onBackPressed()  {
        getActivity().finish();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
            }
        }.start();

    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void cargarListadoSucursal() {
        try {
            JSONObject jsonObj1 = new JSONObject();
            jsonObj1.put(Configuracion.getApiUrlSucursal(), id_sucursal);
            bgtSucursal = new BGTCargarSucursal(Configuracion.getApiUrlSucursal(),getActivity());
            bgtSucursal.execute();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

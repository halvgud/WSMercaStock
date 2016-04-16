package mx.mercatto.mercastock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import mx.mercatto.mercastock.BGT.BackGroundTask;


public class RegistroUsuario extends Fragment implements View.OnClickListener{

    private String sexo="M";
    private BackGroundTask bgt;
    EditText txtusuario ;
    EditText txtpassword;
    EditText txtpassword2;
    EditText txtnombre ;
    EditText txtapellido ;
    Spinner txtsexo;
    Spinner listaSucSpinner;
    Button guardar;
    String id_sucursal="";
    public RegistroUsuario() {

    }

    // TODO: Rename and change types and number of parameters
    private String[] arraySpinner;
    protected View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.rootView = inflater.inflate(R.layout.fragment_registro_usuario, container, false);
        getActivity().setTitle("Registrar Usuario");
        arraySpinner=new String[]{
                "Masculino","Femenino"
        };
        txtsexo = (Spinner) rootView.findViewById(R.id.spinnerSexo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        txtsexo.setAdapter(adapter);
        txtsexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sexo = "M";
                } else {
                    sexo = "F";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        txtusuario = (EditText) rootView.findViewById(R.id.editText5);
        txtpassword = (EditText) rootView.findViewById(R.id.editText6);
        txtpassword2= (EditText) rootView.findViewById(R.id.editText7);
        txtnombre = (EditText) rootView.findViewById(R.id.editText8);
        txtapellido = (EditText) rootView.findViewById(R.id.editText9);
        guardar = (Button) rootView.findViewById(R.id.button7);
        guardar.setOnClickListener(this);
        cargarListadoSucursal();
        //Evaluaciones
        txtusuario.addTextChangedListener(new TextWatcher() {
            String value1 = "";
            String value2 = "";
            String value3 = "";
            String value4 = "";
            String value5 = "";
            String gg = "";

            @Override
            public void afterTextChanged(Editable s) {
                value1 = txtusuario.getText().toString();
                value2=txtpassword.getText().toString();
                value3=txtpassword2.getText().toString();
                value4=txtnombre.getText().toString();
                value5=txtapellido.getText().toString();

                if ((!value1.equals(gg) && !value2.equals(gg) && !value3.equals(gg) && !value4.equals(gg) && !value5.equals(gg)) && (value2.equals(value3))&&(value2.length()==4 && value3.length()==4)) {
                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
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
        txtpassword.addTextChangedListener(new TextWatcher() {
            String value1 = "";
            String value2 = "";
            String value3 = "";
            String value4 = "";
            String value5 = "";
            String gg = "";

            @Override
            public void afterTextChanged(Editable s) {
                value1 = txtusuario.getText().toString();
                value2=txtpassword.getText().toString();
                value3=txtpassword2.getText().toString();
                value4=txtnombre.getText().toString();
                value5=txtapellido.getText().toString();

                if ((!value1.equals(gg) && !value2.equals(gg) && !value3.equals(gg) && !value4.equals(gg) && !value5.equals(gg)) && (value2.equals(value3))&&(value2.length()==4 && value3.length()==4)) {
                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
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
        txtpassword.addTextChangedListener(new TextWatcher() {
            String value1 = "";
            String value2 = "";
            String value3 = "";
            String value4 = "";
            String value5 = "";
            String gg = "";

            @Override
            public void afterTextChanged(Editable s) {
                value1 = txtusuario.getText().toString();
                value2=txtpassword.getText().toString();
                value3=txtpassword2.getText().toString();
                value4=txtnombre.getText().toString();
                value5=txtapellido.getText().toString();

                if ((!value1.equals(gg) && !value2.equals(gg) && !value3.equals(gg) && !value4.equals(gg) && !value5.equals(gg)) && (value2.equals(value3))&&(value2.length()==4 && value3.length()==4)) {
                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
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
        txtnombre.addTextChangedListener(new TextWatcher() {
            String value1 = "";
            String value2 = "";
            String value3 = "";
            String value4 = "";
            String value5 = "";
            String gg = "";

            @Override
            public void afterTextChanged(Editable s) {
                value1 = txtusuario.getText().toString();
                value2=txtpassword.getText().toString();
                value3=txtpassword2.getText().toString();
                value4=txtnombre.getText().toString();
                value5=txtapellido.getText().toString();

                if ((!value1.equals(gg) && !value2.equals(gg) && !value3.equals(gg) && !value4.equals(gg) && !value5.equals(gg)) && (value2.equals(value3))&&(value2.length()==4 && value3.length()==4)) {
                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
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
        txtapellido.addTextChangedListener(new TextWatcher() {
            String value1 = "";
            String value2 = "";
            String value3 = "";
            String value4 = "";
            String value5 = "";
            String gg = "";

            @Override
            public void afterTextChanged(Editable s) {
                value1 = txtusuario.getText().toString();
                value2=txtpassword.getText().toString();
                value3=txtpassword2.getText().toString();
                value4=txtnombre.getText().toString();
                value5=txtapellido.getText().toString();

                if ((!value1.equals(gg) && !value2.equals(gg) && !value3.equals(gg) && !value4.equals(gg) && !value5.equals(gg)) && (value2.equals(value3))&&(value2.length()==4 && value3.length()==4)) {
                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
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

    protected FragmentActivity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (FragmentActivity)activity;
    }

    public void cargarListadoSucursal() {
        try {
            JSONObject jsonObj1 = new JSONObject();
            jsonObj1.put(Configuracion.getApiUrlSucursal(), id_sucursal);
            bgt = new BackGroundTask(Configuracion.getApiUrlSucursal(), "GET", null,getActivity(),9);
            bgt.execute();
        } catch (JSONException e) {
           showToast(e.getMessage());
        }
    }
    @Override
    public void onClick(View view){

        String usuario = txtusuario.getText().toString().toUpperCase();
        String password = txtpassword.getText().toString();
        String password2 = txtpassword2.getText().toString();
        String nombre = txtnombre.getText().toString();
        String apellido = txtapellido.getText().toString();
        String contacto = "";

        String claveapi = "";

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String idsucursal = settings.getString("idSucursal","");
        SharedPreferences.Editor editor = settings.edit();
        if(usuario.length()>0&&password.length()>0&&password2.length()>0&&nombre.length()>0&&apellido.length()>0) {
            if(password.length()==4&&password2.length()==4){
                if (password.equals(password2)) {
                    try {
                        JSONObject jsonObj1 = new JSONObject();
                        jsonObj1.put("usuario", usuario);
                        jsonObj1.put("contrasena", password);
                        jsonObj1.put("nombre", nombre);
                        jsonObj1.put("apellido", apellido);
                        jsonObj1.put("sexo", sexo);
                        jsonObj1.put("contacto", contacto);
                        jsonObj1.put("idSucursal", Configuracion.settings.getString("idSucursal","0"));
                        jsonObj1.put("claveApi", "");
                        jsonObj1.put("idNivelAutorizacion",1);
                        jsonObj1.put("idEstado","A");
                        jsonObj1.put("fechaEstado","");
                        jsonObj1.put("fechaSesion","");

                        bgt = new BackGroundTask("http://192.168.1.80/wsMercaStock/usuario/registro", "POST", jsonObj1,getActivity(),14);
                        bgt.execute();

                    } catch (JSONException e) {
                        showToast(e.toString());
                    } catch (Exception e) {

                    }
                } else {
                    showToast("Las contraseñas no coinciden");
                }
            }
            else {
                showToast("Las contraseñas deben contener 4 digitos");
            }
        }
        else {
            showToast("Faltan datos en algún campo");
        }
    }
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

}

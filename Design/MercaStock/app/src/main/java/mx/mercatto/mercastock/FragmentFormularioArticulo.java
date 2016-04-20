package mx.mercatto.mercastock;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import mx.mercatto.mercastock.BGT.BGTCargarListadoArticulo;
import mx.mercatto.mercastock.BGT.BGTPostFormularioArticulo;


public class FragmentFormularioArticulo extends Fragment  implements View.OnClickListener{

    private static String idInventario="";
    private static String NombreArticulo="";
    private static String cat_id="";
    private static String art_id="";
    private static String existencia="";
    private static String esGranel="1";
    private static String clave="";
    private BGTPostFormularioArticulo bgt;
    //private BackGroundTask bgt;
    InputMethodManager imm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_formulario_articulo, container, false);
        Bundle args = getArguments();
        idInventario = args.getString(Configuracion.getIdInventario());
        NombreArticulo = args.getString(Configuracion.getDescripcioArticulo());
        TAG_VALOR_INVENTARIO = args.getString(Configuracion.getIdInventario());
        cat_id = args.getString(Configuracion.getIdCategoria());
        art_id = args.getString(Configuracion.getIdArticulo());
        existencia = args.getString(Configuracion.getExistenciaArticulo());
        esGranel=args.getString(Configuracion.getGranelArticulo());
        clave = args.getString(Configuracion.getClaveArticulo());
        getActivity().setTitle("Artículo :");

        EditText txt1 = (EditText) rootView.findViewById(R.id.editText3);
        TextView txtTituloInferior = (TextView) rootView.findViewById(R.id.FormularioArticulotxtTituloInferior);
        TextView txtCodigoDeBarras = (TextView) rootView.findViewById(R.id.FormularioArticulotxtCodigoDeBarras);
        txtTituloInferior.setText(NombreArticulo);
        txtCodigoDeBarras.setText(clave);
        TextView txtCantidad = (TextView) rootView.findViewById(R.id.textView4);
        txtCantidad.setText("Cantidad por " + args.getString(Configuracion.getUnidadArticulo()) + ":");
        Button upButton = (Button) rootView.findViewById(R.id.button3);
        upButton.setOnClickListener(this);

        txt1.addTextChangedListener(new TextWatcher() {
            String value = "";
            String gg = "";

            @Override
            public void afterTextChanged(Editable s) {
                EditText text = (EditText) rootView.findViewById(R.id.editText3);
                String value = text.getText().toString();
                String gg = "";
                if (value.equals(gg)) {
                    rootView.findViewById(R.id.button3).setEnabled(false);
                } else {
                    rootView.findViewById(R.id.button3).setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                EditText text = (EditText) rootView.findViewById(R.id.editText3);
                String value = text.getText().toString();
                String gg = "";
                if (value.equals(gg)) {
                    rootView.findViewById(R.id.button3).setEnabled(false);
                } else {
                    rootView.findViewById(R.id.button3).setEnabled(true);
                }


            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                EditText text = (EditText) rootView.findViewById(R.id.editText3);
                String value = text.getText().toString();
                String gg = "";
                if (value.equals(gg)) {
                    rootView.findViewById(R.id.button3).setEnabled(false);
                } else {
                    rootView.findViewById(R.id.button3).setEnabled(true);
                }
                //Toast.makeText(getApplicationContext(), "Your3 toast message.",
                //      Toast.LENGTH_SHORT).show();

            }
        });
        if(esGranel.equals("1")){
            txt1.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        else
        {
            txt1.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        final EditText valor;

        valor = (EditText) getActivity().findViewById(R.id.editText3);
        if (Configuracion.getConfirmacion_Mensaje_Gurdado().toString().equals("TRUE")) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
            dialogo1.setTitle("Aviso");
            dialogo1.setMessage("Se va a registrar la cantidad de \n" + valor.getText().toString() + "\n ¿Desea continuar?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    aceptar(valor.getText().toString());
                    imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    //doNothing();

                }
            });
            AlertDialog dialogo=dialogo1.show();
            TextView messageView = (TextView)dialogo.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER);
            //}
        }
        else {
            aceptar(valor.getText().toString());
        }

    }

    private static  String TAG_VALOR_INVENTARIO;
    public void aceptar(String valor) {
        try{
            JSONObject jsobj = new JSONObject();
            jsobj.put("idInventario",idInventario);
            jsobj.put("existenciaRespuesta",valor);
            jsobj.put("art_id",art_id);
            bgt = new BGTPostFormularioArticulo(Configuracion.getApiUrlInventario(),getActivity(),jsobj);
            bgt.execute();
            Toast t=Toast.makeText(getActivity(),"Se ha guardado correctamente.", Toast.LENGTH_SHORT);
            t.show();
        }catch(JSONException e){
            showToast(e.getMessage());
        }
        finally {
            if(BGTCargarListadoArticulo.devolverConteo()>1){
                getActivity().getFragmentManager().popBackStack();
            }
            else{
                FragmentCategoria fragment2 = new FragmentCategoria();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment2);
                fragmentTransaction.commit();
            }
        }
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}


package mx.mercatto.mercastock;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.FragmentManager;
import android.util.Log;
import android.view.MenuInflater;
<<<<<<< HEAD
=======
import android.view.MotionEvent;
import android.view.View;
>>>>>>> origin/master
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import org.json.JSONObject;

import mx.mercatto.mercastock.BGT.BackGroundTask;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String PROJECT_NUMBER="917548048883";

public static String x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // x="1";
      //  if(x=="1")
        setContentView(R.layout.activity_main_logged);
        //else
        //setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//revisarApi();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        GCMClientManager pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {

                Log.d("Registration id", registrationId);
                //send this registrationId to your server
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
       // inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        //imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);


  //      InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Configuracion.Inicializar(this);
        revisarApi();


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
    public void revisarApi() {
        BackGroundTask bgt;
        try {
            Configuracion.settings=PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            JSONObject jsonObj1 = new JSONObject();
            jsonObj1.put("claveApi",Configuracion.settings.getString("ClaveApi",""));
            bgt = new BackGroundTask("http://192.168.1.17/wsMercaStock/usuario/api", "POST", jsonObj1 ,this,13);
            bgt.execute();
        } catch (Exception e){
           // showToast(e.toString());
        }

    }

    @Override
    public void onBackPressed() {

        Fragment currentFragment = this.getFragmentManager().findFragmentById(R.id.content_main);

        if (currentFragment instanceof FragmentLogin) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
            //super.onBackPressed();
        }
        if (currentFragment instanceof FragmentCategoria) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
            //super.onBackPressed();
        }
        if (currentFragment instanceof FragmentArticulo) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                //super.onBackPressed();
                getFragmentManager().popBackStack();
            }

        }
        if (currentFragment instanceof FragmentFormularioArticulo) {
            //getFragmentManager().popBackStack();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                //super.onBackPressed();
                getFragmentManager().popBackStack();
            }
        }
        if (currentFragment instanceof FragmentPassword) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                //super.onBackPressed();
                //getFragmentManager().popBackStack();
                FragmentCategoria fragment2 = new FragmentCategoria();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment2);
                fragmentTransaction.commit();
            }

        }
        if (currentFragment instanceof FragmentSucursal) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                FragmentLogin fragment2 = new FragmentLogin();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment2);
                fragmentTransaction.commit();
            }
        }
        if (currentFragment instanceof RegistroUsuario) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                getFragmentManager().popBackStack();
            }
        }
        if (currentFragment instanceof FragmentSesion) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }


}
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
          //  int x=1;
        //    if(x==1)
        inflater.inflate(R.menu.activity_main_drawer, menu);
            //else
              //  inflater.inflate(R.menu.activity_main_drawer, menu);
           // imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
        return true;
    }
    public static boolean b = false;
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        menu.setGroupVisible(0, false);


      //  invalidateOptionsMenu();
        return true;
    }

     public static MenuItem SeleccionarSucursal;
    public static MenuItem CambiarContrasena;
    public static MenuItem CerrarSesion;
    public static MenuItem CrearUsuario;
//public  static x;

    public static void CambiarEstadoSucursal(boolean bandera){
        SeleccionarSucursal.setEnabled(bandera);
    }
    public static void CambiarEstadoContrasena(boolean bandera){
        CambiarContrasena.setEnabled(bandera);
    }
    public static  void CerrarSesion(boolean bandera){
        CerrarSesion.setEnabled(bandera);
    }
    public static  void CambiarCrearUsuario(boolean bandera){
        CrearUsuario.setEnabled(bandera);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("id", Integer.toString(id));
        //item f = findViewById(R.id.Crear_Usuario);
        if (id == R.id.crearusuario) {
            RegistroUsuario fragment = new RegistroUsuario();
            FragmentManager fragmentManager = this.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
        }else if(id==R.id.cerrarsesion){

            Configuracion.settings= PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            Configuracion.editor = Configuracion.settings.edit();
            Configuracion.editor.putString("usuario", "");
            Configuracion.editor.putString("ClaveApi", "");
            Configuracion.editor.putString("nombre", "");
            Configuracion.editor.apply();
            FragmentLogin fragment = new FragmentLogin();
            FragmentManager fragmentManager = this.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();

        }else if(id==R.id.seleccionarsucursal){

            FragmentSucursal fragment = new FragmentSucursal();
            FragmentManager fragmentManager = this.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
        }else if(id==R.id.cambiarcontrasena){
            FragmentPassword fragment = new FragmentPassword();
            FragmentManager fragmentManager = this.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDestroy() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("usuario", "");
        editor.putString("ClaveApi", "");
        editor.putString("sucursal","");
        editor.apply();
        super.onDestroy();
    }

    @Override
    public  void onResume(){
        revisarApi();
        super.onResume();
    }
}

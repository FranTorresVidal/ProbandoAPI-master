package info.androidhive.jsonparsing;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    ListView listaJson;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaJson = (ListView) findViewById(R.id.list);

        Tarea1 tarea1 = new Tarea1();
        tarea1.cargarContenido(getApplicationContext());
        tarea1.execute(listaJson);
    }


    static class Tarea1 extends AsyncTask<ListView, Void, ArrayAdapter<Clientes>>{
        Context contexto;
        ListView list;
        InputStream is;
        ArrayList<Clientes> listaclientes = new ArrayList<Clientes>();

        public void cargarContenido(Context contexto){
            this.contexto = contexto;
        }

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected ArrayAdapter<Clientes> doInBackground(ListView... params){
            list = params[0];
            String resultado = "fallo";
            Clientes cli;

            HttpClient cliente = new DefaultHttpClient();
            HttpGet peticionGet = new HttpGet("http://shurapi.pe.hu/mensajes");
            try{
                HttpResponse response = cliente.execute(peticionGet);
                HttpEntity contenido = response.getEntity();
                is = contenido.getContent();
            }catch (ClientProtocolException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            BufferedReader buferlector = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String linea = null;
            try {
                while((linea = buferlector.readLine())!=null){
                    sb.append(linea);
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            try{
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            resultado = sb.toString();

            try{
                JSONArray arrayJson = new JSONArray(resultado);
                for(int i = 0;i<arrayJson.length();i++){
                    JSONObject objetoJson = arrayJson.getJSONObject(i);
                    cli = new Clientes(objetoJson.getInt("id"),objetoJson.getString("usuario"),objetoJson.getString("contenido"),objetoJson.getString("fecha"));
                    listaclientes.add(cli);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            ArrayAdapter<Clientes> adaptador = new ArrayAdapter<Clientes>(contexto, android.R.layout.simple_list_item_1, listaclientes);

            return adaptador;

        }

        @Override
        protected void onPostExecute(ArrayAdapter<Clientes> result){
            list.setAdapter(result);
        }

        @Override
        protected void onProgressUpdate(Void... values){

        }
    }
}
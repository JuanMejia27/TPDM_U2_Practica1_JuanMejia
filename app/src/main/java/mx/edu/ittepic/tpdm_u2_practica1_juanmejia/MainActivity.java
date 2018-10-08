package mx.edu.ittepic.tpdm_u2_practica1_juanmejia;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtid, dias, descripcion, calorias;
    Button buscar, eliminar, actualizar;
    ConexionBase basedatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtid = (EditText)findViewById(R.id.id);
        dias = (EditText)findViewById(R.id.dias);
        descripcion = (EditText)findViewById(R.id.descripcion);
        calorias = (EditText)findViewById(R.id.calorias);
        buscar = (Button)findViewById(R.id.buscar);
        eliminar = (Button)findViewById(R.id.eliminar);
        actualizar = (Button)findViewById(R.id.actualizar);

        basedatos = new ConexionBase(this,"base1",null,1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=txtid.getText().toString();
                buscar(id);
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=txtid.getText().toString();
                eliminar(id);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=txtid.getText().toString();
                actualizar(id);
            }
        });


    }//onCreate

    private void buscar(String valor) {
        if(txtid.isEnabled()){
            try {
                SQLiteDatabase base = basedatos.getWritableDatabase();
                String SQL = "select * from CALORIASQ where ID =" + valor;
                Cursor x = base.rawQuery(SQL, null);
                if (x.moveToFirst() == false) {
                    AlertDialog.Builder a = new AlertDialog.Builder(this);
                    a.setMessage("No se encontro Ningun registro con ese id").show();
                    base.close();
                    return;
                }
                Toast.makeText(this, ""+x.getString(0)+x.getString(1)
                        +x.getString(2)+x.getString(3), Toast.LENGTH_SHORT).show();

                dias.setText(x.getString(1));
                descripcion.setText(x.getString(2));
                calorias.setText(x.getString(3));
                base.close();
            } catch (SQLiteException e) {
                mensaje("error", e.getMessage());
            }
        }else{
            txtid.setEnabled(true);
        }

    }//buscar

    private void actualizar(String id) {
        try {
            SQLiteDatabase base= basedatos.getWritableDatabase();

            String SQL ="UPDATE CALORIASQ SET DIAS='{DIAS}',DESCRIPCION='{DESC}', CALORIASQUEMADAS='{CAL}' WHERE ID ="+id;
            SQL=SQL.replace("{DIAS}",dias.getText().toString());
            SQL=SQL.replace("{DESC}",descripcion.getText().toString());
            SQL=SQL.replace("{CAL}",calorias.getText().toString());


            base.execSQL(SQL);

            Toast.makeText(getBaseContext(),"Actualizado correctamente",Toast.LENGTH_SHORT).show();
            txtid.setEnabled(false);
            limpiarCampos();
            base.close();


        }catch (SQLiteException e){
            AlertDialog.Builder a=new AlertDialog.Builder(this);
            a.setMessage("No se pudo actualizar").show();

        }
    }//actualizar

    private void eliminar(String title){
        try {
            SQLiteDatabase database = basedatos.getWritableDatabase();
            database.execSQL("DELETE FROM  CALORIASQ  WHERE ID = '" + title + "'");
            database.close();
            limpiarCampos();
            Toast.makeText(this,"se elimino correctamente",Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e){
            Toast.makeText(this,"error a el eliminar",Toast.LENGTH_SHORT).show();
        }
        txtid.setEnabled(false);
    }//eliminar

    private void insertar(){
        try{
            SQLiteDatabase base = basedatos.getWritableDatabase();
            ContentValues datos = new ContentValues();

            datos.put("DIAS",dias.getText().toString());
            datos.put("DESCRIPCION",descripcion.getText().toString());
            datos.put("CALORIASQUEMADAS",calorias.getText().toString());

            base.insert("CALORIASQ",null,datos);
            base.close();
            mensaje("ATENCION","Se inserto correctamente el dato");
            limpiarCampos();

        }catch(SQLiteException e){
            mensaje("Error",e.getMessage());
        }
    }//insertar

    private void limpiarCampos() {
        dias.setText("");
        descripcion.setText("");
        calorias.setText("");
    }

    private void mensaje(String title,String message){
        AlertDialog.Builder a = new AlertDialog.Builder(this);
        a.setTitle(title).setMessage(message);
    }
}

package mx.edu.ittepic.tpdm_u2_practica1_juanmejia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class ConexionBase extends SQLiteOpenHelper {

    public ConexionBase(Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CALORIASQ("+
                "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "DIAS VARCHAR(200),"+
                "DESCRIPCION VARCHAR(500),"+
                "CALORIASQUEMADAS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

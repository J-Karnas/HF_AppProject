package pl.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "homefinances.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableKategoria = "CREATE TABLE kategoria(" +
                "id_kategoria INTEGER PRIMARY KEY," +
                "nazwa_kat TEXT)"
        db?.execSQL(createTableKategoria)

        val createTableDane = "CREATE TABLE dane(" +
                "id_dane INTEGER PRIMARY KEY," +
                "kwota REAL," +
                "data_time TEXT," +
                "notatka TEXT," +
                "id_kategoria INTEGER," +
                "FOREIGN KEY (id_kategoria) REFERENCES kategoria(id_kategoria))"
        db?.execSQL(createTableDane)

        val insertKategoria = "INSERT INTO `kategoria` (`id_kategoria`, `nazwa_kat`) VALUES" +
                "(1, 'Żywność')," +
                "(2, 'mieszkanie')," +
                "(3, 'transport')," +
                "(4, 'odzież')," +
                "(5, 'prezenty')," +
                "(6, 'rozwój')," +
                "(7, 'zdrowie')," +
                "(8, 'rozrywka')," +
                "(9, 'technologia')," +
                "(10, 'inne')," +
                "(11, 'przychód')"
        db?.execSQL(insertKategoria)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS kategoria")
        db?.execSQL("DROP TABLE IF EXISTS dane")
        onCreate(db)
    }

}
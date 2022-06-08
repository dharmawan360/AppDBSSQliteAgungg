package com.example.appdbssqliteagungg

import android.content.*
import android.database.Cursor
import android.database.sqlite.*

class DbHelper (context: Context): SQLiteOpenHelper(context, "Kampus", null,1){
    var kdMatkul = ""
    var nmMatkul = ""
    var sks      =""
    var sifat    = ""

    private val tabel = "Matkul"
    private var sql = ""

    override fun onCreate(db: SQLiteDatabase?){
        sql = """create table $tabel(
            kd_matkul char(5) primary key,
            nm_matkul varchar(50) not null,
            sks integer not null,
            sifat varchar(7)not null
            )
            """.trimIndent()
        db?.execSQL(sql)
    }
    override fun onUpgrade (db: SQLiteDatabase?, oldVersion : Int, newVersion:Int){
        sql = "drop table if exists $tabel"
        db?.execSQL(sql)
    }
    fun simpan(): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv){
            put ("kd_matkul", kdMatkul)
            put ("nm_matkul", nmMatkul)
            put ("sks", sks)
            put ("sifat",sifat)
        }
        val cmd = db.insert(tabel, null, cv)
        db.close()
        return cmd !=-1L
    }
    fun ubah (kode: String):Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv){
            put("nm_matkul",nmMatkul)
            put ("sks",sks)
            put ("sifat", sifat)
        }
        val cmd = db.update(tabel,cv,"kd_matkul = ?", arrayOf(kode))
        db.close()
        return cmd != -1
    }
    fun hapus(kode: String):Boolean{
        val db = writableDatabase
        val cmd = db.delete(tabel,"kd_matkul",arrayOf(kode))
        return cmd != -1
    }
    fun tampil(): Cursor{
        val db = writableDatabase
        val reader = db.rawQuery("Select * from $tabel",null)
        return reader
    }
}
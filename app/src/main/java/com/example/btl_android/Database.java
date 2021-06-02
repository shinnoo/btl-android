package com.example.btl_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context) {
        super(context,"product",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS product (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(200)," +
                "description VARCHAR(200)," +
                "imgUrl VARCHAR(200))";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addProduct(Product product){
        String sql = "INSERT INTO product(name, description,imgUrl) " +
                "VALUES (?, ?, ?)";

        // mảng đối số thay thế vào chỗ ?
        String[] args = {product.getName(), product.getDescription(), product.getImgUrl()};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, args);
    }

    public int updateProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("imgUrl",product.getImgUrl());

        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(product.getId())};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("product", values, whereClause, whereArgs);
    }

    public List<Product> getListAll(){
        List<Product> products = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("product", null,
                null, null, null, null, null);
        while (cursor != null && cursor.moveToNext()){
            int id      = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            String imgUrl = cursor.getString(3);

            Product product = new Product(id, name, description,imgUrl);
            products.add(product);
        }
        return products;
    }
}

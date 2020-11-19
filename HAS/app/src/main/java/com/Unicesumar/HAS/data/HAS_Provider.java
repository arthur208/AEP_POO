package com.Unicesumar.HAS.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;



public class HAS_Provider extends ContentProvider {

    public static final String LOG_TAG = HAS_Provider.class.getSimpleName();

    private static final int LEMBRETE = 100;

    private static final int LEMBRETE_ID = 101;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(HAS_Contract.CONTENT_AUTHORITY, HAS_Contract.PATH_VEHICLE, LEMBRETE);

        sUriMatcher.addURI(HAS_Contract.CONTENT_AUTHORITY, HAS_Contract.PATH_VEHICLE + "/#", LEMBRETE_ID);

    }

    private HAS_DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new HAS_DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
    String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        //Este cursor irá manter o resultado da consulta
        Cursor cursor = null;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case LEMBRETE:
                cursor = database.query(HAS_Contract.HAS_Acesso.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case LEMBRETE_ID:
                selection = HAS_Contract.HAS_Acesso._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(HAS_Contract.HAS_Acesso.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }


        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LEMBRETE:
                return HAS_Contract.HAS_Acesso.CONTENT_LIST_TYPE;
            case LEMBRETE_ID:
                return HAS_Contract.HAS_Acesso.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("URI desconhecido " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LEMBRETE:
                return insertLembrete(uri, contentValues);

            default:
                throw new IllegalArgumentException("Inserção não suportada para " + uri);
        }
    }

    private Uri insertLembrete(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(HAS_Contract.HAS_Acesso.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Falha ao inserir coluna para " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LEMBRETE:
                rowsDeleted = database.delete(HAS_Contract.HAS_Acesso.TABLE_NAME, selection, selectionArgs);
                break;
            case LEMBRETE_ID:
                selection = HAS_Contract.HAS_Acesso._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(HAS_Contract.HAS_Acesso.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Não é possível deletar para " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LEMBRETE:
                return updateLembrete(uri, contentValues, selection, selectionArgs);
            case LEMBRETE_ID:
                selection = HAS_Contract.HAS_Acesso._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateLembrete(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Não é possível atualizar para " + uri);
        }
    }

    private int updateLembrete(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(HAS_Contract.HAS_Acesso.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

}

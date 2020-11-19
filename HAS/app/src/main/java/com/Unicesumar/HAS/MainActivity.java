package com.Unicesumar.HAS;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.Unicesumar.HAS.R;
import com.Unicesumar.HAS.data.HAS_Contract;
import com.Unicesumar.HAS.data.HAS_DbHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FloatingActionButton mBotãoAdicionar;
    private Toolbar mToolbar;
    CursorAlarmeAdapter mCursorAlarme;
    HAS_DbHelper HASDbHelper = new HAS_DbHelper(this);
    ListView ListaMedicação;
    ProgressDialog prgDialog;

    private static final int VEHICLE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);


        ListaMedicação = (ListView) findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        ListaMedicação.setEmptyView(emptyView);

        mCursorAlarme = new CursorAlarmeAdapter(this, null);
        ListaMedicação.setAdapter(mCursorAlarme);

        ListaMedicação.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ADD_RemedioActivity.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(HAS_Contract.HAS_Acesso.CONTENT_URI, id);

                intent.setData(currentVehicleUri);

                startActivity(intent);

            }
        });


        mBotãoAdicionar = (FloatingActionButton) findViewById(R.id.fab);

        mBotãoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ADD_RemedioActivity.class);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(VEHICLE_LOADER, null, this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                HAS_Contract.HAS_Acesso._ID,
                HAS_Contract.HAS_Acesso.KEY_TITLE,
                HAS_Contract.HAS_Acesso.KEY_DATE,
                HAS_Contract.HAS_Acesso.KEY_TIME,
                HAS_Contract.HAS_Acesso.KEY_REPEAT,
                HAS_Contract.HAS_Acesso.KEY_REPEAT_NO,
                HAS_Contract.HAS_Acesso.KEY_REPEAT_TYPE,
                HAS_Contract.HAS_Acesso.KEY_ACTIVE

        };

        return new CursorLoader(this,
                HAS_Contract.HAS_Acesso.CONTENT_URI,  
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAlarme.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAlarme.swapCursor(null);

    }
}

package waitr.vendorapp.mc.waitruser.DbUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import waitr.vendorapp.mc.waitruser.dataObjects.Item;

/**
 * Created by Manan Wason on 15/11/15.
 */
public class DbSingleton {

    private static DbSingleton mInstance;

    private static Context mContext;

    private static DbHelper mDbHelper;

    private SQLiteDatabase mDb;
    private DatabaseOperations databaseOperations = new DatabaseOperations();

    private DbSingleton(Context context) {
        mContext = context;
        mDbHelper = new DbHelper(mContext);

    }

    public static void init(Context context) {
        if (mInstance == null) {
            mInstance = new DbSingleton(context);
        }
    }

    public static DbSingleton getInstance() {
        return mInstance;
    }

    private void getReadOnlyDatabase() {
        if ((mDb == null) || (!mDb.isReadOnly())) {
            mDb = mDbHelper.getReadableDatabase();
        }
    }

    public ArrayList<Item> getItemsList() {
        getReadOnlyDatabase();
        return databaseOperations.getItemsList(mDb);

    }


    public void insertQueries(ArrayList<String> queries) {
        databaseOperations.insertQueries(queries, mDbHelper);
    }

    public void insertQuery(String query) {
        databaseOperations.insertQuery(query, mDbHelper);
    }

    public void clearDatabase(String table) {
        databaseOperations.clearDatabaseTable(table, mDbHelper);
    }

    public void deleteAllRecords(String tableName) {
        databaseOperations.deleteAllRecords(tableName, mDb);
    }
}

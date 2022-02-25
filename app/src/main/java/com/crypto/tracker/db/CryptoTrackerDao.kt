package com.crypto.tracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.crypto.tracker.model.local.AlertType
import com.crypto.tracker.utils.TABLE_NAME

@Dao
interface CryptoTrackerDao {
    @Query("SELECT * FROM `alertType` $TABLE_NAME")
    fun getAlerts(): LiveData<List<AlertType?>>

    /*
    * Insert the object in database
    * @param alertType, object to be inserted
    */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alertType: AlertType)

    /*
    * update the object in database
    * @param alertType, object to be updated
    */
    @Update
    fun update(alertType: AlertType)

    /*
   * delete the object from database
   * @param alertType, object to be deleted
   */

    @Delete
    fun delete(alertType: AlertType)
}
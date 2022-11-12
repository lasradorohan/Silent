package com.compultra.silent.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.sql.Timestamp

@Dao
interface MessagesDao {
    @Query("SELECT address FROM $KEYS_TABLE UNION SELECT address FROM $INCOMING_TABLE UNION SELECT address FROM $OUTGOING_TABLE")
    fun getAllActiveContacts(): List<String>

    @Query("SELECT MAX(timestamp) FROM $MESSAGES_TABLE WHERE address = :address")
    fun getLatestTimestampForAddress(address: String): Long?

    @Query("SELECT * FROM $MESSAGES_TABLE WHERE address = :address ORDER BY timestamp DESC")
    fun getTextMessagesForAddress(address: String): LiveData<List<SilentMessage.Text>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTextMessages(messages: List<SilentMessage.Text>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTextMessage(message: SilentMessage.Text)

    //TODO: get unread count, latest message

    @Query("SELECT * FROM $MESSAGES_TABLE m1 WHERE timestamp=(SELECT max(m2.timestamp) FROM $MESSAGES_TABLE m2 WHERE m1.address = m2.address) ORDER BY timestamp DESC")
    fun getLatestContactTextMessages(): List<SilentMessage.Text>

    @Query("SELECT * FROM $MESSAGES_TABLE WHERE timestamp=(SELECT max(timestamp) FROM $MESSAGES_TABLE WHERE address = :address)")
    fun getLatestMessageForAddress(address: String): SilentMessage.Text?

    @Query("SELECT * FROM $INCOMING_TABLE")
    fun getIncomingRequests(): LiveData<List<SilentMessage.Request.Incoming>>

    @Query("SELECT * FROM $OUTGOING_TABLE")
    fun getOutgoingRequests(): LiveData<List<SilentMessage.Request.Outgoing>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIncomingRequests(requests: List<SilentMessage.Request.Incoming>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIncomingRequest(request: SilentMessage.Request.Incoming)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOutgoingRequests(requests: List<SilentMessage.Request.Outgoing>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOutgoingRequest(request: SilentMessage.Request.Outgoing)

    @Query("DELETE FROM $INCOMING_TABLE WHERE address = :address")
    fun deleteIncomingRequest(address: String)

    @Query("DELETE FROM $OUTGOING_TABLE WHERE address = :address")
    fun deleteOutgoingRequest(address: String)

    @Query("INSERT OR REPLACE INTO $CONFIG_TABLE (argument, value) VALUES ('${MyConfig.LATEST_TIMESTAMP}', :timestamp)")
    fun setLatestProcessedTimestamp(timestamp: String)

    @Query("SELECT value FROM $CONFIG_TABLE WHERE argument = '${MyConfig.LATEST_TIMESTAMP}'")
    fun getLatestProcessedTimestamp(): String?

    @Query("SELECT COUNT(*) FROM $OUTGOING_TABLE WHERE address = :address")
    fun existsOutgoingRequest(address: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKey(key: AcceptedKey)

    @Query("SELECT COUNT(*) FROM $KEYS_TABLE WHERE address = :address")
    fun existsKey(address: String): Int

    @Query("SELECT publicKey FROM $KEYS_TABLE WHERE address = :address")
    fun getKey(address: String): String?

    @Query("SELECT * FROM $MESSAGES_TABLE WHERE address = :address ORDER BY timestamp DESC")
    fun getMessagesForContact(address: String): List<SilentMessage.Text>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLatestMessage(message: LatestTextMessage)

    @Query("SELECT * FROM $LATEST_TABLE")
    fun getLatestMessages(): LiveData<List<LatestTextMessage>>
}
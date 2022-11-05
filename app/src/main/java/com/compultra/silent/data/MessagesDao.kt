package com.compultra.silent.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessagesDao {
    @Query("SELECT address FROM $KEYS_TABLE UNION SELECT address FROM $INCOMING_TABLE UNION SELECT address FROM $OUTGOING_TABLE")
    fun getAllActiveContacts(): List<String>

    @Query("SELECT MAX(timestamp) FROM $MESSAGES_TABLE WHERE address = :address")
    fun getLatestTimestampForAddress(address: String): Long?

    @Query("SELECT * FROM $MESSAGES_TABLE WHERE address = :number")
    fun getTextMessagesForNumber(number: String): List<SilentMessage.Text>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTextMessages(messages: List<SilentMessage.Text>)

    //TODO: get unread count, latest message

    @Query("SELECT * FROM $MESSAGES_TABLE m1 WHERE timestamp=(SELECT max(m2.timestamp) FROM $MESSAGES_TABLE m2 WHERE m1.address = m2.address) ORDER BY timestamp DESC")
    fun getLatestContactTextMessages(): List<SilentMessage.Text>

    @Query("SELECT * FROM $INCOMING_TABLE")
    fun getIncomingRequests(): List<SilentMessage.Request.Incoming>

    @Query("SELECT * FROM $OUTGOING_TABLE")
    fun getOutgoingRequests(): List<SilentMessage.Request.Outgoing>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIncomingRequests(requests: List<SilentMessage.Request.Incoming>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOutgoingRequests(requests: List<SilentMessage.Request.Outgoing>)

    @Query("DELETE FROM $INCOMING_TABLE WHERE address = :address")
    fun deleteIncomingRequest(address: String)

    @Query("DELETE FROM $OUTGOING_TABLE WHERE address = :address")
    fun deleteOutgoingRequest(address: String)

}
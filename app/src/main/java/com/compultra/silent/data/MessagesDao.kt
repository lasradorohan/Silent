package com.compultra.silent.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessagesDao {
    @Query("SELECT MAX(timestamp) FROM $MESSAGES_TABLE WHERE address = :address")
    fun getLatestTimestampForAddress(address: String): Long?

    @Query("SELECT * FROM $MESSAGES_TABLE WHERE address = :number")
    fun getTextMessagesForNumber(number: String): List<TextMessage>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTextMessages(messages: List<TextMessage>)

    //TODO: get unread count, latest message

    @Query("SELECT * FROM $MESSAGES_TABLE m1 WHERE timestamp=(SELECT max(m2.timestamp) FROM $MESSAGES_TABLE m2 WHERE m1.address = m2.address) ORDER BY timestamp DESC")
    fun getLatestContactTextMessages(): List<TextMessage>

    @Query("SELECT * FROM $INCOMING_TABLE")
    fun getIncomingRequests(): List<IncomingRequestMessage>

    @Query("SELECT * FROM $OUTGOING_TABLE")
    fun getOutgoingRequests(): List<OutgoingRequestMessage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIncomingRequests(requests: List<IncomingRequestMessage>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOutgoingRequests(requests: List<OutgoingRequestMessage>)

    @Query("DELETE FROM $INCOMING_TABLE WHERE address = :address")
    fun deleteIncomingRequest(address: String)

    @Query("DELETE FROM $OUTGOING_TABLE WHERE address = :address")
    fun deleteOutgoingRequest(address: String)

}
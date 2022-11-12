package com.compultra.silent.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Log
import com.compultra.silent.MYTAG
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher



object EncryptionManager {
    private const val ALGORITHM = "RSA"
    private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_ECB
    private const val PADDING = KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1
    private const val ENCRYPTION_SCHEME = "$ALGORITHM/$BLOCK_MODE/$PADDING"


    private const val KEY_ALIAS = "mykey"
    private const val ANDROID_KEY_STORE = "AndroidKeyStore"

    private val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply { load(null) }

    private fun ByteArray.encodeBase64(): String = encodeToString(this, android.util.Base64.DEFAULT)
    private fun String.decodeBase64(): ByteArray = Base64.decode(this, Base64.DEFAULT)

    private val keyPairGenerator =
        KeyPairGenerator.getInstance(ALGORITHM, ANDROID_KEY_STORE).apply {
            initialize(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .setKeySize(2048)
                    .build()
            )
        }

    private val selfKeyPair by lazy {
        keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.PrivateKeyEntry ?: createKeyPair()
    }

    private fun createKeyPair(): KeyStore.PrivateKeyEntry {
        keyPairGenerator.genKeyPair()
        return keyStore.getEntry(KEY_ALIAS, null) as KeyStore.PrivateKeyEntry
    }

    fun getPublicKeyEncoded() = selfKeyPair.certificate.publicKey.encoded.encodeBase64()

    private lateinit var externalPublicKey: PublicKey
    private val externalEncryptCipher: Cipher = Cipher.getInstance(ENCRYPTION_SCHEME)
    private val internalDecryptCipher = Cipher.getInstance(ENCRYPTION_SCHEME)

    fun initExternalPublicKey(encoded: String) {
        externalPublicKey =
            KeyFactory.getInstance(ALGORITHM)
                .generatePublic(X509EncodedKeySpec(encoded.decodeBase64()))
        externalEncryptCipher.init(Cipher.ENCRYPT_MODE, externalPublicKey)
    }

    fun initInternalPrivateKey() {
        internalDecryptCipher.init(Cipher.DECRYPT_MODE, selfKeyPair.privateKey)
    }

    fun encryptStringExternal(body: String) =
        externalEncryptCipher.doFinal(body.toByteArray()).encodeBase64()


    fun decryptStringSelf(encoded: String): String {
        internalDecryptCipher.init(Cipher.DECRYPT_MODE, selfKeyPair.privateKey)
        return String(internalDecryptCipher.doFinal(encoded.decodeBase64()))
    }
//    fun test() {
//        val em = EncryptionManager
//        val publicKey = em.getPublicKeyEncoded()
//        Log.d(MYTAG, "publicKey = $publicKey")
//        val message = "hello"
//        Log.d(MYTAG, "message = $message")
//        em.initExternalPublicKey(publicKey)
//        val encrypted = em.encryptStringExternal(message)
//        Log.d(MYTAG, "encrypted = $encrypted")
//        em.initInternalPrivateKey()
//        val decrypted = em.decryptStringSelf(encrypted)
//        Log.d(MYTAG, "decrypted = $decrypted")
//    }
//        fun test2() {
//        val em = EncryptionManager
//        Log.d(MYTAG, "publicKey = ${em.getPublicKeyEncoded()}")
//        Log.d(MYTAG, "publicKey = ${em.getPublicKeyEncoded()}")
//        Log.d(MYTAG, "publicKey = ${em.getPublicKeyEncoded()}")
//        Log.d(MYTAG, "publicKey = ${em.getPublicKeyEncoded()}")
//    }

}



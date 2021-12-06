package com.iobytex.extensions

import android.content.Intent
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow




fun DecoratedBarcodeView.previewFlow(beepManager: BeepManager)  : Flow<String> = callbackFlow<String> {

    val barcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult?) {
            result ?: return
            try {
                trySend(result.text).isSuccess
            }catch (e: Exception){

            }
        }

        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
            super.possibleResultPoints(resultPoints)
        }
    }

    beepManager.playBeepSoundAndVibrate()

    val formats : List<BarcodeFormat>  = listOf((BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
    barcodeView.decoderFactory = DefaultDecoderFactory(formats)
    initializeFromIntent(Intent.getIntent())
    decodeSingle(barcodeCallback)

    awaitClose {
        pause()
    }
}
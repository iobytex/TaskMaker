package com.iobytex.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.qrcode.encoder.Encoder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.random.Random

object Utils {

    fun getRandomColor(): Int {
        val rand = Random(256)
        return Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))
    }


    enum class TaskFlow{
        Edit,
        Undo,
        Done,
        Delete,
    }



//
//    @Throws(WriterException::class, IOException::class)
//    private fun roundedQRGenerator() {
//
//        CoroutineScope(IO).launch {
//
//            val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
//            val code = Encoder.encode(data, ErrorCorrectionLevel.H, hints)
//            val input = code.matrix ?: throw java.lang.IllegalStateException()
//
//            val width = 900
//            val height = 900
//            val quietZone = 2
//
//            val inputWidth = input.width
//            val inputHeight = input.height
//            val qrWidth = inputWidth + quietZone * 2
//            val qrHeight = inputHeight + quietZone * 2
//            val outputWidth = width.coerceAtLeast(qrWidth)
//            val outputHeight = height.coerceAtLeast(qrHeight)
//            val multiple = (outputWidth / qrWidth).coerceAtMost(outputHeight / qrHeight)
//            val leftPadding = (outputWidth - inputWidth * multiple) / 2
//            val topPadding = (outputHeight - inputHeight * multiple) / 2
//            val FINDER_PATTERN_SIZE = 7
//            val CIRCLE_SCALE_DOWN_FACTOR = 21f / 30f
//            val circleSize = (multiple * CIRCLE_SCALE_DOWN_FACTOR).toInt()
//
//            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//            val canvas = Canvas(bitmap)
//            val paint = Paint()
//            paint.style = Paint.Style.FILL
//            paint.color = WHITE
//            paint.isAntiAlias = true
//            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
//            var color = BLACK
////            val background: Drawable = binding.roundedBgSetter.background
////            if (background is ColorDrawable) {
////                color = background.color
////            }
//            paint.color = color
//
//            var inputY = 0
//            var outputY = topPadding
//            while (inputY < inputHeight) {
//                var inputX = 0
//                var outputX = leftPadding
//                while (inputX < inputWidth) {
//                    if (input[inputX, inputY].toInt() == 1) {
//                        if (!(inputX <= FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE || inputX >= inputWidth - FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE || inputX <= FINDER_PATTERN_SIZE && inputY >= inputHeight - FINDER_PATTERN_SIZE)) {
//                            canvas.drawCircle(
//                                outputX.toFloat(),
//                                outputY.toFloat(),
//                                circleSize * 0.6f,
//                                paint
//                            )
//                        }
//                    }
//                    inputX++
//                    outputX += multiple
//                }
//                inputY++
//                outputY += multiple
//            }
//
//            val circleDiameter = multiple * FINDER_PATTERN_SIZE / 1.8f
//            drawFinderPatternCircleStyle1(canvas, leftPadding, topPadding, circleDiameter, paint)
//            drawFinderPatternCircleStyle1(
//                canvas,
//                leftPadding + circleSize + (inputWidth - FINDER_PATTERN_SIZE) * multiple,
//                topPadding,
//                circleDiameter,
//                paint
//            )
//            //        drawFinderPatternCircleStyle1(canvas, leftPadding*2-4, ((topPadding*2) + (inputHeight - FINDER_PATTERN_SIZE) * multiple)-8, circleDiameter, paint);
//            drawFinderPatternCircleStyle1(
//                canvas,
//                leftPadding,
//                topPadding + circleSize + (inputHeight - FINDER_PATTERN_SIZE) * multiple,
//                circleDiameter,
//                paint
//            )
//
//            withContext(Main){
//                binding.qr.setImageBitmap(bitmap)
//            }
//
//        }
//
//
//    }
//
//    private fun drawFinderPatternCircleStyle1(
//        canvas: Canvas,
//        x: Int,
//        y: Int,
//        circleDiameter: Float,
//        paint: Paint
//    ) {
//        val WHITE_CIRCLE_DIAMETER = circleDiameter * 5 / 7
//        val MIDDLE_DOT_DIAMETER = circleDiameter * 3 / 7
//
//        var color = BLACK
////        val background: Drawable = binding.roundedBgSetter.background
////        if (background is ColorDrawable) {
////            color = background.color
////        }
//        paint.color = color
//        canvas.drawCircle(
//            x + circleDiameter / 1.5f,
//            y + circleDiameter / 1.5f,
//            circleDiameter,
//            paint
//        )
//        paint.color = WHITE
//        canvas.drawCircle(
//            x + circleDiameter / 1.5f,
//            y + circleDiameter / 1.5f,
//            WHITE_CIRCLE_DIAMETER,
//            paint
//        )
//        paint.color = color
//        canvas.drawCircle(
//            x + circleDiameter / 1.5f,
//            y + circleDiameter / 1.5f,
//            MIDDLE_DOT_DIAMETER,
//            paint
//        )
//
//    }
}

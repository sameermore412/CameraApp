package com.more.camerapp.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class FacePointsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : View(context, attrs, defStyleAttr) {
    private val pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        textSize = 100f
    }
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.STROKE
        textSize = 100f
    }

    var points = listOf<PointF>()
        set(value) {
            field = value
        }

    var boundingBox = Rect(0, 0, 0, 0)
        set(value) {
            field = value
        }

    var imageWidth = 0
    var imageHeight = 0
    var needUpdateTransformation = false
    var postScaleWidthOffset = 0f
    var postScaleHeightOffset = 0f
    var scaleFactor = 0f
    var transformationMatrix = Matrix()
    var isImageFlipped = false

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            for (point in points) {
                drawCircle(point.x, point.y, 8f, pointPaint)
            }
            drawRect(boundingBox, linePaint)
        }
    }

    private fun updateTransformationIfNeeded() {
        if (!needUpdateTransformation || imageWidth <= 0 || imageHeight <= 0) {
            return
        }
        val viewAspectRatio = width.toFloat() / height
        val imageAspectRatio: Float = imageWidth as Float / imageHeight
        postScaleWidthOffset = 0f
        postScaleHeightOffset = 0f
        if (viewAspectRatio > imageAspectRatio) {
            // The image needs to be vertically cropped to be displayed in this view.
            scaleFactor = width.toFloat() / imageWidth
            postScaleHeightOffset = (width.toFloat() / imageAspectRatio - height) / 2
        } else {
            // The image needs to be horizontally cropped to be displayed in this view.
            scaleFactor = height.toFloat() / imageHeight
            postScaleWidthOffset = (height.toFloat() * imageAspectRatio - width) / 2
        }
        transformationMatrix.reset()
        transformationMatrix.setScale(scaleFactor, scaleFactor)
        transformationMatrix.postTranslate(-postScaleWidthOffset, -postScaleHeightOffset)
        if (isImageFlipped) {
            transformationMatrix.postScale(-1f, 1f, width / 2f, height / 2f)
        }
        needUpdateTransformation = false
    }

    fun onUpdatePoints(contours: List<PointF>, boundingBox: Rect) {
        this.points = contours
        this.boundingBox = boundingBox
        this.invalidate()
    }
}
package com.example.touch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View




class TouchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private val circleRadius = 100f // Tamaño del círculo

    // Lista para almacenar las posiciones de los toques
    private val touchPoints = mutableListOf<Pair<Float, Float>>()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_MOVE -> {
                // Limpiar la lista para actualizarla con las nuevas posiciones de todos los dedos
                touchPoints.clear()

                // Recorre todos los toques activos
                for (i in 0 until event.pointerCount) {
                    val x = event.getX(i)
                    val y = event.getY(i)
                    touchPoints.add(Pair(x, y))
                }
                invalidate() // Redibuja la vista
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                // Elimina el toque levantado, pero sigue mostrando los otros
                touchPoints.clear()
                for (i in 0 until event.pointerCount) {
                    if (i != event.actionIndex) {
                        val x = event.getX(i)
                        val y = event.getY(i)
                        touchPoints.add(Pair(x, y))
                    }
                }
                invalidate() // Redibuja la vista
            }
            MotionEvent.ACTION_CANCEL -> {
                touchPoints.clear() // Borra todos los toques si se cancela la interacción
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Dibuja un círculo en cada punto de toque
        for (point in touchPoints) {
            canvas.drawCircle(point.first, point.second, circleRadius, paint)
        }
    }
}


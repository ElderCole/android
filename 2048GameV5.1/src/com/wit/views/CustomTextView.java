package com.wit.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {

	public CustomTextView(Context context) {
		super(context);
	}

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());

		// »­padding±ß¿ò
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(20);
		paint.setColor(Color.GRAY);
		canvas.drawRect(rect, paint);

		// »­×óÉÏ±ß¿ò
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(2);
		paint.setColor(Color.LTGRAY);
		canvas.drawLine(2, 2, 2, getMeasuredHeight() - 2, paint);
		canvas.drawLine(2, 2, getMeasuredWidth() - 2, 2, paint);

		// »­ÓÒÏÂ±ß¿ò
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLACK);
		canvas.drawLine(getMeasuredWidth() - 2, 2, getMeasuredWidth() - 2,
				getMeasuredHeight() - 2, paint);
		canvas.drawLine(2, getMeasuredHeight() - 2, getMeasuredWidth() - 2,
				getMeasuredHeight() - 2, paint);

		// »­ÄÚÔ²¾ØÐÎ±ß¿ò
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(2);
		RectF rectF = new RectF(10, 10, getMeasuredWidth() - 10,
				getMeasuredHeight() - 10);
		canvas.drawRoundRect(rectF, 4, 4, paint);

		super.onDraw(canvas);
	}

}

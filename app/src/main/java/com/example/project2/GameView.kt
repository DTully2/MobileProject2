package com.example.project2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet?=null) : SurfaceView(mContext, attrs), Runnable {
    private var mSurfaceHolder: SurfaceHolder
    private val mPaint: Paint
    private val mPath: Path
    private var mViewWidth = 0
    private var mViewHeight = 0
    private var mFlashlightCone:FlashLightCone?=null
    private var mBitmap: Bitmap?=null
    private var mBitmapX = 0
    private var mBitmapY = 0
    private var mWinnerRect: RectF?=null
    private var mRunning = false
    private var mGameThread: Thread? = null

    init{
        mSurfaceHolder = holder
        mPaint = Paint()
        mPaint.color = Color.DKGRAY
        mPath = Path()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewWidth = w
        mViewHeight = h
        mFlashlightCone= FlashLightCone(mViewWidth, mViewHeight)
        mPaint.textSize = (mViewHeight/5).toFloat()
        mBitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.treasure)
        setUpBitmap()
    }
    private fun setUpBitmap(){
        mBitmapX = Math.floor(Math.random()*(mViewWidth-mBitmap!!.width)).toInt()
        mBitmapY = Math.floor(Math.random()*(mViewHeight-mBitmap!!.height)).toInt()
        mWinnerRect = RectF(
            mBitmapX.toFloat(),
            mBitmapY.toFloat(),
            (mBitmapX+mBitmap!!.width).toFloat(),
            (mBitmapY+mBitmap!!.height).toFloat()
        )
    }

    fun pause(){
        mRunning = false
        try{
            mGameThread!!.join()
        } catch(e:InterruptedException){

        }
    }

    fun resume(){
        mRunning = true
        mGameThread = Thread(this)
        mGameThread!!.start()
    }

    private fun updateFrame(newX:Int, newY:Int){
        mFlashlightCone!!.update(newX,newY)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                setUpBitmap()
                updateFrame(x.toInt(),y.toInt())
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                updateFrame(x.toInt(),y.toInt())
                invalidate()
            }
            else ->{}
        }
        //return super.onTouchEvent(event)
        return true
    }

    override fun run() {
        var canvas: Canvas
        while (mRunning){
            if (mSurfaceHolder.surface.isValid){
                val  x:Int? = mFlashlightCone?.getX()
                val y:Int? = mFlashlightCone?.getY()
                val radius:Int ? = mFlashlightCone?.getRadius()
                canvas = mSurfaceHolder.lockCanvas()
                canvas.save()
                canvas.drawColor(Color.GREEN)
                canvas.drawBitmap(mBitmap!!, mBitmapX.toFloat(), mBitmapY.toFloat(), mPaint)
                mPath.addCircle(x!!.toFloat(), y!!.toFloat(),radius!!.toFloat(), Path.Direction.CCW)
                canvas.clipOutPath(mPath)
                canvas.drawColor(Color.BLACK)
                val mFinalbitmap = BitmapFactory.decodeResource(resources, R.drawable.map)
                val bitty = Bitmap.createScaledBitmap(mFinalbitmap, canvas.width, canvas.height, true)
                canvas.drawBitmap(bitty, 0f, 0f, mPaint)
                if (x>mWinnerRect!!.left && x < mWinnerRect!!.right && y>mWinnerRect!!.top && y<mWinnerRect!!.bottom){
                    canvas.drawColor(Color.WHITE)
                    canvas.drawBitmap(mBitmap!!,mBitmapX.toFloat(),mBitmapY.toFloat(), mPaint)
                    canvas.drawText("WIN", (mViewWidth/3).toFloat(), (mViewHeight/2).toFloat(),mPaint)
                }
                //}
                mPath.rewind()
                canvas.restore()
                mSurfaceHolder.unlockCanvasAndPost(canvas)
            }
        }

    }
}

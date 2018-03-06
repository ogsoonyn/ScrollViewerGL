// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package sle.imagescroller;

import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;
import java.lang.reflect.Field;
import java.nio.*;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

// Referenced classes of package sle.imagescroller:
//            BackLightFlasher, TextureHelper, RawResourceReader, ShaderHelper

public class MainActivity extends AppCompatActivity
{
    private class MainRenderer
        implements android.opengl.GLSurfaceView.Renderer
    {

        private void initiateTextures()
        {
            Field afield[] = sle/imagescroller/R$drawable.getFields();
            int i = 0;
            while(i < afield.length) 
            {
                try
                {
                    if(afield[i].toString().contains("main"))
                        mMainTextureList.add(Integer.valueOf(TextureHelper.loadTexture(getApplicationContext(), afield[i].getInt(afield[i]))));
                }
                catch(IllegalAccessException illegalaccessexception)
                {
                    illegalaccessexception.printStackTrace();
                }
                i++;
            }
        }

        public void LoadMainImage(int i)
        {
            texture[0] = TextureHelper.loadTexture(getApplicationContext(), i);
        }

        public void SwipeDown()
        {
            direction = -1F;
        }

        public void SwipeLeft()
        {
            mainnumber = ((mainnumber - 1) + mMainTextureList.size()) % mMainTextureList.size();
        }

        public void SwipeRight()
        {
            mainnumber = (mainnumber + 1) % mMainTextureList.size();
        }

        public void SwipeUp()
        {
            direction = 1.0F;
        }

        public void onDrawFrame(GL10 gl10)
        {
            frameTimes[frameIndex] = (int)(System.nanoTime() - time);
            float f = 0.0F;
            for(int i = 0; i < frameTimes.length; i++)
                f += frameTimes[i];

            f /= frameTimes.length;
            scrollOffsetY = (scrollOffsetY + (int)(((scrollSpeed * f) / 3E+007F) * direction)) % 1280;
            time = System.nanoTime();
            frameIndex = (frameIndex + 1) % 100;
            GLES20.glClear(16640);
            GLES20.glUseProgram(mProgramHandle);
            mPositions.position(0);
            GLES20.glVertexAttribPointer(mPositionHandle, 3, 5126, false, 0, mPositions);
            GLES20.glEnableVertexAttribArray(mPositionHandle);
            mTextureCoordinates.position(0);
            GLES20.glVertexAttribPointer(mTextureCoordHandle, 2, 5126, false, 0, mTextureCoordinates);
            GLES20.glEnableVertexAttribArray(mTextureCoordHandle);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, ((Integer)mMainTextureList.get(mainnumber)).intValue());
            GLES20.glUniform1i(mSamplerHandle, 0);
            GLES20.glUniform2f(mScrollOffsetHandle, scrollOffsetX, scrollOffsetY);
            GLES20.glDrawArrays(4, 0, 6);
        }

        public void onSurfaceChanged(GL10 gl10, int i, int j)
        {
        }

        public void onSurfaceCreated(GL10 gl10, EGLConfig eglconfig)
        {
            initiateTextures();
            gl10 = RawResourceReader.readTextFileFromRawResource(getApplicationContext(), 0x7f060001);
            eglconfig = RawResourceReader.readTextFileFromRawResource(getApplicationContext(), 0x7f060000);
            mProgramHandle = ShaderHelper.createAndLinkProgram(ShaderHelper.compileShader(35633, gl10), ShaderHelper.compileShader(35632, eglconfig));
            mSamplerHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_Texture");
            mScrollOffsetHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_scrollOffset");
            mPositionHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_Position");
            mTextureCoordHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_TexCoordinate");
            gl10 = new float[18];
            GL10 _tmp = gl10;
            gl10[0] = -1F;
            gl10[1] = 1.0F;
            gl10[2] = 1.0F;
            gl10[3] = -1F;
            gl10[4] = -1F;
            gl10[5] = 1.0F;
            gl10[6] = 1.0F;
            gl10[7] = 1.0F;
            gl10[8] = 1.0F;
            gl10[9] = -1F;
            gl10[10] = -1F;
            gl10[11] = 1.0F;
            gl10[12] = 1.0F;
            gl10[13] = -1F;
            gl10[14] = 1.0F;
            gl10[15] = 1.0F;
            gl10[16] = 1.0F;
            gl10[17] = 1.0F;
            eglconfig = new float[12];
            EGLConfig _tmp1 = eglconfig;
            eglconfig[0] = 0.0F;
            eglconfig[1] = 0.0F;
            eglconfig[2] = 0.0F;
            eglconfig[3] = 1.0F;
            eglconfig[4] = 1.0F;
            eglconfig[5] = 0.0F;
            eglconfig[6] = 0.0F;
            eglconfig[7] = 1.0F;
            eglconfig[8] = 1.0F;
            eglconfig[9] = 1.0F;
            eglconfig[10] = 1.0F;
            eglconfig[11] = 0.0F;
            mPositions = ByteBuffer.allocateDirect(gl10.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            mPositions.put(gl10).position(0);
            mTextureCoordinates = ByteBuffer.allocateDirect(eglconfig.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            mTextureCoordinates.put(eglconfig).position(0);
        }

        float direction;
        int mPositionHandle;
        FloatBuffer mPositions;
        int mProgramHandle;
        int mSamplerHandle;
        int mScrollOffsetHandle;
        int mTextureCoordHandle;
        FloatBuffer mTextureCoordinates;
        int mainnumber;
        int scrollOffsetX;
        int scrollOffsetY;
        int texture[];
        final MainActivity this$0;
        float zoomLevel;

        private MainRenderer()
        {
            this$0 = MainActivity.this;
            super();
            zoomLevel = 1.0F;
            texture = new int[3];
            scrollOffsetX = 0;
            scrollOffsetY = 0;
            direction = 1.0F;
            mainnumber = 0;
        }

    }


    public MainActivity()
    {
        mMainTextureList = new ArrayList();
        scrollSpeed = 25F;
        flashing = false;
        frameIndex = 0;
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f040019);
        getWindow().getDecorView().findViewById(0x1020002).setSystemUiVisibility(3846);
        time = System.nanoTime();
        frameTimes = new int[100];
        for(int i = 0; i < frameTimes.length; i++)
            frameTimes[i] = 10;

        OpenGLsurface = new GLSurfaceView(this);
        OpenGLsurface.setEGLContextClientVersion(2);
        renderer = new MainRenderer();
        OpenGLsurface.setRenderer(renderer);
        setContentView(OpenGLsurface);
        OpenGLsurface.setRenderMode(1);
        tvSpeed = new TextView(this);
        tvSpeed.setX(10F);
        tvSpeed.setY(1020F);
        tvSpeed.setText(Integer.toString((int)scrollSpeed * 2));
        sbSpeed = new SeekBar(this);
        sbSpeed.setMax(50);
        sbSpeed.setX(-470F);
        sbSpeed.setY(500F);
        sbSpeed.setRotation(270F);
        sbSpeed.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekbar, int j, boolean flag)
            {
                if(flag)
                {
                    scrollSpeed = j;
                    tvSpeed.setText(Integer.toString((int)scrollSpeed * 2));
                }
            }

            public void onStartTrackingTouch(SeekBar seekbar)
            {
            }

            public void onStopTrackingTouch(SeekBar seekbar)
            {
            }

            final MainActivity this$0;

            
            {
                this$0 = MainActivity.this;
                super();
            }
        }
);
        sbSpeed.setProgress((int)scrollSpeed);
        addContentView(sbSpeed, new android.app.ActionBar.LayoutParams(1000, 60));
        addContentView(tvSpeed, new android.view.ViewGroup.LayoutParams(50, 100));
        backLightFlasher = new BackLightFlasher();
        flashingbutton = new Button(this);
        flashingbutton.setText("Flash");
        flashingbutton.setX(20F);
        flashingbutton.setY(1150F);
        flashingbutton.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                view = MainActivity.this;
                int j;
                boolean flag;
                if(!flashing)
                    flag = true;
                else
                    flag = false;
                view.flashing = flag;
                view = flashingbutton.getBackground();
                if(flashing)
                    j = 0xff00ff00;
                else
                    j = 0xffff0000;
                view.setColorFilter(j, android.graphics.PorterDuff.Mode.MULTIPLY);
                if(flashing)
                {
                    backLightFlasher.startFlashing();
                    return;
                } else
                {
                    backLightFlasher.stopFlashing();
                    return;
                }
            }

            final MainActivity this$0;

            
            {
                this$0 = MainActivity.this;
                super();
            }
        }
);
        addContentView(flashingbutton, new android.view.ViewGroup.LayoutParams(150, 100));
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        android.view.MotionEvent.PointerCoords pointercoords;
        motionevent.getPointerCount();
        int i = motionevent.getActionIndex();
        motionevent.getPointerId(i);
        motionevent.getAction();
        pointercoords = new android.view.MotionEvent.PointerCoords();
        motionevent.getPointerCoords(i, pointercoords);
        motionevent.getAction() & 0xff;
        JVM INSTR tableswitch 0 1: default 64
    //                   0 190
    //                   1 70;
           goto _L1 _L2 _L3
_L1:
        return super.onTouchEvent(motionevent);
_L3:
        if((double)(pointercoords.x - downEventCoords.x) > 150D)
            renderer.SwipeRight();
        else
        if((double)(pointercoords.x - downEventCoords.x) < -150D)
            renderer.SwipeLeft();
        else
        if((double)(pointercoords.y - downEventCoords.y) > 150D)
            renderer.SwipeDown();
        else
        if((double)(pointercoords.y - downEventCoords.y) < -150D)
            renderer.SwipeUp();
        continue; /* Loop/switch isn't completed */
_L2:
        downEventCoords = pointercoords;
        getWindow().getDecorView().findViewById(0x1020002).setSystemUiVisibility(3846);
        if(true) goto _L1; else goto _L4
_L4:
    }

    public static SeekBar sbSpeed;
    GLSurfaceView OpenGLsurface;
    BackLightFlasher backLightFlasher;
    android.view.MotionEvent.PointerCoords downEventCoords;
    boolean flashing;
    Button flashingbutton;
    int frameIndex;
    int frameTimes[];
    List mMainTextureList;
    MainRenderer renderer;
    float scrollSpeed;
    long time;
    TextView tvSpeed;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package sle.imagescroller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

class TextureHelper
{

    TextureHelper()
    {
    }

    private static void checkGlError(String s)
    {
        int i = GLES20.glGetError();
        if(i != 0)
        {
            Log.e("GLError", (new StringBuilder()).append(s).append(": glError ").append(i).toString());
            throw new RuntimeException((new StringBuilder()).append(s).append(": glError ").append(i).toString());
        } else
        {
            return;
        }
    }

    public static int loadTexture(Context context, int i)
    {
        int ai[] = new int[1];
        GLES20.glGenTextures(1, ai, 0);
        if(ai[0] != 0)
        {
            android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
            options.inScaled = false;
            context = BitmapFactory.decodeResource(context.getResources(), i, options);
            GLES20.glBindTexture(3553, ai[0]);
            GLES20.glTexParameteri(3553, 10241, 9728);
            GLES20.glTexParameteri(3553, 10240, 9728);
            GLUtils.texImage2D(3553, 0, context, 0);
            context.recycle();
            checkGlError("Textures loaded");
        }
        if(ai[0] == 0)
            throw new RuntimeException("Error loading texture.");
        else
            return ai[0];
    }
}

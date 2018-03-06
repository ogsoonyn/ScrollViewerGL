// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package sle.imagescroller;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

// Referenced classes of package sle.imagescroller:
//            RawResourceReader

public class ShaderHelper
{

    public ShaderHelper()
    {
    }

    public static int CompileProgram(int i, int j, Context context)
    {
        return createAndLinkProgram(compileShader(35633, RawResourceReader.readTextFileFromRawResource(context, i)), compileShader(35632, RawResourceReader.readTextFileFromRawResource(context, j)));
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

    public static int compileShader(int i, String s)
    {
        int j = GLES20.glCreateShader(i);
        i = j;
        if(j != 0)
        {
            GLES20.glShaderSource(j, s);
            GLES20.glCompileShader(j);
            s = new int[1];
            GLES20.glGetShaderiv(j, 35713, s, 0);
            i = j;
            if(s[0] == 0)
            {
                Log.e("ShaderHelper", (new StringBuilder()).append("Error compiling shader: ").append(GLES20.glGetShaderInfoLog(j)).toString());
                GLES20.glDeleteShader(j);
                i = 0;
            }
        }
        if(i == 0)
            throw new RuntimeException("Error creating shader.");
        else
            return i;
    }

    public static int createAndLinkProgram(int i, int j)
    {
        int k = GLES20.glCreateProgram();
        if(k == 0)
            throw new RuntimeException("Error creating program.");
        GLES20.glAttachShader(k, i);
        GLES20.glAttachShader(k, j);
        checkGlError("About to Link");
        GLES20.glLinkProgram(k);
        int ai[] = new int[1];
        GLES20.glGetProgramiv(k, 35714, ai, 0);
        i = k;
        if(ai[0] == 0)
        {
            Log.e("ShaderHelper", (new StringBuilder()).append("Error compiling program: ").append(GLES20.glGetProgramInfoLog(k)).toString());
            GLES20.glDeleteProgram(k);
            i = 0;
        }
        return i;
    }

    private static final String TAG = "ShaderHelper";
}

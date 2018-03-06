// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package sle.imagescroller;

import android.util.Log;
import java.io.InputStream;
import java.io.OutputStream;

public class BackLightFlasher
{

    public BackLightFlasher()
    {
        actHi = true;
        flashWidth = 100;
        flashOffset = 0;
        flashOffBrightness = 34;
        flashOnBrightness = 110;
        initialise();
    }

    private static void adbecho(String s)
    {
        try
        {
            Process process = Runtime.getRuntime().exec(new String[] {
                "/system/bin/sh", "-c", (new StringBuilder()).append("echo ").append(s).append(" > /proc/driver/SHDISP").toString()
            });
            process.waitFor();
            process.getInputStream().close();
            process.getOutputStream().close();
            process.getErrorStream().close();
            process.destroy();
            return;
        }
        catch(Exception exception)
        {
            Log.d("Failed", s);
        }
    }

    void initialise()
    {
        adbecho("9002ec50b0");
        stopFlashing();
    }

    public void startFlashing()
    {
        String s;
        String s1;
        String s2;
        if(actHi)
            adbecho("20052d");
        else
            adbecho("200524");
        s = String.format("%1$-2s", new Object[] {
            Integer.toHexString(flashOffset)
        });
        s1 = String.format("%1$-2s", new Object[] {
            Integer.toHexString(flashWidth)
        });
        s2 = String.format("%1$-2s", new Object[] {
            Integer.toHexString(flashOnBrightness)
        });
        adbecho("9002ec50b0");
        adbecho((new StringBuilder()).append("200a").append(s2).toString());
        adbecho((new StringBuilder()).append("9003ed01").append(s).append(s1).toString());
    }

    public void stopFlashing()
    {
        String s = String.format("%1$-2s", new Object[] {
            Integer.toHexString(flashOffBrightness)
        });
        adbecho((new StringBuilder()).append("200a").append(s).toString());
        adbecho("20052d");
        adbecho("200520");
    }

    boolean actHi;
    int flashOffBrightness;
    int flashOffset;
    int flashOnBrightness;
    public int flashWidth;
}

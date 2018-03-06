// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package sle.imagescroller;

import android.content.Context;
import android.content.res.Resources;
import java.io.*;

public class RawResourceReader
{

    public RawResourceReader()
    {
    }

    public static String readTextFileFromInputStream(Context context, InputStream inputstream)
    {
        context = new BufferedReader(new InputStreamReader(inputstream));
        inputstream = new StringBuilder();
_L1:
        String s;
        try
        {
            s = context.readLine();
        }
        // Misplaced declaration of an exception variable
        catch(Context context)
        {
            return null;
        }
        if(s == null)
            break MISSING_BLOCK_LABEL_52;
        inputstream.append(s);
        inputstream.append('\n');
          goto _L1
        return inputstream.toString();
    }

    public static String readTextFileFromRawResource(Context context, int i)
    {
        StringBuilder stringbuilder;
        context = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(i)));
        stringbuilder = new StringBuilder();
_L1:
        String s;
        try
        {
            s = context.readLine();
        }
        // Misplaced declaration of an exception variable
        catch(Context context)
        {
            return null;
        }
        if(s == null)
            break MISSING_BLOCK_LABEL_59;
        stringbuilder.append(s);
        stringbuilder.append('\n');
          goto _L1
        return stringbuilder.toString();
    }
}

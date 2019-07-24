package com.pretty.strawberry.utils;

import android.content.Context;

import java.io.File;

public class utils {

    private static File rootDir;

    public static String getFilenameFromPath(String filepath)
    {
        if (!isFileNonEmptyExtension(filepath)) return filepath;
        String filename = null;
        int lastSeparatorIndex = filepath.lastIndexOf(File.separator);
        if (lastSeparatorIndex == -1)
        {
            filename = filepath;
        }
        else
        {
            filename = filepath.substring(lastSeparatorIndex + 1);
        }
        return filename;
    }
    public static boolean isFileNonEmptyExtension(String filepath)
    {
        String extension = getFileExtension(filepath);
        if (extension != null && extension.length() > 0) return true;
        return false;
    }

    public static String getFileExtension(String filePath)
    {
        if (filePath == null)
        {
            return null;
        }
        String extension = "";
        int i = filePath.lastIndexOf('.');
        int p = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));
        if (i > p)
        {
            extension = filePath.substring(i + 1);
        }
        return extension;
    }
    public static File getRootDir(Context context)
    {
        if (rootDir == null) {
            rootDir = context != null ? context.getFilesDir() : null;
        }
        return rootDir;
    }

}

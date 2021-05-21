package me.matamor.generalapi.api.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class IOUtils {

    public static InputStreamReader createReader(File file) throws FileNotFoundException {
        return new InputStreamReader(new BufferedInputStream(new FileInputStream(file)), StandardCharsets.UTF_8);
    }

    public static OutputStreamWriter createWriter(File file) throws FileNotFoundException {
        return new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)), StandardCharsets.UTF_8);
    }
}
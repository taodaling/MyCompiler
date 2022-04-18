package org.dalingtao;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {
    public static String readAll(InputStream is, Charset charset) throws IOException {
        byte[] data = new byte[1 << 13];
        ByteArrayOutputStream bis = new ByteArrayOutputStream();
        int len;
        while ((len = is.read(data)) != -1) {
            bis.write(data, 0, len);
        }
        return bis.toString(charset);
    }

    public static String readClassResource(String path, Charset charset, ClassLoader loader) throws IOException {
        try (var is = loader.getResourceAsStream(path)) {
            return readAll(is, charset);
        }
    }

    public static String readApplicationClassResource(String path, Charset charset) throws IOException {
        return readClassResource(path, charset, IOUtil.class.getClassLoader());
    }


    public static String readAll(File file, Charset charset) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return readAll(fis, charset);
        }
    }

    public static String readAll(File file) throws IOException {
        return readAll(file, StandardCharsets.UTF_8);
    }

    public static String readAll(String file) throws IOException {
        return readAll(new File(file));
    }

    public static String readAll(String file, Charset charset) throws IOException {
        return readAll(new File(file), charset);
    }

    public static void writeAll(OutputStream os, byte[] data) throws IOException {
        os.write(data);
    }

    public static void writeAll(File file, byte[] data) throws IOException {
        try (var os = new FileOutputStream(file)) {
            writeAll(os, data);
        }
    }

    public static void writeAll(String file, byte[] data) throws IOException {
        writeAll(new File(file), data);
    }

    public static List<String> readLines(InputStream is, Charset charset, boolean skipBlankLine) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
        List<String> ans = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isBlank() && skipBlankLine) {
                continue;
            }
            ans.add(line);
        }
        return ans;
    }
}

package net.nonswag.tnl.core.api.file.helper;

import net.nonswag.tnl.core.api.errors.DownloadException;
import net.nonswag.tnl.core.api.logger.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class FileDownloader {

    private FileDownloader() {
    }

    public static void download(@Nonnull URL url, @Nonnull File directory) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String disposition = connection.getHeaderField("Content-Disposition");
            if (disposition == null) throw new DownloadException("content disposition is missing");
            if (!disposition.contains("filename=")) throw new DownloadException("no filename found");
            String[] split = disposition.split("filename=");
            String fileName = split[1].split(" ")[0];
            InputStream inputStream = connection.getInputStream();
            File file = new File(directory.getAbsolutePath(), fileName);
            FileHelper.createFile(file);
            FileOutputStream outputStream = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[4096];
            Logger.debug.println("Starting download of file <'" + fileName + "'>");
            while ((bytesRead = inputStream.read(buffer)) != -1) outputStream.write(buffer, 0, bytesRead);
            outputStream.close();
            inputStream.close();
            Logger.debug.println("Successfully downloaded file <'" + fileName + "'>");
        } else Logger.error.println("No file was found. Server replied HTTP code: " + responseCode);
        connection.disconnect();
    }

    public static void download(@Nonnull String url, @Nonnull String directory) throws IOException {
        download(url, new File(directory));
    }

    public static void download(@Nonnull String url, @Nonnull File directory) throws IOException {
        download(new URL(url), directory);
    }
}

package util;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.File;
import java.io.IOException;

public class DownloadFile {
    public static void download(String filePath, String url) throws IOException {
        File fileDownload = new File(filePath);
        Response response = Request.Get(url).execute();
        response.saveContent(fileDownload);
    }
}

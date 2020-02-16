package assignment4_Tweet_Analysis_From_HTTP_Request;

import okhttp3.*;

import java.io.IOException;

	
public class OkHttpGetPost {

    // one instance, reuse
    private final OkHttpClient httpClient = new OkHttpClient();
    final static String URLEndpoint = "http://kevinstwitterclient2.azurewebsites.net/api/products";


    protected String sendGet(String url) throws Exception {

    	Request request = new Request.Builder().url(url).build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }

    }


}


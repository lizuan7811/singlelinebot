package singlelinebot.util;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

public class HttpUtil {
	
	private static final int minCONNECT_TIMEOUT=3000;
	
	private static final int minWRITE_TIMEOUT=3000;
	
	private static final int minREAD_TIMEOUT=3000;
	
	private static final int minCALL_TIMEOUT=3000;
	
	private static final int connectionPoolSize=1000;
	
	private static ConnectionPool mConnectionPool=new ConnectionPool(connectionPoolSize,30,TimeUnit.MINUTES);
	
	public static OkHttpClient getOkHttpClient() {
		return new OkHttpClient.Builder()
				.connectTimeout(minCONNECT_TIMEOUT,TimeUnit.MILLISECONDS)
				.readTimeout(minREAD_TIMEOUT,TimeUnit.MILLISECONDS)
				.writeTimeout(minWRITE_TIMEOUT,TimeUnit.MILLISECONDS)
				.callTimeout(minCALL_TIMEOUT,TimeUnit.MILLISECONDS)
				.connectionPool(mConnectionPool)
				.build();
	}
	

}

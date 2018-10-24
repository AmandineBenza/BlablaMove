package com.xaamruda.bbm.commons.json;

import org.springframework.http.converter.json.GsonBuilderUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public final class JsonUtils {

	private static Gson gson = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().create();
	
	private JsonUtils() {}
	
	public static <T> String toJson(T o) {
		return gson.toJson(o);
	}
	
	public static JsonObject getFromJson(String jsonContent) {
		return gson.fromJson(jsonContent, JsonObject.class);
	}
	
	public static <T> T getFromJson(String jsonContent, Class<T> clazz) {
		return gson.fromJson(jsonContent, clazz);
	}
	
}

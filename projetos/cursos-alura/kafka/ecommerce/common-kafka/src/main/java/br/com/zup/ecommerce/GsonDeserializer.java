package br.com.zup.ecommerce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GsonDeserializer<T> implements Deserializer<T> {

    public static final String TYPE_CONFIG = "br.com.zup.ecommerce.type_config";
    private final Gson gson = new GsonBuilder().create();
    private Class<T> type;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String typeName = String.valueOf(configs.get(TYPE_CONFIG));
        try {
            type = (Class<T>) Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro de deserialização");
        }
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        return gson.fromJson(new String(data), type);
    }
}

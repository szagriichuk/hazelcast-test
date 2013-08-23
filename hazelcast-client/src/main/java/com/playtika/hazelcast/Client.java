package com.playtika.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.playtika.hazelcast.task.ReadTask;
import com.playtika.hazelcast.task.TestingTask;

/**
 * @author szagriichuk
 */
public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        PlaytikaProperties properties = client.loadProperties("client.properties");
        ClientConfig clientConfig = client.createClientConfig(properties);
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
        properties.put("loop.count", String.valueOf(hazelcastInstance.getMap("test").size()));
        for (int i = 0; i < 100; i++) {
            client.readDataFromMap(properties, hazelcastInstance);
        }
    }

    private void readDataFromMap(PlaytikaProperties properties, HazelcastInstance hazelcastInstance) {
        TestingTask testingTask = new ReadTask();
        testingTask.executeTask("test", hazelcastInstance, properties);
    }

    private ClientConfig createClientConfig(PlaytikaProperties properties) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.addAddress(properties.get("address"));
        return clientConfig;
    }

    private PlaytikaProperties loadProperties(String fileName) {
        return new PlaytikaProperties(fileName);
    }
}

package com.playtika.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author szagriichuk
 */
public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        Server server = new Server();
        PlaytikaProperties playtikaProperties = server.readProperties("server.properties");
        Config config = server.createConfig(playtikaProperties);
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        writeToMap("test", hazelcastInstance, playtikaProperties);
        readFromMap("test", hazelcastInstance, playtikaProperties);
    }

    private static void readFromMap(String mapId, HazelcastInstance hazelcastInstance, PlaytikaProperties playtikaProperties) {
        LOGGER.info("Start reading from Map");
        IMap<Integer, String> stringMap = hazelcastInstance.getMap(mapId);
        int loopCount = Integer.parseInt(playtikaProperties.get("loop.count"));
        long startTime = System.nanoTime();
        for (int i = 0; i < loopCount; i++) {
            stringMap.get(i);
        }
        System.out.println("Reading time is " + (System.nanoTime() - startTime) / 1000000000 + " sec.");
    }

    private static void writeToMap(String mapId, HazelcastInstance hazelcastInstance, PlaytikaProperties playtikaProperties) {
        LOGGER.info("Start writing to Map");
        IMap<Integer, String> stringMap = hazelcastInstance.getMap(mapId);
        int loopCount = Integer.parseInt(playtikaProperties.get("loop.count"));
        String text = playtikaProperties.get("text");
        long startTime = System.nanoTime();
        for (int i = 0; i < loopCount; i++) {
            stringMap.put(i, text + i);
        }
        System.out.println("Writing time is " + (System.nanoTime() - startTime) / 1000000000 + " sec.");
    }

    private PlaytikaProperties readProperties(String fileName) {
        return new PlaytikaProperties(fileName);
    }

    private Config createConfig(PlaytikaProperties playtikaProperties) {
        return null;  //TODO: implement configurable server
    }
}

package com.playtika.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.playtika.hazelcast.task.ReadTask;
import com.playtika.hazelcast.task.TestingTask;
import com.playtika.hazelcast.task.WritingTask;

/**
 * @author szagriichuk
 */
public class Server {
    public static void main(String[] args) {
        Server server = new Server();
        PlaytikaProperties playtikaProperties = server.readProperties("server.properties");
        Config config = server.createConfig(playtikaProperties);
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        server.writeToMap("test", hazelcastInstance, playtikaProperties);
        server.readFromMap("test", hazelcastInstance, playtikaProperties);
    }

    private void readFromMap(String mapId, HazelcastInstance hazelcastInstance, PlaytikaProperties playtikaProperties) {
        TestingTask testingTask = new ReadTask();
        testingTask.executeTask(mapId, hazelcastInstance, playtikaProperties);
    }

    private void writeToMap(String mapId, HazelcastInstance hazelcastInstance, PlaytikaProperties playtikaProperties) {
        TestingTask testingTask = new WritingTask();
        testingTask.executeTask(mapId, hazelcastInstance, playtikaProperties);
    }

    private PlaytikaProperties readProperties(String fileName) {
        return new PlaytikaProperties(fileName);
    }

    private Config createConfig(PlaytikaProperties playtikaProperties) {
        return null;  //TODO: implement configurable server
    }
}

package com.playtika.hazelcast.task;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.playtika.hazelcast.PlaytikaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author szagriichuk
 */
public class ReadTask implements TestingTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadTask.class);

    @Override
    public void executeTask(String mapId, HazelcastInstance hazelcastInstance, PlaytikaProperties playtikaProperties) {
        LOGGER.info("Start reading from Map");
        IMap<Integer, String> stringMap = hazelcastInstance.getMap(mapId);
        int loopCount = Integer.parseInt(playtikaProperties.get("loop.count"));
        long startTime = System.nanoTime();
        for (int i = 0; i < loopCount; i++) {
            stringMap.get(i);
        }
        LOGGER.info("Reading time is " + (System.nanoTime() - startTime) / 1000000000 + " sec.");
    }
}

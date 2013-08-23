package com.playtika.hazelcast.task;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.playtika.hazelcast.PlaytikaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author szagriichuk
 */
public class WritingTask implements TestingTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(WritingTask.class);

    @Override
    public void executeTask(String mapId, HazelcastInstance hazelcastInstance, PlaytikaProperties playtikaProperties) {
        LOGGER.info("Start writing to Map");
        IMap<Integer, String> stringMap = hazelcastInstance.getMap(mapId);
        int loopCount = Integer.parseInt(playtikaProperties.get("loop.count"));
        String text = playtikaProperties.get("text");
        long startTime = System.nanoTime();
        for (int i = 0; i < loopCount; i++) {
            stringMap.put(i, text + i);
        }
        LOGGER.info("Writing time is " + (System.nanoTime() - startTime) / 1000000000 + " sec.");

    }
}

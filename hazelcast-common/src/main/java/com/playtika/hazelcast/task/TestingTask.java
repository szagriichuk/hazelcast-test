package com.playtika.hazelcast.task;

import com.hazelcast.core.HazelcastInstance;
import com.playtika.hazelcast.PlaytikaProperties;

/**
 * @author szagriichuk
 */
public interface TestingTask {
    void executeTask(String mapId, HazelcastInstance hazelcastInstance, PlaytikaProperties playtikaProperties);
}

package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        var rsl = memory.computeIfPresent(model.getId(),
                (key, value) -> {
                    if (value.getVersion() != model.getVersion()) {
                        throw new OptimisticException("Version are not equals");
                    }
                    Base newModel = new Base(key, value.getVersion() + 1);
                    newModel.setName(model.getName());
                    return newModel;
                });
        return rsl != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Map<Integer, Base> getMemory() {
        return memory;
    }

    public int size() {
        return memory.size();
    }
}

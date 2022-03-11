package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private ConcurrentHashMap<String,
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queue
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp = null;
        if ("GET".equals(req.httpRequestType())) {
            if (queue.putIfAbsent(req.getSourceName(), queue.get(req.getSourceName())) != null) {
                resp = new Resp(
                        queue.get(req.getSourceName()).get(req.getParam()).poll(), "200 OK\r\n");
            } else {
                queue.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            }
        } else if ("POST".equals(req.httpRequestType())) {
            int x = 1;
        }
        return null;
    }
}

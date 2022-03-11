package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp = null;
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).add(req.getParam());
            resp = new Resp(req.getParam(), "200 OK\r\n");
        } else if ("GET".equals(req.httpRequestType())) {
            resp = new Resp(queue.get(req.getSourceName()).poll(), "200 OK\r\n");
        }
        return resp;
    }
}

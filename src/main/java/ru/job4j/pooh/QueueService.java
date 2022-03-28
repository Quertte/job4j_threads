package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp = new Resp("", "204 No Content");
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).add(req.getParam());
            resp = new Resp(req.getParam(), "200 OK");
        } else if ("GET".equals(req.httpRequestType())) {
            if (queue.get(req.getSourceName()) != null) {
                String text = queue.get(req.getSourceName()).poll();
                resp = text != null ? new Resp(text, "200 OK")
                        : resp;
            }
        }
        return resp;
    }
}

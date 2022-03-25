package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String,
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topic
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp = null;
        if ("GET".equals(req.httpRequestType())) {
            topic.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            if (topic.get(req.getSourceName()).get(req.getParam()) != null) {
                resp = new Resp(topic.get(req.getSourceName()).get(
                        req.getParam()).poll(), "200 OK\r\n");
            } else if (topic.get(req.getSourceName()).get(req.getParam()) == null) {
                topic.get(req.getSourceName()).putIfAbsent(
                        req.getParam(), new ConcurrentLinkedQueue<>());
                resp = new Resp("", "204 No Content\r\n");
            }
        } else if ("POST".equals(req.httpRequestType())) {
            if (topic.get(req.getSourceName()) == null) {
                resp = new Resp("", "204 No Content\r\n");
            } else {
                ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> val = topic.get(
                        req.getSourceName());
                for (ConcurrentLinkedQueue<String> clq : val.values()) {
                    clq.add(req.getParam());
                }
                resp = new Resp(req.getParam(), "200 OK\r\n");
            }
        }
        return resp;
    }
}

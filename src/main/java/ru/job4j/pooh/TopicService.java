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
            if (topic.get(req.getSourceName()).get(req.getParam()) == null) {
                resp = new Resp("", "204 No Content");
                topic.get(req.getSourceName()).putIfAbsent(
                        req.getParam(), new ConcurrentLinkedQueue<>());
            } else {
                resp = new Resp(topic.get(req.getSourceName()).get(
                        req.getParam()).poll(), "200 OK");
            }
        } else if ("POST".equals(req.httpRequestType())) {
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> val = topic.get(
                    req.getSourceName());
            if (val == null) {
                resp = new Resp("", "204 No Content");
            } else {
                for (ConcurrentLinkedQueue<String> clq : val.values()) {
                    clq.add(req.getParam());
                }
                resp = new Resp(req.getParam(), "200 OK");
            }
        }
        return resp;
    }
}

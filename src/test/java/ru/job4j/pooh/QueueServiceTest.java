package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class QueueServiceTest {
    @Test
    public void whenPostThenGetQueue() {
        QueueService service = new QueueService();
        String paramForPostMethod = "temperature=18";
        service.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = service.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
    }
}
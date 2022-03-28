package ru.job4j.pooh;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class QueueServiceTest {
    @Test
    public void whenPostThenGetQueue() {
        QueueService service = new QueueService();
        String paramForPostMethod = "temperature=18";
        /* Добавляем данные в очередь weather. Режим queue */
        service.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        /* Забираем данные из очереди weather. Режим queue */
        Resp result = service.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
    }

    @Test
    public void whenQueueServiceTesting() {
        QueueService queueService = new QueueService();
        String weatherSourceName = "weather";
        String trafficSourceName = "traffic";
        String otherSourceName = "other";
        String paramForWeatherSourceName = "temperature=18";
        String paramForTrafficSourceName = "cars=20188";

        queueService.process(
                new Req("POST", "queue", weatherSourceName, paramForWeatherSourceName)
        );
        queueService.process(
                new Req("POST", "queue", trafficSourceName, paramForTrafficSourceName)
        );
        Resp resultFromWeatherSource1 = queueService.process(
                new Req("GET", "queue", weatherSourceName, null)
        );
        Resp resultFromWeatherSource2 = queueService.process(
                new Req("GET", "queue", weatherSourceName, null)
        );
        Resp resultFromTrafficSource1 = queueService.process(
                new Req("GET", "queue", trafficSourceName, null)
        );
        Resp resultFromTrafficSource2 = queueService.process(
                new Req("GET", "queue", trafficSourceName, null)
        );
        Resp resultFromOtherSource1 = queueService.process(
                new Req("GET", "queue", otherSourceName, null)
        );
        assertThat(resultFromWeatherSource1.text(), Matchers.is("temperature=18"));
        assertThat(resultFromWeatherSource2.text(), Matchers.is(""));
        assertThat(resultFromTrafficSource1.text(), Matchers.is("cars=20188"));
        assertThat(resultFromTrafficSource2.text(), Matchers.is(""));
        assertThat(resultFromOtherSource1.text(), Matchers.is(""));
    }
}
package com.bartzilla.multithreadtasks.services.task;

import com.bartzilla.model.cron.CronTask;
import com.bartzilla.model.cron.CronTaskRepository;
import com.bartzilla.schedule.Counter;
import com.bartzilla.services.task.impl.CronTaskServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit Test for CronService
 *
 * @author Cipriano Sanchez
 */

@RunWith(MockitoJUnitRunner.class)
public class CronServiceTest {
    @Mock
    CronTaskRepository cronTaskRepository;

    @InjectMocks
    CronTaskServiceImpl cronTaskServiceImpl;

    @Test
    public void testCreateCronTask() {
        CronTask task = new CronTask("123", "cron-project", 1, 10, new Date(), false);
        when(cronTaskRepository.save(task)).thenReturn(task);

        final CronTask savedTask = cronTaskServiceImpl.createCronTask(task);

        assertEquals(savedTask, task);
    }

    @Test()
    public void executeCronTask() throws IllegalStateException {
        CronTask task = new CronTask("123", "cron-project", 1, 10, new Date(), false);
        final Map<String, Counter> counters = mock(ConcurrentHashMap.class);
        when(cronTaskRepository.save(task)).thenReturn(task);
        ReflectionTestUtils.setField(cronTaskServiceImpl, "counters", counters);

        cronTaskServiceImpl.executeCronTask(task);
        verify(counters, times(1)).put(eq(task.getId()), any(Counter.class));
        verify(counters, times(1)).containsKey(task.getId());
        verify(cronTaskRepository, times(1)).save(task);
    }

    @Test(expected = IllegalStateException.class)
    public void executeCronTaskWithException() throws IllegalStateException {
        CronTask task = new CronTask("123", "cron-project", 1, 10, new Date(), false);

        final Map<String, Counter> counters = new ConcurrentHashMap<>();
        ReflectionTestUtils.setField(cronTaskServiceImpl, "counters", counters);

        Counter counter = new Counter(task);
        counters.put(task.getId(), counter);

        cronTaskServiceImpl.executeCronTask(task);
    }

}

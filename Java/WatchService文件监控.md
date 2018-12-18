WatchService
======
简介
------
java1.7中 提供了WatchService来监控系统中文件的变化。该监控是基于操作系统的文件系统监控器，可以监控系统是所有文件的变化，这种监控是无需遍历、无需比较的，是一种基于信号收发的监控，因此效率一定是最高的；现在Java对其进行了包装，可以直接在Java程序中使用OS的文件系统监控器了。

用法
------
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;

/**
 * @author qinhao
 */
public class LogWatchService extends Thread {


    private static final Logger logger = LoggerFactory.getLogger(LogWatchService.class);
    private static final String uri = "/moni/";

    private WatchService watchService;
    private Path path;

    public LogWatchService() {
        try {
            watchService = FileSystems.getDefault().newWatchService();
            path = Paths.get(uri);
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            logger.error("logWatchService初始化异常.\n" + e.getMessage(), e);
        }
        Runtime.getRuntime().addShutdownHook(watchThread);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 存在问题: 修改一次文件但会触发两次的ENTRY_MODIFY事件
                WatchKey watchKey = watchService.take();
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    String context = event.kind() + ", " + event.context().toString() + "\n";
                    logger.info(context);
                }
                watchKey.reset();// 重置监听器,watchKey.take():Retrieves and removes next watch key, waiting if none are yet present.
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * JVM关闭前执行的线程
     */
    private Thread watchThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                watchService.close();
                logger.info("logWatchService已关闭.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
}

```
存在问题
------
**问题：** 使用WatchService监控文件系统时，修改一个文件（只修改一次），有时候会连续触发两次 EVENT_MODIFY 事件。
1. 可能是因为操作系统修改文件时会修改修改日期和文件内容(不确定)

**解决：**

package netty.router.service;

import io.netty.channel.ChannelPipeline;
import netty.sync.handler.FileSyncServerHandler;
import netty.sync.initializer.FileSyncProtocolInitializer;
import netty.websocket.WebSocketProtocolInitializer;
import netty.websocket.WebSocketServerHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 路由服务
 *
 * @author chen
 */
public class RouterService {

    private Map<Byte, Method> routeTable;

    public RouterService() {
        routeTable = new ConcurrentHashMap<>();
        try {
            routeTable.put((byte) 'G', this.getClass().getDeclaredMethod("webSocketService", ChannelPipeline.class));
            routeTable.put((byte) 'F', this.getClass().getDeclaredMethod("fileSyncService", ChannelPipeline.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        System.out.println(this.getClass().getSimpleName() + " init");
    }

    /**
     * 切换到websocket服务
     *
     * @param pipeline
     */
    private void webSocketService(ChannelPipeline pipeline) {
        pipeline.addLast(new WebSocketProtocolInitializer());
        pipeline.addLast(new WebSocketServerHandler());
    }

    /**
     * 切换到文件同步服务
     *
     * @param pipeline
     */
    private void fileSyncService(ChannelPipeline pipeline) {
        pipeline.addLast(new FileSyncProtocolInitializer());
        pipeline.addLast(new FileSyncServerHandler());
    }

    /**
     * 根据 magic(幻数) 进行选择响应的服务
     *
     * @param magic    幻数
     * @param pipeline 当前服务器的pipeline
     */
    public void route(byte magic, ChannelPipeline pipeline) {
        try {
            // 根据magic调用方法
            Method method = routeTable.get(magic);
            method.invoke(this, pipeline);

            System.out.println("select: " + method.getName());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

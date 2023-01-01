package netty.sync;

import java.nio.file.WatchEvent;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * 文件同步常量标识类
 */
public class Constant {

    /**
     * 文件路径传输的替换字符
     */
    public static final char replaceChar = '#';

    /**
     * 文件标识
     */
    public static final char FILE = 'F';

    /**
     * 目录标识
     */
    public static final char DIR = 'D';

    /**
     * 创建事件
     */
    public static final char CREATE = 'C';

    /**
     * 删除事件
     */
    public static final char DELETE = 'D';

    /**
     * 修改事件
     */
    public static final char MODIFY = 'M';

    /**
     * 项目根目录
     */
    public static final String root = System.getProperty("user.dir");


    /**
     * 文件监听事件类型  创建/删除/修改
     */
    public static final WatchEvent.Kind[] kinds = new WatchEvent.Kind[]{ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY};

    /**
     * 可传送文件的最大长度
     */
    public static final int maxLength = 65536;

    /**
     * 系统标识
     */
    public static final boolean os = System.getProperty("os.name").toLowerCase().contains("window");

}

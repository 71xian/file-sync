package netty.sync;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * 文件同步常量标识类
 */
public interface Constant {

    /**
     * 文件路径传输的替换字符
     */
    char replaceChar = '#';

    /**
     * 文件标识
     */
    char FILE = 'F';

    /**
     * 目录标识
     */
    char DIR = 'D';

    /**
     * 创建事件
     */
    char CREATE = 'C';

    /**
     * 删除事件
     */
    char DELETE = 'D';

    /**
     * 修改事件
     */
    char MODIFY = 'M';

    /**
     * 项目根目录
     */
    Path root = Path.of(".").normalize();


    /**
     * 文件监听事件类型  创建/删除/修改
     */
    WatchEvent.Kind[] kinds = new WatchEvent.Kind[]{ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY};

    /**
     * 可传送文件的最大长度
     */
    int maxLength = 65536;

    /**
     * 系统标识
     */
    boolean os = File.pathSeparatorChar == ';';

}

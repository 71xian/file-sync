package netty.listen;

import java.io.IOException;

public class CmdProcess {

    public void process(String cmd) throws IOException {
        Runtime.getRuntime().exec(cmd);
    }

}

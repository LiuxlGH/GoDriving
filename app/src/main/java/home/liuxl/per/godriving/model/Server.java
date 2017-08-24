package per.liuxl.home.godriving.model;

import fi.iki.elonen.NanoHTTPD;
import per.liuxl.home.godriving.service.ServerService;

/**
 * Created by Liuxl on 2017/8/23.
 */

public class Server extends NanoHTTPD {
    public Server(int serverPort, ServerService serverService) {
        super(serverPort);

    }
}

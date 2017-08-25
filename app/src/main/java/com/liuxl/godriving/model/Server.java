package com.liuxl.godriving.model;

import com.liuxl.godriving.service.ServerService;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Liuxl on 2017/8/23.
 */

public class Server extends NanoHTTPD {
    public Server(int serverPort, ServerService serverService) {
        super(serverPort);

    }
}

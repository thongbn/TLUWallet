package com.server.server;
 

 
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import com.server.cmd.syncBudget.*;
import com.server.cmd.getBudget.*;
 

public class FileServer
{
    public static void main( String[] args ) throws Exception
    {

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.setConnectors(new Connector[] { connector });
 

        ContextHandler readfile = new ContextHandler();
        readfile.setContextPath("/budget/getBudget");
        readfile.setHandler(new ReceiveGetBudgetHandler());
 

        ContextHandler syncfile = new ContextHandler();
        syncfile.setContextPath("/budget/syncBudget");
        syncfile.setHandler(new ReceiveSyncBugetHandler());
 

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] { readfile, syncfile });
        server.setHandler(contexts);
 

        server.start();
        server.join();
    }
}
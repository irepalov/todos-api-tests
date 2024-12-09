package api;

import Data.Stash;
import com.sun.istack.NotNull;
import todos_autotests.Props;
import okhttp3.*;
import org.aeonbits.owner.ConfigFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class WebSockets {
    private static Props props = ConfigFactory.create(Props.class);
    private OkHttpClient client;
    private WebSocket webSocket;
    private CountDownLatch latch;
    private AtomicReference<String> receivedMessage = new AtomicReference<>();


    /**
     * Connect to the WebSocket.
     */
    public void connect() throws Exception {
        String env = System.getProperty("environment");
        if (env == null || env.isEmpty()) {
            throw new IllegalArgumentException("[WebSockets] environment is not specified. Please provide it via Maven profiles.");
        }
        Stash.currentEnv = env;

        String wsUrl = System.getProperty("websockets.url");
        if (wsUrl == null || wsUrl.isEmpty()) {
            throw new IllegalArgumentException("[WebSockets] Base URL is not specified. Please provide it via Maven profiles.");
        }

        System.out.println("[WebSockets] connect called with wsUrl=" + wsUrl);
        client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(wsUrl).build();

        // initialize latch as soon as message appeared in websockets
        latch = new CountDownLatch(1);

        webSocket = client.newWebSocket(request, new WebSocketListener() {

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                System.out.println("[WebSockets] onOpen: WebSocket connection established");
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                System.out.println("[WebSockets] onMessage received text: " + text);
                // record message into wsResponseMessage
                Stash.wsResponseMessage = text;
                System.out.println("[WebSockets] onMessage: message saved to Stash.wsMessage");
                // Since message was received, release the latch
                receivedMessage.set(text);
                latch.countDown();
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                System.out.println("[WebSockets] onClosing: code=" + code + ", reason=" + reason);
                webSocket.close(1000, null);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
                System.out.println("[WebSockets] onFailure: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    /**
     * Wait for a message from the WebSocket for up to timeoutSec seconds.
     * If no message arrives, log an error and return null.
     */
    public String waitForMessageFromWs(int timeoutSec) throws InterruptedException {
        System.out.println("[WebSockets] waitForMessageFromWs called with timeoutSec=" + timeoutSec);
        boolean received = latch.await(timeoutSec, TimeUnit.SECONDS);
        if (received) {
            System.out.println("[WebSockets] waitForMessageFromWs: message received within timeout");
            return receivedMessage.get();
        } else {
            System.out.println("[WebSockets] waitForMessageFromWs: no message received within timeout");
            return null;
        }
    }

    /**
     * Disconnect the WebSocket.
     */
    public void disconnect() {
        System.out.println("[WebSockets] disconnect called");
        if (webSocket != null) {
            System.out.println("[WebSockets] closing websocket");
            webSocket.close(1000, "Done");
            webSocket = null;
        }
        if (client != null) {
            System.out.println("[WebSockets] shutting down client executor service");
            client.dispatcher().executorService().shutdown();
        }
        System.out.println("[WebSockets] WebSocket disconnected");
    }


}
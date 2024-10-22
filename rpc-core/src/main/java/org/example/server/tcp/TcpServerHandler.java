package org.example.server.tcp;

import io.vertx.core.Handler;
import io.vertx.core.net.NetSocket;

/**
 * Http请求处理
 * 动态代理
 */
public class TcpServerHandler implements Handler<NetSocket> {

    /**
     * 处理请求
     * @param netSocket
     */
    @Override
    public void handle(NetSocket netSocket) {
        //处理连接
        TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
            //处理请求代码
        });
        netSocket.handler(bufferHandlerWrapper);
    }


    /**
     * 响应
     * @param request
     * @param rpcResponse
     * @param serializer
     */
   /* private void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response()
                .putHeader("content-type", "application/json");
        try{
            //序列化
            byte[] serialized = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialized));
        }catch (IOException e){
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }

*/
}

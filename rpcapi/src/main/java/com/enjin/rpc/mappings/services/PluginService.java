package com.enjin.rpc.mappings.services;

import com.enjin.core.services.Service;
import com.enjin.rpc.EnjinRPC;
import com.enjin.rpc.mappings.deserializers.InstructionDeserializer;
import com.enjin.rpc.mappings.mappings.general.RPCData;
import com.enjin.rpc.mappings.mappings.plugin.Instruction;
import com.enjin.rpc.mappings.mappings.plugin.Status;
import com.enjin.rpc.mappings.mappings.plugin.SyncResponse;
import com.enjin.rpc.mappings.mappings.plugin.TagData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginService implements Service {
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Instruction.class, new InstructionDeserializer())
            .create();

    public RPCData<Boolean> auth(final String authkey, final int port, final boolean save) {
        String method = "Plugin.auth";
        Map<String, Object> parameters = new HashMap<String, Object>() {{
            put("authkey", authkey);
            put("port", port);
            put("save", save);
        }};

        int id = EnjinRPC.getNextRequestId();

        JSONRPC2Session session = null;
        JSONRPC2Request request = null;
        JSONRPC2Response response = null;

        try {
            session = EnjinRPC.getSession("minecraft.php");
            request = new JSONRPC2Request(method, parameters, id);
            response = session.send(request);

            EnjinRPC.debug("JSONRPC2 Request: " + request.toJSONString());
            EnjinRPC.debug("JSONRPC2 Response: " + response.toJSONString());

            RPCData<Boolean> data = EnjinRPC.gson.fromJson(response.toJSONString(), new TypeToken<RPCData<Boolean>>() {}.getType());
            data.setRequest(request);
            data.setResponse(response);
            return data;
        } catch (JSONRPC2SessionException e) {
            EnjinRPC.debug(e.getMessage());
            EnjinRPC.debug("Failed Request to " + session.getURL().toString() + ": " + request.toJSONString());
            return null;
        }
    }

    public RPCData<SyncResponse> sync(final String authkey, final Status status) {
        String method = "Plugin.sync";
        Map<String, Object> parameters = new HashMap<String, Object>() {{
            put("authkey", authkey);
            put("status", EnjinRPC.gson.fromJson(EnjinRPC.gson.toJson(status), Object.class));
        }};

        int id = EnjinRPC.getNextRequestId();

        JSONRPC2Session session = null;
        JSONRPC2Request request = null;
        JSONRPC2Response response = null;

        try {
            session = EnjinRPC.getSession("minecraft.php");
            request = new JSONRPC2Request(method, parameters, id);
            response = session.send(request);

            EnjinRPC.debug("JSONRPC2 Request: " + request.toJSONString());
            EnjinRPC.debug("JSONRPC2 Response: " + response.toJSONString());

            RPCData<SyncResponse> data = PluginService.gson.fromJson(response.toJSONString(), new TypeToken<RPCData<SyncResponse>>() {}.getType());
            data.setRequest(request);
            data.setResponse(response);
            return data;
        } catch (JSONRPC2SessionException e) {
            EnjinRPC.debug(e.getMessage());
            EnjinRPC.debug("Failed Request to " + session.getURL().toString() + ": " + request.toJSONString());
            return null;
        }
    }

    public RPCData<List<TagData>> getTags(final String authkey, final String player) {
        String method = "Plugin.getTags";
        Map<String, Object> parameters = new HashMap<String, Object>() {{
            put("authkey", authkey);
            put("player", player);
        }};

        int id = EnjinRPC.getNextRequestId();

        JSONRPC2Session session = null;
        JSONRPC2Request request = null;
        JSONRPC2Response response = null;

        try {
            session = EnjinRPC.getSession("minecraft.php");
            request = new JSONRPC2Request(method, parameters, id);
            response = session.send(request);

            EnjinRPC.debug("JSONRPC2 Request: " + request.toJSONString());
            EnjinRPC.debug("JSONRPC2 Response: " + response.toJSONString());

            RPCData<List<TagData>> data = EnjinRPC.gson.fromJson(response.toJSONString(), new TypeToken<RPCData<ArrayList<TagData>>>() {}.getType());
            data.setRequest(request);
            data.setResponse(response);
            return data;
        } catch (JSONRPC2SessionException e) {
            EnjinRPC.debug(e.getMessage());
            EnjinRPC.debug("Failed Request to " + session.getURL().toString() + ": " + request.toJSONString());
            return null;
        }
    }

    public RPCData<Boolean> setRank(final String authkey, final String player, final String group, final String world) {
        String method = "Plugin.setRank";
        Map<String, Object> parameters = new HashMap<String, Object>() {{
            put("authkey", authkey);
            put("player", player);
            put("group", group);
            put("world", world);
        }};

        int id = EnjinRPC.getNextRequestId();

        JSONRPC2Session session = null;
        JSONRPC2Request request = null;
        JSONRPC2Response response = null;

        try {
            session = EnjinRPC.getSession("minecraft.php");
            request = new JSONRPC2Request(method, parameters, id);
            response = session.send(request);

            EnjinRPC.debug("JSONRPC2 Request: " + request.toJSONString());
            EnjinRPC.debug("JSONRPC2 Response: " + response.toJSONString());

            RPCData<Boolean> data = EnjinRPC.gson.fromJson(response.toJSONString(), new TypeToken<RPCData<Boolean>>() {}.getType());
            data.setRequest(request);
            data.setResponse(response);
            return data;
        } catch (JSONRPC2SessionException e) {
            EnjinRPC.debug(e.getMessage());
            EnjinRPC.debug("Failed Request to " + session.getURL().toString() + ": " + request.toJSONString());
            return null;
        }
    }

    public RPCData<Boolean> removeRank(final String authkey, final String player, final String group, final String world) {
        String method = "Plugin.removeRank";
        Map<String, Object> parameters = new HashMap<String, Object>() {{
            put("authkey", authkey);
            put("player", player);
            put("group", group);
            put("world", world);
        }};

        int id = EnjinRPC.getNextRequestId();

        JSONRPC2Session session = null;
        JSONRPC2Request request = null;
        JSONRPC2Response response = null;

        try {
            session = EnjinRPC.getSession("minecraft.php");
            request = new JSONRPC2Request(method, parameters, id);
            response = session.send(request);

            EnjinRPC.debug("JSONRPC2 Request: " + request.toJSONString());
            EnjinRPC.debug("JSONRPC2 Response: " + response.toJSONString());

            RPCData<Boolean> data = EnjinRPC.gson.fromJson(response.toJSONString(), new TypeToken<RPCData<Boolean>>() {}.getType());
            data.setRequest(request);
            data.setResponse(response);
            return data;
        } catch (JSONRPC2SessionException e) {
            EnjinRPC.debug(e.getMessage());
            EnjinRPC.debug("Failed Request to " + session.getURL().toString() + ": " + request.toJSONString());
            return null;
        }
    }
}
package minegame159.meteorclient.json;

import com.google.gson.*;
import minegame159.meteorclient.modules.Module;
import minegame159.meteorclient.modules.ModuleManager;

import java.lang.reflect.Type;

public class ModuleManagerSerializer implements JsonSerializer<ModuleManager>, JsonDeserializer<ModuleManager> {
    @Override
    public JsonElement serialize(ModuleManager src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray a = new JsonArray();

        for (Module module : src.getAll()) {
            if (module.serialize) {
                a.add(ModuleSerializer.serialize(module, context));
            }
        }

        return a;
    }

    @Override
    public ModuleManager deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ModuleManager.INSTANCE = new ModuleManager();

        for (JsonElement e : json.getAsJsonArray()) {
            JsonObject o = e.getAsJsonObject();
            Module module = ModuleManager.INSTANCE.get(o.get("name").getAsString());
            if (module == null) continue;
            ModuleSerializer.deserialize(module, o, context);
        }

        return ModuleManager.INSTANCE;
    }
}

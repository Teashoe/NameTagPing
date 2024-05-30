package dev.paulsoporan.pingnametags.config;

import dev.paulsoporan.pingnametags.PingNametagsClientMod;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Key;
import net.minecraft.text.Text;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jetbrains.annotations.NotNull;

public class PingNametagsConfig {
    private static final boolean DEFAULT_ENABLED = true;

    private static final PingTextPosition DEFAULT_PING_TEXT_POSITION = PingTextPosition.Right;

    private static final String DEFAULT_PING_TEXT_FORMAT_STRING = "(%dms)";

    @NotNull
    private boolean enabled = DEFAULT_ENABLED;

    @NotNull
    private PingTextPosition pingTextPosition = DEFAULT_PING_TEXT_POSITION;

    @NotNull
    private String pingTextFormatString = DEFAULT_PING_TEXT_FORMAT_STRING;

    static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable(String.format("config.%s.title", PingNametagsClientMod.MOD_ID)));

        PingNametagsConfig config = PingNametagsConfigManager.getConfig();

        builder.getOrCreateCategory(Text.literal("general"))
                .addEntry(ConfigEntryBuilder.create()
                        .startBooleanToggle(Text.translatable(String.format("config.%s.enabled", PingNametagsClientMod.MOD_ID)), config.getEnabled())
                        .setDefaultValue(DEFAULT_ENABLED)
                        .setTooltip(Text.translatable(String.format("config.%s.enabled.description", PingNametagsClientMod.MOD_ID)))
                        .setSaveConsumer(b -> config.enabled = b)
                        .build())
                .addEntry(ConfigEntryBuilder.create()
                        .startEnumSelector(Text.translatable(String.format("config.%s.pingTextPosition", PingNametagsClientMod.MOD_ID)), PingTextPosition.class, config.getPingTextPosition())
                        .setDefaultValue(DEFAULT_PING_TEXT_POSITION)
                        .setTooltip(Text.translatable(String.format("config.%s.pingTextPosition.description", PingNametagsClientMod.MOD_ID)))
                        .setSaveConsumer(p -> config.pingTextPosition = p)
                        .build())
                .addEntry(ConfigEntryBuilder.create()
                        .startStrField(Text.translatable(String.format("config.%s.pingTextFormatString", PingNametagsClientMod.MOD_ID)), config.getPingTextFormatString())
                        .setDefaultValue(DEFAULT_PING_TEXT_FORMAT_STRING)
                        .setTooltip(Text.translatable(String.format("config.%s.pingTextFormatString.description", PingNametagsClientMod.MOD_ID)))
                        .setSaveConsumer(s -> config.pingTextFormatString = s)
                        .build());

        builder.setSavingRunnable(PingNametagsConfigManager::save);

        return builder.build();
    }

    public boolean getEnabled() {
        return enabled;
    }

    public PingTextPosition getPingTextPosition() {
        return pingTextPosition;
    }

    public String getPingTextFormatString() {
        return pingTextFormatString;
    }

    public void toggleEnabled() {
        enabled = !enabled;
    }
}

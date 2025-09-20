package net.sierr.cosmictitles.message;

import java.util.Locale;

public enum MessageKey
{
    NO_PERMISSION,
    PLUGIN_RELOAD,
    NO_ARGUMENTS,
    COMMAND_USAGE,
    COMMAND_USAGE_CONSOLE,
    PLAYER_SPECIFY,
    TITLE_UNSET,
    TITLE_SET,
    TITLE_SPECIFY,
    TYPE_SPECIFY,
    TITLE_UNKNOWN;

    @Override
    public String toString()
    {
        return super.toString().toLowerCase(Locale.ROOT);
    }
}

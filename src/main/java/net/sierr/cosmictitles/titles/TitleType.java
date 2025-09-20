package net.sierr.cosmictitles.titles;

import javax.annotation.Nullable;
import java.util.Locale;

public enum TitleType
{
    PREFIX,
    SUFFIX,
    ANY;

    @Override
    public String toString()
    {
        return super.toString().toLowerCase(Locale.ROOT);
    }

    /**
     * @param argument The argument to check.
     * @return If the argument can be parsed as TitleType.
     * @since 0.1.0
     */
    public static boolean check(String argument)
    {
        for (TitleType type : TitleType.values())
        {
            if (type.toString().equalsIgnoreCase(argument))
                return true;
        }
        return false;
    }

    /**
     * @param argument The argument to parse.
     * @return The argument as TitleType. Null if check() would fail.
     * @since 0.1.0
     */
    @Nullable
    public static TitleType parse(String argument)
    {
        for (TitleType type : TitleType.values())
        {
            if (type.toString().equalsIgnoreCase(argument))
                return type;
        }
        return null;
    }
}

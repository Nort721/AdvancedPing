package com.nort721.advping.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

/**
 * ServerVersion
 */
@Getter
@RequiredArgsConstructor
public enum ServerVersion {
    V1_7_10(5, "v1_7_R4"),
    V1_8(47, "v1_8_R1"),
    V1_8_3(47, "v1_8_R2"),
    V1_8_8(47, "v1_8_R3"),
    V1_9(107, "v1_9_R1"),
    V1_9_4(110, "v1_9_R2"),
    V1_10(210, "v1_10_R1"),
    V1_11(315, "v1_11_R1"),
    V1_12(335, "v1_12_R1"),
    V1_13(393, "v1_13_R1"),
    V1_13_2(404, "v1_13_R2"),
    V1_14(477, "v1_14_R1"),
    V1_15(573, "v1_15_R1"),
    V1_16(735, "v1_16_R1"),
    V1_16_3(753, "v1_16_R2"),
    V1_16_5(754, "v1_16_R3"),
    V1_17(755, "v1_17_R1"),
    V1_18(757, "v1_18_R1"),
    V1_18_2(758, "v1_18_R2"),
    V1_19(759, "v1_19_R1"),
    V1_19_3(761, "v1_19_R2"),
    V1_20_1(763, "v1_20_R1"),
    UNKNOWN(999, "UNKNOWN");

    @Getter
    private static final ServerVersion serverVersion = fetchGameVersion();

    private final int versionId;
    private final String release;

    private static ServerVersion fetchGameVersion() {
        for (ServerVersion version : values()) {
            if (version.getRelease() != null && version.getRelease().equals(ServerData.SERVER_VERSION_RELEASE))
                return version;
        }
        return UNKNOWN;
    }

    public static ServerVersion getVersion(int versionId) {
        for (ServerVersion version : values()) {
            if (version.getVersionId() == versionId) return version;
        }
        return UNKNOWN;
    }

    public boolean isBelow(ServerVersion version) {
        return this.getVersionId() < version.getVersionId();
    }

    @Deprecated
    public boolean isAbove(ServerVersion version) {
        return this.getVersionId() > version.getVersionId();
    }

    public boolean isOrAbove(ServerVersion version) {
        return this.getVersionId() >= version.getVersionId();
    }
}

final class ServerData {
    static String SERVER_VERSION_RELEASE = Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit", "").replace(".", "");
}


package com.dmzformoptions.util;

/** Raw access to DMZ's {@code OutlineShaderConfig} plus the option added by this addon. */
public interface OutlineAccess {
    boolean dmzfo$addToBase();

    String dmzfo$rawPrimary();

    String dmzfo$rawSecondary();

    String dmzfo$rawMode();

    double dmzfo$rawFire();

    double dmzfo$rawGlow();

    void dmzfo$fill(String primary, String secondary, double thickness, double fire, String mode, double glow);
}

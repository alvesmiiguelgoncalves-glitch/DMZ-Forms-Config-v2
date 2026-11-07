package com.dmzformoptions.util;

/** Lets the addon build an {@code OutlineShaderConfig} (its fields are private in DMZ). */
public interface OutlineAccess {
    void dmzfo$fill(String primary, String secondary, double thickness, double fire, String mode, double glow);
}

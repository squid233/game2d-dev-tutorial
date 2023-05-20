/*
 * MIT License
 *
 * Copyright (c) 2023 squid233
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package io.github.squid233.game;

import org.overrun.glib.RuntimeHelper;
import org.overrun.glib.gl.GLLoader;
import org.overrun.glib.glfw.GLFW;
import org.overrun.glib.util.MemoryStack;

import java.lang.foreign.MemorySegment;

/**
 * @author squid233
 * @since 0.1.0
 */
public final class Main {
    private MemorySegment window;

    private void init() {
        if (!GLFW.init()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }
        try (MemoryStack stack = MemoryStack.stackPush()) {
            window = GLFW.createWindow(stack, 800, 600, "Game", MemorySegment.NULL, MemorySegment.NULL);
        }
        if (RuntimeHelper.isNullptr(window)) {
            throw new IllegalStateException("Failed to create the window");
        }
        GLFW.makeContextCurrent(window);
        GLLoader.loadConfined(true, GLFW::ngetProcAddress);
        run();
        dispose();
    }

    private void run() {
        while (!GLFW.windowShouldClose(window)) {
            GLFW.pollEvents();
            GLFW.swapBuffers(window);
        }
    }

    private void dispose() {
        GLFW.destroyWindow(window);
        GLFW.terminate();
    }

    public static void main(String[] args) {
        new Main().init();
    }
}

package org.liquidengine.legui.demo;

import org.joml.Vector2i;
import org.liquidengine.legui.animation.AnimatorProvider;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.image.BufferedImageRGBA;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.system.context.CallbackKeeper;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.context.DefaultCallbackKeeper;
import org.liquidengine.legui.system.handler.processor.SystemEventProcessor;
import org.liquidengine.legui.system.handler.processor.SystemEventProcessorImpl;
import org.liquidengine.legui.system.layout.LayoutManager;
import org.liquidengine.legui.system.renderer.Renderer;
import org.liquidengine.legui.system.renderer.nvg.NvgRenderer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Alexander on 17.12.2016.
 */
public class SingleClassExampleImageRendering {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    private static volatile boolean running = false;
    private static BufferedImageRGBA img;
    private static int textureWidth;
    private static int textureHeight;
    private static int color;
    private static ByteBuffer byteBuffer;

    public static void main(String[] args) throws IOException {
        System.setProperty("joml.nounsafe", Boolean.TRUE.toString());
        System.setProperty("java.awt.headless", Boolean.TRUE.toString());
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Can't initialize GLFW");
        }
        long window = glfwCreateWindow(WIDTH, HEIGHT, "Single Class Example", NULL, NULL);
        glfwShowWindow(window);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwSwapInterval(0);

        // Firstly we need to create frame component for window.
        Frame frame = new Frame(WIDTH, HEIGHT);
        // we can add elements here or on the fly
        createGuiElements(frame);

        // We need to create legui context which shared by renderer and event processor.
        // Also we need to pass event processor for ui events such as click on component, key typing and etc.
        Context context = new Context(window);

        // We need to create callback keeper which will hold all of callbacks.
        // These callbacks will be used in initialization of system event processor
        // (will be added callbacks which will push system events to event queue and after that processed by SystemEventProcessor)
        CallbackKeeper keeper = new DefaultCallbackKeeper();

        // register callbacks for window. Note: all previously binded callbacks will be unbinded.
        CallbackKeeper.registerCallbacks(window, keeper);

        GLFWKeyCallbackI glfwKeyCallbackI = (w1, key, code, action, mods) -> running = !(key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE);
        GLFWWindowCloseCallbackI glfwWindowCloseCallbackI = w -> running = false;

        // if we want to create some callbacks for system events you should create and put them to keeper
        //
        // Wrong:
        // glfwSetKeyCallback(window, glfwKeyCallbackI);
        // glfwSetWindowCloseCallback(window, glfwWindowCloseCallbackI);
        //
        // Right:
        keeper.getChainKeyCallback().add(glfwKeyCallbackI);
        keeper.getChainWindowCloseCallback().add(glfwWindowCloseCallbackI);

        // Event processor for system events. System events should be processed and translated to gui events.
        SystemEventProcessor systemEventProcessor = new SystemEventProcessorImpl();
        SystemEventProcessor.addDefaultCallbacks(keeper, systemEventProcessor);

        // Also we need to create renderer provider
        // and create renderer which will render our ui components.
        Renderer renderer = new NvgRenderer();

        // Initialization finished, so we can start render loop.
        running = true;

        // Everything can be done in one thread as well as in separated threads.
        // Here is one-thread example.

        // before render loop we need to initialize renderer
        renderer.initialize();

        while (running) {

            // Before rendering we need to update context with window size and window framebuffer size
            //{
            //    int[] windowWidth = {0}, windowHeight = {0};
            //    GLFW.glfwGetWindowSize(window, windowWidth, windowHeight);
            //    int[] frameBufferWidth = {0}, frameBufferHeight = {0};
            //    GLFW.glfwGetFramebufferSize(window, frameBufferWidth, frameBufferHeight);
            //    int[] xpos = {0}, ypos = {0};
            //    GLFW.glfwGetWindowPos(window, xpos, ypos);
            //    double[] mx = {0}, my = {0};
            //    GLFW.glfwGetCursorPos(window, mx, my);
            //
            //    context.update(windowWidth[0], windowHeight[0],
            //            frameBufferWidth[0], frameBufferHeight[0],
            //            xpos[0], ypos[0],
            //            mx[0], my[0]
            //    );
            //}

            // Also we can do it in one line
            context.updateGlfwWindow();
            Vector2i windowSize = context.getFramebufferSize();

            glClearColor(1, 1, 1, 1);
            // Set viewport size
            glViewport(0, 0, windowSize.x, windowSize.y);
            // Clear screen
            glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

            // We need to relayout components.
            LayoutManager.getInstance().layout(frame);

            // render frame
            renderer.render(frame, context);

            update();
            // poll events to callbacks
            glfwPollEvents();
            glfwSwapBuffers(window);

            // Now we need to process events. Firstly we need to process system events.
            systemEventProcessor.processEvents(frame, context);

            // When system events are translated to GUI events we need to process them.
            // This event processor calls listeners added to ui components
            EventProcessorProvider.getInstance().processEvents();

            // Run animations. Should be also called cause some components use animations for updating state.
            AnimatorProvider.getAnimator().runAnimations();
        }

        // And when rendering is ended we need to destroy renderer
        renderer.destroy();

        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private static void update() {
        // generate new image and pass it to pixel buffer.
        img.updateImageData(generateImage(byteBuffer));
    }

    private static void createGuiElements(Frame frame) {

        Widget widget = new Widget("video", 10, 40, WIDTH - 20, HEIGHT - 50);
        widget.getContainer().getStyle().setDisplay(Style.DisplayType.FLEX);
        widget.setCloseable(false);

        ImageView imageView = new ImageView();
        imageView.getStyle().setPosition(Style.PositionType.ABSOLUTE);
        imageView.getStyle().setTop(0);
        imageView.getStyle().setBottom(0);
        imageView.getStyle().setLeft(0);
        imageView.getStyle().setRight(0);
        imageView.getStyle().getBackground().setColor(ColorConstants.lightGray());

        if (imageView.getImage() == null) {
            textureWidth = WIDTH / 2;
            textureHeight = HEIGHT / 2;
            byteBuffer = generateEmptyImage(ByteBuffer.allocate(textureWidth * textureHeight * 4));

            img = new BufferedImageRGBA(textureWidth, textureHeight);
            img.updateImageData(generateEmptyImage(byteBuffer));
            imageView.setImage(img);
        };

        widget.getContainer().add(imageView);
        frame.getContainer().add(widget);
    }

    static ByteBuffer generateImage(ByteBuffer buffer) {
        // copy 4 bytes at once
        for (int i = 0; i < textureHeight; ++i) {
            for (int j = 0; j < textureWidth; ++j) {
                int c = (color / 10 + i * j) % 200 + (color % 1000) / 100;
                buffer.put((textureWidth * i + j) * 4 + 0, (byte) (c >>> 24));
                buffer.put((textureWidth * i + j) * 4 + 1, (byte) (c >>> 16));
                buffer.put((textureWidth * i + j) * 4 + 2, (byte) (c >>> 8));
                buffer.put((textureWidth * i + j) * 4 + 3, (byte) (c));
            }
        }
        color++;
        buffer.rewind();
        return buffer;
    }

    static ByteBuffer generateEmptyImage(ByteBuffer buffer) {
        // copy 4 bytes at once
        for (int i = 0; i < textureHeight; ++i) {
            for (int j = 0; j < textureWidth; ++j) {
                buffer.put((textureWidth * i + j) * 4 + 0, (byte) 0);
                buffer.put((textureWidth * i + j) * 4 + 1, (byte) 0);
                buffer.put((textureWidth * i + j) * 4 + 2, (byte) 0);
                buffer.put((textureWidth * i + j) * 4 + 3, (byte) 0);
            }
        }
        buffer.rewind();
        return buffer;
    }

}
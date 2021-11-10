package com.spinyowl.legui.demo;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.spinyowl.legui.DefaultInitializer;
import com.spinyowl.legui.animation.AnimatorProvider;
import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.component.RadioButton;
import com.spinyowl.legui.component.RadioButtonGroup;
import com.spinyowl.legui.event.CursorEnterEvent;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.CursorEnterEventListener;
import com.spinyowl.legui.listener.MouseClickEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.system.context.CallbackKeeper;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.layout.LayoutManager;
import com.spinyowl.legui.system.renderer.Renderer;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;

public class SingleClassExampleGuiOverGL {

  public static final int WIDTH = 400;
  public static final int HEIGHT = 200;
  //@formatter:off
  private final String vertexShaderSource =
      "#version 330 core\n" +
          "layout (location = 0) in vec3 aPos;\n" +
          "\n" +
          "void main()\n" +
          "{\n" +
          "    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
          "}";
  private final String fragmentShaderSource =
      "#version 330 core\n" +
          "out vec4 FragColor;\n" +
          "\n" +
          "void main()\n" +
          "{\n" +
          "    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n" +
          "} ";
  private final float[] vertices = {
      -0.5f, -0.5f, 0.0f,
      0.5f, -0.5f, 0.0f,
      0.0f, 0.5f, 0.0f
  };
  private final int[] indices = {  // note that we start from 0!
      0, 1, 3,  // first Triangle
      1, 2, 3   // second Triangle
  };
  private volatile boolean running = false;
  private volatile boolean hiding = false;
  private long window;
  private Frame frame;
  private DefaultInitializer initializer;
  private int vbo;
  private int ebo;
  private int vao;
  private ShaderProgram shaderProgram;

  public static void main(String[] args) throws IOException {
    System.setProperty("joml.nounsafe", Boolean.TRUE.toString());
    System.setProperty("java.awt.headless", Boolean.TRUE.toString());
    SingleClassExampleGuiOverGL demo = new SingleClassExampleGuiOverGL();
    demo.start();
  }

  public void start() {
    if (!GLFW.glfwInit()) {
      throw new RuntimeException("Can't initialize GLFW");
    }
    window = createWindow();
    frame = createFrameWithGUI();
    initializer = new DefaultInitializer(window, frame);

    // initialization
    initializeGuiWithCallbacks();
    initializeCustomFrame();
    running = true;

    loop();

    // And when rendering is ended we need to destroy renderer
    initializer.getRenderer().destroy();
    dispose();
    glfwDestroyWindow(window);
    glfwTerminate();
  }

  private void initializeGuiWithCallbacks() {
    GLFWKeyCallbackI escapeCallback = (w1, key, code, action, mods) -> running = !(
        key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE);

    // used to skip gui rendering
    GLFWKeyCallbackI hideCallback = (w1, key, code, action, mods) -> {
      if (key == GLFW_KEY_H && action == GLFW_RELEASE) {
        hiding = !hiding;
      }
    };
    GLFWWindowCloseCallbackI windowCloseCallback = w -> running = false;

    CallbackKeeper keeper = initializer.getCallbackKeeper();
    keeper.getChainKeyCallback().add(escapeCallback);
    keeper.getChainKeyCallback().add(hideCallback);
    keeper.getChainWindowCloseCallback().add(windowCloseCallback);

    Renderer renderer = initializer.getRenderer();
    renderer.initialize();
  }

  private void loop() {
    Renderer renderer = initializer.getRenderer();
    Context context = initializer.getContext();
    while (running) {
      context.updateGlfwWindow();
      Vector2i windowSize = context.getFramebufferSize();

      glClearColor(1, 1, 1, 1);
      // Set viewport size
      glViewport(0, 0, windowSize.x, windowSize.y);
      // Clear screen
      glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
      glEnable(GL_BLEND);
      glDisable(GL_DEPTH_TEST);

      // render custom frame
      renderCustomFrame();

      // render gui frame
      if (!hiding) {
        renderer.render(frame, context);
      }

      // poll events to callbacks
      glfwPollEvents();
      glfwSwapBuffers(window);

      // Now we need to process events. Firstly we need to process system events.
      initializer.getSystemEventProcessor().processEvents(frame, context);

      // When system events are translated to GUI events we need to process them.
      // This event processor calls listeners added to ui components
      EventProcessorProvider.getInstance().processEvents();

      // When everything done we need to relayout components.
      LayoutManager.getInstance().layout(frame);

      // Run animations. Should be also called cause some components use animations for updating state.
      AnimatorProvider.getAnimator().runAnimations();
    }
  }

  private long createWindow() {
    long window = glfwCreateWindow(WIDTH, HEIGHT, "Single Class Example", NULL, NULL);
    glfwShowWindow(window);

    glfwMakeContextCurrent(window);
    GL.createCapabilities();
    glfwSwapInterval(0);
    return window;
  }

  //@formatter:on
  private void initializeCustomFrame() {
    shaderProgram = new ShaderProgram();
    shaderProgram.attachVertexShader(vertexShaderSource);
    shaderProgram.attachFragmentShader(fragmentShaderSource);
    shaderProgram.link();

    // Generate and bind a Vertex Array
    vao = glGenVertexArrays();
    glBindVertexArray(vao);

    // Create a FloatBuffer of vertices
    FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
    verticesBuffer.put(vertices).flip();

    // Create a Buffer Object and upload the vertices buffer
    vbo = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

    // Point the buffer at location 0, the location we set
    // inside the vertex shader. You can use any location
    // but the locations should match
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
    glBindVertexArray(0);
  }

  private void renderCustomFrame() {
    // Use our program
    shaderProgram.bind();

    // Bind the vertex array and enable our location
    glBindVertexArray(vao);
    glEnableVertexAttribArray(0);

    // Draw a triangle of 3 vertices
    glDrawArrays(GL_TRIANGLES, 0, 3);

    // Disable our location
    glDisableVertexAttribArray(0);
    glBindVertexArray(0);

    // Un-bind our program
    ShaderProgram.unbind();
  }

  private Frame createFrameWithGUI() {
    Frame frame = new Frame(WIDTH, HEIGHT);
    // Set background color for frame
    frame.getContainer().getStyle().getBackground().setColor(ColorConstants.transparent());
    frame.getContainer().setFocusable(false);

    Button button = new Button("Add components", 20, 20, 160, 30);
    SimpleLineBorder border = new SimpleLineBorder(ColorConstants.black(), 1);
    button.getStyle().setBorder(border);

    boolean[] added = {false};
    button.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
      if (!added[0]) {
        added[0] = true;
        for (Component c : generateOnFly()) {
          frame.getContainer().add(c);
        }
      }
    });

    button.getListenerMap()
        .addListener(CursorEnterEvent.class, (CursorEnterEventListener) System.out::println);

    frame.getContainer().add(button);
    return frame;
  }

  private List<Component> generateOnFly() {
    List<Component> list = new ArrayList<>();

    Label label = new Label(20, 60, 200, 20);
    label.getTextState().setText("Generated on fly label");
    label.getStyle().setTextColor(ColorConstants.red());

    RadioButtonGroup group = new RadioButtonGroup();
    RadioButton radioButtonFirst = new RadioButton("First", 20, 90, 200, 20);
    RadioButton radioButtonSecond = new RadioButton("Second", 20, 110, 200, 20);

    radioButtonFirst.setRadioButtonGroup(group);
    radioButtonSecond.setRadioButtonGroup(group);

    list.add(label);
    list.add(radioButtonFirst);
    list.add(radioButtonSecond);

    return list;
  }

  private void dispose() {
    // Dispose the program
    shaderProgram.dispose();

    // Dispose the vertex array
    glBindVertexArray(0);
    glDeleteVertexArrays(vao);

    // Dispose the buffer object
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glDeleteBuffers(vbo);
  }
}

class ShaderProgram {

  // ProgramID
  int programID;

  // Vertex Shader ID
  int vertexShaderID;
  // Fragment Shader ID
  int fragmentShaderID;

  /**
   * Create a new ShaderProgram.
   */
  public ShaderProgram() {
    programID = glCreateProgram();
  }

  /**
   * Unbind the shader program.
   */
  public static void unbind() {
    glUseProgram(0);
  }

  /**
   * Attach a Vertex Shader to this program.
   *
   * @param vertexShaderSource source code of the vertex shader.
   */
  public void attachVertexShader(String vertexShaderSource) {
    // Create the shader and set the source
    vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
    glShaderSource(vertexShaderID, vertexShaderSource);

    // Compile the shader
    glCompileShader(vertexShaderID);

    // Check for errors
    if (glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == GL_FALSE) {
      throw new RuntimeException("Error creating vertex shader\n"
          + glGetShaderInfoLog(vertexShaderID, glGetShaderi(vertexShaderID, GL_INFO_LOG_LENGTH)));
    }

    // Attach the shader
    glAttachShader(programID, vertexShaderID);
  }

  /**
   * Attach a Fragment Shader to this program.
   *
   * @param fragmentShaderSource source code of the Fragment Shader.
   */
  public void attachFragmentShader(String fragmentShaderSource) {

    // Create the shader and set the source
    fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(fragmentShaderID, fragmentShaderSource);

    // Compile the shader
    glCompileShader(fragmentShaderID);

    // Check for errors
    if (glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) == GL_FALSE) {
      throw new RuntimeException("Error creating fragment shader\n"
          + glGetShaderInfoLog(fragmentShaderID,
          glGetShaderi(fragmentShaderID, GL_INFO_LOG_LENGTH)));
    }

    // Attach the shader
    glAttachShader(programID, fragmentShaderID);
  }

  /**
   * Links this program in order to use.
   */
  public void link() {
    // Link this program
    glLinkProgram(programID);

    // Check for linking errors
    if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
      throw new RuntimeException("Unable to link shader program:");
    }
  }

  /**
   * Bind this program to use.
   */
  public void bind() {
    glUseProgram(programID);
  }

  /**
   * Dispose the program and shaders.
   */
  public void dispose() {
    // Unbind the program
    unbind();

    // Detach the shaders
    glDetachShader(programID, vertexShaderID);
    glDetachShader(programID, fragmentShaderID);

    // Delete the shaders
    glDeleteShader(vertexShaderID);
    glDeleteShader(fragmentShaderID);

    // Delete the program
    glDeleteProgram(programID);
  }

  /**
   * @return The ID of this program.
   */
  public int getID() {
    return programID;
  }
}
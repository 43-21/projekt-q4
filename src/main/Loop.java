package main;

import graphics.Display;
import input.Controller;
import input.Input;
import overlay.Overlay;
import support.Options;
import world.Positioned;
import world.World;

// Game Loop
public class Loop implements Runnable, State {
    private World world;
    private Display display;

    int state = SIMULATION;

    private Overlay overlay;
    private Controller controller;

    private final double frameRate = 1.0 / Options.fps;
    private final double updateRate = 1.0 / Options.ups;
    private int fps, ups;

    private long nextStatTime;
    private long currentTime = System.currentTimeMillis();

    public Loop(Input input) {
        this.world = new World(Options.width, Options.height);
        this.overlay = new Overlay();
        world.overlay = overlay;
        this.display = new Display(Options.width, Options.height, input);

        this.controller = new Controller(input);
    }

    // Eigentliche Loop
    @Override
    public void run() {
        double accumulator = 0;
        double rAccumulator = 0;
        long lastUpdate = System.currentTimeMillis();
        long lastRender = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while(true) {
            if(controller.isRequestingPause()) {
                if(state == PAUSE) {
                    state = SIMULATION;
                    lastUpdate = System.currentTimeMillis();
                }
                else {
                    state = PAUSE;
                }
            }

            if(controller.getMouseClicked()) {
                Positioned object = world.getPositionedOnMouse(display.getCanvas().getMousePosition());
                overlay.setFocus(object);
            }
            
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastRender) / 1000.0;
            double lastUpdateTimeInSeconds = (currentTime - lastUpdate) / 1000.0;
            if(state == SIMULATION) accumulator += lastUpdateTimeInSeconds;
            rAccumulator += lastRenderTimeInSeconds;
            lastRender = currentTime;
            lastUpdate = currentTime;
            boolean updated = state == SIMULATION ? false : true;

            while(accumulator >= updateRate || (rAccumulator >= frameRate && updated)) {
                if(accumulator >= updateRate) {
                    update();
                    accumulator -= updateRate;
                    updated = true;
                }

                else {
                    updated = false;
                }

                if(rAccumulator >= frameRate) {
                    render();
                    rAccumulator -= frameRate;
                }
            }
            printStats();
        }
    }

    private void printStats() {
        if(System.currentTimeMillis() > nextStatTime) {
            System.out.println(String.format("FPS: %d, UPS: %d", fps, ups));
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void update() {
        overlay.clear();
        if(state == SIMULATION) world.update();
        ups++;
    }

    private void render() {
        display.render(world, overlay);
        fps++;
    }
}
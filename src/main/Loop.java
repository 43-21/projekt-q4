package main;

import java.awt.MouseInfo;

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

    public Loop(World world, Display display) {
        this.world = world;
        this.overlay = new Overlay();
        world.overlay = overlay;
        this.display = display;

        this.controller = new Controller(new Input());
    }

    // Eigentlicher Loop
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
                }
                else {
                    state = PAUSE;
                }
            }
    
            if(controller.isRequestingSave()) {
                //speicherung muss hier initiiert werden, wenn nicht schon gespeichert wird.
                state = SAVING;
            }

            if(controller.getMouseClicked()) {
                Positioned object = world.getPositionedOnMouse(MouseInfo.getPointerInfo().getLocation());
            }

            
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastRender) / 1000.0;
            double lastUpdateTimeInSeconds = (currentTime - lastUpdate) / 1000.0;
            accumulator += lastUpdateTimeInSeconds;
            rAccumulator += lastRenderTimeInSeconds;
            lastRender = currentTime;
            lastUpdate = currentTime;
            boolean updated = false;

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

    // Print was gerade passiert
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
import java.util.ArrayList;

import graphics.Display;
import overlay.Overlay;
import support.Options;
import world.World;

// Game Loop
public class Loop implements Runnable {
    private World world;
    private Display display;

    private Overlay overlay;

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
        world.update();
        ups++;
    }

    private void render() {
        display.render(world, overlay);
        fps++;
    }
}
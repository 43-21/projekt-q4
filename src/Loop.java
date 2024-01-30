import graphics.Display;
import options.Options;
import world.World;

public class Loop implements Runnable {
    private World world;
    private Display display;

    private final double frameRate = 1.0 / Options.fps;
    private final double updateRate = 1.0 / Options.ups;
    private int fps, ups;

    private long nextStatTime;
    private long currentTime = System.currentTimeMillis();

    public Loop(World world, Display display) {
        this.world = world;
        this.display = display;
    }

    @Override
    public void run() {
        double accumulator = 0;
        double rAccumulator = 0;
        long lastUpdate = System.currentTimeMillis();
        long lastRender = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while(true) {
            //updates
            //while updaterate isnt fulfilled, update
            //render
            //if its time to render, render
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastRender) / 1000.0;
            double lastUpdateTimeInSeconds = (currentTime - lastUpdate) / 1000.0;
            accumulator += lastUpdateTimeInSeconds;
            rAccumulator += lastRenderTimeInSeconds;
            lastRender = currentTime;
            lastUpdate = currentTime;

            while(accumulator >= updateRate || rAccumulator >= frameRate) {
                if(accumulator >= updateRate) {
                    update();
                    accumulator -= updateRate;
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
        world.update();
        ups++;
    }

    private void render() {
        display.render(world.getObjects());
        fps++;
    }
}
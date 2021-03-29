import core.Grid;
import core.Location;
import core.Simulation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import rules.GameOfLife;
import rules.Rule;

import java.util.ArrayList;

public class DrawGrid extends Application {
    private static int sceneWidth = 900;
    private static int sceneHeight = 900;
    public static int cellSizeX;
    public static int cellSizeY;
    public static final float cellReduction = 0.1f;
    private static Scene gridScene;
    private static Grid grid;
    private static Simulation sim;
    private static GraphicsContext graphics;
    private static Stage primaryStage;
    private static Color borderColor = Color.BLACK;
    private static Color aliveColor = Color.LAVENDER;
    private static Color deadColor = Color.DARKSLATEGREY;
    private static int[][] realWorld;
    private static boolean isPlaying = false;

    private static int maxGenerated = 1;
    private static int currentGeneration = maxGenerated;


    private void draw() {
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                Location here = new Location(j, i);
                int state = grid.getState(here);

                graphics.setFill(borderColor);
                graphics.fillRect(here.x() * cellSizeX, here.y() * cellSizeY, cellSizeX, cellSizeY);

                if (state == 1) {
                    graphics.setFill(aliveColor);
                } else {
                    graphics.setFill(deadColor);
                }

                graphics.fillRect((here.x() * cellSizeX) + (cellReduction - 2f),
                                  (here.y() * cellSizeY) + (cellReduction - 2f),
                                     (cellSizeX - cellReduction), (cellSizeY - cellReduction));
            }
        }
    }

    private void handleKeyPressed(KeyEvent event) throws Exception {
        switch(event.getCode()) {
            case SPACE:
                grid.setWorld(realWorld);
                sim.step();
                realWorld = grid.getWorld();
                maxGenerated++;
                currentGeneration = maxGenerated;
                break;
            case LEFT:
                if (currentGeneration > 1) {
                    currentGeneration--;
                    int[][] previousWorld = sim.getFromRecord(currentGeneration);
                    grid.setWorld(previousWorld);
                }
                break;
            case RIGHT:
                if (currentGeneration < maxGenerated) {
                    currentGeneration++;
                    int[][] previousWorld = sim.getFromRecord(currentGeneration);
                    grid.setWorld(previousWorld);
                }
                break;
            case UP:
                currentGeneration = maxGenerated;
                int[][] lastWorld = sim.getFromRecord(currentGeneration);
                grid.setWorld(lastWorld);
                break;
            case DOWN:
                currentGeneration = 1;
                int[][] firstWorld = sim.getFromRecord(currentGeneration);
                grid.setWorld(firstWorld);
                break;
            case P:
                DrawGrid.isPlaying = !DrawGrid.isPlaying;
                break;
        }

        primaryStage.setTitle("AutoGrid [" + currentGeneration + "/" + maxGenerated + "]");
        this.draw();
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);

        final Canvas canvas = new Canvas(sceneWidth, sceneHeight);

        root.getChildren().add(canvas);
        graphics = canvas.getGraphicsContext2D();

        root.setOnMouseClicked(event -> {
            int cellX = (int) Math.ceil(event.getX() / cellSizeX) - 1;
            int cellY = (int) Math.ceil(event.getY() / cellSizeY) - 1;
            Location mousePos = new Location(cellX, cellY);
            try {
                int state = grid.getState(mousePos);
                if (state == 1) {
                    grid.setState(mousePos, 0);
                } else {
                    grid.setState(mousePos, 1);
                }
                if (currentGeneration != maxGenerated) {
                    sim.updateLastGeneration(currentGeneration);
                    maxGenerated = currentGeneration;
                    realWorld = grid.getWorld();
                    primaryStage.setTitle("AutoGrid [" + currentGeneration + "/" + maxGenerated + "]");
                }
                this.draw();
            } catch(Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        });
        this.draw();
        gridScene = new Scene(root, sceneWidth, sceneHeight);

        gridScene.setOnKeyPressed(event -> {
            try {
                handleKeyPressed(event);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        });

        DrawGrid.primaryStage = primaryStage;
        primaryStage.setTitle("AutoGrid Simulator");
        primaryStage.setScene(gridScene);
        primaryStage.show();

        Thread playThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isPlaying) {
                    Platform.runLater(() -> {
                        grid.setWorld(realWorld);
                        try {
                            sim.step();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        realWorld = grid.getWorld();
                        maxGenerated++;
                        currentGeneration = maxGenerated;
                        draw();
                        primaryStage.setTitle("AutoGrid [" + currentGeneration + "/" + maxGenerated + "]");
                    });
                }
            }
        });
        playThread.setDaemon(true);
        playThread.start();
    }

    private static Grid template(String gridType, int width, int height) throws Exception {
        Grid g = new Grid(width, height);
        switch(gridType) {
            case "allOnes":
                g.setOnes();
                return g;
            case "allZeros":
                g.setState(0);
                return g;
            case "gameOfLife":
                ArrayList<Location> locs = new ArrayList<>();
                int[][] coords = {{0,1}, {1,2}, {2,2}, {2,1}, {2,0}};
                for (int[] cell : coords) {
                    locs.add(new Location(cell[0], cell[1]));
                }
                g.setState(locs, 1);
                return g;
            case "random":
                g.setRandom(0, 1);
                return g;
            default:
                throw new Exception("core.Grid template not found!");
        }
    }

    public static void main(String[] args) throws Exception {
        grid = template("gameOfLife", 100, 100);
        Rule simulation_rules = new GameOfLife();

        realWorld = grid.getWorld();
        grid.saveGrid();
        sim = new Simulation(grid, simulation_rules);

        cellSizeX = sceneWidth / grid.getWidth();
        cellSizeY = sceneHeight / grid.getHeight();
        launch(args);
    }
}

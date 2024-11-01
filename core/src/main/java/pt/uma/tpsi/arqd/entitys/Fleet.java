package pt.uma.tpsi.arqd.entitys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Fleet {

    private ArrayList<Ship> fleet;
    private ArrayList<Explosion> explosions; // List to hold active explosions
    private SpriteBatch batch;
    int screenHeight = Gdx.graphics.getHeight();

    public Fleet(int posX, int posY, SpriteBatch batch, String bulletString, int colum, int rows, int width, int height) {
        this.fleet = new ArrayList<Ship>();
        this.explosions = new ArrayList<>(); // Initialize explosion list
        this.batch = batch;

        for (int i = 0; i < 10; i++) {
            Ship s = new SmallShip(posX + i * 158, posY + 50, batch, "enemy-small.png", bulletString, colum, rows, width, height);
            Ship m = new MediumShip(posX + i * 150, posY + 100, batch, "enemy-medium.png", bulletString, colum, rows, width, height);
            Ship l = new LargeShip(posX + i * 150, posY + 150, batch, "enemy-big.png", bulletString, colum, rows, width, height);
            l.create();
            s.create();
            m.create();
            fleet.add(l);
            fleet.add(s);
            fleet.add(m);
        }

        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                randomShoot();
            }
        }, 3, 2);
    }

    public void handleCollision(Player player) {
        ArrayList<Laser> lasers = player.getLaserArrayList();
        lasers.removeIf(laser -> {
            for (Ship ship : fleet) {
                if (ship.boundingBox.overlaps(laser.boundingBox)) {
                    ship.explode();
                    player.Score += 5;

                    Explosion explosion = new Explosion(ship.posX, ship.posY, batch, "explosion.png", 5, 1);
                    explosions.add(explosion);




                    return true;
                }
            }
            return false;
        });
        fleet.removeIf(Ship::isDestroyed);
    }

    public void handlePlayerLaser(Player player) {
        for (Ship ship : fleet) {
            if (ship.laser != null && player.boundingBox.overlaps(ship.laser.boundingBox)) {
                player.HP -= ship.getDamage();
                System.out.println(player.StringHealth());
                ship.laser = null;
            }
        }
    }

    public boolean isOutOfBounds() {
        boolean outOfBounds = false;

        for (Ship ship : fleet) {
            if (ship.laser != null) {
                if (ship.laser.posY > screenHeight || ship.laser.posY < 0) {
                    System.out.println("The laser is out of bounds.");
                    ship.laser = null;
                    outOfBounds = true;
                }
            }
        }

        return outOfBounds;
    }

    public void randomShoot() {
        if (!fleet.isEmpty()) {
            Random r = new Random();
            int randomIndex = r.nextInt(fleet.size());
            System.out.println("Shoot");
            fleet.get(randomIndex).shoot();
        }
    }

    public void create() {
        for (Ship s : fleet) {
            s.create();
        }
    }

    public void render() {
        for (Ship s : fleet) {
            s.render();
        }

        // Render active explosions
        for (Explosion explosion : explosions) {
            explosion.animator.render(explosion.posX, explosion.posY);
        }
    }
}

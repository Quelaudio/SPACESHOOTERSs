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

    private Ship ship;
    private Laser laser;
    private boolean gameOver;

    public Fleet(int posX, int posY, SpriteBatch batch, String bulletString, int colum, int rows, int width, int height) {
        this.fleet = new ArrayList<Ship>();
        this.explosions = new ArrayList<>(); // Initialize explosion list
        this.batch = batch;

        for (int i = 0; i < 8; i++) {
            Ship s = new SmallShip(posX + i * 158, posY + 50, batch, "enemy-small.png", bulletString, colum, rows, width, height);
            Ship m = new MediumShip(posX + i * 150, posY + 100, batch, "enemy-medium.png", bulletString, colum, rows, width, height);
            Ship l = new LargeShip(posX + i * 150, posY + 150, batch, "enemy-big.png", bulletString, colum, rows, width, height);
            l.create();
            s.create();
            m.create();
//            fleet.add(l);
//            fleet.add(s);
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
                    explosions.add(new Explosion(ship.posX, ship.posY, batch, "explosion.png", 4, 1));
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
        player.verifyDeath();
    }

    public boolean isOutOfBounds() {
        boolean outOfBounds = false;

        for (Ship ship : fleet) {
            if (ship.laser != null) {
                if (ship.laser.posY > screenHeight || ship.laser.posY < 0) {
                    ship.laser = null;
                    outOfBounds = true;
                }
            }
        }

        return outOfBounds;
    }

    public boolean isAllShipsDestroyed() {
        System.out.println("Fleet size: " + fleet.size());
        return fleet.isEmpty();
    }


    public void randomShoot() {
        if (!fleet.isEmpty() && !gameOver) {
            Random r = new Random();
            int randomIndex = r.nextInt(fleet.size());
            fleet.get(randomIndex).shoot();
        }
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
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


        Iterator<Explosion> iterator = explosions.iterator();
        //cria um Iterator para a lista explosions que vai modificar enquanto corre
        //o while apenas ira percorrer se existir uma "explosao" aseguir
        while (iterator.hasNext()) {
            //apanha a proxima explosao
            Explosion explosion = iterator.next();
            explosion.render();

            if (explosion.isFinished()) {
                iterator.remove();
            }
        }


    }
}

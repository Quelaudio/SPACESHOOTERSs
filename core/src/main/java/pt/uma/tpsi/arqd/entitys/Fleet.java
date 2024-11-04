package pt.uma.tpsi.arqd.entitys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Fleet {

    private ArrayList<Ship> fleet;
    private ArrayList<Explosion> explosions;
    private SpriteBatch batch;
    boolean outOfBounds = false;
    int screenHeight = Gdx.graphics.getHeight();

    private boolean gameOver;

    public Fleet(int posX, int posY, SpriteBatch batch, String bulletString, int colum, int rows, int width, int height) {
        this.fleet = new ArrayList<Ship>();
        this.explosions = new ArrayList<>();
        this.batch = batch;

        for (int i = 0; i < 8; i++) {
            Ship s = new SmallShip(360 + i * ( 68), posY + 50, batch, "enemy-small.png", bulletString, colum, rows, width, height);
            Ship m = new MediumShip(300 + i * 90, posY + 100, batch, "enemy-medium.png", bulletString, colum, rows, width, height);
            Ship l = new LargeShip(320 + i * 80, posY + 150, batch, "enemy-big.png", bulletString, colum, rows, width, height);
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
                    explosions.add(new Explosion(ship.posX, ship.posY, batch, "explosion.png", 5, 1));
                    return true;
                }
            }
            return false;
        });
        fleet.removeIf(Ship::isDestroyed);
    }

    public void handlePlayerLife(Player player) {
        for (Ship ship : fleet) {
            if (ship.laser != null && player.boundingBox.overlaps(ship.laser.boundingBox)) {
                player.HP -= ship.getDamage();
                System.out.println(player.StringHealth());
                ship.laser = null;
            }
        }
        player.verifyDeath();
    }

    public void isOutOfBounds() {


        for (Ship ship : fleet) {
            if (ship.laser != null) {
                if (ship.laser.posY > screenHeight || ship.laser.posY < 0) {
                    ship.laser = null;
                    outOfBounds = true;
                }
            }
        }


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

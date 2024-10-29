package pt.uma.tpsi.arqd.entitys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.Random;

public class Fleet  {

    private ArrayList<Ship> fleet;
    private ArrayList<Laser> laserFleet;
    public Fleet(int posX, int posY, SpriteBatch batch, String bulletString, int colum, int rows,int width, int height){
        this.fleet = new ArrayList<Ship>();
        for( int i=0; i<10; i++){
            Ship l = new SmallShip( posX + i * 158, posY+ 50,  batch,  "enemy-small.png", bulletString,  colum,  rows, width, height);
            Ship s = new MediumShip( posX + i * 150, posY + 100,  batch,  "enemy-medium.png", bulletString,  colum,  rows, width, height);
            Ship m = new LargeShip( posX + i * 150, posY +150,  batch,  "enemy-big.png", bulletString,  colum,  rows, width, height);
            l.create();
            s.create();
            m.create();
            fleet.add(l);
            fleet.add(s);
            fleet.add(m);
        }

        Timer timer= new Timer();
        timer.scheduleTask(new Timer.Task(){
            @Override
            public void run(){
                randomShoot();
            }
        }, 3, 2);
    }

    //Handle collision vai verificar se existe contato entre a entidade lase
    // e a entidade Ship  caso haja "OverLap" a nave "explode" e é removida da ArrayList<Ship> fleet
    public void handleCollision(Player player){
        ArrayList<Laser> lasers = player.getLaser();
        lasers.removeIf(laser -> {
            for(Ship ship : fleet) {
                if(ship.boundingBox.overlaps(laser.boundingBox)) {
                    ship.explode();
                    return true;
                }
            }
            return false;
        });
        fleet.removeIf(Ship::isDestroyed);
    }


    public void randomShoot() {
        if (!fleet.isEmpty()) {
            Random r = new Random();
            int randomIndex = r.nextInt(fleet.size());
            System.out.println("cageuyi");
            fleet.get(randomIndex).shoot();
        }
    }

    public void create() {
        for(Ship s : fleet){
            s.create();
        }
    }

    public void render() {
        for(Ship s : fleet){
            s.render();
        }


    }
}

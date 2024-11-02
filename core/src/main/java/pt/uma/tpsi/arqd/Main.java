package pt.uma.tpsi.arqd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.entitys.Fleet;
import pt.uma.tpsi.arqd.entitys.Player;
import pt.uma.tpsi.arqd.entitys.SmallShip;
import pt.uma.tpsi.arqd.game.BackgroundManagement;
import pt.uma.tpsi.arqd.game.BitmapFont;


public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private BackgroundManagement backgroundManagement;
    private com.badlogic.gdx.graphics.g2d.BitmapFont font;
    private Player player ;
    private SmallShip enemy;
    private Fleet fleet;
    private boolean  gameOver = false;



    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1280, 720);
        batch = new SpriteBatch();

        font = new com.badlogic.gdx.graphics.g2d.BitmapFont(Gdx.files.internal("gamefont.fnt"), Gdx.files.internal("gamefont.png"), false);
        backgroundManagement = new BackgroundManagement(batch);
        player=new Player(100, 100, batch,"ship.png","laser-bolts",5,2,64,64);
        fleet = new Fleet(100 , 450, batch, "laser-bolts",2, 1,64, 64);




        player.create();

    }


    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        backgroundManagement.render();
        BitmapFont.drawText(10,680,player.StringHealth(),batch);
        BitmapFont.drawText(1000,680,player.scoreString(),batch);
        if (player.HP <= 0) {
            gameOver = true;
            BitmapFont.drawText(500, 360, "Game Over", batch);
        } else if (fleet.isAllShipsDestroyed()) {
            BitmapFont.drawText(500, 360, "You Win!", batch);
            System.out.println(fleet.isAllShipsDestroyed());
        }
        player.render();
        fleet.render();


        batch.end();
        fleet.handleCollision(player);
        fleet.handlePlayerLaser(player);
        fleet.isOutOfBounds();
        fleet.setGameOver(gameOver);
        player.handleExplosion();


    }






    @Override
    public void dispose() {
        batch.dispose();
    }
}

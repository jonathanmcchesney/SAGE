package dreamteam.agile_game_engine.util;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


public class CameraTest {
    @Test
    public void testCameraCreation(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        assertNotNull(camera);
    }
    @Test
    public void testGetEyeX(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        assertNotNull(camera.getEyeX());
    }
    @Test
    public void testGetEyeY(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        assertNotNull(camera.getEyeY());
    }
    @Test
    public void testGetEyeZ(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        assertNotNull(camera.getEyeZ());
    }
    @Test
    public void testGetCamX(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        assertNotNull(camera.getCamX());
    }
    @Test
    public void testGetCamY(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        assertNotNull(camera.getCamY());
    }
    @Test
    public void testGetCamZ(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        assertNotNull(camera.getCamZ());
    }
    @Test
    public void testGetUpX(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        assertNotNull(camera.getUpX());
    }
    @Test
    public void testGetUpY(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        assertNotNull(camera.getUpY());
    }
    @Test
    public void testGetUpZ(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        assertNotNull(camera.getUpZ());
    }
    @Test
    public void testGetPosition(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        assertNotNull(camera.getPosition());
    }
    @Test
    public void testMoveEyeX(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.moveEyeX(1.0f);

        assertEquals(camera.getEyeX(), 2.0f);
    }
    @Test
    public void testMoveEyeY(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.moveEyeY(2.0f);

        assertEquals(camera.getEyeY(), 3.0f);
    }
    @Test
    public void testMoveEyeZ(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.moveEyeZ(0.5f);

        assertEquals(camera.getEyeZ(), 1.5f);
    }
    @Test
    public void testMoveCamX(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.moveCamX(4.0f);

        assertEquals(camera.getCamX(), 5.0f);
    }
    @Test
    public void testMoveCamY(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.moveCamY(1.0f);

        assertEquals(camera.getCamY(), 2.0f);
    }
    @Test
    public void testMoveCamZ(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.moveCamZ(4.0f);

        assertEquals(camera.getCamZ(), 5.0f);
    }
    @Test
    public void testMoveUpX(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.moveUpX(1.0f);

        assertEquals(camera.getUpX(), 2.0f);
    }
    @Test
    public void testMoveUpY(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.moveCamX(0.5f);

        assertEquals(camera.getCamX(), 1.5f);
    }
    @Test
    public void testMoveUpZ(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.moveCamX(2.5f);

        assertEquals(camera.getCamX(), 3.5f);
    }
    @Test
    public void testSetEyeX(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.setEyeX(4.0f);

        assertEquals(camera.getEyeX(), 4.0f);
    }
    @Test
    public void testSetEyeY(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.setEyeY(3.0f);

        assertEquals(camera.getEyeY(), 3.0f);
    }
    @Test
    public void testSetEyeZ(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.setEyeZ(1.0f);

        assertEquals(camera.getEyeZ(), 1.0f);
    }
    @Test
    public void testSetCamX(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.setCamX(3.5f);

        assertEquals(camera.getCamX(), 3.5f);
    }
    @Test
    public void  testSetCamY(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.setCamY(0.5f);

        assertEquals(camera.getCamY(), 0.5f);
    }
    @Test
    public void testSetCamZ(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.setCamZ(1.0f);

        assertEquals(camera.getCamZ(), 1.0f);
    }
    @Test
    public void testSetUpX(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.setUpX(1.0f);

        assertEquals(camera.getUpX(), 1.0f);
    }
    @Test
    public void testSetUpY(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.setUpY(2.0f);

        assertEquals(camera.getUpY(), 2.0f);
    }
    @Test
    public void testSetUpZ(){
        Camera camera = new Camera(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        camera.setUpZ(3.0f);

        assertEquals(camera.getUpZ(), 3.0f);
    }


}
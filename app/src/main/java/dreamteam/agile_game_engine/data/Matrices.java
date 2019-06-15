package dreamteam.agile_game_engine.data;


import android.opengl.Matrix;
/*
 * This class was created by Aoife
 */

public class Matrices
{
    /**
     * This method sets up the MVPMatrix that takes in each unique modelMatrix of every gameObject
     * @param p - projectionMatrix
     * @param v - viewMatrix
     * @param mv - modelViewMatrix
     * @param modelMatrix - modelMatrix
     * @param mvp - the final mvpMatrix to draw to
     * @return - the mvpMatrix
     */
    public static float[] setUpMVPMatrix(float[] p, float[] v, float[] mv, float[] modelMatrix, float[] mvp)
    {

        Matrix.multiplyMM(mv, 0, v, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvp, 0, p, 0, mv, 0);

        Matrix.setIdentityM(modelMatrix, 0);

        return mvp;
    }
}
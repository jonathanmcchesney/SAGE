package dreamteam.agile_game_engine.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import static android.opengl.GLUtils.texImage2D;


/**
 * Adapted from: OpenGL ES 2 for Android: A Quick-Start Guide (Pragmatic Programmers) by Kevin Brothaler.
 * This class was created by Erin.
 */

public class TextureHelper
{
    /**
     * Loads a cubemap texture from the provided resources and returns the
     * texture ID. Returns 0 if the load failed.
     *
     * @param context - the context.
     * @param cubeResources - array of resources corresponding to the cube map.
     * @return the created cube map.
     */
    public static int loadCubeMap(Context context, int[] cubeResources)
    {
        final int[] textureObjectIds = new int[1];
        GLES20.glGenTextures(1, textureObjectIds, 0);

        if (textureObjectIds[0] == 0)
        {
            return 0;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap[] cubeBitmaps = new Bitmap[6];
        for (int i = 0; i < 6; i++)
        {
            cubeBitmaps[i] =
                    BitmapFactory.decodeResource(context.getResources(),
                            cubeResources[i], options);

            if (cubeBitmaps[i] == null)
            {
                GLES20.glDeleteTextures(1, textureObjectIds, 0);
                return 0;
            }
        }
        // Linear filtering for magnification
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, textureObjectIds[0]);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        texImage2D(GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, 0, cubeBitmaps[0], 0);
        texImage2D(GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0, cubeBitmaps[1], 0);

        texImage2D(GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, 0, cubeBitmaps[2], 0);
        texImage2D(GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Y, 0, cubeBitmaps[3], 0);

        texImage2D(GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, 0, cubeBitmaps[4], 0);
        texImage2D(GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Z, 0, cubeBitmaps[5], 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        //recycle the bitmaps after they are loaded.
        for (Bitmap bitmap : cubeBitmaps)
        {
            bitmap.recycle();
        }

        return textureObjectIds[0];
    }
}

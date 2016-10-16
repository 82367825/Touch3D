package com.zero.touch3d;

/**
 * @author linzewu
 * @date 16/10/16
 */
public interface IMenu {
    
//    void onAnimShow();

    /**
     * 手指在菜单上移动
     * @param x 横坐标
     * @param y 纵坐标
     * @return 此时的菜单命令 {@link MenuCommand}
     */
    String onTouchMove(float x, float y);

    /**
     * 手指在菜单上抬起
     * @param x 横坐标
     * @param y 纵坐标
     * @return 此时的菜单命令 {@link MenuCommand}
     */
    String onTouchUp(float x, float y);
    
}

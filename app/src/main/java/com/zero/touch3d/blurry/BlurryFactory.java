package com.zero.touch3d.blurry;

/**
 * 虚化效果工厂类
 * @author linzewu
 * @date 16-10-13
 */
public class BlurryFactory {
    
    public static IBlurry createRenderScript() {
        return new BlurryRenderScript();
    }
    
    public static IBlurry createFastBlur() {
        return new BlurryFastBlur();
    }
    
}

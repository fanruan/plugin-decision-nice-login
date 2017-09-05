package com.fr.plugin.nice.locale;

import com.fr.stable.fun.impl.AbstractLocaleFinder;

/**
 * Created by zpc on 16/8/15.
 */
public class FreshLoginLocaleFinder extends AbstractLocaleFinder {

    /**
     * 国际化文件路径
     *
     * @return 路径
     */
    public String find() {
        return "com/fr/plugin/nice/locale/nice_login";
    }
}
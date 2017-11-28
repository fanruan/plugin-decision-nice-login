package com.fr.plugin.nice;

import com.fr.fs.fun.impl.AbstractAccessProvider;
import com.fr.plugin.nice.web.base.services.NiceLoginFlushHtmlAction;
import com.fr.plugin.transform.FunctionRecorder;
import com.fr.web.core.ActionNoSessionCMD;

/**
 * Created by richie on 2017/2/23.
 */
@FunctionRecorder
public class NiceLoginAccess extends AbstractAccessProvider {

    @Override
    public String matchPluginID() {
        return NiceLoginConstants.PLUGIN_ID;
    }

    @Override
    public String coverImagePath() {
        return "/com/fr/plugin/nice/web/images/nice.png";
    }

    @Override
    public ActionNoSessionCMD flushHtmlAction() {
        return new NiceLoginFlushHtmlAction();
    }
}

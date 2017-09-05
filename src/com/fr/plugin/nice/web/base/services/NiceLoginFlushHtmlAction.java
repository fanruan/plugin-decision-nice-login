package com.fr.plugin.nice.web.base.services;

import com.fr.fs.FSConfig;
import com.fr.fs.FSRegisterForBI;
import com.fr.fs.web.service.AbstractLoginFlushHtmlAction;
import com.fr.plugin.ExtraClassManager;
import com.fr.plugin.PluginLicense;
import com.fr.plugin.PluginLicenseManager;
import com.fr.plugin.nice.NiceLoginConstants;
import com.fr.privilege.PrivilegeManager;
import com.fr.stable.StringUtils;
import com.fr.stable.fun.FunctionHelper;
import com.fr.stable.fun.FunctionProcessor;
import com.fr.stable.fun.impl.AbstractFunctionProcessor;
import com.fr.web.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 小灰灰 on 2016/6/21.
 */
public class NiceLoginFlushHtmlAction extends AbstractLoginFlushHtmlAction {

    public static final FunctionProcessor FUNCTION_RECORD = new AbstractFunctionProcessor() {
        @Override
        public int getId() {
            return FunctionHelper.generateFunctionID(NiceLoginConstants.PLUGIN_ID);
        }

        @Override
        public String getLocaleKey() {
            return "FS-Plugin_Nice_Login";
        }
    };

    public String getCMD() {
        return "fs_signin";
    }

    /**
     * 操作的执行方法
     *
     * @param req http请求对象
     * @param res http响应对象
     * @throws Exception 抛出异常
     */
    public void actionCMD(HttpServletRequest req, HttpServletResponse res) throws Exception {
        //功能点记录
        FunctionProcessor processor = ExtraClassManager.getInstance().getFunctionProcessor();
        if (processor != null) {
            processor.recordFunction(FUNCTION_RECORD);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("company", FSConfig.getProviderInstance().getSystemAttr().getLoginTitle4FS());
        java.util.Map<String, Object> parameterMap = WebUtils.parameters4SessionIDInfor(req);
        map.put("isSupportFS", FSRegisterForBI.isSupportFS());
        map.put("backgroundImageID", FSConfig.getProviderInstance().getSystemAttr().getBgImageID4FS());
        map.put("logoImageID4FS", FSConfig.getProviderInstance().getSystemAttr().getLogoImageID4FS());
        map.put("loginTitle4FS", FSConfig.getProviderInstance().getSystemAttr().getLoginTitle4FS());

        map.putAll(parameterMap);
        if (!PrivilegeManager.getProviderInstance().hasSetFSSystemPW()) {
            WebUtils.writeOutTemplate("/com/fr/fs/web/system_manager_set.html", res, map);//不管什么风格的登录插件，注册界面都是用老版本的
        } else {
            String loginUrl = FSConfig.getProviderInstance().getSystemAttr().getLoginUrl4FS();
            if (StringUtils.isNotBlank(loginUrl)) {
                res.sendRedirect(loginUrl);
            } else {
                FSConfig.getProviderInstance().setLoginPluginId(NiceLoginConstants.PLUGIN_ID);//REPORT-398 注册一下当前登录风格
                WebUtils.writeOutTemplate("/com/fr/plugin/nice/web/html/login.html", res, map);
            }
        }
    }

    @Override
    public int layerIndex() {
        return 1;
    }
}

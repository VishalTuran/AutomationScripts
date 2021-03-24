package tivo.Serviceconsole.flows;

import tivo.Serviceconsole.common.Common;
import tivo.Serviceconsole.pages.GlobalPolicy;
import tivo.Serviceconsole.pages.Login;
import tivo.Serviceconsole.pages.Manage;

public class GlobalPolicyFlow {
    private Common ServiceConsoleUtil;
    private Manage manageObj;
    private Login loginObj;
    private GlobalPolicy globalPolicyObj;
    String envName = "CableCo04-cDVRQE1";

    public GlobalPolicyFlow(String configFile) {
        loginObj = new Login(configFile);
        manageObj = new Manage(configFile);
        ServiceConsoleUtil=new Common(configFile);
        globalPolicyObj=new GlobalPolicy(configFile);
    }

    public void loginserviceconsole() {
        loginObj.openWebPage();
        loginObj.loginwithvalidcred();
        ServiceConsoleUtil.selectOrganisation(envName);
    }

    public void logoutserviceconsole() {
        loginObj.logout();
    }

    public void globalPolicyPublish(){
        loginObj.clickmanagetab();
        manageObj.globalPolicyReact();
        globalPolicyObj.globalPolicyPublish();
    }

    public void transcodeAvailabilityOnDefaultAcrossProviderON(){
        loginObj.clickmanagetab();
        manageObj.globalPolicyReact();
        globalPolicyObj.transcodeAvailabilityOnDefaultAcrossProviderON();
    }

    public void transcodeAvailabilityOnDefaultAcrossProviderOFF(){
        loginObj.clickmanagetab();
        manageObj.globalPolicyReact();
        globalPolicyObj.transcodeAvailabilityOnDefaultAcrossProviderOFF();
    }
}

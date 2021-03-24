package tivo.Serviceconsole.pages;

import helper.XmlFinder;
import org.testng.Assert;
import selenium.WebDriverUtil;
import tivo.Serviceconsole.common.Common;

import java.util.List;

public class GlobalPolicy {
    private Common ServiceConsoleUtil;
    private XmlFinder ExtractXMLUtil;
    int currentMaxGlobalConcurrentSession=100;
    String publishSuccessMsg="Global Policy is published successfully";

    public GlobalPolicy(String ConfigFile) {
        ExtractXMLUtil = new XmlFinder(ConfigFile);
        ServiceConsoleUtil = new Common(ConfigFile);
    }

    /**
     * Verify if the Global Rules Section is Present or not
     */
    public void verifyGlobalRuleSectionPresence(){
        WebDriverUtil.waitForSeconds(2000);
        Assert.assertTrue(WebDriverUtil.isElementPresent(ExtractXMLUtil.getElmValFromXml("globalPolicy","globalRuleSection")),"Global Rules Section not Present");
    }

    /**
     * Verify if the Provider Rules Section is Present or not
     */
    public void verifyProviderRuleSectionPresence(){
        Assert.assertTrue(WebDriverUtil.isElementPresent(ExtractXMLUtil.getElmValFromXml("globalPolicy","providerRuleSection")),"Provider Rules Section not Present");
    }

    /**
     * Verify if the Global Rules Section is Present or not
     */
    public void verifyHistorySectionPresence(){
        Assert.assertTrue(WebDriverUtil.isElementPresent(ExtractXMLUtil.getElmValFromXml("globalPolicy","historySection")),"History Section not Present");
    }

    /**
     * Enter Max Global Concurrent Sessions by Modifying existing value
     */
    public void enterMaxGlobalConcurrentSessions(){
        WebDriverUtil.waitForSeconds(2000);
        currentMaxGlobalConcurrentSession=Integer.parseInt(WebDriverUtil.getElementValue(ExtractXMLUtil.getElmValFromXml("globalPolicy","maxGlobalConcurrentSessions")));
        WebDriverUtil.clearAndTypeWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy","maxGlobalConcurrentSessions"),String.valueOf(currentMaxGlobalConcurrentSession-1));
    }

    /**
     * Revert Max Global Concurrent Sessions to old value
     */
    public void revertMaxGlobalConcurrentSessions(){
        WebDriverUtil.clearAndTypeWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy","maxGlobalConcurrentSessions"),String.valueOf(currentMaxGlobalConcurrentSession));
    }

    /**
     * Publish Global Policy
     */
    public void globalPolicyPublish(){
        verifyGlobalRuleSectionPresence();
        verifyProviderRuleSectionPresence();
        verifyHistorySectionPresence();
        enterMaxGlobalConcurrentSessions();
        clickOnPublishButton();
        handlePopUp();
        handleToastMsg(publishSuccessMsg);
        verifyHistory("Max Global Concurrent Sessions is modified from '"+currentMaxGlobalConcurrentSession+"' to '"+(currentMaxGlobalConcurrentSession-1)+"'");
        revertMaxGlobalConcurrentSessions();
        clickOnPublishButton();
        handlePopUp();
        handleToastMsg(publishSuccessMsg);
        verifyHistory("Max Global Concurrent Sessions is modified from '"+(currentMaxGlobalConcurrentSession-1)+"' to '"+(currentMaxGlobalConcurrentSession)+"'");
    }

    /**
     * Check Default Provider Toggle State
     * @return true if on else false
     */
    public boolean checkDefaultAcrossProviderState(){
        WebDriverUtil.scrollToElement(ExtractXMLUtil.getElmValFromXml("globalPolicy","defaultAcrossProviders"));
        String state=WebDriverUtil.getElement(ExtractXMLUtil.getElmValFromXml("globalPolicy","defaultAcrossProviders")).getAttribute("class");
        return state.contains("checked");
    }

    /**
     * Check If Video Provider exist in Providers
     * @return true if present else false
     */
    public boolean checkIfVideoProviderExistInProvidersInput(){
        List<String> allProviders=WebDriverUtil.getTextFormListofElements(ExtractXMLUtil.getElmValFromXml("globalPolicy","allProvidersInput"));
        for(String provider:allProviders){
            System.out.println(provider);
            if(provider.contains("Video Provider")){
                return true;
            }
        }
        return false;
    }

    /**
     * Check If Video Provider exist in Providers Table
     * @return true if present else false
     */
    public boolean checkIfVideoProviderExistInProvidersTable(){
        List<String> allProviders=WebDriverUtil.getTextFormListofElements(ExtractXMLUtil.getElmValFromXml("globalPolicy","allProvidersTable"));
        for(String provider:allProviders){
            System.out.println(provider);
            if(provider.contains("videoProvider")){
                return true;
            }
        }
        return false;
    }

    /**
     * Check Transcode Padding Availability on Default Across Provider ON
     */
    public void transcodeAvailabilityOnDefaultAcrossProviderON(){
        if(!checkDefaultAcrossProviderState()){
            WebDriverUtil.jsClickWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy","defaultAcrossProviders"));
        }
        if(!checkIfVideoProviderExistInProvidersInput()){
            WebDriverUtil.jsClickWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy","addProviderInput"));
            try {
                WebDriverUtil.clickWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy","videoProvider"));
            }catch(Exception e){
                Assert.fail("Video Provider-abcnews not available");
            }
        }
        WebDriverUtil.waitForSeconds(3000);
        Assert.assertTrue(WebDriverUtil.getElement(ExtractXMLUtil.getElmValFromXml("globalPolicy","transcodeStartPadding")).isDisplayed(),"Transcoding Start Padding not available on Video Provider Selection");
        Assert.assertTrue(WebDriverUtil.getElement(ExtractXMLUtil.getElmValFromXml("globalPolicy","transcodeEndPadding")).isDisplayed(),"Transcoding End Padding not available on Video Provider Selection");
    }

    /**
     * Check Transcode Padding Availability on Default Across Provider OFF
     */
    public void transcodeAvailabilityOnDefaultAcrossProviderOFF(){
        if(checkDefaultAcrossProviderState()){
            WebDriverUtil.jsClickWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy","defaultAcrossProviders"));
        }
        if(!checkIfVideoProviderExistInProvidersTable()){
            WebDriverUtil.jsClickWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy","newButton"));
            WebDriverUtil.waitForElementToBeVisible(ExtractXMLUtil.getElmValFromXml("globalPolicy","providerPopUp"));
            try {
                WebDriverUtil.selectByTextWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy","providersSelect"),"Video Provider - abcnews - tivo:pt.1005122");
            }catch(Exception e){
                Assert.fail("Video Provider-abcnews not available");
            }
        }else{
            WebDriverUtil.jsClickWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy","videoProviderTable"));
            WebDriverUtil.waitForElementToBeVisible(ExtractXMLUtil.getElmValFromXml("globalPolicy","providerPopUp"));
        }
        WebDriverUtil.waitForSeconds(3000);
        Assert.assertTrue(WebDriverUtil.getElement(ExtractXMLUtil.getElmValFromXml("globalPolicy","transcodeStartPaddingPopup")).isDisplayed(),"Transcoding Start Padding not available on Video Provider Selection");
        Assert.assertTrue(WebDriverUtil.getElement(ExtractXMLUtil.getElmValFromXml("globalPolicy","transcodeEndPaddingPopup")).isDisplayed(),"Transcoding End Padding not available on Video Provider Selection");
        WebDriverUtil.jsClickWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy","closeButton"));
    }

    /**
     * Click on Publish Button
     */
    public void clickOnPublishButton(){
        WebDriverUtil.jsClickWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy","publishBtn"));
        WebDriverUtil.waitForSeconds(2000);
    }

    /**
     * Handle Pop Up
     */
    public void handlePopUp() {
        WebDriverUtil.waitForSeconds(5000);
        boolean isPopUpMsgPresent = WebDriverUtil.isElementPresent(ExtractXMLUtil.getElmValFromXml("globalPolicy", "popUpMsg"));
        boolean isPopUpOkBtnPresent = WebDriverUtil.isElementPresent(ExtractXMLUtil.getElmValFromXml("globalPolicy", "popUpOk"));
        Assert.assertTrue(isPopUpMsgPresent, "Pop up doesn't appear");
        Assert.assertTrue(isPopUpOkBtnPresent, "Pop up ok doesn't appear");
        System.out.println("PopUp Msg and Ok Button Asserted");
        WebDriverUtil.waitForSeconds(2000);
        WebDriverUtil.clickWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy", "popUpOk"));
        WebDriverUtil.waitForSeconds(5000);
        ServiceConsoleUtil.waitForPageLoad();
    }

    /**
     * Handle Toast Msg
     */
    public void handleToastMsg(String expectedMsg) {
        String actualMsg = WebDriverUtil.getElementText(ExtractXMLUtil.getElmValFromXml("globalPolicy", "successMsg"));
        Assert.assertTrue(actualMsg.contains(expectedMsg));
    }

    /**
     * CLick on History Section
     */
    public void clickOnHistorySection() {
        WebDriverUtil.jsClickWhenReady(ExtractXMLUtil.getElmValFromXml("globalPolicy", "historySection"));
    }

    /**
     * Compare expected Msg with latest log in history
     *
     * @param expectedMsg- Msg compared with actual Msg
     */
    public void verifyHistory(String expectedMsg) {
        clickOnHistorySection();
        WebDriverUtil.waitForElementToBeVisible(ExtractXMLUtil.getElmValFromXml("globalPolicy", "historyTable"));
        WebDriverUtil.waitUntilElementDisappear(ExtractXMLUtil.getElmValFromXml("globalPolicy", "historyLoader"));
        String actualMsg = WebDriverUtil.getElementText(ExtractXMLUtil.getElmValFromXml("globalPolicy", "historyLog"));
        Assert.assertTrue(actualMsg.contains(expectedMsg), "Actual Msg->" + actualMsg + " And Expected Msg->" + expectedMsg);
    }
}

package tivo.Serviceconsole.test;

import org.testng.annotations.Test;
import selenium.WebTest;
import tivo.Serviceconsole.flows.GlobalPolicyFlow;
import tivo.Serviceconsole.helper.SCconst;

public class GlobalPolicyTest {
    GlobalPolicyFlow globalPolicyFlow=new GlobalPolicyFlow(SCconst.SERVICECONSOLEFLOW);

    /**
     * Title-
     * Global Policy- Publish
     * Steps-
     * 1.Login to SC.
     * 2.Navigate to Manage>>Linear Publishing>>Global Policy
     * 3.Verify A.
     * 4.Modify few fields(Say: Modify Max Global Concurrent sessions from 100 to 99).
     * 5.Click on Publish.Verify B,C.
     * 6.Revert the changes made in step 4 and click on Publish. Verify D
     *
     * Expected Result-
     * A. Global Rules, Provider rules, History should be displayed.
     * B. Toast message showing Station Policy successfully Published should be displayed.
     * C. History should be captured successfully.
     * D. Changes should be successfully saved and History should be stored.
     */
    @WebTest
    @Test(groups = {"testrail:C9914407","Sanity"}, description = "Global Policy- Publish", priority = 1)
    public void globalPolicyPublish(){
        globalPolicyFlow.loginserviceconsole();
        globalPolicyFlow.globalPolicyPublish();
        globalPolicyFlow.logoutserviceconsole();
    }

    /**
     * Title-
     * linear Publishing-Global Policy - Default Across Provider=ON - Video Provider-Transcode Start Padding and Transport End Padding - Availability
     *
     * Steps-
     * 1.Login to TSC with valid user name and password
     * 2.Navigate to Manage>>Linear Publishing>>Global Policy
     * 3.Expand Provider Rules Section
     * 4.Default Across Provider = ON
     * 5.Select "Video Provider" under Providers section and verify whether "Transcode Start Padding" and "Transport End Padding" text fields are present are not
     *
     * Expected Result-
     * "Transcode Start Padding" and "Transport End Padding" text fields should be available when user selects "Video Provider"
     */
    @WebTest
    @Test(groups = {"testrail:C11678406"}, description = "linear Publishing-Global Policy - Default Across Provider=ON - Video Provider-Transcode Start Padding and Transport End Padding - Availability", priority = 2)
    public void transcodeAvailabilityOnDefaultAcrossProviderON(){
        globalPolicyFlow.loginserviceconsole();
        globalPolicyFlow.transcodeAvailabilityOnDefaultAcrossProviderON();
        globalPolicyFlow.logoutserviceconsole();
    }

    /**
     * Title-
     * linear Publishing-Global Policy - Default Across Provider=OFF - Video Provider-Transcode Start Padding and Transport End Padding - Availability
     *
     * Steps-
     * Login to TSC with valid user name and password
     * Navigate to Manage>>Linear Publishing>>Global Policy
     * Expand Provider Rules Section
     * Default Across Provider = OFF
     * Click on "Video Provider" under Provider Partner Type coulmn(If not present click on Add New )
     * Verify whether "Transcode Start Padding" and "Transport End Padding" text fields are present are not
     *
     * Expected Result
     * "Transcode Start Padding" and "Transport End Padding" text fields should be available when user selects "Video Provider"
     */

    @WebTest
    @Test(groups = {"testrail:C11678407"}, description = "linear Publishing-Global Policy - Default Across Provider=OFF - Video Provider-Transcode Start Padding and Transport End Padding - Availability", priority = 3)
    public void transcodeAvailabilityOnDefaultAcrossProviderOFF(){
        globalPolicyFlow.loginserviceconsole();
        globalPolicyFlow.transcodeAvailabilityOnDefaultAcrossProviderOFF();
        globalPolicyFlow.logoutserviceconsole();
    }
}
